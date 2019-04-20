
/**
 * 依赖
 * ./marked.min.js
 * 使用方法：
 * load_paragraph(data,root_id)
 * @param data: json
 * @param root_id:string, 为已存在的div的id，作为根div
 * 即可渲染组件
 */




(function(){
    var script = document.createElement("script");
    script.type = "text/javascript";
    script.src = "./js/marked.min.js";
    document.body.appendChild(script);
})()


function load_paragraph(json_data,root_id) {
        
    let section_div = document.createElement('div');
    section_div.id = json_data.sid;
    document.getElementById(root_id).append(section_div);
    section_div.innerHTML = marked(json_data.sectionName);
    let knowledges = json_data['knowledges']
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
                    p_div.id = it.pid;
                    k_div.append(p_div)
                    p_div.innerHTML += marked(it.paragraphContent)
                })
            }
        })
    }

}
// load_paragraph(section)