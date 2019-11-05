package com.zhiku.controller;

import com.zhiku.entity.Article;
import com.zhiku.service.ArticleService;
import com.zhiku.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.logging.Logger;

/**
* 文章控制层
*/

@CrossOrigin(value = "*")
@Controller
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //TODO 获取全部文章，后续可以加入根据不同部门获取文章
    /**
     * 获取所有文章
     * @return 文章列表
     */
    @ResponseBody
    @RequestMapping(value = "getArticles",method = RequestMethod.GET)
    public ResponseData getAllArticle(){
        ResponseData responseData = ResponseData.ok();
        List<Article> articles = articleService.getAllArticle();
        responseData.putDataValue("data",articles);
        return responseData;
    }
}
