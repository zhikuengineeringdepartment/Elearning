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
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/*格式化返回数据
*TODO:此方法主要用来解决ObjectId返回不是字符串问题，但会拦截所有请求返回，并且再通过springboot配置或@JsonFormat配置返回序列化就无效了（可能与此方法冲突）
*TODO:如用spring.jackson.date-format=yyyy-MM-dd HH:mm:ss配置日期返回格式就不起作用，暂时通过在下面方法中加对Date的序列化来解决
*TODO:其他类型的返回数据的序列化也需要在这里写，最好找到办法解决配置无效问题，或用其他方案解决返回ObjecId不是字符串问题，删掉下面方法
*/
/**
 * 统一返回response控制
 * ObjectId返回时返回字符串
 * @author cf
 *
 */
@ControllerAdvice
public class MyResponseBodyAdvice implements ResponseBodyAdvice<Object>{
    /**
     * 是否拦截
     * @param returnType 控制类调用的方法
     * @param converterType
     * @return
     */
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
        //添加date返回控制
        config.put( Date.class, new DateJsonSerializer());
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
class DateJsonSerializer implements ObjectSerializer{

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType,int features) throws IOException {
        SerializeWriter out = serializer.getWriter();
        if (object == null) {
            serializer.getWriter().writeNull();
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone( TimeZone.getTimeZone("Etc/GMT-8"));
        out.write("\"" + sdf.format( (Date) object ) + "\"");
    }
}