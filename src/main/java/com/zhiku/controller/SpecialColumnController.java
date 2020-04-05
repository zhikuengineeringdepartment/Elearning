package com.zhiku.controller;

import com.zhiku.entity.Course;
import com.zhiku.entity.mysql.SpecialColumn;
import com.zhiku.service.SpecialColumnService;
import com.zhiku.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@CrossOrigin(value = "*")
@Controller
@RequestMapping(value = "/specialColumn")
public class SpecialColumnController {


    @Autowired
    private SpecialColumnService specialColumnService;


    /**
     * 该方法用于前台使用用户获取所有的专栏(不包括删除的)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getAll" ,method = RequestMethod.GET)
    public ResponseData getAllSpecialColumn(Integer start,Integer size){
        ResponseData responseData = null;
        SpecialColumn specialColumn=new SpecialColumn();
        specialColumn.setIsDelete(0);
        try {
            List<SpecialColumn> specialColumns = specialColumnService.list(specialColumn,start,size);
            int total=specialColumnService.getTotal(specialColumn);
            responseData=ResponseData.ok();
            responseData.putDataValue("specialColumns",specialColumns);
            responseData.putDataValue("total",total);
        }catch (Exception e){
            responseData=ResponseData.serverInternalError();
            e.printStackTrace();
        }
        return responseData;
    }

}
