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
        </div>
        <el-table class="taskListTable" :data="taskList" style="width: 100%">
            <el-table-column type="expand">
                <template slot-scope="{row}">
                    <el-form label-position="left" inline class="demo-table-expand">
                        <el-form-item label="项目描述">
                            <span>{{ row.appTypeDesc }}</span>
                        </el-form-item>
                    </el-form>
                </template>
            </el-table-column>
            <el-table-column label="原型 ID" prop="id"></el-table-column>
            <el-table-column label="APP ID" prop="appId"></el-table-column>
            <el-table-column label="项目类型" prop="appTypeDesc"></el-table-column>
            <el-table-column label="项目名称" prop="appName"></el-table-column>
            <el-table-column label="状态" prop="statusCodeDesc"></el-table-column>
            <el-table-column label="创建人" prop="createdUser"></el-table-column>
            <el-table-column label="创建人创建时间" prop="createdTime"></el-table-column>
            <el-table-column label="操作">
                <template slot-scope="{row}">
                    <m-button class="downLoad" @click="download(row.id)"><i class="el-icon-download"/>下载</m-button>
                </template>
            </el-table-column>
        </el-table>
        <add :visible.sync="visibleDialog"
             title="测试弹框"
             @submit="submitAddForm"
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
    import AuthModel from '@/api/Model/authModel.js'
    import {mixinModule, cmpModule} from './autoImport.js'

    const authModel = new AuthModel()
    export default {
        name: 'index',
        components: cmpModule,
        mixins: mixinModule,
        data() {
            return {
                appTypeParams: {
                    id: '',
                    appId: '',
                    pageNum: 1,
                    pageSize: 10,
                },
                total: 10,
                taskList: [],
                visibleDialog: false
            }
        },
        created() {
            this.getTaskList()
        },
        methods: {
            async getTaskList() {
                const res = await authModel.testApi(this.appTypeParams)
                if (res) {
                    const {list, total} = res
                    this.total = total
                    this.taskList = list || []
                }
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
                this.visibleDialog = true
            },
            async submitAddForm(form) {
                const status = await authModel.addArchetypes(form)
                if (status) {
                    this.visibleDialog = false
                    this.getTaskList()
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
            width 1005
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

            .SearchItem
                width 300px
                margin-right 20px
                display flex
                align-items center

                .label
                    width 80px


        .taskListTable
            margin-bottom: 10px;

            .downLoad
                background: #4cae50;
                color #fff
</style>
