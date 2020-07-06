const SUCCESS_CODE = 1

const Enum = {
    StatusCodeEnum: {
        1: '生成中',
        2: '成功',
        3: '失败'
    },
    OptBtnEnum: [{
        optText: '下载',
        optFnc: 'download',
        optShowCode: SUCCESS_CODE,
        optClass: {'downLoad': true},
        iconClass: {'el-icon-download': true},
    }, {
        optText: '编辑',
        optFnc: 'editAppType',
        optClass: {'edit': true},
        iconClass: {'el-icon-edit': true},
    }, {
        optText: '删除',
        optFnc: 'delAppType',
        optClass: {'delete': true},
        iconClass: {'el-icon-delete': true},
    }]
}
export default Enum