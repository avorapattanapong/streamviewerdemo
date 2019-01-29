var path = require('path');
var ROOT = path.resolve(__dirname, 'src/main/resources/static/');

var SRC = path.resolve(__dirname, 'src/main/frontend/js/');
var DEST = path.resolve(__dirname, ROOT + '/dist/');

module.exports = {
    mode: 'development',
    entry: {
        index: SRC + '/index.jsx'
    },
    output: {
        filename: '[name].bundle.js',
        path: DEST
    },
    module: {
        rules: [
            {
                test : /\.(js|jsx)$/,
                include : SRC,
                loader : 'babel-loader',
                query: {
                    presets: ['es2017', 'react'],
                    plugins: [
                        "transform-class-properties",
                        "transform-object-rest-spread"
                    ],
                },
                exclude: /node_modules/
            },
            {
                test: /\.less$/,
                use: [
                    {loader:'style-loader'},
                    {loader:'css-loader'},
                    {loader:'less-loader'}
                ]
            },
            {
                test: /\.css$/,
                use: [
                    {loader:'style-loader'},
                    {loader:'css-loader'}
                ]
            },
        ]
    },
    resolve: {
        modules: [
            path.resolve(ROOT, 'jsx'),
            path.resolve(ROOT, 'less'),
            'node_modules'
        ],
        extensions: ['*', '.js', '.jsx']
    },
};