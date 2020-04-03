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
 * @description: 将启动日志和事件日志分离
 * @date: 20/03/28下午 7:06
 * @version:1.0
 * @since: jdk 1.8
 */
public class MyInterceptor implements Interceptor {
    static String startFlag = "\"en\":\"start\"";
    static List<Event> eventList = new ArrayList<>();

    /* public static void main(String[] args) {
         //测试字符串
         System.out.println(startFlag);
     }
 */
    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        //获取body里面的数据
        byte[] body = event.getBody();
        String bodyStr = new String(body, Charset.forName("GBK"));
        //根据 日志数据的类型，在header中添加要发往的topic名
        Map<String, String> headers = event.getHeaders();
        //声明变量判断数据合法性
        boolean isLegal = true;
        //如果判断为true则是启动日志
        if (bodyStr.contains(startFlag)) {
            headers.put("topic", "topic_start");
            isLegal = ETCUtil.vilidateStartLogs(bodyStr);
        } else {
            //埋点事件日志
            headers.put("topic", "topic_event");
            //进行埋点事件日志ETC
            isLegal = ETCUtil.validateEventLogs(bodyStr);

        }
        if (!isLegal) {
            return null;
        } else {
            //解决taidir读取数据必须依据换行符来读取
          // event.setBody((bodyStr+'\n').getBytes());
            return event;
        }
    }

    @Override
    public List<Event> intercept(List<Event> events) {
        eventList.clear();
        for (Event event : events) {
            Event e = intercept(event);
            if (e != null) {
                eventList.add(e);
            }
        }

        return eventList;
    }

    @Override
    public void close() {

    }

    public static class Builder implements Interceptor.Builder {

        @Override
        public Interceptor build() {
            return new MyInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
