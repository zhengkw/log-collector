package com.zhengkw.flume.interceptors;

import com.alibaba.fastjson.JSONObject;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:SecondInterceptor
 * @author: zhengkw
 * @description: 为event添加一个时间戳的header
 * 时间戳取自于event里的信息
 * @date: 20/03/30下午 2:42
 * @version:1.0
 * @since: jdk 1.8
 */
public class SecondInterceptor implements Interceptor {
    private String startFlag = "\"en\":\"start\"";
    static List<Event> eventList = new ArrayList<>();

    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {

        byte[] body = event.getBody();
        //用new的方式将byte数组转换成字符串，不指定chaset为默认UTF-8
        String str = new String(body, Charset.forName("GBK"));
        String strBody = str.trim();
        Map<String, String> headers = event.getHeaders();
        if (strBody.contains(startFlag)) {
            JSONObject jsonObject = JSONObject.parseObject(strBody);
            String ts = jsonObject.getString("t");
            headers.put("topic", "topic_start");
            headers.put("timestamp", ts);


        } else {
            String[] split = strBody.split("\\|", 2);
            headers.put("topic", "topic_event");
            headers.put("timestamp", split[0]);
        }


        return event;
    }

    @Override
    public List<Event> intercept(List<Event> events) {
        for (Event event : events) {
            Event e = intercept(event);
            eventList.add(e);
        }
        return eventList;
    }

    @Override
    public void close() {

    }

    public static class Builder implements Interceptor.Builder {

        // 返回一个拦截器对象
        @Override
        public Interceptor build() {
            return new SecondInterceptor();
        }

        //读取agent配置文件中的参数
        @Override
        public void configure(Context context) {

        }
    }
}
