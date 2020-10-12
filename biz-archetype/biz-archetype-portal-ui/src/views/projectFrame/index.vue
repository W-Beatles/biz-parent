<template>
    <div class="TaskSearch">
        <div class="SearchHeader">
            <div class="SearchItem">
                <p class="label">原型 ID:</p>
                <el-input v-model="appTypeParams.id" placeholder="请输入内容"></el-input>
            </div>
            <div class="SearchItem">
                <p class="label">AppID:</p>
                <el-input v-model="appTypeParams.appId" placeholder="请输入内容"></el-input>
            </div>
            <m-button type="info" class="clear" @click="resetSearch">清空</m-button>
            <m-button class="search" @click="queryAppType">查询</m-button>
            <m-button class="add" @click="addAsync">新建</m-button>
            <m-button class="export" @click="getAppTypeExportSid">导出</m-button>
        </div>
        <el-table class="taskListTable" :data="taskList" style="width: 100%">
            <el-table-column type="expand">
                <template slot-scope="{row}">
                    <el-form label-position="left" class="demo-table-expand">
                        <el-form-item label="包名">
                            <span>{{ PACKAGE_PREFIX + row.packageName }}</span>
                        </el-form-item>
                        <el-form-item label="项目描述">
                            <span>{{ row.description || '-' }}</span>
                        </el-form-item>
                    </el-form>
                </template>
            </el-table-column>
            <el-table-column label="原型ID" prop="id" width="80"></el-table-column>
            <el-table-column label="AppID" prop="appId" width="300"></el-table-column>
            <el-table-column label="项目名称" prop="appName" width="300"></el-table-column>
            <el-table-column label="项目类型" prop="appTypeDesc"></el-table-column>
            <el-table-column label="状态">
                <template slot-scope="{row}">
                    <el-tag :type="StatusCodeTagType[row.statusCode]">
                        <i :class="[StatusCodeTag[row.statusCode]]"/>{{` ${row.statusCodeDesc}`}}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="创建人" prop="createdUser"></el-table-column>
            <el-table-column label="创建时间" prop="createdTime"></el-table-column>
            <el-table-column label="操作" width="200" fixed="right">
                <template slot-scope="{row}">
                    <div class="optBtnBlock">
                        <m-button
                                v-for="(opt, index) in optBtnTypeList"
                                v-if="index || opt.optShowCode === row.statusCode"
                                :key="index"
                                :class="opt.optClass"
                                @click="optClick(opt.optFnc, row.id)">
                            <i :class="opt.iconClass"/>&nbsp;&nbsp;{{opt.optText}}
                        </m-button>
                    </div>
                </template>
            </el-table-column>
        </el-table>
        <add :visible.sync="visibleDialog"
             v-if="visibleDialog"
             title="测试弹框"
             :id="optId"
             :loading="addLoading"
             :appTypeTempMap="appTypeTempMap"
             :appTypeOptions="appTypeOptions"
             @submit="submitAddForm"
             @close="visibleDialog = false"
        ></add>
        <el-pagination
                @size-change="pageSizeChange"
                :current-page="appTypeParams.pageNum"
                :page-size="appTypeParams.pageSize"
                background
                @current-change="currentChange"
                :page-sizes="[5, 10, 20, 50]"
                layout="prev, pager, next, sizes, jumper"
                :total="total">
        </el-pagination>
    </div>
</template>

