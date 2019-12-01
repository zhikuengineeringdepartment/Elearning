package com.zhiku.service;

import com.zhiku.entity.Picture;
import com.zhiku.mapper.*;
import com.zhiku.util.ResponseData;
import com.zhiku.util.SmallTools;
import com.zhiku.view.PictureView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PictureService {
    @Value("jpg|png|Gif")
    private String type_reg;
    @Value( "${picture.save_path}" )
    private String picSavePath;
    @Autowired
    private PictureMapper pictureMapper;

    private boolean checkFile(MultipartFile file){
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

    private String saveFile(MultipartFile file) throws IOException {
        String relative=null;
        if(file!=null) {
            //如果文件夹不存在则创建
            relative=getOnlyPath();
            File dir =new File(picSavePath+File.separator+relative);
            if  (!dir .exists()&&!dir.isDirectory()) {
                dir .mkdirs();
            }
            String filename = file.getOriginalFilename();
            assert filename != null;
            String suffix = filename.substring(filename.lastIndexOf('.')+1);
            relative+=File.separator+SmallTools.uuid()+"."+suffix;
            String path=picSavePath+File.separator+relative;
            //上传
            file.transferTo(new File(path));
        }
        return relative;
    }

    /**
     * 添加图片
     * @param file 图片文件
     * @param picture 图片实体类，包含用户id、课程id、章id、节id、//TODO:无法传id，询问是否传seq
     * @return 错误信息
     */
    public String addPicture(MultipartFile file,Picture picture){
        if (!checkFile( file )) {
            return "文件类型不符合，请上传jpg或png或Gif类型！";
        }
        String orgName = file.getOriginalFilename();
        String url="";
        try{
            url= "/pictures/"+saveFile( file ).replace( "\\","/" );
        }catch (Exception e){
            e.printStackTrace();
            return "课程内容文件上传失败！";
        }

        picture.setOrgName( orgName );
        picture.setUrl( url );
        if(picture.getCreateTime()==null){
            picture.setCreateTime( new Date(  ) );
        }
        pictureMapper.insertSelective( picture );
        return null;
    }

    /**
     * 得到用户上传的图片列表
     * @param uid 用户id
     * @return 错误信息
     */
    public List<PictureView> getList(Integer uid,Integer page,Integer pageSize){
        Integer m;
        if(page==null||page<=0||pageSize==null||pageSize<=0){
            m=null;
        }else{
            m=(page-1)*pageSize;
        }
        return pictureMapper.selectByUid( uid,m,pageSize );
    }

    /**
     * 删除一条记录
     * @param picId 图片记录id
     */
    public void delete(Integer picId){
        pictureMapper.deleteByPrimaryKey( picId );
    }

    //得到唯一路径
    private String getOnlyPath() {
        String path;
        try {
            Date now=new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date ntime=new Date(  );
            Date nDay=sdf.parse( sdf.format( ntime ) );
            path=SmallTools.md5( (""+sdf.format( ntime )+"zhiku").getBytes() )+File.separator
                    +SmallTools.md5( (""+(ntime.getTime()-nDay.getTime())/6000+"zhiku").getBytes() );
        }catch (Exception e){
            long now=System.currentTimeMillis();
            long min=now/6000;
            long day=min/(60*24);
            path=SmallTools.md5( (""+day+"zhiku").getBytes() )+File.separator+SmallTools.md5( (""+min+"zhiku").getBytes() );
        }
        return path;
    }
}
