package com.zhiku.service;

import com.zhiku.entity.Course;
import com.zhiku.mapper.CourseMapper;
import com.zhiku.mapper.KnowledgeMapper;
import com.zhiku.mapper.ParagraphMapper;
import com.zhiku.mapper.SectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.zhiku.util.md2Database.Md2Save;

import java.io.File;
import java.io.IOException;

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
            path=rootFPath+filename.substring(0,filename.lastIndexOf('.'))+System.currentTimeMillis()+"."+suffix;
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
        return Md2Save.toolRun(filePath,cid);
    }

    public boolean deleteCourse(Integer cid) {
        paragraphMapper.deleteBySeqCourse( cid );
        knowledgeMapper.deleteBySeqCourse( cid );
        sectionMapper.deleteByCourseId( cid );
        int re=courseMapper.deleteByPrimaryKey( cid );
        return re != 0;
    }
}
