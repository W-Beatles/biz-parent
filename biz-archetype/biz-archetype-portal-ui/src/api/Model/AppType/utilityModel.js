import AuthModel from '@/api/Model/AppType/authModel.js'
import Request from "@/api/Model";

class UtilityModel extends AuthModel {
    appName = '/service-utility'

    async getAppTypeDiction(param, cbLoading, cb) {
        const KEY = 'APP_TYPE_ENUM'
        const {data} = await Request.requestWholeModel(`${this.generateUrl('appTypeDic')}${KEY}`, 'get', param)
        return data
    }
}

export default UtilityModel