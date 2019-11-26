package com.zhiku.service;

import com.zhiku.entity.Course;
import com.zhiku.entity.Knowledge;
import com.zhiku.entity.Paragraph;
import com.zhiku.entity.Section;
import com.zhiku.mapper.CourseMapper;
import com.zhiku.mapper.KnowledgeMapper;
import com.zhiku.mapper.ParagraphMapper;
import com.zhiku.mapper.SectionMapper;
import com.zhiku.util.SmallTools;
import com.zhiku.view.CourseView;
import com.zhiku.view.KnowledgeView;
import com.zhiku.view.SectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.zhiku.util.md2Database.Md2Save;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MdSaveService {
    @Value("md|txt")
    private String type_reg;
    @Value("${file_path.md_save}")
    private String rootFPath;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private SectionMapper sectionMapper;
    @Autowired
    private KnowledgeMapper knowledgeMapper;
    @Autowired
    private ParagraphMapper paragraphMapper;

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

    public int createCourse(String title,String describe,String iconPath) throws Exception {
        Course rc=courseMapper.selectByTitleDec(title,describe);
        if(rc!=null){
            throw new Exception("创建课程失败,课程名和描述不能都与现有课程重复!");
        }
        Course course=new Course();
        course.setCourseName( title );
        course.setCourseDesc( describe );
        course.setCourseIcon( iconPath );
        int re=courseMapper.insertGetId( course );
        if(re==0){
            throw new Exception("创建课程失败!");
        }
        return course.getCid();
    }

    public String saveContent(String filePath,int cid) throws IOException {
        List<Section> sections=new ArrayList<>( );
        List<Knowledge> knowledges=new ArrayList<>( );
        List<Paragraph> paragraphs=new ArrayList<>( );
        //存入实体类中
        String errorStr=Md2Save.md2Entitys( filePath,cid,sections,knowledges,paragraphs );
        if(errorStr!=null)
            return errorStr;
        //查重
        if(duplicateChecking(cid,sections)){
            return "error:文件内容与数据库中章节(名)有重复！请检测文件或先清空原课程数据";
        }
        int re=0;
        //批量存入节
        re=sectionMapper.insertAll(sections);
        if(re==0)
            return "插入失败或文件中没有节";
        //批量存入知识点
        re=knowledgeMapper.insertAllGetIds( knowledges );
        if(re==0)
            return "插入失败或文件中没有段落";
        int[] kids=new int[knowledges.size()];
        int j=0;
        //如果数组是从头按顺序储存和遍历的，kid才准确
        for (Knowledge knowledge:knowledges) {
            kids[j]=knowledge.getKid();
            ++j;
        }
        //如果传的别名，而非clone，才能改变成功
        for (Paragraph paragraph:paragraphs) {
            paragraph.setParagraphKnowledge( kids[paragraph.getParagraphKnowledge()] );
        }
        re=paragraphMapper.insertAll( paragraphs );
        if(re==0)
            return "插入失败或文件中没有段落";

        return null;
    }

    public boolean deleteCourse(Integer cid) {
        paragraphMapper.deleteBySeqCourse( cid );
        knowledgeMapper.deleteBySeqCourse( cid );
        sectionMapper.deleteByCourseId( cid );
        int re=courseMapper.deleteByPrimaryKey( cid );
        return re != 0;
    }

    public String preview(String filePath, Map<Integer,SectionView> sectionViewMap, CourseView courseView) throws IOException {
        List<Section> sections=new ArrayList<>( );
        List<Knowledge> knowledges=new ArrayList<>( );
        List<Paragraph> paragraphs=new ArrayList<>( );
        int cid=1;
        //存入实体类中//节id会自动附
        String errorStr=Md2Save.md2Entitys( filePath,cid,sections,knowledges,paragraphs );
        if(errorStr!=null)
            return errorStr;
        //通过md2Entitys，三个实体列表都是按seq从小到大的顺序的，可以直接拼装
        int oldSid=0,oldKid=0;
        int sSeq=Integer.parseInt(sections.get(oldSid).getSectionSeq());
        int kSeq=knowledges.get(oldKid).getKnowledgeSeq();
        List<KnowledgeView> knowledgeViews=new ArrayList<>(  );
        List<Paragraph> paragraphs1=new ArrayList<>(  );
        for(;sSeq<kSeq/100;){
            sectionViewMap.put( sections.get( oldSid ).getSid(),null );
            ++oldSid;
            sSeq=Integer.parseInt(sections.get( oldSid ).getSectionSeq());
        }
        for(Paragraph paragraph:paragraphs){
            int pSeq=paragraph.getParagraphSeq();
            int pKSeq=pSeq/1000;
            if(pKSeq>kSeq){//新知识点
                //存旧知识点视图
                knowledgeViews.add( saveKView( knowledges.get( oldKid ),paragraphs1 ) );
                paragraphs1=new ArrayList<>();
                ++oldKid;
                kSeq=knowledges.get( oldKid ).getKnowledgeSeq();

                int kSSeq=kSeq/100;
                if(kSSeq>sSeq){//新节
                    //存节视图
                    Section section=sections.get( oldSid );
                    sectionViewMap.put( section.getSid(),saveSView( section,knowledgeViews ) );
                    knowledgeViews=new ArrayList<>(  );
                    ++oldSid;
                    sSeq=Integer.parseInt(sections.get( oldSid ).getSectionSeq());
                    //如果有无知识点节
                    while (kSSeq>sSeq){
                        sectionViewMap.put( sections.get( oldSid ).getSid(),null );
                        ++oldSid;
                        sSeq=Integer.parseInt(sections.get( oldSid ).getSectionSeq());
                    }
                }

                //如果有无段落知识点
                while (pKSeq>kSeq){
                    knowledgeViews.add( saveKView( knowledges.get( oldKid ),null ) );
                    oldKid+=1;
                    kSeq=knowledges.get( oldKid ).getKnowledgeSeq();
                    int kSSeq1=kSeq/100;
                    if(kSSeq1>sSeq){//新节
                        //存节视图
                        Section section=sections.get( oldSid );
                        sectionViewMap.put( section.getSid(),saveSView( section,knowledgeViews ) );
                        knowledgeViews=new ArrayList<>(  );
                        ++oldSid;
                        sSeq=Integer.parseInt(section.getSectionSeq());
                        //如果有无知识点节
                        while (kSSeq1>sSeq){
                            Section section1=sections.get( oldSid );
                            sectionViewMap.put( section1.getSid(),null );
                            ++oldSid;
                            sSeq=Integer.parseInt(section1.getSectionSeq());
                        }
                    }
                }
            }
            paragraphs1.add( paragraph );
        }
        if(paragraphs1.size()>0){
            knowledgeViews.add( saveKView( knowledges.get( oldKid ),paragraphs1 ) );
        }
        if(knowledgeViews.size()>0){
            Section section=sections.get( oldSid );
            sectionViewMap.put( section.getSid(),saveSView( section,knowledgeViews ) );
        }

        courseView.setSections( sections );
        courseView.setCid( 1 );
        courseView.setCourseName( "内容效果预览" );

        return null;
    }

    private KnowledgeView saveKView(Knowledge knowledge,List<Paragraph> paragraphs1){
        KnowledgeView knowledgeView=new KnowledgeView();
        knowledgeView.setParagraphs( paragraphs1 );
        knowledgeView.setKid( knowledge.getKid() );
        knowledgeView.setKnowledgeName( knowledge.getKnowledgeName() );
        knowledgeView.setKnowledgeSection( knowledge.getKnowledgeSection() );
        knowledgeView.setKnowledgeSeq( knowledge.getKnowledgeSeq() );
        return knowledgeView;
    }

    private SectionView saveSView(Section section,List<KnowledgeView> knowledgeViews){
        SectionView sectionView=new SectionView();
        sectionView.setKnowledgeViews( knowledgeViews );
        sectionView.setSid( section.getSid() );
        sectionView.setSectionCourse( section.getSectionCourse() );
        sectionView.setSectionName( section.getSectionName() );
        sectionView.setSectionRecommendPath( section.getSectionRecommendPath() );
        sectionView.setSectionSeq( section.getSectionSeq() );
        return sectionView;
    }

    //查重
    private boolean duplicateChecking(Integer courseID,List<Section> sections){
        int secseq=sectionMapper.selectSectionMaxID(courseID);
        if(secseq==0||sections==null){
            return false;
        }else{//查重
            for(Section section:sections){
                if(section!=null&&section.getSectionName()!=null
                        &&sectionMapper.selectSectionID(section.getSectionName(),courseID)!=0){
                    return true;
                }
            }
        }
        return false;
    }
}
