import vue from "@vitejs/plugin-vue";
import { defineConfig } from "vite";
import requireTransform from 'vite-plugin-require-transform';
const { resolve } = require("path");
export default defineConfig({
  base: "./",
  plugins: [vue(), requireTransform({
    fileRegex: /.js$|.vue$/
  }),],
  optimizeDeps: {
    include: ["schart.js"],
  },
  resolve: {
    alias: {
      "@": resolve(__dirname, "src"),
    },
  },
  server: {
    port: 4000,
    proxy: {
      "/api": {
        target: "http://localhost:6000",
        changeOrigin: true,
      },
      "/v1": {
        target: "https://restapi.amap.com/v3/geocode",
        changeOrigin: true,
        pathRewrite: {
          '^/amap': ''
        }
      },
    },
  },
});
