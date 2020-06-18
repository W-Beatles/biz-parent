const modulesFiles = require.context("./modules", true, /\.js$/);
const EnumModule = modulesFiles.keys().reduce((modules, modulePath) => {
    const value = modulesFiles(modulePath)
    return {...EnumModule, ...value.default}
}, {})

export default EnumModule;
