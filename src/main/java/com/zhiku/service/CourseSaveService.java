package com.zhiku.service;

import com.zhiku.entity.Course;
import com.zhiku.entity.mongodb.Child;
import com.zhiku.entity.mongodb.Index;
import com.zhiku.entity.mongodb.Paragraph;
import com.zhiku.mongo.ContentTemplate;
import com.zhiku.mongo.CourseTemplate;
import com.zhiku.mongo.IndexTemplate;
import com.zhiku.util.ChildUtil;
import com.zhiku.util.SmallTools;
import com.zhiku.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.zhiku.util.md2Database.Md2Save;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class CourseSaveService {
    @Value("md|txt")
    private String type_reg;
    @Value("${file_path.md_save}")
    private String rootFPath;
    @Autowired
    private CourseTemplate courseTemplate;
    @Autowired
    private IndexTemplate indexTemplate;
    @Autowired
    private ContentTemplate contentTemplate;

    public boolean checkFile(MultipartFile file){
        boolean re=false;
        if(file!=null)
        {
            String filename = file.getOriginalFilename();
            assert filename != null;
            String suffix = filename.substring(filename.lastIndexOf('.')+1);
            if(suffix.matches(type_reg)){
                re=true;
            }
        }
        return re;
    }

    public String saveFile(MultipartFile file) throws IOException {
        String path=null;
        if(file!=null) {
            //如果文件夹不存在则创建
            File dir =new File(rootFPath);
            if  (!dir .exists()&&!dir .isDirectory()) {
                dir .mkdirs();
            }
            String filename = file.getOriginalFilename();
            assert filename != null;
            String suffix = filename.substring(filename.lastIndexOf('.')+1);
            path=rootFPath+File.separator+filename.substring(0,filename.lastIndexOf('.'))+"-"+ SmallTools.uuid()+"."+suffix;
            //上传
            file.transferTo(new File(path));
        }
        return path;
    }

    public Course createCourse(String title,String vid,String describe,String iconPath) {
        Course course=new Course();
        course.setCourseName( title );
        course.setCourseDesc( describe );
        course.setCourseIcon( iconPath );
        course.setVid( vid );
        courseTemplate.insert(course);
        return course;
    }

    public String update(Integer cid,String title,String vid,String describe,String iconPath) {
        if(courseTemplate.findByPrimaryKey(cid)==null)
            return "没有这个课程";
        Course course=new Course();
        course.setCid( cid );
        course.setCourseName( title );
        course.setCourseDesc( describe );
        course.setCourseIcon( iconPath );
        course.setVid( vid );
        courseTemplate.updateByPrimaryKey(course);
        return null;
    }

    /**
     * 储存课程内容
     * 适用于添加新课程内容，增加内容，替换内容
     */
    public String saveContent(String filePath,int cid,String vid,List<ChapterProgressView> seqs) throws IOException {
        List<Child> childList=new ArrayList<>(  );
        List<Paragraph> paragraphs=new ArrayList<>(  );
        //存入实体类中
        String errorStr=Md2Save.md2Entitys( filePath,childList,paragraphs );
        if(errorStr!=null)
            return errorStr;
        //确保结构严格，没有章添加空章
        if(childList.get( 0 ).getLevel()!=1){
            Child chapter=new Child();
            if(seqs==null||seqs.size()==0||seqs.get( 0 ).getChapter()==null)
                return "解析失败，不确定章号";
            chapter.setSection_seq( seqs.get( 0 ).getChapter() );
            chapter.setLevel( 1 );
            chapter.setSection_name( null );//空掌以标题为null标识
            chapter.setSub( childList );
            childList=new ArrayList<>(  );
            childList.add( chapter );
        }
        //修改序列
        if(seqs!=null&&seqs.size()>0){
            List<Child> tempList=new ArrayList<>(  );//不在seqs中的要移除
            int i=-1;
            for(Child child:childList){
                if(i>=(seqs.size()-1))
                   break;
                if(child.getLevel()!=1)//第一层只存章
                    continue;
                tempList.add( child );
                ChapterProgressView chapterProgressView=seqs.get( ++i );
                child.setSection_seq( chapterProgressView.getChapter() );
                List<Integer> secseq=chapterProgressView.getSections();
                List<Child> sections=child.getSub();
                if(secseq!=null&&secseq.size()>0&&sections!=null){//否则不用改变原顺序
                    int j=-1;
                    List<Child> tempSections=new ArrayList<>(  );
                    for (Child section:sections){
                        if(j>=(secseq.size()-1))
                            break;
                        tempSections.add( section );
                        if(section.getLevel()!=2) {//节
                            continue;
                        }
                        section.setSection_seq( secseq.get(++j) );
                    }
                    child.setSub( tempSections );
                }

            }
            childList=tempList;
        }
        if(childList.size()==0)
            return "格式错误，文件中没有章或节";
        //查询目录
        if(vid==null){
            Course course=courseTemplate.findByPrimaryKey(cid);
            if(course==null)
                return "课程不存在";
            vid=course.getVid();
        }
        Index index=indexTemplate.findByPrimaryKey(cid,vid);
        if(index==null) {//新课程，没有目录，创建新目录
            index = new Index();
            index.setCid( cid );
            index.setVid( vid );
            index.setCatalog( new ArrayList<>(  ) );
        }

        //合并目录
        index.setCatalog(ChildUtil.merge(index.getCatalog(),childList));
        //空章标题为null，改为""
        for(Child child:index.getCatalog()){
            if(child.getSection_name(  )==null)
                child.setSection_name( "" );
        }
        //储存//新加入的节和知识点id均为负
        Map<Integer,Integer> kid2xkid=indexTemplate.upset(index);
        List<Paragraph> paragraphsx=new ArrayList<>(  );
        for(Paragraph paragraph:paragraphs){
            if(kid2xkid.get( paragraph.getParagraphKnowledge() )!=null){
                paragraph.setParagraphKnowledge(kid2xkid.get( paragraph.getParagraphKnowledge() ) );
                paragraphsx.add( paragraph );
            }
        }
        contentTemplate.instertAll(paragraphsx);

        return null;
    }

    public void deleteCourse(Integer cid) {
        courseTemplate.deleteByCid(cid);
    }

    public void delete(Integer cid,String vid,List<ChapterProgressView> seqs) {
        if(seqs==null||seqs.size()==0){
            indexTemplate.deleteByPrimaryKey(cid,vid);
        }else{
            int maxCata=0,maxSec=0;
            for(ChapterProgressView chapterProgressView:seqs) {
                if (chapterProgressView.getChapter() > maxCata) {
                    maxCata = chapterProgressView.getChapter();
                }
                if(chapterProgressView.getSections()!=null&&chapterProgressView.getSections().size()>maxSec){
                    maxSec=chapterProgressView.getSections().size();
                }
            }
            boolean[] removeChapter=new boolean[maxCata+1];
            Boolean[][] removeSection=new Boolean[maxCata+1][maxSec+1];
            for(ChapterProgressView chapterProgressView:seqs){
                if(chapterProgressView.getSections()==null||chapterProgressView.getSections().size()==0) {
                    removeChapter[chapterProgressView.getChapter()] = true;
                }else{
                    for(Integer i:chapterProgressView.getSections()){
                        removeSection[chapterProgressView.getChapter()][i]=true;
                    }
                }
            }
            Index index=indexTemplate.findByPrimaryKey( cid,vid );
            List<Child> xchapters=new ArrayList<>(  );
            for(Child chapter:index.getCatalog(  )){
                if(chapter.getLevel()!=2)
                    continue;
                if(chapter.getSection_seq()<=maxCata){
                    if(removeChapter[chapter.getSection_seq()])//整章删除
                        continue;
                    List<Child> xsectionx=new ArrayList<>(  );
                    for(Child section:chapter.getSub()){
                        if(section.getSection_seq()<maxSec&&!removeSection[chapter.getSection_seq()][section.getSection_seq()]){
                            xsectionx.add( section );
                        }
                    }
                    if (xsectionx.size()>0){
                        chapter.setSub( xsectionx );
                        xchapters.add( chapter );
                    }
                }
                xchapters.add( chapter );
            }
            if(xchapters.size()>0){
                index.setCatalog( xchapters );
                indexTemplate.upset( index );
            }else{
                indexTemplate.deleteByPrimaryKey(cid,vid);
            }
        }
        Course course=courseTemplate.findByPrimaryKey( cid );
        if(course.getVid().equals( vid )){//课程版本要换
            List<String> vids=courseTemplate.getVids( cid );
            if(!vids.get( vids.size()-1 ).equals( vid )){
                course.setVid( vids.get( vids.size()-1 ) );
                courseTemplate.upset( course );
            }else if(vids.size()==1){
                courseTemplate.deleteByCid( cid );
            }else{
                course.setVid( vids.get( vids.size()-2 ) );
                courseTemplate.upset( course );
            }
        }
    }

    public CourseView preview(String filePath, Map<String,SectionContentView> sectionViewMap) throws IOException {
        List<Child> chapterList=new ArrayList<>(  );
        List<Paragraph> paragraphs=new ArrayList<>(  );
        //存入实体类中
        String errorStr=Md2Save.md2Entitys( filePath,chapterList,paragraphs );
        if(errorStr!=null)
            return null;
        if(chapterList.get( 0 ).getLevel()!=1){//确保结构严格，没有章
            Child chapter=new Child();
            chapter.setSection_seq( 0 );
            chapter.setLevel( 1 );
            chapter.setSection_name( "" );
            chapter.setSub( chapterList );
            chapterList=new ArrayList<>(  );
            chapterList.add( chapter );
        }
        Course course=new Course();
        course.setCid( 0 );
        course.setVid( "V" );
        course.setCourseName( "内容效果预览" );
        Index index=new Index();
        index.setCid( 0 );
        index.setVid( "V" );
        index.setCatalog( chapterList );
        CourseView courseView1= IndexTemplate.index2CourseView(index,course);
        //生成节视图对应
        int maxKid= ChildUtil.maxSid(chapterList,3);
        int minKid= ChildUtil.minSid(chapterList,3);
        KnowledgeView[] knowledgeViews=new KnowledgeView[maxKid-minKid+1];//储存地址，便于找到
        //创建KnowledgeView，SectionContentView
        for(Child child1:chapterList){//章
            if(child1==null||child1.getSub()==null)
                continue;
            for(Child child2:child1.getSub()){//节
                if(child2==null||child2.getSub()==null)
                    continue;
                List<KnowledgeView> knowledgeViewList=new ArrayList<>(  );
                for(Child child3:child2.getSub()){//知识点
                    if(child3==null)
                        continue;
                    knowledgeViews[child3.getSid()-minKid]=child2KnowledgeView(child3,new ArrayList<>(  ) );
                    knowledgeViewList.add( knowledgeViews[child3.getSid()-minKid] );
                }
                sectionViewMap.put( ""+child2.getSid(),child2SectionCView(child2, knowledgeViewList ,0) );
            }
        }
        //填装段落
        for(Paragraph paragraph:paragraphs) {
            if(paragraph.getParagraphKnowledge()>maxKid)
                continue;
//            paragraph.setPid( new ObjectId(  ) );
            knowledgeViews[paragraph.getParagraphKnowledge()-minKid].getParagraphs().add(paragraph);
        }

        return courseView1;
//
//
//        //通过md2Entitys，三个实体列表都是按seq从小到大的顺序的，可以直接拼装
//        int oldSid=0,oldKid=0;
//        int sSeq=Integer.parseInt(sections.get(oldSid).getSectionSeq());
//        int kSeq=knowledges.get(oldKid).getKnowledgeSeq();
//        List<KnowledgeView> knowledgeViews=new ArrayList<>(  );
//        List<Paragraph> paragraphs1=new ArrayList<>(  );
//        for(;sSeq<kSeq/100;){
//            sectionViewMap.put( sections.get( oldSid ).getSid(),null );
//            ++oldSid;
//            sSeq=Integer.parseInt(sections.get( oldSid ).getSectionSeq());
//        }
//        for(Paragraph paragraph:paragraphs){
//            int pSeq=paragraph.getParagraphSeq();
//            int pKSeq=pSeq/1000;
//            if(pKSeq>kSeq){//新知识点
//                //存旧知识点视图
//                knowledgeViews.add( saveKView( knowledges.get( oldKid ),paragraphs1 ) );
//                paragraphs1=new ArrayList<>();
//                ++oldKid;
//                kSeq=knowledges.get( oldKid ).getKnowledgeSeq();
//
//                int kSSeq=kSeq/100;
//                if(kSSeq>sSeq){//新节
//                    //存节视图
//                    Section section=sections.get( oldSid );
//                    sectionViewMap.put( section.getSid(),saveSView( section,knowledgeViews ) );
//                    knowledgeViews=new ArrayList<>(  );
//                    ++oldSid;
//                    sSeq=Integer.parseInt(sections.get( oldSid ).getSectionSeq());
//                    //如果有无知识点节
//                    while (kSSeq>sSeq){
//                        sectionViewMap.put( sections.get( oldSid ).getSid(),null );
//                        ++oldSid;
//                        sSeq=Integer.parseInt(sections.get( oldSid ).getSectionSeq());
//                    }
//                }
//
//                //如果有无段落知识点
//                while (pKSeq>kSeq){
//                    knowledgeViews.add( saveKView( knowledges.get( oldKid ),null ) );
//                    oldKid+=1;
//                    kSeq=knowledges.get( oldKid ).getKnowledgeSeq();
//                    int kSSeq1=kSeq/100;
//                    if(kSSeq1>sSeq){//新节
//                        //存节视图
//                        Section section=sections.get( oldSid );
//                        sectionViewMap.put( section.getSid(),saveSView( section,knowledgeViews ) );
//                        knowledgeViews=new ArrayList<>(  );
//                        ++oldSid;
//                        sSeq=Integer.parseInt(section.getSectionSeq());
//                        //如果有无知识点节
//                        while (kSSeq1>sSeq){
//                            Section section1=sections.get( oldSid );
//                            sectionViewMap.put( section1.getSid(),null );
//                            ++oldSid;
//                            sSeq=Integer.parseInt(section1.getSectionSeq());
//                        }
//                    }
//                }
//            }
//            paragraphs1.add( paragraph );
//        }
//        if(paragraphs1.size()>0){
//            knowledgeViews.add( saveKView( knowledges.get( oldKid ),paragraphs1 ) );
//        }
//        if(knowledgeViews.size()>0){
//            Section section=sections.get( oldSid );
//            sectionViewMap.put( section.getSid(),saveSView( section,knowledgeViews ) );
//        }
//
//        courseView.setSections( sections );
//        courseView.setCid( 1 );
//        courseView.setCourseName( "内容效果预览" );
    }

    private KnowledgeView child2KnowledgeView(Child child,List<Paragraph> paragraphs1){
        KnowledgeView knowledgeView=new KnowledgeView();
        knowledgeView.setKid( child.getSid() );
        knowledgeView.setKnowledgeSeq( child.getSection_seq() );
        knowledgeView.setKnowledgeSection( child.getSid() );
        knowledgeView.setKnowledgeName( child.getSection_name() );
        knowledgeView.setParagraphs( paragraphs1);
        return knowledgeView;
    }

    private SectionContentView child2SectionCView(Child child, List<KnowledgeView> knowledgeViews,Integer cid){
        SectionContentView sectionView=new SectionContentView();
        sectionView.setKnowledgeViews( knowledgeViews );
        sectionView.setSid( child.getSid() );
        sectionView.setSectionCourse( cid );
        sectionView.setSectionName( child.getSection_name()  );
        sectionView.setSectionSeq( child.getSection_seq());
        return sectionView;
    }

    public List<ChapterProgressView> getProgress(Integer cid,String vid){
        if(vid==null){
            //查询出课程
            Course course=courseTemplate.findByPrimaryKey( cid );
            if (course==null)
                return null;
            vid=course.getVid();
        }
        Index index=indexTemplate.getCatalog(cid,vid);
        if (index==null)
            return null;
        //记录序号
        List<ChapterProgressView> chapterProgressViews=new ArrayList<>(  );

        List<Child> chapters=index.getCatalog();
        for(Child chapter:chapters){
            if(chapter==null||chapter.getLevel()!=1)//第一层不是章，没有上级章无法记录
                continue;
            ChapterProgressView chapterProgressView=new ChapterProgressView();
            chapterProgressView.setChapter( chapter.getSection_seq() );
            chapterProgressView.setSections( new ArrayList<>(  ) );
            //节
            List<Child> sections=chapter.getSub();
            if(sections!=null){
                for (Child section:sections){
                    if (section==null||section.getLevel()!=2)
                        continue;
                    chapterProgressView.getSections().add( section.getSection_seq() );
                }
            }
            chapterProgressViews.add( chapterProgressView );
        }
        return chapterProgressViews;
    }

//    //查重
//    private boolean duplicateChecking(Integer courseID,List<Section> sections){
//        int secseq=sectionMapper.selectSectionMaxID(courseID);
//        if(secseq==0||sections==null){
//            return false;
//        }else{//查重
//            for(Section section:sections){
//                if(section!=null&&section.getSectionName()!=null
//                        &&sectionMapper.selectSectionID(section.getSectionName(),courseID)!=0){
//                    return true;
//                }
//            }
//        }
//        return false;
//    }


}
