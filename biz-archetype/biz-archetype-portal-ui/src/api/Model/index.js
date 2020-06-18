import httpAsync from '../req.js'

class Request {
    static async requestModel(url, method, param) {
        const resData = await httpAsync.request(url, method, param)
        return resData.data
    }

    static async requestWholeModel(url, method, param) {
        return await httpAsync.request(url, method, param)
    }

    static async requestNetsModel(url, method, param) {
        return await httpAsync.request(url, method, param)
    }
}

export default Request
