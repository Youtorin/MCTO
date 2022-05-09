const path = require("path");
function resolve(dir) {
    return path.join(__dirname, dir);
}
module.exports = {
    lintOnSave: false,
    assetsDir: 'static',
    parallel: false,
    publicPath: './',
    devServer: {
        host: 'localhost',
        port: 3000,
        proxy: {
            "/api": {
                target: "http://localhost:6000", // 目标地址
                changeOrigin: true, // 是否跨域
            }
        }
    },
    chainWebpack: config => {
        config.resolve.alias
            .set("@", resolve("src"))
            .set("assets", resolve("src/assets"))
            .set("components", resolve("src/components"))
            .set("base", resolve("baseConfig"))
            .set("public", resolve("public"));
    }
}
