/*
消息列表组件
 */
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
            type:1,
            messages:[]
        }
    },
    props:[],
    template: messageListTemplate,
    created:function(){
        //this.getMessages();
    },
    methods:{
        getMessages:function(){
            var _this = this;
            axios.get('user/getMessages',{
                params:{
                    uid:0,
                    type:this.type
                },
                withCredentials:true
            })
                .then(function(response){
                    _this.messages = response.data.data.myMessages;
                    for(var i=0;i<_this.messages.length;i++){
                        _this.messages[i].messageDate = getFormatDate(response.data.data.myMessages[i].messageDate)
                    }
                })
                .catch(function(err){
                    console.log(err);
                });
        }
    },

}

Vue.component("my_message_list",messageListModule);