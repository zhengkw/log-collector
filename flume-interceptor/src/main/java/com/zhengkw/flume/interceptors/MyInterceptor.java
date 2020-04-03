package com.zhengkw.flume.interceptors;

import com.zhengkw.flume.utils.ETLUtil;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:MyInterceptor
 * @author: zhengkw
 * @description:
 * @date: 20/03/28下午 11:27
 * @version:1.0
 * @since: jdk 1.8
 */
public class MyInterceptor implements Interceptor {

    private String startFlag = "\"en\":\"start\"";

    private List<Event> results = new ArrayList<>();

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

        //声明一个变量判断数据是否合法
        boolean isLegal = true;


        //判断当前的event中的数据，是否是启动日志
        if (bodyStr.contains(startFlag)) {

            headers.put("topic", "topic_start");

            //进行启动日志的ETL处理
            isLegal = ETLUtil.validStartLog(bodyStr);


        } else {
            //埋点事件日志
            headers.put("topic", "topic_event");

            //进行埋点事件日志的ETL处理
            isLegal = ETLUtil.validEventLog(bodyStr);

        }

        //通过标记判断数据是否合法
        if (!isLegal) {
            return null;
        }

        return event;
    }

    //拦截，建议调用intercept(Event event)
    @Override
    public List<Event> intercept(List<Event> events) {

        //先清空results
        results.clear();

        for (Event event : events) {

            Event e = intercept(event);

            //判断拦截的数据是否合法
            if (e != null) {

                //将合法的数据放入到集合中
                results.add(e);

            }

        }

        return results;
    }

    //关闭时调用
    @Override
    public void close() {

    }

    public static class Builder implements Interceptor.Builder {

        // 返回一个拦截器对象
        @Override
        public Interceptor build() {
            return new MyInterceptor();
        }

        //读取agent配置文件中的参数
        @Override
        public void configure(Context context) {

        }
    }
}