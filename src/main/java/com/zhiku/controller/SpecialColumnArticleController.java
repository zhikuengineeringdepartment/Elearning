package com.zhiku.controller;


import com.zhiku.entity.mysql.SpecialColumnArticle;
import com.zhiku.service.SpecialColumnArticleService;
import com.zhiku.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "*")
@Controller
@RequestMapping(value = "/specialColumnArticle")
public class SpecialColumnArticleController {

    @Autowired
    private SpecialColumnArticleService specialColumnArticleService;


    /**
     * 该方法用于前台使用用户获取所有的专栏文章(不包括删除的)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getAll" ,method = RequestMethod.GET)
    public ResponseData getAllSpecialColumnArticle(Integer start, Integer size){
        ResponseData responseData = null;
        SpecialColumnArticle specialColumnArticle=new SpecialColumnArticle();
        specialColumnArticle.setIsDelete(0);
        try {
            List<SpecialColumnArticle> specialColumnArticleList = specialColumnArticleService.list(specialColumnArticle,start,size);
            int total=specialColumnArticleService.getTotal(specialColumnArticle);
            responseData=ResponseData.ok();
            responseData.putDataValue("specialColumnArticleList",specialColumnArticleList);
            responseData.putDataValue("total",total);
        }catch (Exception e){
            responseData=ResponseData.serverInternalError();
            responseData.setMessage("执行异常");
            e.printStackTrace();
        }
        return responseData;
    }

    /**
     * 该方法用于前台使用用户获取所有的专栏文章(不包括删除的)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getAllByType" ,method = RequestMethod.GET)
    public ResponseData getAllSpecialColumnArticleByType(@RequestParam(value="specialColumnId",required=true)Integer specialColumnId, Integer start,Integer size){
        ResponseData responseData = null;
        SpecialColumnArticle specialColumnArticle=new SpecialColumnArticle();
        specialColumnArticle.setIsDelete(0);
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
}
