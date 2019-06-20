/*
文件模块的主页面组件
 */
var fileMainTemplate = `
<el-main id="fileList" style="margin-top: 20px">
        <el-row style="display: flex;flex-direction: column;align-items: center">
            <el-col :span="16" style="margin-bottom: 20px">
                <el-card shadow="always" style="padding: 20px;">
                    <h1 style="margin-bottom: 10px;font-size: 48px">Hello , world</h1>
                    <p>每一个不曾起舞的日子都是对生命的浪费</p>
                    <el-button type="primary" @click="gotoUpload">上传<i class="el-icon-upload el-icon--right"></i></el-button>
                </el-card>
            </el-col>

            <el-col :span="16" @scroll.native="lazyLoading">
                <el-row>
                    <el-col :span="10">
                        <!--<el-cascader :value="fileCourse" :options="colleges" :props="college_major" filterable :change-on-select="false"></el-cascader>-->
                        <my_course_select v-on:get-course-value="set_course_value"></my_course_select>
                    </el-col>
                    <el-col :span="10">
                        <el-input placeholder="搜索" v-model="fileListForm.keyWord" class="input-with-select">
                            <el-button slot="append" icon="el-icon-search" @click="doSearch"></el-button>
                        </el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-button id="file_order" icon="el-icon-sort-down" style="margin-left:10px;" @click="changeOrder">按时间降序</el-button>
                    </el-col>
                </el-row>
                
            </el-col>

            <el-col :span="16" style="margin-top: 20px">
                <my_file v-for="jfile in my_files" :jfile="jfile"></my_file>
            </el-col>
        </el-row>
    </el-main>
`
var impermanentFileMainTemplate = `
`;
var fileMainModule = {

    data: function () {
        return {
            fileListForm: {
                keyWord: '',
                order: true,
                fileCourse: '',
                page: 1
            },
            my_files: [],
            // colleges: [
            //     {
            //         id:1,
            //         name:'软件学院',
            //         _majors:[
            //             {
            //                 id:2,
            //                 name:"软件工程"
            //             },
            //             {
            //                 name:"数媒"
            //             }
            //         ]
            //     }
            // ],
            // college_major:{
            //     value: 'id',
            //     label: 'name',
            //     children: '_majors'
            // }
        }
    },
    props: [],
    template: fileMainTemplate,
    // template: impermanentFileMainTemplate,
    created: function () {
        this.scrollToDown();
        this.getFileList(this.fileListForm.page);
        // this.open();
    },
    methods: {
        // open() {
        //     this.$alert('', '敬请期待', {
        //         confirmButtonText: '确定',
        //         customClass: 'messageBox-confirm',
        //     });
        // }
            gotoUpload:function(){
                this.$router.push('/fileUpload')
            },
            changeOrder(){
                this.fileListForm.order = !this.fileListForm.order
                this.$nextTick(()=>{
                    if(!this.fileListForm.order){
                        document.getElementById('file_order').children[0].className ='el-icon-sort-up'
                        document.getElementById("file_order").children[1].innerHTML = '按时间升序'
                    }else{
                        document.getElementById('file_order').children[0].className ='el-icon-sort-down'
                        document.getElementById("file_order").children[1].innerHTML = '按时间降序'
                    }
                    this.my_files.reverse();
                })

            },
            set_course_value:function(cid){
                this.fileListForm.fileCourse = cid;
            },
            set_keyword:function(key){
                this.fileListForm.keyWord = key;
            },
            doSearch:function(){
                this.my_files = [];
                this.fileListForm.page = 1;
                this.getFileList(this.fileListForm.page);
            },
            getFileList:function(page){
                var _this =this;
                axios.get('file/getFileList',{
                    params:{
                        keyWord:this.fileListForm.keyWord,
                        fileCourse:this.fileListForm.fileCourse,
                        page:page,
                        order:this.fileListForm.order
                    }
                })
                    .then(function(response){
                        for(var i=0;i<response.data.data.files.length;i++){
                            response.data.data.files[i].fileUploadTime = getFormatDate(response.data.data.files[i].fileUploadTime)
                        }
                        if(response.data.data.files.length != 0){
                            _this.my_files = _this.my_files.concat(response.data.data.files);
                        }else{
                            // alert("已经到最后了")
                        }
                    })
                    .catch(function(err){
                        console.log(err);
                    });
            },
            scrollToDown(){
                //TODO 目前通过判断路径的方式只在文件部分实现滚动事件，不过不是长久之计，应该修改为只对特定组件或者特定页面的滚动事件
                document.onscroll = e =>{
                    if(this.$route.path === '/fileMain'){
                        this.lazyLoading();
                    }
                }
            },
            lazyLoading () { // 滚动到底部，再加载的处理事件
                let scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
                let clientHeight = document.documentElement.clientHeight;
                let scrollHeight = document.documentElement.scrollHeight;
                if (scrollTop + clientHeight >= scrollHeight) { // 如果滚动到接近底部，自动加载下一页
                    //事件处理
                    this.fileListForm.page++;
                    console.log(this.fileListForm.page)
                    this.getFileList(this.fileListForm.page)
                }
            }
    }

}
Vue.component("my_file_main", fileMainModule);