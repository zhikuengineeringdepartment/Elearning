package com.zhiku.controller.admin;


import com.alibaba.fastjson.JSONObject;
import com.zhiku.entity.mysql.SpecialColumn;
import com.zhiku.entity.mysql.SpecialColumnArticle;
import com.zhiku.service.SpecialColumnArticleService;
import com.zhiku.service.SpecialColumnService;
import com.zhiku.util.ResponseData;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@CrossOrigin(value = "*")
@Controller
@RequestMapping(value = "/backstage/specialColumnArticle")
public class SpecialColumnArticleAdminController {

    @Autowired
    private SpecialColumnArticleService specialColumnArticleService;

    @Autowired
    private SpecialColumnService specialColumnService;

    /**
     * 后台管理员获取所有专栏文章信息(包括已经删除的)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getAll" ,method = RequestMethod.GET)
    public ResponseData getAllSpecialColumnArticle(Integer start, Integer size){
        ResponseData responseData = null;
        SpecialColumnArticle specialColumnArticle=new SpecialColumnArticle();
        try {
            List<SpecialColumnArticle> specialColumnArticles = specialColumnArticleService.list(specialColumnArticle,start,size);
            int total=specialColumnArticleService.getTotal(specialColumnArticle);
            responseData=ResponseData.ok();
            responseData.putDataValue("specialColumnArticles",specialColumnArticles);
            responseData.putDataValue("total",total);
        }catch (Exception e){
            responseData=ResponseData.serverInternalError();
            e.printStackTrace();
        }
        return responseData;
    }


    /**
     * 后台使用用户按照类别获取所有的专栏文章(包括删除的)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getAllByType" ,method = RequestMethod.GET)
    public ResponseData getAllSpecialColumnArticleByType(@RequestParam(value="specialColumnId",required=true)Integer specialColumnId, Integer start,Integer size){
        ResponseData responseData = null;
        SpecialColumnArticle specialColumnArticle=new SpecialColumnArticle();
        specialColumnArticle.setSpecialColumnId(specialColumnId);
        try {
            List<SpecialColumnArticle> specialColumnArticleList = specialColumnArticleService.list(specialColumnArticle,start,size);
            int total=specialColumnArticleService.getTotal(specialColumnArticle);
            responseData=ResponseData.ok();
            responseData.putDataValue("specialColumns",specialColumnArticleList);
            responseData.putDataValue("total",total);
        }catch (Exception e){
            responseData=ResponseData.serverInternalError();
            responseData.setMessage("执行异常");
            e.printStackTrace();
        }
        return responseData;
    }

    /**
     * 添加专栏文章
     * @param articleTitle 文章标题
     * @param articleUrl   文章URL
     * @param articlePicUrl 图片路径
     * @param specialColumnId 专栏id
     * @return
     */
    @ResponseBody
    @RequestMapping("/add")
    public ResponseData add(@RequestParam(value="articleTitle",required = true)String articleTitle,
                            @RequestParam(value="articleUrl",required = true)String articleUrl,
                            @RequestParam(value="articlePicUrl",required = true)String articlePicUrl,
                            @RequestParam(value="specialColumnId",required = true)Integer specialColumnId ){
        ResponseData responseData=null;
        if(StringUtils.isBlank(articleTitle)||StringUtils.isBlank(articleUrl)
                ||StringUtils.isBlank(articlePicUrl)){
            responseData=ResponseData.serverInternalError();
            responseData.setMessage("参数非法");
            return responseData;
        }
        SpecialColumn specialColumn=new SpecialColumn();
        specialColumn.setSid(specialColumnId);
        int total=specialColumnService.getTotal(specialColumn);
        if(total!=1){
            responseData=ResponseData.serverInternalError();
            responseData.setMessage("参数非法");
            return responseData;
        }
        Date date=new Date();
        SpecialColumnArticle article=new SpecialColumnArticle(articleTitle, articleUrl,articlePicUrl,specialColumnId,date,date,0);
        try {
            int res=specialColumnArticleService.add(article);
            if(res>0){
                responseData=ResponseData.ok();
            }else{
                responseData=ResponseData.serverInternalError();
                responseData.setMessage("操作失败");
            }
        }catch (Exception e) {
            responseData = ResponseData.serverInternalError();
            responseData.setMessage("执行异常");
        }
        return responseData;
    }


