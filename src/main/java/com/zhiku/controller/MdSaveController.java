package com.zhiku.controller;


import com.zhiku.entity.User;
import com.zhiku.service.MdSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping(value = "/backstage/course")
public class MdSaveController {
    //todo：前端页面目前使用webapp下的jsp，待修改
    //管理员uid，只有登录这些账号只能访问页面和使用功能
    private int[] admUids={101};
    @Autowired
    MdSaveService mdSaveService;

    @RequestMapping(value = {"/create","/save","/delete"},method = RequestMethod.GET)
    public String create(User user){
        if(isAdm(user)){
            return "saveCourse";
        }else{
            return "";
        }
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public String create(User user,Model model, @RequestParam("file") MultipartFile file,
                         @RequestParam("title") String title,
                         @RequestParam("describe") String describe, @RequestParam("icon_path") String iconPath)
            throws Exception {
        if(!isAdm(user)) {
            return "";
        }
        model.addAttribute("message",null);
        if(title==null||title.equals( "" )){
            model.addAttribute("message","课程名称不能为空");
            return "saveCourse";
        }
        int cid=0;
        try{
            cid= mdSaveService.createCourse( title,describe,iconPath );
        }catch (Exception e){
            model.addAttribute("message",e.getMessage());
            return "saveCourse";
        }
        model.addAttribute("message","创建成功！");
        //储存文件
        if(Objects.equals( file.getOriginalFilename(), "" )){
            model.addAttribute("lastCourseId",""+cid);//!!!直接传int，用(string)转换获取，还报错
            return "saveCourse";
        }
        if (!mdSaveService.checkFile( file )) {
            model.addAttribute("message","文件类型不符合，请上传.md或.txt类型！");
            return "saveCourse";
        }
        String filePath="";
        try{
            filePath= mdSaveService.saveFile( file );
        }catch (Exception e){
            model.addAttribute("message","课程创建成功，内容文件上传失败！");
            return "saveCourse";
        }

        String re=mdSaveService.saveContent( filePath,cid );
        if(re!=null){
            model.addAttribute("message",re+"!");
        }
        return "saveCourse";
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(User user,Model model, @RequestParam("file") MultipartFile file, @RequestParam("cid") String scid)
            throws IOException {
        if(!isAdm(user)) {
            return "";
        }
        if(scid==null||scid.equals( "" )){
            model.addAttribute("message","课程id不能为空");
            return "saveCourse";
        }
        int cid= Integer.parseInt(scid );
        //储存文件
        if(Objects.equals( file.getOriginalFilename(), "" )){
            model.addAttribute("message","课程文件不能为空");
            return "saveCourse";
        }
        if (!mdSaveService.checkFile( file )) {
            model.addAttribute("message","文件类型不符合，请上传.md或.txt类型！");
            return "saveCourse";
        }
        String filePath="";
        try{
            filePath= mdSaveService.saveFile( file );
        }catch (Exception e){
            model.addAttribute("lastCourse",null);
            model.addAttribute("message","课程内容文件上传失败！");
            return "saveCourse";
        }

        String re=mdSaveService.saveContent( filePath,cid );
        if(re!=null){
            model.addAttribute("message",re+"!");
        }else{
            model.addAttribute("message","添加成功");
        }
        return "saveCourse";
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public String delete(User user,Model model, @RequestParam("cid") String scid) {
        if(!isAdm(user)) {
            return "";
        }
        if(scid==null||scid.equals( "" )){
            model.addAttribute("message","课程id不能为空");
            return "saveCourse";
        }
        int cid=Integer.parseInt( scid );
        boolean re= mdSaveService.deleteCourse( cid );
        if(re){
            model.addAttribute("message","删除成功!");
        }else{
            model.addAttribute("message","删除失败!");
        }
        return "saveCourse";
    }

    private boolean isAdm(User user){
        boolean isAdm=false;
        int uid=user.getUid();
        for(int i=0;i<admUids.length;i++){
            if(uid==admUids[i]){
                return true;
            }
        }
        return false;
    }

}
