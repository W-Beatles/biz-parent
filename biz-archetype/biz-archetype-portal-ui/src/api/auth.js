import Request from "@/api/Model";

class Auth {
    constructor() {
        this.redirect_uri = 'http://admin.gezimm.com:9527/index'
        this.access_url = 'http://access.waynechu.cn:9050/oauth/authorize'
        this.client_id = 'h5'
        this.type = 'code'
        this.auth_url = `${this.access_url}?client_id=${this.client_id}&response_type=${this.type}&scope=all&redirect_uri=${this.redirect_uri}`
    }

    authRedirect() {
        alert(this.auth_url)
        console.log('this.auth_url', this.auth_url)
        window.location.href = this.auth_url
    }

    static async getToken({code}) {
        const urlStr = `grant_type=authorization_code&code=${code}&client_id=h5&client_secret=123456`
        console.log('code----------', urlStr)
        const {access_token} = await Request.requestNormal(`/oauth/token?${urlStr}`, 'post')
        localStorage.setItem('Token', access_token)
    }

}

export default Auth