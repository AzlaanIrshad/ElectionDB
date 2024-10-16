import { fileURLToPath, URL } from 'node:url';
import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  server: {
    proxy: {
      '/api': {
        target: 'http://backend:8080', // Proxy to backend service
        changeOrigin: true, // Change origin of host header to target URL
        rewrite: (path) => path.replace(/^\/api/, ''), // Rewrite path to remove /api prefix
      },
    },
  },
});
