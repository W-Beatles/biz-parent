import Request from "@/api/Model";

class UtilityModel {
    appName = '/service-utility'

    geneUri(key) {
        return Request.generateUrl(this.appName, key)
    }

    async getAppTypeDiction(param) {
        const KEY = 'APP_TYPE_ENUM'
        const {data} = await Request.requestWholeModel(`${this.geneUri('SU_AppTypeDic')}${KEY}`, 'get', param)
        return data
    }

    async getExportSid(exportUrl, param) {
        return await Request.requestWholeModel(`${this.geneUri('SU_ExportSid')}${exportUrl}`, 'POST', param)
    }

    async getExportStatus(sid) {
        const {data} = await Request.requestWholeModel(`${this.geneUri('SU_ExportStatus')}${sid}`, 'POST', {})
        return data
    }
}

export default UtilityModel