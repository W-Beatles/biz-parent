import Request from "@/api/Model";

const redirect_uri = 'redirect_uri=http://admin.gezimm.com:9527'
const access_url = 'http://access.waynechu.cn:9050/oauth/authorize'
const client_id = 'client_id=h5'
const type = 'response_type=code'
const scope = 'scope=all'
const grant_type = 'grant_type=authorization_code'
const refresh_grant_type = 'grant_type=refresh_token'
const client_secret = 'client_secret=123456'
const auth_url = `${access_url}?${client_id}&${type}&${scope}&${redirect_uri}`

export function redirect() {
    let {hash} = window.location
    window.location.href = auth_url + `&state=${hash.split('#/')[1]}`
}


export async function getToken() {
    const {access_token} = JSON.parse(localStorage.getItem('TOKEN_INFO')) || {}
    const code = new URLSearchParams(window.location.search).get('code')
    const state = new URLSearchParams(window.location.search).get('state')
    let {origin} = window.location
    if (code) {
        const urlStr = `${grant_type}&code=${code}&${client_id}&${client_secret}&${redirect_uri}`
        await genToken(urlStr)
        window.location.href = `${origin}/#/${state}`
    }
    if (!code && !access_token) {
        redirect()
    }
}

/**
 * expires_in: token过期时间（单位是S）
 * @returns {Promise<void>}
 */
export async function refreshToken() {
    if (window.interVal) {
        return
    }
    clearInterval(window.interVal)
    const {refresh_token, expires_in} = JSON.parse(localStorage.getItem('TOKEN_INFO')) || {}
    const urlStr = `${refresh_grant_type}&${client_id}&${client_secret}&refresh_token=${refresh_token}`
    window.interVal = setInterval(async () => {
        console.log(' ----------', expires_in)
        await genToken(urlStr)
    }, (expires_in - 1) * 1000) // 提前一秒刷新, 保证页面无感替换token
}


async function genToken(urlStr) {
    const tokenInfo = await Request.requestAccess(`/oauth/token?${urlStr}`, 'post')
    localStorage.setItem('TOKEN_INFO', JSON.stringify(tokenInfo))
}
