import { defineConfig } from 'vite';
import laravel from 'laravel-vite-plugin';
// import { createRequire } from 'module';
// const require = createRequire(import.meta.url);
// const path = require('path');

export default defineConfig({
    plugins: [
        laravel({
            input: [
                'resources/css/app.css',
                'resources/js/app.js',
            ],
            refresh: true,
        }),
    ],
    resolve: {
        alias: {
            'jquery': 'jquery/dist/jquery.min.js',
            '$': 'jquery',
            'jQuery': 'jquery',
        },
    },
});
