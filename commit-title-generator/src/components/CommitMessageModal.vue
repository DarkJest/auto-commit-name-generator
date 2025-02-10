<template>
  <TransitionRoot appear :show="isOpen" as="template">
    <Dialog as="div" @close="closeModal" class="relative z-50">
      <TransitionChild
          as="template"
          enter="duration-300 ease-out"
          enter-from="opacity-0"
          enter-to="opacity-100"
          leave="duration-200 ease-in"
          leave-from="opacity-100"
          leave-to="opacity-0"
      >
        <div class="fixed inset-0 bg-black/50 backdrop-blur-sm" />
      </TransitionChild>

      <div class="fixed inset-0 overflow-y-auto">
        <div class="flex min-h-full items-center justify-center p-4">
          <TransitionChild
              as="template"
              enter="duration-300 ease-out"
              enter-from="opacity-0 scale-95"
              enter-to="opacity-100 scale-100"
              leave="duration-200 ease-in"
              leave-from="opacity-100 scale-100"
              leave-to="opacity-0 scale-95"
          >
            <DialogPanel class="w-full max-w-2xl overflow-hidden rounded-xl bg-gray-900 shadow-xl ring-1 ring-gray-700">
              <div class="relative p-6">
                <div class="absolute right-4 top-4 flex gap-2">
                  <button
                      @click="copyToClipboard"
                      class="group rounded-lg p-2 text-gray-400 hover:bg-gray-800 hover:text-gray-100"
                      :class="{ 'text-green-500': copied }"
                  >
                    <component :is="copied ? CheckIcon : CopyIcon" class="h-5 w-5" />
                    <span class="absolute right-0 -top-8 hidden rounded-md bg-gray-800 px-2 py-1 text-sm text-gray-100 group-hover:block">
                      {{ copied ? 'Copied!' : 'Copy message' }}
                    </span>
                  </button>
                  <button
                      @click="closeModal"
                      class="group rounded-lg p-2 text-gray-400 hover:bg-gray-800 hover:text-gray-100"
                  >
                    <XIcon class="h-5 w-5" />
                    <span class="absolute right-0 -top-8 hidden rounded-md bg-gray-800 px-2 py-1 text-sm text-gray-100 group-hover:block">
                      Close
                    </span>
                  </button>
                </div>

                <DialogTitle class="text-xl font-medium text-gray-100">
                  Commit Details
                </DialogTitle>

                <div class="mt-6 space-y-4">
                  <div>
                    <div class="mb-2 flex items-center text-sm font-medium text-gray-400">
                      <MessageSquareIcon class="mr-2 h-4 w-4" />
                      Commit Message
                    </div>
                    <textarea
                        v-model="localCommitMessage"
                        rows="4"
                        class="w-full rounded-lg bg-gray-800/50 p-4 font-mono text-sm text-gray-100 placeholder-gray-500 ring-1 ring-gray-700 transition-shadow focus:outline-none focus:ring-2 focus:ring-emerald-500"
                        placeholder="Enter your commit message..."
                    />
                  </div>

                  <div class="rounded-lg bg-gray-800/30 p-4">
                    <div class="mb-2 flex items-center text-sm font-medium text-gray-400">
                      <InfoIcon class="mr-2 h-4 w-4" />
                      Suggested Format
                    </div>
                    <div class="space-y-2 font-mono text-sm text-gray-300">
                      <div class="text-emerald-400">feat(scope): short description</div>
                      <div class="pl-4 text-gray-500">
                        - Detailed change 1<br/>
                        - Detailed change 2<br/>
                        - Additional context
                      </div>
                    </div>
                  </div>
                </div>

                <div class="mt-8 flex justify-end gap-3">
                  <button
                      @click="closeModal"
                      class="flex items-center gap-2 rounded-lg px-4 py-2 text-sm font-medium text-gray-400 hover:bg-gray-800 hover:text-gray-100"
                  >
                    <XIcon class="h-4 w-4" />
                    Cancel
                  </button>
                  <button
                      @click="submit"
                      class="flex items-center gap-2 rounded-lg bg-emerald-600 px-4 py-2 text-sm font-medium text-white hover:bg-emerald-500"
                  >
                    <CheckIcon class="h-4 w-4" />
                    Confirm
                  </button>
                </div>
              </div>
            </DialogPanel>
          </TransitionChild>
        </div>
      </div>
    </Dialog>
  </TransitionRoot>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';
import {
  Dialog,
  DialogPanel,
  DialogTitle,
  TransitionRoot,
  TransitionChild,
} from '@headlessui/vue';
import {
  X as XIcon,
  Copy as CopyIcon,
  Check as CheckIcon,
  MessageSquare as MessageSquareIcon,
  Info as InfoIcon
} from 'lucide-vue-next';

const props = defineProps<{
  commitMessage: string;
  isOpen: boolean;
}>();

const emits = defineEmits(['update:commitMessage', 'onCloseModal', 'onSubmit']);

const localCommitMessage = ref(props.commitMessage);
const copied = ref(false);

watch(() => props.commitMessage, (newValue) => {
  localCommitMessage.value = newValue;
});

const copyToClipboard = async () => {
  await navigator.clipboard.writeText(localCommitMessage.value);
  copied.value = true;
  setTimeout(() => {
    copied.value = false;
  }, 2000);
};

const closeModal = () => {
  emits('onCloseModal');
};

const submit = () => {
  emits('update:commitMessage', localCommitMessage.value);
  emits('onSubmit');
};
</script>