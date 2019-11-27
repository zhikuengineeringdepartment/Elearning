package com.zhiku.mongo;

import com.zhiku.entity.Child;
import com.zhiku.entity.Course;
import com.zhiku.entity.Index;
import com.zhiku.entity.Paragraph;
import com.zhiku.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static org.springframework.data.mongodb.core.query.Query.query;

@Component
public class IndexTemplate {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 获取指定课程的左栏索引（修复前端需要处理逻辑的问题）
     * @param cid
     * @return courseview
     */
    public CourseView getLeftIndex(int cid){
        //查询出课程号
        Criteria classinfo = Criteria.where("cid").is(cid);
        List<Course> courses = mongoTemplate.find(query(classinfo), Course.class);
        Course course = courses.get(0);
        //查询出对应课程号的一级索引
        Criteria index = Criteria.where("cid").is(cid);
        List<Index> indexlist = mongoTemplate.find(query(index), Index.class);
        Index i = indexlist.get(0);//修改表结构之后，indexlist里应该只有一条index记录
        List<SectionView> sections = new ArrayList<SectionView>();
        List<Child> children = i.getCatalog();
        int ind = 0;
        for(Child c : children){
            List<SubView> sub = new ArrayList<SubView>();
            SectionView sec = new SectionView();
            //获取catalog中的第一层的一个child结构
            //进行相应的处理，把数据装进SectionView里面
            sec.setTitle(c.getSection_name().substring(2,5));
            sec.setIndex(ind);
            ind++;
            //把当前这个child的内容装进subview里，把subview装到sub里面
            SubView subView = new SubView();
            subView.setSid(c.getSid());
            subView.setSectionName(c.getSection_name());
            subView.setSectionSeq(c.getSection_seq());
            subView.setSectionCourse(cid);
            sub.add(subView);
            List<Child> children1 = c.getSub();
            //如果这个child结构下面还有嵌套的child结构，则取出第二层
            if( children1 != null){
                for(Child c1 : children1){
                    //进行相应的处理
                    //把当前这个child的内容装进subview里，把subview装到sub里面
                    SubView subView1 = new SubView();
                    subView1.setSid(c1.getSid());
                    subView1.setSectionName(c1.getSection_name());
                    subView1.setSectionSeq(c1.getSection_seq());
                    subView1.setSectionCourse(cid);
                    sub.add(subView1);
                }
            }
            //把sub装到sec里面
            sec.setSub(sub);
            //把sec装到sections里面
            sections.add(sec);
        }
        //System.out.println(sections);
        CourseView courseView = new CourseView();
        courseView.setSections(sections);
        courseView.setCid(course.getCid());
        courseView.setCourseName(course.getCourseName());
        courseView.setCourseDesc(course.getCourseDesc());
        courseView.setCourseIcon(course.getCourseIcon());
        return courseView;
    }

    /**
     * 获取指定节的全部内容
     * @param sid
     * @return
     */
    public SectionContentView getSectionContent(int sid){
        int cid = Integer.parseInt(Integer.toString(sid).substring(0,3));
        Criteria sectioninfo = Criteria.where("cid").is(cid);
        List<Index> indexlist = mongoTemplate.find(query(sectioninfo), Index.class);
        Index i = indexlist.get(0);//修改表结构之后，indexlist里应该只有一条index记录
        //在这条index记录里查找需要的节
        List<Child> children = i.getCatalog();
        ListIterator<Child> iter = children.listIterator();
        Child deschild = iter.next();
        if(iter.hasNext()){
            do{
                deschild = iter.next();
            }
            while(iter.hasNext() && deschild.getSid() != sid);
        }
        //这时候deschild就是目标节
        //设置好需要的变量
        SectionContentView sectionContentView = new SectionContentView();
        List<KnowledgeView> knowledgeViews = new ArrayList<>();
        //对deschild进行处理
        sectionContentView.setSid(deschild.getSid());
        sectionContentView.setSectionName(deschild.getSection_name());
        sectionContentView.setSectionSeq(deschild.getSection_seq());
        sectionContentView.setSectionCourse(cid);

        List<Child> children1 = deschild.getSub();
        if(children1 != null){
            for(Child c1 : children1){
                List<Child> children2 = c1.getSub();
                if(children2 != null){
                    for(Child c2 : children2){
                        KnowledgeView knowledgeView = new KnowledgeView();
                        knowledgeView.setKid(c2.getSid());
                        knowledgeView.setKnowledgeSeq(Integer.parseInt(c2.getSection_seq()));
                        knowledgeView.setKnowledgeName(c2.getSection_name());
                        knowledgeView.setKnowledgeSection(c1.getSid());
                        //查找content表，找到本节对应的所有段落
                        Criteria paragraphinfo = Criteria.where("paragraph_knowledge").is(c2.getSid());
                        List<Paragraph> paragraphs = mongoTemplate.find(query(paragraphinfo), Paragraph.class);
                        knowledgeView.setParagraphs(paragraphs);

                        //把knowledgeView装到knowledgeViews里面
                        knowledgeViews.add(knowledgeView);
                    }
                }
            }
        }
        sectionContentView.setKnowledgeViews(knowledgeViews);
        return sectionContentView;
    }
}

