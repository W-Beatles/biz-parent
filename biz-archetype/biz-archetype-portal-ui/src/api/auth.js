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
        window.location.href = this.auth_url
    }

}

export default Auth