<template>
	<div class="mb-8">
		<div class="flex items-center justify-between">
			<div class="flex items-center gap-4">
				<button
					@click="handleSelectFolder"
					class="flex items-center gap-2 px-4 py-2 bg-indigo-600 hover:bg-indigo-700 rounded-lg transition"
				>
					<FolderOpen class="w-5 h-5" />
					<span>{{
						repoPath ? 'Change Repository' : 'Select Repository'
					}}</span>
				</button>

				<button
					v-if="repoPath"
					@click="handleFetchFiles"
					class="p-2 bg-gray-700 hover:bg-gray-600 rounded-lg transition"
					title="Refresh"
				>
					<RefreshCw class="w-5 h-5" />
				</button>
			</div>

			<button
				v-if="files.length"
				@click="handleGenerateTitle"
				class="px-4 py-2 bg-green-600 hover:bg-green-700 rounded-lg transition"
			>
				Generate Commit Message
			</button>
		</div>

    <div
        v-if="repoPath"
        @click="openFolder"
        class="mt-4 px-4 py-2 rounded-lg bg-slate-800/30 backdrop-blur-sm border border-slate-700/50 inline-flex items-center gap-2 text-sm">
      <svg class="w-4 h-4 text-slate-400" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
        <path stroke-linecap="round" stroke-linejoin="round" d="M3 7v10a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2h-6l-2-2H5a2 2 0 00-2 2z" />
      </svg>
      <span class="text-slate-400">{{ repoPath }}</span>
    </div>
	</div>
</template>

<script setup lang="ts">
import { ref, defineEmits } from 'vue';
import { open } from '@tauri-apps/plugin-dialog';
import { open as openShell} from '@tauri-apps/plugin-shell';
import { FolderOpen, RefreshCw } from 'lucide-vue-next';
import {GitFile} from "../types";

const emits = defineEmits(['select-folder', 'fetch-files', 'generate-title']);
const props = defineProps<{
	files: GitFile[];
}>();

const repoPath = ref<string | null>(null);

const openFolder = async () => {
  if (repoPath.value)
    await openShell(repoPath.value);
}

const handleSelectFolder = async () => {
	try {
		const selected = await open({
			directory: true,
			multiple: false,
		});
		if (typeof selected === 'string') {
			repoPath.value = selected;
			emits('select-folder', selected);
		}
	} catch (error) {
		console.error('Failed to select folder:', error);
	}
};

const handleFetchFiles = async () => {
	if (!repoPath.value) {
		console.error('Repository path is not selected');
		return;
	}
	try {
		emits('fetch-files', props.files);
	} catch (error) {
		console.error('Failed to fetch git files:', error);
	}
};

const handleGenerateTitle = async () => {
  emits('generate-title', null);
};
</script>

<style scoped>
/* Add any specific styles for this component here */
</style>
