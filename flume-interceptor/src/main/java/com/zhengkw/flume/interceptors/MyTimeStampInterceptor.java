package com.zhengkw.flume.interceptors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:MyTimeStampInterceptor
 * @author: zhengkw
 * @description:
 * @date: 20/03/31下午 9:49
 * @version:1.0
 * @since: jdk 1.8
 */
public class MyTimeStampInterceptor  implements Interceptor{


    private String startFlag="\"en\":\"start\"";

    //初始化
    @Override
    public void initialize() {

    }

    //核心拦截逻辑
    @Override
    public Event intercept(Event event) {

        //获取body
        byte[] body = event.getBody();
        //转换body为string类型
        String bodyStr = new String(body, Charset.forName("GBK"));

        //根据 日志数据的类型，在header中添加要发往的topic名
        Map<String, String> headers = event.getHeaders();

        //判断当前的event中的数据，是否是启动日志
        if (bodyStr.contains(startFlag)){

            JSONObject jsonObject = JSON.parseObject(bodyStr);

            String ts = jsonObject.getString("t");

            headers.put("topic","topic_start");
            headers.put("timestamp",ts);

        }else{

            String[] words = bodyStr.split("\\|");
            //埋点事件日志
            headers.put("topic","topic_event");

            headers.put("timestamp",words[0]);


        }

        return event;
    }

    //拦截，建议调用intercept(Event event)
    @Override
    public List<Event> intercept(List<Event> events) {

        for (Event event : events) {

            intercept(event);

        }

        return events;
    }

    //关闭时调用
    @Override
    public void close() {

    }

    public static class Builder implements Interceptor.Builder {

        // 返回一个拦截器对象
        @Override
        public Interceptor build() {
            return new MyTimeStampInterceptor();
        }

        //读取agent配置文件中的参数
        @Override
        public void configure(Context context) {

        }
    }

}
