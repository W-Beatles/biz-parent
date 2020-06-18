<template>
    <div>hhhhhhhhhhhh</div>
</template>

<script>
    import AuthModel from '@/api/Model/authModel.js'
    const authModel = new AuthModel()
    export default {
        name: 'index',
        data() {
            return {}
        },
        created() {
            authModel.testApi()
        },
        methods: {}
    }
</script>

<style lang="stylus">

</style>
