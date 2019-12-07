package com.zhiku.controller.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiku.service.CourseSaveService;
import com.zhiku.util.ResponseData;
import com.zhiku.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;

@CrossOrigin(value = "*")
@Controller
@RequestMapping(value = "/backstage/course")
public class CourseSaveController {
    @Autowired
    CourseSaveService courseSaveService;

    /**
     * 创建课程
//     * @param user 自动获取
     * @param title 课程名称
     * @param vid 版本号
     * @param describe 课程描述
     * @param iconPath 课程图片地址
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData create(String title,String describe,String vid,String iconPath){
        courseSaveService.createCourse( title,vid,describe,iconPath );
        return ResponseData.ok();
    }

    /**
     * 修改课程，可修改课程版本号、标题、图片、描述
//     * @param user 自动获取
     * @param title 课程名称
     * @param vid 版本号
     * @param describe 课程描述
     * @param iconPath 课程图片地址
     */
    @RequestMapping(value = "/update_info",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData update(Integer cid,
                               @RequestParam(required = false)String title,
                               @RequestParam(required = false)String describe,
                               @RequestParam(required = false)String vid,
                               @RequestParam(required = false)String iconPath){
        String error=courseSaveService.update( cid,title,vid,describe,iconPath );
        if(error!=null){
            return new ResponseData( 400,error );
        }
        return ResponseData.ok();
    }


    //TODO:覆盖课程内容、删除课程内容，都没有处理段落（目前段落都不删），应当判断段落是否收藏，然后删除或者禁止删
    /**
     * 添加课程内容
//     * @param file md文件
//     * @param cid 课程id,vid 版本号
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData save(MultipartFile file, Integer cid,String vid,String seqs) throws IOException {
        List<ChapterProgressView> chapterProgressViews=null;
        //转换seqs
        if(seqs!=null){
            try {
                chapterProgressViews=new ArrayList<>(  );
                JSONArray jsonArray=JSONArray.parseArray( seqs );
                for (int i=0; i < jsonArray.size(); i++)    {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    ChapterProgressView chapterProgressView=new ChapterProgressView();
                    chapterProgressView.setChapter( jsonObject.getObject("chapter",Integer.TYPE) );
                    chapterProgressView.setSections( jsonObject.getObject( "sections",List.class ) );
                    chapterProgressViews.add( chapterProgressView );
                }
            }catch (Exception e){
                e.printStackTrace();
                return new ResponseData( 400,"参数格式错误" );
            }
        }
        //储存文件
        if(Objects.equals( file.getOriginalFilename(), "" )){
            return new ResponseData(400,"课程文件不能为空");
        }
        if (!courseSaveService.checkFile( file )) {
            return new ResponseData(400,"文件类型不符合，请上传.md或.txt类型！");
        }
        String filePath="";
        try{
            filePath= courseSaveService.saveFile( file );
        }catch (Exception e){
            return new ResponseData(400,"文件上传失败");
        }
        //储存课程内容
        String re= courseSaveService.saveContent( filePath,cid,vid, chapterProgressViews);
        if(re!=null){
            return new ResponseData(400,re+"!");
        }
        return ResponseData.ok();
    }

    /**
     * 删除课程
     * @param cid 课程id
     * @param vid 课程版本号
     * @param seqs 要删除的章节,不传表示课程全部删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData delete(Integer cid,String vid, String seqs) {
        List<ChapterProgressView> chapterProgressViews=null;
        //转换seqs
        if(seqs!=null){
            try {
                chapterProgressViews=new ArrayList<>(  );
                JSONArray jsonArray=JSONArray.parseArray( seqs );
                for (int i=0; i < jsonArray.size(); i++)    {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    ChapterProgressView chapterProgressView=new ChapterProgressView();
                    chapterProgressView.setChapter( jsonObject.getObject("chapter",Integer.TYPE) );
                    chapterProgressView.setSections( jsonObject.getObject( "sections",List.class ) );
                    chapterProgressViews.add( chapterProgressView );
                }
            }catch (Exception e){
                e.printStackTrace();
                return new ResponseData( 400,"参数格式错误" );
            }
        }

        courseSaveService.delete( cid,vid,chapterProgressViews );
        return  ResponseData.ok();
    }

    /**
     * 预览课程内容
     * @param file md|txt文件
     */
    @RequestMapping(value = "/preview",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData preview( MultipartFile file) throws IOException {
        //储存文件
        if(Objects.equals( file.getOriginalFilename(), "" )){
            return new ResponseData(400,"课程文件不能为空");
        }
        if (!courseSaveService.checkFile( file )) {
            return new ResponseData(400,"文件类型不符合，请上传.md或.txt类型！");
        }
        String filePath="";
        try{
            filePath= courseSaveService.saveFile( file );
        }catch (Exception e){
            return new ResponseData(400,"文件上传失败！");
        }
        Map<String, SectionContentView> sectionViewMap=new HashMap<>(  );
        CourseView courseView= courseSaveService.preview( filePath,sectionViewMap );

        ResponseData responseData=new ResponseData( );
        responseData.putDataValue( "courseView",courseView );
        responseData.putDataValue( "sectionViewMap",sectionViewMap );//？key为Integer的map无法转化为json ！！

        return responseData;
    }

    /**
     * 获取课程内容添加进度
     * @param cid 课程id
     * @param vid 版本号
     */
    @RequestMapping(value = "/progress")
    @ResponseBody
    public ResponseData progress(Integer cid,String vid){
//        if(!isAdm(user)) {
//            return ResponseData.powerError();
//        }
        List<ChapterProgressView> chapterProgressViews=courseSaveService.getProgress(cid,vid);

        ResponseData responseData=new ResponseData(  );
        responseData.putDataValue( "progress",chapterProgressViews );
        return responseData;
    }

}
