package com.zhiku.service;

import com.zhiku.entity.mongodb.Child;
import com.zhiku.entity.mongodb.Index;
import com.zhiku.entity.mongodb.Paragraph;
import com.zhiku.mongo.ContentTemplate;
import com.zhiku.mongo.IndexTemplate;
import com.zhiku.view.CourseView;
import com.zhiku.view.KnowledgeView;
import com.zhiku.view.SectionContentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class IndexService {
    @Autowired
    private IndexTemplate indexTemplate;
    @Autowired
    private ContentTemplate contentTemplate;

    public CourseView getLeftIndex(int cid,String vid){
        CourseView courseView = indexTemplate.getLeftIndex2(cid,vid);
        return courseView;
    }

    public SectionContentView getSecondLevelIndex(int sid){
        SectionContentView sectionContentView = indexTemplate.getSectionContent(sid);
        return sectionContentView;
    }

    public SectionContentView getSecondLevelIndex2(int sid,Integer cid,String vid){
        Index index=indexTemplate.getSectionIndexBySid(sid,cid,vid );
        if(index==null)
            return null;
        Child deschild=index.getCatalog().get( 0 );
        //设置好需要的变量
        SectionContentView sectionContentView = new SectionContentView();
        List<KnowledgeView> knowledgeViews = new ArrayList<>();
        //对deschild进行处理
        sectionContentView.setSid(deschild.getSid());
        sectionContentView.setSectionName(deschild.getSection_name());
        sectionContentView.setSectionSeq(deschild.getSection_seq());
        sectionContentView.setSectionCourse(index.getCid());

        List<Child> children = deschild.getSub();//知识点列表
        if(children == null){
            return sectionContentView;
        }
        //查询段落
        //kid按seq排序
        Collections.sort( children, new Comparator<Child>() {
            @Override
            public int compare(Child o1, Child o2) {
                return o1.getSection_seq()-o2.getSection_seq();
            }
        });

        List<Integer> kids=new ArrayList<>(  );
        for(Child c1 : children){
            kids.add(c1.getSid());
        }

        List<Paragraph> paragraphs=contentTemplate.findBySortKids(kids);//按kids，

        for(Child c1 : children){
            KnowledgeView knowledgeView = new KnowledgeView();
            knowledgeView.setKid(c1.getSid());
            knowledgeView.setKnowledgeSeq(c1.getSection_seq());
            knowledgeView.setKnowledgeName(c1.getSection_name());
            knowledgeView.setKnowledgeSection(c1.getSid());
            //把knowledgeView装到knowledgeViews里面
            List<Paragraph> paragraphs1=new ArrayList<>(  );
            knowledgeView.setParagraphs( paragraphs1 );
            knowledgeViews.add(knowledgeView);
        }
        int i=0;
        for (Paragraph paragraph:paragraphs){
            if(i==knowledgeViews.size())
                break;
            KnowledgeView knowledgeView=knowledgeViews.get(i);
            while (++i<knowledgeViews.size()&&knowledgeView.getKid()!=paragraph.getParagraphKnowledge() ){
                knowledgeView=knowledgeViews.get(i);
            }
            knowledgeView.getParagraphs().add( paragraph );
        }
        //TODO:知识点列表已排序，段落缺少按seq排序

        sectionContentView.setKnowledgeViews(knowledgeViews);
        return sectionContentView;
    }

}
