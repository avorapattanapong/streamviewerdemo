var path = require('path');
var ROOT = path.resolve(__dirname, 'src/main/resources/static/');

var SRC = path.resolve(__dirname, 'src/main/frontend/js/');
var DEST = path.resolve(__dirname, ROOT + '/dist/');

module.exports = {
    entry: {
        index: SRC + '/index.js'
    },
    output: {
        filename: '[name].bundle.js',
        path: DEST
    },
    module: {
        rules: [
            {
                test: /\.(js|jsx)$/,
                exclude: /node_modules/,
                use: ['babel-loader']
            }
        ]
    },
    resolve: {
        extensions: ['*', '.js', '.jsx']
    },
};