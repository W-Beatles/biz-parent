const pageMixin = {
    methods: {
        queryAppType() {
            this.getTaskList()
        },
        resetSearch() {
            this.appTypeParams = {
                id: '',
                appId: '',
                pageNum: 1,
                pageSize: 10,
            }
            this.queryAppType()
        },
        currentChange(val) {
            this.appTypeParams.pageNum = val
            this.getTaskList()
        },
        pageSizeChange(val) {
            // 重置页数
            this.appTypeParams.pageNum = 1
            this.appTypeParams.pageSize = val
            this.getTaskList()
        }
    }
}
export default pageMixin