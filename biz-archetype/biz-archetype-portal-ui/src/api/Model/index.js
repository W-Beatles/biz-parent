import httpAsync from '../req.js'
import Urls from "@/api/urls/index";

const host = 'http://gateway.waynechu.cn:9010'
const access_host = 'http://access.waynechu.cn:9050'

class Request {
    static generateUrl(appName, key) {
        return `${host}${appName}${Urls[key]}`
    }

    static async requestModel(url, method, param) {
        const resData = await httpAsync.request(url, method, param)
        return resData.data
    }

    static async requestNormal(url, method, param) {
        const resData = await httpAsync.request(`${access_host}${url}`, method, param)
        return resData.data
    }

    static async requestAccess(url, method, param) {
        return await httpAsync.request(`${access_host}${url}`, method, param)
    }

    static async requestWholeModel(url, method, param) {
        return await httpAsync.request(url, method, param)
    }

    static async requestNetsModel(url, method, param) {
        return await httpAsync.request(url, method, param)
    }

    static async requestDownModel(url, method, param) {
        const {data, headers} = await httpAsync.request(url, method, param, true)
        const fileName = headers["content-disposition"].split(';')[1].split('=')[1]
        return {
            data,
            fileName
        }
    }
}

export default Request
