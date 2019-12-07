package com.zhiku.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value( "${picture.save_path}" )
    private String picSavePath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if(!registry.hasMappingForPattern("/pictures/**")){
            File dir =new File(picSavePath);
            if  (!dir.exists()&&!dir.isDirectory()) {
                dir .mkdirs();
            }
            registry.addResourceHandler("/pictures/**")
                    .addResourceLocations("file:"+picSavePath);
        }
//        super.addResourceHandlers(registry);
    }
}