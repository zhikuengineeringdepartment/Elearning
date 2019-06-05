/*
活动模块组件
 */
var activityTemplate = `
<el-card shadow="always" style="padding: 20px; margin-bottom: 10px">
    <div style="display: flex;justify-content: space-between;">
        <div>
            <h3>{{activity.title}}</h3>
            <small>{{activity.date}}</small>
            <p>{{activity.descs}}</p>
            <a :href="activity.link" target="_blank"><p>查看详情</p></a>
        </div>
        <div>
            <a :href="activity.link" target="_blank"><img :src="activity.img" alt="" style="width: 300px;height: auto;"></a>
        </div>
    </div>
</el-card>
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