const modulesFiles = require.context("./modules", true, /\.js$/);
const UrlModule = modulesFiles.keys().reduce((UrlModule, modulePath) => {
    const value = modulesFiles(modulePath)
    return {...UrlModule, ...value.default}
}, {})

export default UrlModule
