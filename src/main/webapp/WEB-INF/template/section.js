let sectionTemplate = `
<div id="sectionView_"></div>
`



var sectionModule = {
    data: function () {
        return {

        }
    },
    props:["sectionView"],
    template: sectionTemplate,
    mounted:function(){
        load_paragraph(this.sectionView,"sectionView_");
    },
    methods:{

    }
}

Vue.component('my_section',sectionModule)


