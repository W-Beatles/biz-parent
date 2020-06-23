<template>
    <div class="addAppTypeDia">
        <el-dialog title="新建原型"
                   v-bind="$attrs"
                   v-on="$listeners">
            <el-form :model="form">
                <el-form-item label="AppId" :label-width="formLabelWidth">
                    <el-input v-model="form.appId" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="项目名称" :label-width="formLabelWidth">
                    <el-input v-model="form.appName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="项目类型" :label-width="formLabelWidth">
                    <el-select v-model="form.appType" placeholder="请选择活动区域">
                        <el-option label="Service" value="0"></el-option>
                        <el-option label="SDK" value="1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="项目描述" :label-width="formLabelWidth">
                    <el-input v-model="form.description" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="上传git" :label-width="formLabelWidth">
                    <el-radio-group v-model="form.gitUploadType">
                        <el-radio :label="true">是</el-radio>
                        <el-radio :label="false">否</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="包名" :label-width="formLabelWidth">
                    <el-input v-model="form.packageName" autocomplete="off"></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="$emit('submit', form)">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        inheritAttrs: false,
        name: "add",
        data() {
            return {
                formLabelWidth: '80px',
                form: {
                    appId: "",
                    appName: "",
                    appType: '',
                    description: "",
                    gitUploadType: false,
                    packageName: ""
                }
            }
        }
    }
</script>

<style lang="stylus" scoped>

</style>