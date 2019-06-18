/*
活动模块组件
 */
var activityTemplate = `
<a :href="activity.link" target="_blank" style="text-decoration:none;">
<el-card style="padding: 0 20px; margin-bottom: 10px;box-shadow: none;">
    <div style="display: flex;justify-content: space-between;flex-direction: row;align-items: center;">
        <div>
            <h3>{{activity.title}}</h3>
        </div>
        <div>
            <img :src="activity.img" alt="" style="width: 130px;height: 100px;">
        </div>
    </div>
</el-card>
</a>
`

var activityModule = {
    data:function () {
        return{
        }
    },
    props:['activity'],
    template: activityTemplate,
    created(){

    },
    methods:{

    }

}

Vue.component("my_activity",activityModule);