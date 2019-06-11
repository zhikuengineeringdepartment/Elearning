/*
活动模块组件
 */
var activityMainTemplate =
 `
<!-- <el-main>
<el-row type="flex" class="row-bg" justify="center">
<el-col :span="16">
    <template v-for="activity in activities">
        <my_activity :activity="activity"></my_activity>
    </template>
    </el-col>
</el-row>
</el-main> -->
`

var activityMainModule = {
    data:function () {
        return{
            // activities: [
            //     {
            //         title:"六一就该聊点羞羞哒R18少儿不宜鸭 | 山软杂谈",
            //         date:"2019:05:31",
            //         img:"img/IMG_4025.JPG",
            //         descs:"我怀疑你在开车，但是我没有证据！",
            //         link:"https://mp.weixin.qq.com/s?__biz=MzI2NzU5OTg0Nw==&mid=2247484343&idx=1&sn=6742a149c61ef654f5e8f64211c3528c&chksm=eafd293fdd8aa02962555a18e6493abf776bf846cf1a422cf219e194de0b2d9cc4d16735f038&scene=0&xtrack=1&pass_ticket=HIs3sQh%2FK6MxexapPhXIeL6hHwRYovunWj9TArjL2uixEpwzMORAcR2eOoZOrV5p#rd"
            //     },
            //     {
            //         title:"六一就该聊点羞羞哒R18少儿不宜鸭 | 山软杂谈",
            //         date:"2019:05:31",
            //         img:"img/IMG_4025.JPG",
            //         descs:"我怀疑你在开车，但是我没有证据！",
            //         link:"https://mp.weixin.qq.com/s?__biz=MzI2NzU5OTg0Nw==&mid=2247484343&idx=1&sn=6742a149c61ef654f5e8f64211c3528c&chksm=eafd293fdd8aa02962555a18e6493abf776bf846cf1a422cf219e194de0b2d9cc4d16735f038&scene=0&xtrack=1&pass_ticket=HIs3sQh%2FK6MxexapPhXIeL6hHwRYovunWj9TArjL2uixEpwzMORAcR2eOoZOrV5p#rd"
            //     },
            //     {
            //         title:"六一就该聊点羞羞哒R18少儿不宜鸭 | 山软杂谈",
            //         date:"2019:05:31",
            //         img:"img/IMG_4025.JPG",
            //         descs:"我怀疑你在开车，但是我没有证据！",
            //         link:"https://mp.weixin.qq.com/s?__biz=MzI2NzU5OTg0Nw==&mid=2247484343&idx=1&sn=6742a149c61ef654f5e8f64211c3528c&chksm=eafd293fdd8aa02962555a18e6493abf776bf846cf1a422cf219e194de0b2d9cc4d16735f038&scene=0&xtrack=1&pass_ticket=HIs3sQh%2FK6MxexapPhXIeL6hHwRYovunWj9TArjL2uixEpwzMORAcR2eOoZOrV5p#rd"
            //     }
            // ]
        }
    },
    props:[],
    template: activityMainTemplate,
    created(){
        // this.pleaseWait();
        this.open();
    },
    methods:{
        // pleaseWait:function(){
        //     alert("敬请期待");
            
        // }
        open() {
            this.$alert('', '敬请期待', {
              confirmButtonText: '确定',
              customClass: 'messageBox-confirm',//配置自定义类名以修改样式

            });
          }
        }
}

Vue.component("my_activity_main",activityMainModule);