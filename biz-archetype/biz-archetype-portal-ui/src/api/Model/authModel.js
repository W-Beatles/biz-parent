import Request from './index.js'
import Urls from '../urls/authUrl'
import {
    MessageBox
} from 'element-ui'
import fa from "element-ui/src/locale/lang/fa";

const host = 'http://192.168.43.118:9010'
const SUCCESS_CODE = 10000

class AuthModel {
    appName = '/biz-archetype-portal'

    generateUrl(key) {
        return `${host}${this.appName}${Urls[key]}`
    }

    async testApi(param) {
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

    async addArchetypes(param) {
        const {code, message} = await Request.requestWholeModel(this.generateUrl('addArch'), 'post', param)
        return this.errTip(code, message)
        // return
    }

    async errTip(code, message = '接口异常') {
        let status = true
        if (code !== SUCCESS_CODE) {
            await MessageBox.alert(message, '错误提示', {
                confirmButtonText: '确定',
                type: 'error'
            })
            status = false
        }
        return status
    }
}

export default AuthModel
