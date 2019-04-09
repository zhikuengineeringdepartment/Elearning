var messageListTemplate = `
<div>
<div>全部消息</div>
                <ul>
                    <template v-for="message in messages">
                        <li style="list-style: none;margin: 20px 0px; border-top: 1px solid #f0f0f0">
                            <my_message :msg="message"></my_message>
                        </li>
                    </template>
                </ul>
</div>
`

var messageListModule = {
    data:function () {
        return{
            messages:[]
        }
    },
    props:[],
    template: messageListTemplate,
    created:function(){
        this.getMessages();
    },
    methods:{
        getMessages:function(){
            this.messages = [
                {
                    user:{
                        icon:'./img/logo.jpg',
                        name:'zhiku'
                    },
                    time:'2018-09-15',
                    content:"你好，你手动一条来自智库的信息，请及时查收。"
                },
                {
                    user:{
                        icon:'./img/logo.jpg',
                        name:'zhiku'
                    },
                    time:'2018-09-15',
                    content:"你好，你手动一条来自智库的信息，请及时查收。"
                },
                {
                    user:{
                        icon:'./img/logo.jpg',
                        name:'zhiku'
                    },
                    time:'2018-09-15',
                    content:"你好，你手动一条来自智库的信息，请及时查收。"
                }
            ]
        }
    },

}

Vue.component("my_message_list",messageListModule);