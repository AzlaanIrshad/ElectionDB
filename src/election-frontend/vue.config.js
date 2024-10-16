module.exports = {
    devServer: {
        proxy: {
            '/api': {
                target: 'http://backend:8080',
                changeOrigin: true,
            },
        },
    },
};
