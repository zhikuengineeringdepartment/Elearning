let aaa = `
<div class="knowlege" >
    <h3>{{knowledge.title}}</h3>
    <template v-for="paragraph in knowledge.paragraphs">
        <template v-if="paragraph.type === 't'">
            <p>{{paragraph.content}}</p>
        </template>
        <template v-else-if="paragraph.type === 'm'">
            <img src="paragraph.content"/>
        </template>
        <template v-else>
            <a href="paragraph.content"></a>
        </template>
    </template>
</div>
`



var bottomCounter = {
    data: function () {
        return {
            count: 0
        }
    },
    props:['knowledge'],
    template: aaa,
    methods:{
        addCount:function () {
            this.count++;
        }
    }
}

Vue.component('bottom-counter',bottomCounter)


