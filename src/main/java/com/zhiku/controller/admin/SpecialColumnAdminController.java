package com.zhiku.controller.admin;
import com.alibaba.fastjson.JSONObject;
import com.zhiku.entity.mysql.SpecialColumn;
import com.zhiku.service.SpecialColumnService;
import com.zhiku.util.ResponseData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;


@CrossOrigin(value = "*")
@Controller
@RequestMapping(value = "/backstage/specialColumn")
public class SpecialColumnAdminController {

    @Autowired
    private SpecialColumnService specialColumnService;


    /**
     * 后台管理员获取所有专栏信息(包括已经删除的)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getAll" ,method = RequestMethod.GET)
    public ResponseData getAllSpecialColumn(Integer start,Integer size){
        ResponseData responseData = null;
        SpecialColumn specialColumn=new SpecialColumn();
        try {
            List<SpecialColumn> specialColumns = specialColumnService.list(specialColumn,start,size);
            int total=specialColumnService.getTotal(specialColumn);
            responseData=ResponseData.ok();
            responseData.putDataValue("specialColumns",specialColumns);
            responseData.putDataValue("total",total);
        }catch (Exception e){
            responseData=ResponseData.serverInternalError();
            responseData.setMessage("执行异常");
            e.printStackTrace();
        }
        return responseData;
    }


    @ResponseBody
    @RequestMapping("/add")
    public ResponseData add(@RequestParam(value="specialcName",required = true)String specialcName,
                            @RequestParam(value="speaiclcRemark",required = true)String speaiclcRemark){
        ResponseData responseData=null;
        if(StringUtils.isBlank(specialcName)||StringUtils.isBlank(speaiclcRemark)){
            responseData=ResponseData.serverInternalError();
            responseData.setMessage("参数非法");
            return responseData;
        }
        Date date=new Date();
        SpecialColumn specialColumn=new SpecialColumn(specialcName,speaiclcRemark,date,date,0);
        try {
            int res=specialColumnService.add(specialColumn);
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


    @ResponseBody
    @RequestMapping("/update")
    public ResponseData update(@RequestParam(value="sid",required = true)Integer sid,@RequestParam(value="specialcName",required = true)String specialcName,@RequestParam(value="speaiclcRemark",required = true)String speaiclcRemark){
        ResponseData responseData=null;
        if(StringUtils.isBlank(specialcName)||StringUtils.isBlank(speaiclcRemark)){
            responseData=ResponseData.serverInternalError();
            responseData.setMessage("参数非法");
            return responseData;
        }
        SpecialColumn specialColumn=new SpecialColumn();
        Date date=new Date();
        specialColumn.setSid(sid);
        specialColumn.setSpecialcName(specialcName);
        specialColumn.setSpecialcRemark(speaiclcRemark);
        specialColumn.setUpdateTime(date);
        try {
            int res=specialColumnService.update(specialColumn);
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
             res= specialColumnService.delete(id);
             if(res!=1){
                 responseData=ResponseData.serverInternalError();
                 responseData.setMessage("删除失败,参数不存在或对应类别下有文章禁止删除");
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
           int res= specialColumnService.delete(Integer.parseInt(idsStr[i]));
           count+=res;
        }
        result.put("code", 200);
        result.put("count",count);
        result.put("message","OK");
        return result;
    }



}
