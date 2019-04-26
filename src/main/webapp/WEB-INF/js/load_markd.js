/**
 * 依赖
 * ./marked.min.js
 * 使用方法：
 * load_paragraph(data,root_id)
 * @param data: json
 * @param root_id:string, 为已存在的div的id，作为根div
 * @param saveFunc:function 保存笔记的方法 
 * @param collectionF：function 收藏
 * 即可渲染组件
 */




// (function(){
//     var script = document.createElement("script");
//     script.type = "text/javascript";
//     script.src = "./js/marked.min.js";
//     document.body.appendChild(script);
// })()

function load_paragraph(json_data, root_id ,saveFunc,collectionF) {
	saveFunc = typeof(saveFunc)==='function'?saveFunc:new Function();
	collectionF = typeof(collectionF)==='function'?collectionF:new Function();

	document.getElementById(root_id).innerHTML = ""
	let section_div = document.createElement('div');
	section_div.id = json_data.sid;
	document.getElementById(root_id).append(section_div);
	section_div.innerHTML = marked(json_data.sectionName);
	let knowledges = json_data['knowledgeViews']
	if (knowledges != '') {
		knowledges.map((item, index) => {
			let k_div = document.createElement('div');
			k_div.id = item.kid;
			document.getElementById(root_id).append(k_div);
			k_div.innerHTML = marked(item.knowledgeName);
			p_in_ks = item['paragraphs']
			if (p_in_ks != '') {
				p_in_ks.map((it, ind) => {
					let p_div = document.createElement('div');
					p_div.id = it.paragraphSeq;
					k_div.append(p_div)
					p_div.innerHTML += marked(it.paragraphContent)


					/*更改及测试 */
					p_div.className = "pdiv"; //创建css类

					let p_divSub1 = document.createElement('div'); //新建子dom节点div（收藏）
					p_div.append(p_divSub1);
					p_divSub1.className = "SubpdivC";
					p_divSub1.innerText = "收藏";

					let p_divSub2 = document.createElement('div'); //新建子dom节点div（笔记）
					p_div.append(p_divSub2);
					p_divSub2.className = "SubpdivN";
					p_divSub2.innerText = "笔记";
					//p_div.dataset.balloonData="收藏|笔记";
					//console.log(p_div.dataset);


					//TODO添加点击事件监听
					p_divSub1.addEventListener("mousedown", function() {
						//console.log("按下了收藏");
						collectionF(this.parentElement.id)
						

					});
                    //为点击笔记添加点击监听
 					p_divSub2.addEventListener("mousedown", function() {
						console.log("按下了笔记");
						console.log(this.parentElement.id);
						// popLayer.style.display="block";
						
						var self = this;
						//var baseText = null;

						console.log(self.parentElement.children[1]);
						console.log(self.parentElement.id);
						//let popUp = self.parentElement.children[1];
						// popUp.style.visibility = "visible";
						//popUp.style.shadow="10px 10px 5px #888888";
						//if (baseText == null) baseText = popUp.innerHTML;
						if(!document.getElementById(self.parentElement.id+'_notes')){
							createNote(self.parentElement.id,saveFunc)
							}	
						else{
							document.getElementById(self.parentElement.id+'_notes').style.visibility="visible";
						}
					    
					});
				
                    
				});
			}
		})
	}
};

function createNote(id,saveF,content) {
	$('#'+id).after('<textarea class="input_notes" id='+id+'_notes></textarea>');	
	    //('#'+self.parentElement.id).after(inputNote);
	$('#'+id+'_notes').after('<button class="savebutton" id='+id+'_savebutton>保存</button>');	
	
	document.getElementById(id+'_savebutton').addEventListener('mousedown',function(event){
		saveF(id,document.getElementById(id+'_notes').value);
		this.style.visibility='hidden';
	});
	document.getElementById(id+'_notes').addEventListener('blur',function(event){
		console.log("你失焦了");
		//alert("你失焦了");
		//失焦后自动保存
		this.style.background="#ffffff";
		saveF(id,document.getElementById(id+'_notes').value);
		if(this.value===''){
			this.style.visibility="hidden";
			
		}
		document.getElementById(id+'_savebutton').style.visibility="hidden";
		//console.log(self.parentElement);			    								
	});
	
	document.getElementById(id+'_notes').addEventListener('focus',function(){
		//this.contentEditable=true;
	    this.style.background="#E1F5FE";
		document.getElementById(id+'_savebutton').style.visibility="visible";
	});
	//如果参数数目为3
	if (arguments.length==3){
		//input value =content
		document.getElementById(id+'_notes').value=content;
	}else{
		
	}
}
