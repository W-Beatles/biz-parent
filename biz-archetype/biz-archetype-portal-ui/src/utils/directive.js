export const limitChar = {
    update: function (el, {modifiers}) {
        try {
            let num = el.value.toString()
            num = num.replace(/^[a-z]/g, '')
            el.value = num
            modifiers.lazy ? el.dispatchEvent(new Event('change')) : el.dispatchEvent(new Event('input'))
        } catch (e) {
            console.log('e', e)
        }
    }
}

Vue.directive('limitChar', limitChar)