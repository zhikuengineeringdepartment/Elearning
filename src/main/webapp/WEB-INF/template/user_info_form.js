/*
用户个人信息修改表单组件
 */
var userInfoFormTemplate = `
<el-form ref="form" :model="baseInfo" label-width="80px">
                    <div style="display: flex;align-items: center;margin: 10px;padding: 20px;">
                        <img :src="baseInfo.userAvatar" width="160px" height="160px">
                        <el-button type="primary" size="small" round style="margin: 20px;">更换头像</el-button>
                    </div>
                    <el-form-item label="用户昵称">
                        <el-input v-model="baseInfo.userNick"></el-input>
                    </el-form-item>
                    <el-form-item label="用户邮箱" style="display: flex;align-items: center;margin-left: 0">
                        <div  style="display: flex;align-items: center;margin-left:-80px" >
                            <el-input v-model="baseInfo.userEmail" disabled></el-input>
                            <el-button type="primary" size="small" round style="margin: 20px;">更换邮箱绑定</el-button>
                        </div>
                    </el-form-item>
                    <el-form-item label="手机号">
                        <el-input v-model="baseInfo.userPhone"></el-input>
                    </el-form-item>
                    <el-form-item label="所属专业">
                        <el-cascader :options="colleges" :props="college_major" change-on-select></el-cascader>
                    </el-form-item>
                    <el-form-item label="密码" style="display: flex;align-items: center;">
                        <el-button type="primary" size="small" round style="margin: -80px;">修改密码</el-button>
                    </el-form-item>
                </el-form>
`

var userInfoFormModule = {
    data:function () {
        return{
            baseInfo:{},
            colleges:[],
            college_major:{
                value: 'id',
                label: 'name',
                children: '_majors'
            }
        }
    },
    props:[],
    template: userInfoFormTemplate,
    created:function(){
        this.getUserInfo();
        this.getColleges();
    },
    methods:{
        getUserInfo:function(){
            var _this = this;
            axios.get('user/getBaseInfo',{
                params:{
                    uid:0
                }
            })
                .then(function(response){
                    _this.baseInfo = response.data.data.baseInfo;
                })
                .then(function () {
                    if(root.login){
                        _this.getNoteView(sid);
                    }
                })
                .catch(function(err){
                    console.log(err);
                });
        },
        getColleges:function(){
            this.colleges = [
                {
                    id:1,
                    name:'软件学院',
                    _majors:[
                        {
                            id:2,
                            name:"软件工程"
                        },
                        {
                            name:"数媒"
                        }
                    ]
                }
            ]
        }
    },

}

Vue.component("my_baseInfo_form",userInfoFormModule);