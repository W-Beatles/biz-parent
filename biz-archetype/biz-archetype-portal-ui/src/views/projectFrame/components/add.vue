<template>
    <div class="addAppTypeDia">
        <el-dialog :title="form.isEdit ? '编辑原型' : '新建原型'"
                   v-bind="$attrs"
                   width="34%"
                   v-on="$listeners">
            <el-form :model="form" v-loading="loading">
                <el-form-item label="AppId" :label-width="formLabelWidth" required>
                    <el-input v-model="form.appId" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="项目类型" :label-width="formLabelWidth" required>
                    <el-select v-model="form.appType" placeholder="请选择项目类型" @change="appNameItemArr = []">
                        <el-option label="Service" :value="0"></el-option>
                        <el-option label="SDK" :value="1"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="项目名称" :label-width="formLabelWidth" required>
                    <template v-for="(typeItem, typeIndex) in generaAppType(form.appType)">
                        <span class="appTypeTemp">{{typeItem}}</span>
                        <el-input :style="{width: genAppItemPx(appNameItemArr[typeIndex])}"
                                  v-if="typeIndex !== appNameItemArr.length"
                                  v-model="appNameItemArr[typeIndex]" autocomplete="off"
                                  @input="inputAppName($event, typeIndex)"
                        ></el-input>
                    </template>
                </el-form-item>
                <el-form-item label="包名" :label-width="formLabelWidth" required>
                    <el-input v-model="form.packageName" autocomplete="off">
                        <template slot="prepend">cn.waynechu.</template>
                    </el-input>
                </el-form-item>
                <el-form-item label="上传git" :label-width="formLabelWidth" required>
                    <el-radio-group v-model="form.gitUploadType">
                        <el-radio :label="true">是</el-radio>
                        <el-radio :label="false">否</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="项目描述" :label-width="formLabelWidth">
                    <el-input v-model="form.description"
                              type="textarea"
                              :autosize="{ minRows: 4, maxRows: 6}"
                              maxlength="400"
                              show-word-limit></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="$emit('close')">取 消</el-button>
                <el-button type="primary" @click="$emit('submit', form)">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import AuthModel from '@/api/Model/authModel.js'

    const authModel = new AuthModel()
    const CHAR_M_LEN = 13
    const CHAR_NORMAL_LEN = 7
    const CHAR_LEN_DEFAULT = '20px'
    const SPlIT_FLAG = '%%'
    const InitForm = {
        appId: "",
        appName: "",
        appType: 0,
        description: "",
        gitUploadType: false,
        isEdit: false,
        packageName: ""
    }
    export default {
        inheritAttrs: false,
        name: "add",
        props: {
            loading: Boolean,
            id: {
                type: Number,
                default: () => -1
            }
        },
        data() {
            return {
                AppNameTemplateList: ['%%-biz-spring%%-cloud-service-%%', 'biz-%%-boot-%%-starter'],
                formLabelWidth: '80px',
                form: InitForm,
                popItem: '',
                appNameItemArr: [],
                AppNameReverseReg: ''
            }
        },
        watch: {
            appNameItemArr: {
                handler(arr) {
                    let concatAppName = ''
                    let tempOriArr = this.AppNameTemplateList[this.form.appType].split(SPlIT_FLAG)
                    tempOriArr.forEach((oriItem, oriIndex) => {
                        concatAppName += `${oriItem}${arr[oriIndex] || ''}`
                    })
                    this.$set(this.form, 'appName', concatAppName.trim())
                },
                deep: true
            },
            async id(val) {
                const isEdit = val > 0
                this.form = InitForm
                if (isEdit) {
                    const {data} = await authModel.getArchetype(val)
                    this.form = data
                    this.reverseAppName(this.form)
                }
                this.$set(this.form, 'isEdit', isEdit)
            }
        },
        methods: {
            generaAppType(type) {
                let tempArr = this.AppNameTemplateList[type].split(SPlIT_FLAG)
                this.appNameItemArr.length = tempArr.length - 1
                return tempArr
            },
            genAppItemPx(item) {
                if (!item) return CHAR_LEN_DEFAULT
                let regM = /m/g
                regM.exec(item)
                return `${item.length * CHAR_NORMAL_LEN + 10}px`
            },
            inputAppName(value, index) {
                const regName = value.replace(/[^\a-z]/g, '')
                this.$set(this.appNameItemArr, index, regName)
                return 0
            },
            getReverseReg(appType) {
                const splitReg = new RegExp(SPlIT_FLAG, 'g')
                return this.AppNameTemplateList[appType].replace(splitReg, '(\\w+)')
            },
            reverseAppName({appType, appName}) {
                const matchReg = new RegExp(this.getReverseReg(appType))
                let arr = appName.match(matchReg) || []
                arr.shift()
                this.appNameItemArr = arr
            }
        }
    }
</script>

<style lang="stylus" scoped>

</style>