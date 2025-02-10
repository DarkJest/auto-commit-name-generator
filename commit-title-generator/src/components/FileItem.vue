<template>
	<div
		class="flex items-center justify-between p-4 hover:bg-gray-700/30 transition group"
	>
		<div class="flex items-center gap-3">
			<component
				:is="getFileIcon(file.full_path)"
				class="w-5 h-5 text-gray-400"
			/>
			<span class="text-sm">{{ file.path }}</span>
		</div>

		<div class="flex items-center gap-3">
			<span
				class="text-xs px-2 py-1 rounded"
				:class="{
					'bg-green-500/20 text-green-400': file.status === 'added',
					'bg-yellow-500/20 text-yellow-400': file.status === 'modified',
					'bg-red-500/20 text-red-400': file.status === 'deleted',
				}"
			>
				{{ file.status }}
			</span>

			<button
				@click="openFile(file.full_path)"
				class="p-1 opacity-0 group-hover:opacity-100 hover:bg-gray-600 rounded transition"
				title="Open file"
			>
				<Eye class="w-4 h-4" />
			</button>
		</div>
	</div>
</template>

<script setup lang="ts">
import { defineProps } from 'vue';
import { Eye, FileCode, FileImage, FileText, File } from 'lucide-vue-next';
import {getFileTypeFromExtension, GitFile} from '../types';

defineProps<{
	file: GitFile;
	openFile: (filePath: string) => void;
}>();

const getFileIcon = (filename: string) => {
  const type = getFileTypeFromExtension(filename);
  return type === 'code' ? FileCode :
         type === 'image' ? FileImage :
         type === 'text' ? FileText : File;
};
</script>

<style scoped>
/* Add any specific styles for the FileItem component here */
</style>
