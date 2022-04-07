import vue from "@vitejs/plugin-vue";
import { defineConfig } from "vite";
const { resolve } = require("path");
export default defineConfig({
  base: "./",
  plugins: [vue()],
  optimizeDeps: {
    include: ["schart.js"],
  },
  alias: {
    "@": resolve(__dirname, "src"),
  },
  server: {
    port: 3000,
    proxy: {
      "/api": {
        target: "http://localhost:6000",
        changeOrigin: true,
      },
    },
  },
});
