package com.zhiku.util.md2Database;

import com.zhiku.entity.Knowledge;
import com.zhiku.entity.Paragraph;
import com.zhiku.entity.Section;
import com.zhiku.mapper.KnowledgeMapper;
import com.zhiku.mapper.ParagraphMapper;
import com.zhiku.mapper.SectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Service
public class Md2Save {
    /***********i|type|char*******************
     * |0|#|21|
     * |1|##|22|
     * |2|###|23|
     * |3|图片|'I'|
     * |4|表格|'T'|
     * |5|列表|'L'|
     * |6|脚注|60|
     * |7|换行文本|'P'|
     * |8|不换行文本|72|
     * |9|代码块|'C'|
     * content==null表示在其他段中合并储存
     */
    @Autowired
    private SectionMapper sectionMapper;
    @Autowired
    private KnowledgeMapper knowledgeMapper;
    @Autowired
    private ParagraphMapper paragraphMapper;

    public static final  char[] type;
    static {
        type = new char[]{21, 22, 23, 'I', 'T', 'L', 60, 'P', 72, 'C'};
    }

    private static Md2Save md2pagUtils;
    @PostConstruct
    public void init() {
        md2pagUtils = this;
    }

    /**
     * 调用该方法储存md文件
     * 注：多线程同时调用是，尽管其他变量均在方法内创建，但sectionMapper等是注入的全局变量，可能会有干扰，也可能不会，需要测试
     * @param filePath 文件完整路径
     * @param courseID 课程id
     * @return 错误码 为null表示没有错
     * @throws IOException _
     */
    public static String toolRun(String filePath,Integer courseID) throws IOException {
        int courseSeq=courseID;
        //读取文件
        FileUtils fu = new FileUtils();
        ArrayList<String> strArr = fu.read2Array(filePath, "UTF-8");
        ArrayList<tempParagraph> saveP=new ArrayList<tempParagraph>( );
        String errorstr;
        //识别表格、列表、标题、图片、代码块
        errorstr=findType( strArr,saveP );
        if(errorstr!=null){
            return errorstr;
        }

//        //test输出识别结果
//        for(tempParagraph tp:saveP){
//            if(tp.getContent()==null){
//            }else if(tp.getType()==type[0]){
//                System.out.print( "\n"+tp.id+":<标题1>:"+tp.getContent());
//            }else if(tp.getType()==type[1]){
//                System.out.print( "\n"+tp.id+":<标题2>:"+tp.getContent());
//            }else if(tp.getType()==type[2]){
//                System.out.print( "\n"+tp.id+":<标题3>:"+tp.getContent());
//            }else if(tp.getType()==type[3]){
//                System.out.print( "\n"+tp.id+":<图片>:"+tp.getContent());
//            }else if(tp.getType()==type[4]){
//                System.out.print( "\n"+tp.id+":<表格>:"+tp.getContent());
//            }else if(tp.getType()==type[5]){
//                System.out.print( "\n"+tp.id+":<列表>:"+tp.getContent());
//            }else if(tp.getType()==type[7]){
//                System.out.print( "\n"+tp.id+":<段落>:"+tp.getContent());
//            }else if(tp.getType()==type[6]){//脚注
//                System.out.print( "[<"+tp.getContent()+">]");
//            }else if(tp.getType()==type[8]){//不换行
//                System.out.print(tp.getContent());
//            }else if(tp.getType()==type[9]){
//                System.out.print( "\n"+tp.id+":<代码块>:"+tp.getContent());
//            }
//        }

        //储存
        errorstr=saveParagraph(saveP,courseID,courseSeq);
        if(errorstr!=null){
            return errorstr;
        }

        return null;//返回null表示没有问题
    }
    //按CSDN编辑器的标准判断，不一定符合所有的morkdown标准
    private static String findType(ArrayList<String> strArr, ArrayList<tempParagraph> saveP){
        int jid=0;//行号
        boolean islist=false,istable=false,iscode=false,
                isendList=false,//普通段前一行为空或由+-*与空格交替存在为列表的最后一行
                isstartTable=false;//表格是否开始，任何表格不连贯都判断为表格结束，所以不用记表格是否到了最后一行
        //标题对应的标志
        String title_1 = "# ",title_2 = "## ",title_3 = "### ",image="![",code="```";

        ArrayList<String> strA2=new ArrayList<String> ();
        int strArrLength=strArr.size();
        for(jid=1;jid<=strArrLength;jid++){
            String str0=strArr.get( jid-1 );
            String strp=str0.trim();
            if(strp.equals( "" )){//按照CSDN编辑器，空行也能终结表格
                if(islist){//新规则，列表遇空行一定终结
                    strA2.add(str0);//空行对列表显示也有影响
                    saveTabListCode(strA2,saveP, Md2Save.type[5],jid);
                    islist=false;
                }else if(istable){
                    saveTabListCode(strA2,saveP, Md2Save.type[4],jid);
                    istable=false;
                    isstartTable=false;
                }
                isendList=true;
                continue;
            }
            //判断是否是+-*与空格交替存在
            boolean tempb=true;
            String[] list_str=strp.split( " " );
            for (String tstr:list_str){//不是空行没有纯空格，分割后一定有非空字符串集
                if(!tstr.equals("")&&!tstr.equals("+")&&!tstr.equals("-")&&!tstr.equals("*")){
                    tempb=false;
                }
            }
            if(tempb){//可能只有一个+,所以不能放到列表内部判断，必须提前判断
                if(istable){
                    saveTabListCode(strA2,saveP, Md2Save.type[4],jid);
                    istable=false;isstartTable=false;
                }
                strA2.add(str0);//不是空行应该保存
                islist=true;
                isendList=true;
            }else if(strp.startsWith(code)){//代码块
                if(istable){
                    saveTabListCode(strA2,saveP, Md2Save.type[4],jid);
                    istable=false;isstartTable=false;
                }else if(islist){
                    saveTabListCode(strA2,saveP, Md2Save.type[5],jid);
                    islist=false;
                }
                strA2.add(str0);
                if(iscode){
                    saveTabListCode(strA2,saveP, Md2Save.type[9],jid);
                    iscode=false;
                }else{
                    iscode=true;
                }
            }else if(iscode){//如果不是代码块终结符，任何字符串都是代码，其他类型不需要检测
                strA2.add(str0);
            }else if (strp.startsWith(title_3)) {//知识点
                if(istable){
                    saveTabListCode(strA2,saveP, Md2Save.type[4],jid);
                    istable=false;
                    isstartTable=false;
                }else if(islist){
                    saveTabListCode(strA2,saveP, Md2Save.type[5],jid);
                    islist=false;
                }
                saveP.add(new tempParagraph(jid, Md2Save.type[2],str0));

            }else if(strp.startsWith(title_2)){//节##
                if(istable){
                    saveTabListCode(strA2,saveP, Md2Save.type[4],jid);
                    istable=false;
                    isstartTable=false;
                }else if(islist){
                    saveTabListCode(strA2,saveP, Md2Save.type[5],jid);
                    islist=false;
                }
                saveP.add(new tempParagraph(jid, Md2Save.type[1],str0));
            }else if(strp.startsWith(title_1)){//节#
                if(istable){
                    saveTabListCode(strA2,saveP, Md2Save.type[4],jid);
                    istable=false;
                    isstartTable=false;
                }else if(islist){
                    saveTabListCode(strA2,saveP, Md2Save.type[5],jid);
                    islist=false;
                }
                saveP.add(new tempParagraph(jid, Md2Save.type[0],str0));
            }else if((strp.length()>1&&(strp.charAt( 0 )=='+'||strp.charAt( 0 )=='-'||strp.charAt( 0 )=='*')
                    &&strp.charAt( 1 )==' ')||(islist&&!isendList)){//列表,判断顺序不能改，开头不是+-*的也可能是列表,所以先判断列表
                if(istable){
                    saveTabListCode(strA2,saveP, Md2Save.type[4],jid);
                    istable=false;
                    isstartTable=false;
                }
                strA2.add(str0);
                islist=true;
                isendList=false;
            }else if(strp.startsWith(image)){//图片
                if(istable){
                    saveTabListCode(strA2,saveP, Md2Save.type[4],jid);
                    istable=false;
                    isstartTable=false;
                }else if(islist){
                    saveTabListCode(strA2,saveP, Md2Save.type[5],jid);
                    islist=false;
                }
                saveP.add(new tempParagraph(jid, Md2Save.type[3],str0));
            }else if(isstartTable&&isHaveT( strp )){//表格
                if(islist){
                    saveTabListCode(strA2,saveP, Md2Save.type[5],jid);
                    islist=false;
                }
                strA2.add(str0);
                istable=true;
            }else if(jid<strArrLength&&isStartTable(strp,strArr.get( jid ))){//是否即将有表格
                if(islist){
                    saveTabListCode(strA2,saveP, Md2Save.type[5],jid);
                    islist=false;
                }
                strA2.add(str0);
                istable=true;
                isstartTable=true;

            }else{//普通段落,列表遇仅空行结束，普通段落不能判断列表
                if(istable){
                    saveTabListCode(strA2,saveP, Md2Save.type[4],jid);
                }
                istable=false;
                isstartTable=false;
                saveP.add(new tempParagraph(jid, Md2Save.type[7],str0));
            }
//            /////////////////////////////
//            System.out.println( "<<<<<<<<<<<<<<<<<<<<<<<<<");
//            System.out.println( str0 );
//            System.out.println( strArr.get( jid ) );
//
//            System.out.println( "istable:"+istable );
//            System.out.println( "islist:"+islist );
//            System.out.println( "isstartTable:"+isstartTable );
//            System.out.println( "isendList:"+isendList );
//            System.out.println( ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" );
//
//            ///////////////////////////////////////
        }
        if(islist){
            saveTabListCode(strA2,saveP, Md2Save.type[5],jid);
            islist=false;
        }else if(istable){
            saveTabListCode(strA2,saveP, Md2Save.type[4],jid);
            istable=false;
            isstartTable=false;
        }
        return null;
    }