    /**
     * 更新专栏文章
     * @param articleTitle 文章标题
     * @param articleUrl   文章URL
     * @param articlePicUrl 图片路径
     * @param specialColumnId 专栏id
     * @param aid  文章id
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public ResponseData update(@RequestParam(value="articleTitle",required = true)String articleTitle,@RequestParam(value="articleUrl",required = true)String articleUrl,
                               @RequestParam(value="articlePicUrl",required = true)String articlePicUrl,@RequestParam(value="specialColumnId",required = true)Integer specialColumnId,
                               @RequestParam(value="aid",required = true)Integer aid){
        ResponseData responseData=null;
        if(StringUtils.isBlank(articleTitle)||StringUtils.isBlank(articleUrl)
                ||StringUtils.isBlank(articlePicUrl)){
            responseData=ResponseData.serverInternalError();
            responseData.setMessage("参数非法");
            return responseData;
        }
        SpecialColumn specialColumn=new SpecialColumn();
        specialColumn.setSid(specialColumnId);
        int total=specialColumnService.getTotal(specialColumn);
        if(total!=1){
            responseData=ResponseData.serverInternalError();
            responseData.setMessage("参数非法");
            return responseData;
        }
        try {
            SpecialColumnArticle specialColumnArticle=new SpecialColumnArticle(aid,articleTitle,articleUrl,articlePicUrl,specialColumnId,new Date());
            int res=specialColumnArticleService.update(specialColumnArticle);
            if(res>0){
                responseData=ResponseData.ok();
            }else{
                responseData=ResponseData.serverInternalError();
                responseData.setMessage("更新失败");
            }
        }catch (Exception e) {
            responseData = ResponseData.serverInternalError();
            responseData.setMessage("执行异常");
        }
        return responseData;
    }

    /**
     * 用于删除专栏信息（标志位删除）
     * @param id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/delete")
    public ResponseData delete(@RequestParam(value="id")Integer id)throws Exception{
        ResponseData responseData=null;
        if(id==null){
            responseData=ResponseData.serverInternalError();
            responseData.setMessage("参数非法");
            return responseData;
        }
        int res=-1;
        try {
            res= specialColumnArticleService.delete(id);
            if(res!=1){
                responseData=ResponseData.serverInternalError();
                responseData.setMessage("删除失败或相关数据不存在");
                return responseData;
            }
        }catch (Exception e){
            responseData=ResponseData.serverInternalError();
            responseData.setMessage("执行异常");
            return responseData;
        }
        return responseData=ResponseData.ok();
    }

    /**
     * 用于批量删除专栏信息（标志位删除）
     * @param ids
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/deleteBatch")
    public JSONObject deleteBatch(@RequestParam(value="ids")String ids)throws Exception{
        JSONObject result=new JSONObject();
        if(ids==null|| StringUtils.isBlank(ids)||StringUtils.isEmpty(ids)){
            result.put("code",500);
            result.put("message","参数非法");
            return result;
        }
        String []idsStr=null;
        try{
            idsStr=ids.split(",");
        }catch (Exception e){
            result.put("code",500);
            result.put("message","参数非法");
            return result;
        }
        int count=0;
        for(int i=0;i<idsStr.length;i++){
            int res= specialColumnArticleService.delete(Integer.parseInt(idsStr[i]));
            count+=res;
        }
        result.put("code", 200);
        result.put("count",count);
        result.put("message","OK");
        return result;
    }


}
