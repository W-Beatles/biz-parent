<template>
    <div>
        <svg-icon v-if="icon" :icon-class='icon' />
        <span v-if="title" slot='title'>{{title}}</span><span v-if="isSubMenu" style="float: right;margin-right: 40px"> </span>
    </div>
</template>
<script>
export default {
  name: 'MenuItem',
  props: {
    icon: {
      type: String,
      default: ''
    },
    title: {
      type: String,
      default: ''
    },
    isSubMenu: {
      type: Boolean,
      default: false
    }
  }
}
</script>
