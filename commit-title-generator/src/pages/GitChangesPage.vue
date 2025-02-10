<template>
  <div class="min-h-screen bg-gradient-to-br from-slate-900 via-slate-800 to-slate-900 text-slate-100 antialiased">
    <main class="container mx-auto px-6 py-12 max-w-5xl">
      <RepositoryActions
          :repoPath="repoPath"
          @select-folder="selectFolder"
          @fetch-files="fetchFiles"
          @generate-title="generateTitle"
          :files="files"
          class="mb-8 bg-slate-800/30 rounded-2xl p-6 backdrop-blur-sm border border-slate-700/50 shadow-xl transition-all duration-300 hover:shadow-slate-700/10"
      />

      <div v-if="files.length"
           class="grid grid-cols-3 gap-4 mb-8">
        <div v-for="(value, key) in stats"
             :key="key"
             class="bg-slate-800/30 rounded-xl p-4 backdrop-blur-sm border border-slate-700/50 transition-all duration-300 hover:scale-102 hover:shadow-lg">
          <div class="text-sm text-slate-400 mb-1 capitalize">{{key}}</div>
          <div class="text-2xl font-semibold">{{value}}</div>
        </div>
      </div>

      <div v-if="files.length"
           class="bg-slate-800/30 rounded-2xl overflow-hidden backdrop-blur-sm border border-slate-700/50 transition-all duration-300">
        <div class="p-6 border-b border-slate-700/50">
          <h2 class="text-xl font-semibold bg-gradient-to-r from-blue-400 to-indigo-400 bg-clip-text text-transparent">
            Changed Files
          </h2>
        </div>

        <div class="divide-y divide-slate-700/50">
          <FileItem
              v-for="file in files"
              :key="file.path"
              :file="file"
              :open-file="openFile"
              class="transition-colors duration-200 hover:bg-slate-700/30"
          />
        </div>
      </div>

      <div v-else
           class="text-center py-24 rounded-2xl bg-slate-800/30 backdrop-blur-sm border border-slate-700/50">
        <div class="text-slate-400 text-lg">
          {{ repoPath ? 'No changed files found' : 'Select a repository to start' }}
        </div>
      </div>
    </main>

    <CommitMessageModal
        v-model:commitMessage="commitMessage"
        :is-open="isOpenModal"
        @on-close-modal="closeModal"
        @on-submit="submitCommit"
    />
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue';
import { invoke } from '@tauri-apps/api/core';
import { open as openShell } from '@tauri-apps/plugin-shell';
import CommitMessageModal from '../components/CommitMessageModal.vue';
import FileItem from '../components/FileItem.vue';
import RepositoryActions from '../components/RepositoryActions.vue';
import { GitFile } from '../types';

const files = ref<GitFile[]>([]);
const repoPath = ref<string | null>(null);
const isOpenModal = ref(false);
const commitMessage = ref('');

const closeModal = () => {
  isOpenModal.value = false;
};

const submitCommit = () => {
  console.log('Submitting commit with message:', commitMessage.value);
  isOpenModal.value = false;
};

const selectFolder = async (selectedPath: string) => {
	repoPath.value = selectedPath;
	await fetchFiles();
};

const openFile = async (filePath: GitFile['full_path']) => {
	await openShell(filePath);
};

const fetchFiles = async (fetchedFiles?: GitFile[]) => {
	if (fetchedFiles) {
		files.value = fetchedFiles;
	} else if (repoPath.value) {
		try {
			files.value = await invoke('get_files', { repoPath: repoPath.value });
			console.log(files.value);
		} catch (error) {
			console.error('Failed to fetch git files:', error);
		}
	} else {
		console.error('Repository path is not selected');
	}
};

const generateTitle = async (generatedFiles?: Record<string, string>) => {
  if (generatedFiles) {
    commitMessage.value = generatedFiles.files;
    isOpenModal.value = true;
  } else {
    try {
      invoke<Record<string, string>>('generate_title', {
        editedFiles: files.value.map(f => f.path),
      }).then(r => {
        commitMessage.value = r.files;
        isOpenModal.value = true;
      });
    } catch (error) {
      console.error('Failed to generate title:', error);
    }
  }
};

const stats = computed(() => {
	return {
		added: files.value.filter(f => f.status === 'added').length,
		modified: files.value.filter(f => f.status === 'modified').length,
		deleted: files.value.filter(f => f.status === 'deleted').length,
	};
});
</script>
<style>
.scale-102 {
  transform: scale(1.02);
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

.FileItem-enter-active {
  animation: fadeIn 0.3s ease-out;
}
</style>
