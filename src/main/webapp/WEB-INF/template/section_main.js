let sectionMainTemplate = `
<el-main>
        <el-row type="flex" class="row-bg" justify="center">
            <el-col :span="4">
                <h5>{{courseView.name}}</h5>
                <el-menu
                        default-active="2"
                        class="el-menu-vertical-demo">
                    <template v-for="section in courseView.sections">
                        <el-menu-item index="section.id">
                            <i class="el-icon-arrow-right"></i>
                            <span slot="title">{{section.sectionName}}</span>
                        </el-menu-item>
                    </template>
                </el-menu>
            </el-col>
            <el-col :span="16" style="margin: 20px">
                <my_section :sectionView="sectionView"></my_section>
            </el-col>
        </el-row>
    </el-main>
`



var sectionMainModule = {
    data: function () {
        return {
            courseView: {
                "cid": 100,
                "courseName": "java高级程序设计",
                "courseDesc": "java语言基础",
                "courseIcon": "/img/default.png",
                "sections": [
                    {
                        "sid": 10001,
                        "sectionName": "基本类型",
                        "sectionSeq": "1.1",
                        "sectionRecommendPath": "a/b.txt",
                        "sectionCourse": 100
                    },
                    {
                        "sid": 10002,
                        "sectionName": "循环语句",
                        "sectionSeq": "1.2",
                        "sectionRecommendPath": "a/c.txt",
                        "sectionCourse": 100
                    }
                ]
            },
            sectionView:{
                sid:10001,
                sectionName:"## 1.4.第三节，关于图片",
                knowledges:[
                    {
                        kid:1000101,
                        knowledgeName:"### 1.4.1.图片表示",
                        paragraphs:[
                            {
                                pid:100010101,
                                paragraphContent:"图片的markdown标准：![首页](https://img-blog.csdnimg.cn/20190319214343939.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2Jhb3dlaV8wOTE1,size_16,color_FFFFFF,t_70)  ",
                                paragraphType:'p',
                                newline:'y'
                            },
                            {
                                pid:100010102,
                                paragraphContent:"“图片的描述”功能暂未计划开发，请用“#”代替，如1.1.2.结构框图中图片。图片要**单独**一段。",
                                paragraphType:'c',
                                newline: 'y'
                            }
                        ]
                    },
                    {
                        kid:1000102,
                        knowledgeName:"### 1.4.2.图片网址",
                        paragraphs:[
                            {
                                pid:100010201,
                                paragraphContent:"将图片通过后台管理网页上传到服务器中，会得到一个网址，如",
                                paragraphType:'p',
                                newline:'y'
                            },
                            {
                                pid:100010202,
                                paragraphContent:"|ID| 内容 |\n" +
                                    "|--|--|\n" +
                                    "|1|表格第二行表示列数，行数没有限制|\n" +
                                    "|2|不同于markdown,表内空格都有效（应该吧，看渲染怎么写了（此处重点，看1.7的第5条））|",
                                paragraphType:'m',
                                newline:'y'
                            }
                        ]
                    },
                    {
                        kid:1000103,
                        knowledgeName:"### 1.4.3.总结",
                        paragraphs:[
                            {
                                pid:100010301,
                                paragraphContent:" + 列表不要嵌套列举，使用字符串的列举也不建议嵌套，只用\"+ \"无法嵌套\n" +
                                    " + 不要用下划线\n" +
                                    " + 不能用文字环绕等\n" +
                                    " + 除上述标准，其他markdown标准，暂不支持\n" +
                                    " + 不要滥用“（）”，不要像1.5.表格中ID2的内容一样写吐槽或自己不确定的知识，要严肃；补充说明尽量单独一段。",
                                paragraphType:'p',
                                newline:'n'
                            },
                            {
                                pid:100010302,
                                paragraphContent:"像百度百科某些关键字可以链接的它的百科一样，文本中如果有作者想让读者拓展学习的词，可以标注为链接，链接地址用“#”表示，程序会查询链接表单得到正确链接地址，如：“本部分涉及[数据库迸发机制](#)，此处不赘述，自己看”。",
                                paragraphType:'a',
                                newline:'n'
                            }
                        ]
                    }
                ]
            }
        }
    },
    props:[],
    template: sectionMainTemplate,
    created:function(){

    },
    methods:{
        getCourseView:function(cid){
            console.log(cid);
        },
        getSectionView:function(sid){
            console.log(sid);
        }
    }
}

Vue.component('my_section_main',sectionMainModule)


