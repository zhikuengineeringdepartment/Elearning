let paragraphTemplate = `
<p>
<template v-if="!((paragraph.newline === 'y' && inline)||(paragraph.newline === 'n' && inline))">
   hahaha
</template>
<template v-else>
    lalala
</template>
</p>
`



var paragraphModule = {
    data: function () {
        return {
        }
    },
    props:['paragraph','preTag','inline'],
    template: paragraphTemplate,
    created:function(){
        // if((paragraph.newline === 'y' && inline === 'n')||(paragraph.newline === 'n' && inline === 'y')){
            this.$emit('function_a');
        // }
    },
    methods:{
        addCount:function () {
            this.count++;
        }
    }
}

Vue.component('paragraph',paragraphModule)

