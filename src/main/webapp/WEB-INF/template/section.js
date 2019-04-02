let sectionTemplate = `
<div id="section.sid" >
    <template v-for="knowledge in section.knowledges">
        <h3 id="knowledge.kid">{{knowledge.knowledgeName}}</h3>{{preTag}}
        <paragraph v-for="paragraph in knowledge.paragraphs" v-bind:paragraph="paragraph" v-bind:preTag="preTag" v-bind:inline="inline" v-on:function_a="function_b"></paragraph>
    </template>
</div>
`



var sectionModule = {
    data: function () {
        return {
            preTag:null,
            inline:false
        }
    },
    props:['section'],
    template: sectionTemplate,
    methods:{
        addCount:function () {
            this.count++;
        },
        function_b:function () {
            this.inline = !this.inline;
        }
    }
}

Vue.component('my_section',sectionModule)


