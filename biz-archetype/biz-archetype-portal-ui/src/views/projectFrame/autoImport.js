const modulesMixinFiles = require.context('./mixins', true, /\.js$/)
export const mixinModule = modulesMixinFiles.keys().reduce((routerModule, modulePath) => {
    const value = modulesMixinFiles(modulePath)
    return routerModule.concat(value.default)
}, [])

const modulesCmpFiles = require.context('./components', true, /\.vue$/)
export const cmpModule = modulesCmpFiles.keys().reduce((cmpModule, modulePath) => {
    const moduleName = modulePath.replace(/^\.\/(.*)\.\w+$/, '$1')
    const value = modulesCmpFiles(modulePath)
    cmpModule[moduleName] = value.default
    return cmpModule
}, {})

