var fileMainTemplate = `
<el-main style="margin-top: 20px">
        <el-row style="display: flex;flex-direction: column;align-items: center">
            <el-col :span="16" style="margin-bottom: 20px">
                <el-card shadow="always" style="padding: 20px;">
                    <h1 style="margin-bottom: 10px;font-size: 48px">Hello , world</h1>
                    <p>每一个不曾起舞的日子都是对生命的浪费</p>
                    <el-button type="primary">上传<i class="el-icon-upload el-icon--right"></i></el-button>
                </el-card>
            </el-col>

            <el-col :span="16">
                <el-cascader :options="options" change-on-select></el-cascader>
            </el-col>

            <el-col :span="16" style="margin-top: 20px">
                <my_file v-for="(jfile,index) in myfiles" :jfile="jfile" key="index"></my_file>
            </el-col>
        </el-row>
    </el-main>
`

var fileMainModule = {
    data:function () {
        return{

        }
    },
    props:["myfiles","options"],
    template: fileMainTemplate,
    methods:{

    },

}

Vue.component("my_filemain",fileMainModule);