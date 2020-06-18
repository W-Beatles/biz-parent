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

    async testApi() {
        const param = {
            "appId": "string",
            "pageNum": 1,
            "pageSize": 10,
            "taskId": 0
        }
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
}

export default AuthModel
