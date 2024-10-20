import { fileURLToPath, URL } from 'node:url';
import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  server: {
    host: true,
    watch: {
      usePolling: true,
    },
    port: 3000, // Frontend will run on port 3000
    strictPort: true, // Fail if port is already in use
    proxy: {
      '/api': {
        target: 'http://127.0.0.1:8080', // Use IPv4 for consistency
        changeOrigin: true,
        secure: false,
        rewrite: (path) => path.replace(/^\/api/, ''), // Adjust path if needed
      },
      '/auth': {
        target: 'http://127.0.0.1:8080', // Use IPv4 for consistency
        changeOrigin: true,
        secure: false,
        rewrite: (path) => path.replace(/^\/auth/, ''), // Adjust path if needed
      },
    },
  },
});
