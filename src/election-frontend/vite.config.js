import { fileURLToPath, URL } from 'node:url';
import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)) // Alias for src folder
    }
  },
  server: {
    host: true, // Enables access over network and from within Docker container
    watch: {
      usePolling: true,
    },
    port: 5173, // vite port
    strictPort: true
  }
});
