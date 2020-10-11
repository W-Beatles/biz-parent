import axios from 'axios'
import Auth from '@/api/auth.js'

const auth = new Auth()
axios.defaults.timeout = 60 * 1000 // 默认请求超时时间
let cancel, promiseArr = {}
// 请求拦截器
axios.interceptors.request.use(config => {
    //发起请求时，取消掉当前正在进行的相同请求
    if (promiseArr[config.url]) {
        promiseArr[config.url]('操作取消')
        promiseArr[config.url] = cancel
    } else {
        promiseArr[config.url] = cancel
    }
    return config
}, error => {
    return Promise.reject(error)
})

/**
 *
 * @type {{request(url, method, param): *}}
 */
const httpAsync = {
    request(url, method = 'get', param, download) {
        return new Promise((resolve) => {
            const {token_type, access_token} = JSON.parse(localStorage.getItem('TOKEN_INFO')) || {}
            axios({
                method: method,
                url,
                [method === 'get' ? 'params' : 'data']: param,
                [download ? 'responseType' : 'blob']: 'blob',
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8',
                    'authType': 'oauth2',
                    'token': `${token_type} ${access_token}`
                }
            }).then((res) => {
                if (res) {
                    const resData = res['data'] || res['Data']
                    if (download) {
                        resolve(res)
                    } else {
                        resolve(resData)
                    }
                }
            })
        })
    }
}

// 响应拦截器即异常处理
axios.interceptors.response.use(response => {
    return response
}, error => {
    if (error && error.response) {
        switch (error.response.status) {
            case 400:
                error.message = '错误请求'
                break
            case 401:
                error.message = '未授权，请重新登录'
                auth.authRedirect()
                break
            case 403:
                error.message = '拒绝访问'
                break
            case 404:
                error.message = '请求错误,未找到该资源'
                break
            case 405:
                error.message = '请求方法未允许'
                break
            case 408:
                error.message = '请求超时'
                break
            case 500:
                error.message = '服务器端出错'
                break
            case 501:
                error.message = '网络未实现'
                break
            case 502:
                error.message = '网络错误'
                break
            case 503:
                error.message = '服务不可用'
                break
            case 504:
                error.message = '网络超时'
                break
            case 505:
                error.message = 'http版本不支持该请求'
                break
            default:
                error.message = `连接错误${error.response.status}`
        }
    } else {
        error.message = '连接到服务器失败'
    }
    return Promise.resolve(error.response)
})

export default httpAsync
