use git2::Repository;
use serde::{Deserialize, Serialize};
use tauri_plugin_http::{reqwest};

#[derive(Serialize)]
struct GitFile {
    path: String,
    full_path: String,
    status: String,
    file_type: String,
}

#[derive(Serialize)]
#[derive(Deserialize)]
struct GenerateResult {
    message: String,
    files: String,
}

#[derive(Serialize)]
struct EditedFiles {
    editedFiles: Vec<String>,
}

#[tauri::command]
fn get_files(repo_path: String) -> Vec<GitFile> {
    let repo = Repository::open(repo_path).expect("Failed to open repository");

    let mut files = Vec::new();
    let statuses = repo.statuses(None).expect("Failed to get statuses");

    for entry in statuses.iter() {
        let status = entry.status();
        let path = entry.path().unwrap_or("").to_string();

        let full_path = repo.path().parent().unwrap().join(&path).to_str().unwrap().to_string();
        let file_type = if path.ends_with(".rs") || path.ends_with(".ts") || path.ends_with(".js") || path.ends_with(".vue") || path.ends_with(".php") {
            "code"
        } else if path.ends_with(".png") || path.ends_with(".jpg") || path.ends_with(".jpeg") || path.ends_with(".gif") {
            "image"
        } else if path.ends_with(".md") || path.ends_with(".txt") || path.ends_with(".json") {
            "text"
        } else {
            "other"
        };

        let status_str = if status.is_index_new() || status.is_wt_new() {
            "added"
        } else if status.is_index_modified() || status.is_wt_modified() {
            "modified"
        } else if status.is_index_deleted() || status.is_wt_deleted() {
            "deleted"
        } else {
            continue;
        };

        files.push(GitFile {
            path,
            full_path,
            status: status_str.to_string(),
            file_type: file_type.to_string(),
        });
    }

    files
}

#[tauri::command]
async fn generate_title(edited_files: Vec<String>) -> Result<GenerateResult, String> {
    let client = reqwest::Client::new();
    let body = EditedFiles { editedFiles: edited_files };
    let result = client
        .post("http://localhost:8080/api/generate-commit-name/generate")
        .json(&body)
        .send()
        .await;
    match result {
        Ok(response) => {
            if response.status().is_success() {
                match response.json::<GenerateResult>().await {
                    Ok(generate_result) => Ok(generate_result),
                    Err(e) => Err(format!("Failed to parse response: {}", e)),
                }
            } else {
                Err(format!("Request failed with status: {}", response.status()))
            }
        }
        Err(e) => Err(format!("Request failed: {}", e)),
    }
}

#[cfg_attr(mobile, tauri::mobile_entry_point)]
pub fn run() {
    tauri::Builder::default()
        .plugin(tauri_plugin_opener::init())
        .plugin(tauri_plugin_dialog::init())
        .plugin(tauri_plugin_shell::init())
        .invoke_handler(tauri::generate_handler![get_files, generate_title])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
