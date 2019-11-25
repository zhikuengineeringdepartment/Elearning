package com.zhiku.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Value( "${picture.save_path}" )
    private String picSavePath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if(!registry.hasMappingForPattern("/pictures/**")){
            registry.addResourceHandler("/pictures/**")
                    .addResourceLocations("file:"+picSavePath);
        }
        super.addResourceHandlers(registry);
    }
}