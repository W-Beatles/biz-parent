import Request from '../index.js'
import {
    MessageBox
} from 'element-ui'

const SUCCESS_CODE = 10000

class AuthModel {
    constructor() {
        this.appName = '/biz-archetype-portal'
    }

    geneUri(key) {
        return Request.generateUrl(this.appName, key)
    }

    async getAppTypeList(param) {
        const res = await Request.requestWholeModel(this.geneUri('taskSearch'), 'post', param)
        console.log('res', res)
        const {code, data, message} = res
        if (code !== SUCCESS_CODE) {
            await MessageBox.alert(message || '接口异常', '错误提示', {
                confirmButtonText: '确定',
                type: 'error'
            })
        }
        return data
    }

    async downloadArcFile(id) {
        return await Request.requestDownModel(`${this.geneUri('downloadArc')}/${id}`, 'get')
    }

    async delArchetype(id) {
        const res = await Request.requestWholeModel(`${this.geneUri('addArch')}/${id}`, 'delete')
        return this.errTip(res)
    }

    async getArchetype(id) {
        return await Request.requestWholeModel(`${this.geneUri('addArch')}/${id}`, 'get')
    }

    async editArchetype(param, cbLoading, cb) {
        const res = await Request.requestWholeModel(`${this.geneUri('addArch')}/${param.id}`, 'put', param)
        this.errCbTip(res, cbLoading, cb)
    }

    async addArchetypes(param, cbLoading, cb) {
        const res = await Request.requestWholeModel(this.geneUri('addArch'), 'post', param)
        this.errCbTip(res, cbLoading, cb)
    }

    async errTip({code, data = null, message = '接口异常'}, cb) {
        let status = code === SUCCESS_CODE
        await MessageBox.alert(message, '提示', {
            confirmButtonText: '确定',
            type: status ? 'success' : 'error'
        }).then(() => {
            status && cb && cb()
        })
        return {status, data}
    }

    errCbTip(res, cbLoading, cb) {
        const status = res.code === SUCCESS_CODE
        setTimeout(() => {
            cbLoading()
            return this.errTip(res, cb)
        }, status ? 3000 : 0)
        return status
    }
}

export default AuthModel
