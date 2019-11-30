package com.zhiku.config;

import org.bson.types.ObjectId;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import java.io.IOException;
import org.bson.types.ObjectId;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import java.lang.reflect.Type;

/**
 * 统一返回response控制
 * ObjectId返回时返回字符串
 * @author cf
 *
 */
@ControllerAdvice
public class MyResponseBodyAdvice implements ResponseBodyAdvice<Object>{
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        SerializeConfig config = new SerializeConfig();
        //解决mongdb id序列化问题
        config.put(ObjectId.class, new ObjectIdJsonSerializer());
        return JSONObject.parse(JSON.toJSONString(body, config));
    }
}

/**
 * mongdb Object序列化问题
 * @author cf
 *
 */
class ObjectIdJsonSerializer implements ObjectSerializer{

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType,int features) throws IOException {
        SerializeWriter out = serializer.getWriter();
        if (object == null) {
            serializer.getWriter().writeNull();
            return;
        }
        out.write("\"" + ((ObjectId) object).toString() + "\"");
    }
}