    //是否开始表格
    private static boolean isStartTable(String str1,String str2){
        int t1=0;//第一个字符串'|'的个数
        int l=str1.length();
        for(int i=0;i<l;i++){
            if(str1.charAt( i )=='|')
                t1++;
        }
//        //按照CSDN编辑器，第一行'|'个数不能大于下一行"--"加一,但分割字符串开头为|自动多一个空值
//        if(t1>strs2.length){
//            return false;
//        }
        if(t1==0){//CSDN在表格判断中\|多了就转义，少了不转义，太复杂，这里只判断有一个‘|’就为表格
            return false;
        }

        if(str2.trim().equals( "" ))
            return false;
        String[] strs2=str2.trim().split( "-" );
        if(str2.length()==0)//只有全--才长度为0
            return true;
        for (String str : strs2) {
            if (str.equals( "" ))
                continue;
            int l1 = str.length();
            int j = 0;//记'|'个数
            for (int i = 0; i < l1; i++) {
                if (str.charAt( i ) == '|') {
                    j++;
                } else if (str.charAt( i ) != ' ') {//含非"|"或空格也不是表格
                    return false;
                }
            }
            if (j != 1) {//如果'|'个数小于1也不是表格
                return false;
            }
        }
        return true;
    }

    //字符串中是否包含'|'，根据CSDN编辑器，只要有'|'，不管是不是'\|'转义的，都能继续表格，使用不判断是否转义
    private static boolean isHaveT(String str){
        int l=str.length();
        for(int i=0;i<l;i++){
            if(str.charAt( i )=='|')
                return true;
        }
        return false;
    }

