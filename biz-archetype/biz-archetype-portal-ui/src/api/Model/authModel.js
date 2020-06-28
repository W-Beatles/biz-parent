import Request from './index.js'
import Urls from '../urls/authUrl'
import {
    MessageBox
} from 'element-ui'

const host = 'http://192.168.43.118:9010'
const SUCCESS_CODE = 10000

class AuthModel {
    appName = '/biz-archetype-portal'

    generateUrl(key) {
        return `${host}${this.appName}${Urls[key]}`
    }

    async getAppTypeList(param) {
        const res = await Request.requestWholeModel(this.generateUrl('taskSearch'), 'post', param)
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
        return await Request.requestDownModel(`${this.generateUrl('downloadArc')}/${id}`, 'get')
    }

    async delArchetype(id) {
        const res = await Request.requestWholeModel(`${this.generateUrl('addArch')}/${id}`, 'delete')
        return this.errTip(res)
    }

    async getArchetype(id) {
        return await Request.requestWholeModel(`${this.generateUrl('addArch')}/${id}`, 'get')
    }

    async editArchetype(param, cbLoading, cb) {
        const res = await Request.requestWholeModel(`${this.generateUrl('addArch')}/${param.id}`, 'put', param)
        this.errCbTip(res, cbLoading, cb)
    }

    async addArchetypes(param, cbLoading, cb) {
        const res = await Request.requestWholeModel(this.generateUrl('addArch'), 'post', param)
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
