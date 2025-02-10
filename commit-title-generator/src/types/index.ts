export interface GitFile {
	path: string;
	full_path: string;
	status: 'added' | 'modified' | 'deleted';
	file_type: 'code' | 'image' | 'text' | 'other';
}


export const getFileTypeFromExtension = (filename: string): string => {
	const ext = filename.split('.').pop()?.toLowerCase() || '';

	const codeExts = ['js', 'ts', 'py', 'java', 'cpp', 'cs', 'php', 'rb', 'go', 'rust', 'html', 'css', 'vue', 'jsx', 'tsx'];
	const imageExts = ['jpg', 'jpeg', 'png', 'gif', 'svg', 'webp', 'bmp'];
	const textExts = ['txt', 'md', 'json', 'yaml', 'yml', 'xml', 'csv'];

	if (codeExts.includes(ext)) return 'code';
	if (imageExts.includes(ext)) return 'image';
	if (textExts.includes(ext)) return 'text';

	return 'unknown';
};