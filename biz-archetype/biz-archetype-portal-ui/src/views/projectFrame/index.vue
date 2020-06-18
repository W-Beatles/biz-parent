<template>
    <div class="TaskSearch">
        <div class="SearchHeader">
            <div class="SearchItem">
                <p class="label">任务ID:</p>
                <el-input v-model="taskId" placeholder="请输入内容"></el-input>
            </div>
            <div class="SearchItem">
                <p class="label">AppID:</p>
                <el-input v-model="appId" placeholder="请输入内容"></el-input>
            </div>
            <m-button type="info" class="clear">清空</m-button>
            <m-button class="search">查询</m-button>
        </div>
        <el-table class="taskListTable" :data="taskList" style="width: 100%">
            <el-table-column type="expand">
                <template slot-scope="{row}">
                    <el-form label-position="left" inline class="demo-table-expand">
                        <el-form-item label="项目描述">
                            <span>{{ row.desc }}</span>
                        </el-form-item>
                    </el-form>
                </template>
            </el-table-column>
            <el-table-column label="APP ID" prop="appId"></el-table-column>
            <el-table-column label="项目类型" prop="taskType"></el-table-column>
            <el-table-column label="项目模式" prop="modal"></el-table-column>
            <el-table-column label="项目名称" prop="appName"></el-table-column>
            <el-table-column label="状态" prop="statusCode">
                <template slot-scope="{row}">
                    {{Enum.StatusCodeEnum[row.statusCode]}}
                </template>
            </el-table-column>
            <el-table-column label="创建人" prop="creator"></el-table-column>
            <el-table-column label="创建人创建时间" prop="createTime"></el-table-column>
            <el-table-column label="操作">
                <m-button class="downLoad"><i class="el-icon-download"/> 下载</m-button>
            </el-table-column>
        </el-table>
        <el-pagination
                @size-change="pageSizeChange"
                :current-page="pageNum"
                :page-size="pageSize"
                background
                @current-change="currentChange"
                :page-sizes="[10, 20, 50]"
                layout="prev, pager, next, sizes, jumper"
                :total="total">
        </el-pagination>
    </div>
</template>

<script>
    import AuthModel from '@/api/Model/authModel.js'

    const authModel = new AuthModel()
    export default {
        name: 'index',
        data() {
            return {
                taskId: '',
                appId: '',
                pageNum: 1,
                pageSize: 10,
                total: 10,
                taskList: [{
                    appId: "int-spring-cl-tire-installation-service",
                    appName: "string",
                    archetypeCode: 0,
                    createTime: "2020-06-16 01:29:31",
                    creator: "WayneChu",
                    taskType: "server",
                    modal: "普通模式",
                    statusCode: 1,
                    taskId: 191,
                    url: "string",
                    desc: '轮胎安装服务'
                }, {
                    appId: "int-spring-cl-tire-installation-service",
                    appName: "string",
                    archetypeCode: 0,
                    createTime: "2020-06-16 01:29:31",
                    creator: "WayneChu",
                    taskType: "server",
                    modal: "普通模式",
                    statusCode: 1,
                    taskId: 191,
                    url: "string",
                    desc: '轮胎安装服务'
                }, {
                    appId: "int-spring-cl-tire-installation-service",
                    appName: "string",
                    archetypeCode: 0,
                    createTime: "2020-06-16 01:29:31",
                    creator: "WayneChu",
                    taskType: "server",
                    modal: "普通模式",
                    statusCode: 1,
                    taskId: 191,
                    url: "string",
                    desc: '轮胎安装服务'
                }]
            }
        },
        created() {
            this.getTaskList()
        },
        methods: {
            async getTaskList() {
                const res = await authModel.testApi()
                if (res) {
                    const {list, total} = res
                    this.total = total
                    this.taskList = list || []
                }

            },
            currentChange(val) {
                this.pageNum = val
                this.getTaskList()
            },
            pageSizeChange(val) {
                // 重置页数
                this.pageNum = 1
                this.pageSize = val
                this.getTaskList()
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