    private static void saveTabListCode(ArrayList<String> strA2, ArrayList<tempParagraph> saveP, char typec, int jid){
        int l=strA2.size();
        if(l<1)
            return;
        StringBuilder str= new StringBuilder( strA2.get( 0 ) );
        for(int i=1;i<l;i++){
            str.append( "\n" ).append( strA2.get( i ) );
        }
        saveP.add(new tempParagraph(jid,typec, str.toString() ));
        strA2.clear();
    }

    private static String saveParagraph(ArrayList<tempParagraph> saveP,Integer courseID,Integer courseSeq){
        List<Section> sections=new ArrayList<>( );
        List<Knowledge> knowledges=new ArrayList<>( );
        List<Paragraph> paragraphs=new ArrayList<>( );
        int ji=0;
        //序号
        int secseq=md2pagUtils.sectionMapper.selectSectionMaxID(courseID);
        if(secseq==0){
            secseq=courseSeq*100;
        }else{//查重
            for(tempParagraph tp:saveP){
                if((tp.getType()==type[1]||tp.getType()==type[0])&&
                        md2pagUtils.sectionMapper.selectSectionID(tp.getContent(),courseID)!=0){
                    return "error:文件内容与数据库中章节(名)有重复！请检测文件或先清空原课程数据";
                }
            }
        }
        int knowseq=0,pagseq=0;
        int secid=-1,knowid=-1;
        int jkids=-1;

        for(tempParagraph tp:saveP){
            if(tp.getContent()==null) {
                continue;
            }
            if(tp.getType()==type[1]||tp.getType()==type[0]){//章或节
                Section section=new Section();
                section.setSid( ++secseq );
                section.setSectionCourse( courseID );
                section.setSectionName( tp.getContent() );
                section.setSectionSeq( ""+secseq );
                section.setSectionRecommendPath( secseq+".txt" );////////////////////
//                md2pagUtils.sectionMapper.insert(section);
                sections.add(section);
                knowseq=0;
                pagseq=0;
                secid=secseq;
                knowid=-1;//上一个知识点id不可使用
            }else if(tp.getType()==type[2]){//知识点
                Knowledge knowledge=new Knowledge();
                knowledge.setKid( null );
                knowledge.setKnowledgeName( tp.getContent());
                knowledge.setKnowledgeSection(secid);
                knowledge.setKnowledgeSeq( secseq*100+(++knowseq) );
//                md2pagUtils.knowledgeMapper.insertGetId(knowledge);
                knowledges.add(knowledge);
                jkids++;
                pagseq=0;
//                knowledge.getKid()//没有赋值kid还报错
                knowid=0;
            }else{//段落
                if(knowid==-1){//这个段落没有知识点,储存节为知识点
                    if(ji==0){//第一段不是标题，无效段落，防止window自动在开头加"?"
                        ji++;
                        continue;
                    }
//                    if(saveP.get(ji-1).getType()==type[1]){
//                        knowledge.setKnowledgeName( "#"+saveP.get(ji-1).getContent());//补成三级标题
//                    }else{
//                        knowledge.setKnowledgeName( "##"+saveP.get(ji-1).getContent());
//                    }
                    Knowledge knowledge=new Knowledge();
                    knowledge.setKid( null );
                    knowledge.setKnowledgeName( "");
                    knowledge.setKnowledgeSection(secid);
                    knowledge.setKnowledgeSeq(  secseq*100+(++knowseq) );
//                    md2pagUtils.knowledgeMapper.insertGetId(knowledge);
                    knowledges.add(knowledge);
                    jkids++;
                    pagseq=0;
                    knowid=0;
                }
                Paragraph paragraph=new Paragraph();
                paragraph.setParagraphType( ""+tp.getType() );
                paragraph.setParagraphContent( tp.getContent() );
                paragraph.setParagraphKnowledge( jkids );//记下是第几号知识点
                paragraph.setParagraphSeq( secseq*100000+knowseq*1000+(++pagseq) );
                paragraph.setParagraphNewline( "y" );
//                md2pagUtils.paragraphMapper.insert( paragraph );
                paragraphs.add(paragraph);
            }
            ji++;
        }
        int re=0;
        //批量存入节
        re=md2pagUtils.sectionMapper.insertAll(sections);
        if(re==0)
            return "插入失败或文件中没有节";
        //批量存入知识点
        re=md2pagUtils.knowledgeMapper.insertAllGetIds( knowledges );
        if(re==0)
            return "插入失败或文件中没有段落";
        int[] kids=new int[jkids+1];
        int j=0;
        //如果数组是从头按顺序储存和遍历的，kid才准确
        for (Knowledge knowledge:knowledges) {
            kids[j]=knowledge.getKid();
            j++;
        }
        //如果传的别名，而非clone，才能改变成功
        for (Paragraph paragraph:paragraphs) {
            paragraph.setParagraphKnowledge( kids[paragraph.getParagraphKnowledge()] );
        }
        re=md2pagUtils.paragraphMapper.insertAll( paragraphs );
        if(re==0)
            return "插入失败或文件中没有段落";

        return null;
    }

//    //
//    public static void main(String args[]) throws IOException {
//        md2Pag.toolRun("E:\\Workbench\\IDEA\\Zhiku_workbench\\写作模板.md",102);
//    }
}