<script>
    import AuthModel from '@/api/Model/AppType/authModel.js'
    import {mixinModule, cmpModule} from './autoImport.js'
    import Enum from './Enum'
    import UtilityModel from "@/api/Model/AppType/utilityModel";

    const EXPORT_POW = 2
    const PACKAGE_PREFIX = 'cn.waynechu.'
    const EXPORT_URL = 'biz-archetype-portal/archetypes/export'
    const authModel = new AuthModel()
    const utilityModel = new UtilityModel()
    export default {
        name: 'index',
        components: cmpModule,
        mixins: mixinModule,
        data() {
            return {
                StatusCodeTagType: ['', 'success', 'danger'],
                StatusCodeTag: ['el-icon-loading', 'el-icon-check', 'el-icon-warning-outline'],
                PACKAGE_PREFIX: PACKAGE_PREFIX,
                appTypeParams: {
                    id: '',
                    appId: '',
                    pageNum: 1,
                    pageSize: 10,
                },
                optBtnTypeList: Enum.OptBtnEnum,
                total: 10,
                taskList: [],
                visibleDialog: false,
                addLoading: false,
                optId: -1,
                appTypeTempMap: null,
                appTypeOptions: [],
                sid: ''
            }
        },
        created() {
            this.getTaskList()
            this.getAppTypeList()
        },
        methods: {
            async getTaskList() {
                const res = await authModel.getAppTypeList(this.appTypeParams)
                if (res) {
                    const {list, total} = res
                    this.total = total
                    this.taskList = list || []
                }
            },
            optClick(optFnc, id) {
                this[optFnc](id)
            },
            async delAppType(id) {
                const {status} = await authModel.delArchetype(id, 'delete')
                status && this.getTaskList()
            },
            editAppType(id) {
                this.optId = id
                this.visibleDialog = true
            },
            async download(id) {
                const {data, fileName} = await authModel.downloadArcFile(id)
                const blob = new Blob([data], {type: "application/zip"})
                if ('download' in document.createElement('a')) { // 非IE下载
                    const elink = document.createElement('a')
                    elink.download = fileName
                    elink.style.display = 'none'
                    elink.href = window.URL.createObjectURL(blob)
                    document.body.appendChild(elink)
                    elink.click()
                    window.URL.revokeObjectURL(elink.href) // 释放URL 对象
                    document.body.removeChild(elink)
                } else { // IE10+下载
                    navigator.msSaveBlob(blob, fileName)
                }
            },
            addAsync() {
                this.optId = -1
                this.visibleDialog = true
            },
            async submitAddForm(form) {
                this.addLoading = true
                const submitType = form.isEdit ? 'editArchetype' : 'addArchetypes'
                const status = await authModel[submitType](form, this.closeLoadingCb, this.submitCallback)
                console.log('status', status)
            },
            closeLoadingCb() {
                this.addLoading = false
            },
            submitCallback() {
                this.getTaskList()
                this.visibleDialog = false
            },
            async getAppTypeList() {
                this.appTypeOptions = await utilityModel.getAppTypeDiction()
                this.geneAppTemplate(this.appTypeOptions)
            },
            geneAppTemplate(options) {
                let appTypeMap = {}
                options.forEach(opt => {
                    appTypeMap[opt.dicCode] = opt
                })
                this.appTypeTempMap = appTypeMap
            },
            geneTypeDesc(appType) {
                return this.appTypeTempMap[appType].dicDesc
            },
            async getAppTypeExportSid() {
                const {data} = await utilityModel.getExportSid(EXPORT_URL, this.appTypeParams)
                this.sid = data
                let n = 0
                this.exportTimer = setInterval(async () => {
                    await this.getAppTypeExportStatus()
                    n++
                    console.log('n-----', Math.pow(EXPORT_POW, n))
                }, Math.pow(EXPORT_POW, n) * 1000)
                this.$once('hook:beforeDestroy', () => {
                    clearInterval(this.exportTimer)
                })
            },
            async getAppTypeExportStatus() {
                const {status, fileName, url} = await utilityModel.getExportStatus(this.sid)
                console.log('fileName', fileName)
                if (status === 1) {
                    clearInterval(this.exportTimer)
                    if ('download' in document.createElement('a')) { // 非IE下载
                        const elink = document.createElement('a')
                        elink.download = fileName
                        elink.style.display = 'none'
                        elink.href = url
                        document.body.appendChild(elink)
                        elink.click()
                        window.URL.revokeObjectURL(elink.href) // 释放URL 对象
                        document.body.removeChild(elink)
                    }
                    return false
                } else if (status === -1) {
                    clearInterval(this.exportTimer)
                    return false
                }
            }
        }
    }
</script>

<style lang="stylus">
    @import './stylus/index.styl';
    .TaskSearch

        .SearchHeader
            display flex
            width 100%
            align-items center

            .m-button
                width 80px
                height 34px
                line-height: 14px;
                margin-left: 20px;

            .search
                background $activeText
                color #fff

            .add
                background: #ff9f1c;
                color #fff

            .export
                background: #8bb1d7;
                color #fff
                margin-left 10%

            .SearchItem
                width 300px
                margin-right 20px
                display flex
                align-items center

                .label
                    width 80px


        .taskListTable
            margin-bottom: 10px;

            .optBtnBlock
                display flex
                justify-content center

            .m-button
                width 60px
                text-align center
                padding 0
                line-height 30px
                color #fff

            .downLoad
                background: #4cae50;

            .edit
                background: #48cae4;

            .delete
                background: #ee6c4d;
</style>
