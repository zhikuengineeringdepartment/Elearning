/*
文件模块的主页面组件
 */
var fileMainTemplate = `
<el-main style="margin-top: 20px">
        <el-row style="display: flex;flex-direction: column;align-items: center">
            <el-col :span="16" style="margin-bottom: 20px">
                <el-card shadow="always" style="padding: 20px;">
                    <h1 style="margin-bottom: 10px;font-size: 48px">Hello , world</h1>
                    <p>每一个不曾起舞的日子都是对生命的浪费</p>
                    <el-button type="primary" @click="gotoUpload">上传<i class="el-icon-upload el-icon--right"></i></el-button>
                </el-card>
            </el-col>

            <el-col :span="16">
                <el-row>
                    <el-col :span="10">
                        <!--<el-cascader :value="fileCourse" :options="colleges" :props="college_major" filterable :change-on-select="false"></el-cascader>-->
                        <my_course_select v-on:get-course-value="set_course_value"></my_course_select>
                    </el-col>
                    <el-col :span="10">
                        <el-input placeholder="搜索" v-model="fileListForm.keyWord" class="input-with-select">
                            <el-button slot="append" icon="el-icon-search" @click="getFileList"></el-button>
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

var fileMainModule = {
    data:function () {
        return{
            fileListForm:{
                keyWord:'',
                order:true,
                fileCourse:'',
                page:1
            },
            my_files:[],
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
    props:[],
    template: fileMainTemplate,
    created:function(){
        this.getFileList();
    },
    methods:{
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
                this.getFileList();
            })

        },
        set_course_value:function(cid){
            this.fileListForm.fileCourse = cid;
        },
        set_keyword:function(key){
            this.fileListForm.keyWord = key;
        },
        getFileList:function(){
            var _this =this;
            axios.get('file/getFileList',{
                params:{
                    keyWord:this.fileListForm.keyWord,
                    fileCourse:this.fileListForm.fileCourse,
                    page:this.fileListForm.page,
                    order:this.fileListForm.order
                }
            })
                .then(function(response){
                    _this.my_files = response.data.data.files;
                })
                .catch(function(err){
                    console.log(err);
                });
        }
    },

}

Vue.component("my_file_main",fileMainModule);