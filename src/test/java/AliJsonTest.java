import com.alibaba.fastjson.*;
import com.zhengkw.dw.bean.Person;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @ClassName:AliJosonTest
 * @author: zhengkw
 * @description: 测试阿里json工具使用
 * JSON子类JSONObject
 * @date: 20/03/26下午 9:54
 * @version:1.0
 * @since: jdk 1.8
 */
public class AliJsonTest {

    Person person = new Person();
    String json = "{\"name\":\"zhengkw\",\"id\":\"1\",\"gender\":\"male\"}";
    String json1 = "{\"name\":\"marry\",\"id\":\"2\",\"gender\":\"female\"}";
    String json2 = "{\"name\":\"lucy\",\"id\":\"3\",\"gender\":\"female\"}";

    /**
     * @descrption: 自定义对象转 Json串
     * @return: void
     * @date: 20/03/26 下午 11:16
     * @author: zhengkw
     */
    @Test
    public void ObjectToJsonStringTest() {

        person.setName("java");
        person.setId(15);
        person.setGender("male");
        String string = JSON.toJSONString(person);
        System.out.println(string);
    }

    /**
     * @descrption:
     * @return: void
     * @date: 20/03/26 下午 11:57
     * @author: zhengkw
     */
    @Test
    public void ObjectToJsonTest() {
        person.setName("java");
        person.setId(15);
        person.setGender("male");
        JSONObject o = (JSONObject) JSON.toJSON(person);
        System.out.println(o);
    }

    /**
     * @descrption: String转jsonObject   JSONObject实质是一个map
     * @return: void
     * @date: 20/03/26 下午 11:03
     * @author: zhengkw
     */
    @Test
    public void StringToJsonObjectTest() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1001);
        jsonObject.put("name", "jack");
        jsonObject.put("gender", "male");

        System.out.println(jsonObject.toString());
        System.out.println(jsonObject);
        System.out.println(jsonObject.toJSONString());


    }

    /**
     * @descrption: json字符串转自定义对象
     * @return: void
     * @date: 20/03/26 下午 11:21
     * @author: zhengkw
     */
    @Test
    public void JsonStrToObjectTest() {

        Person person = JSONObject.parseObject(json, Person.class);
        System.out.println(person);

    }

    /**
     * @descrption: json串（数组）转自定义对象List<T>
     * 可以直接调用JSONArray构造方法来构造对象
     * @return: void
     * @date: 20/03/26 下午 11:25
     * @author: zhengkw
     */
    @Test
    public void JsonArrToObjectArrTest() {

        JSONArray array = new JSONArray();
        //方法一
        /*JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1001);
        jsonObject.put("name", "jack");
        jsonObject.put("gender", "male");
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("id", 1002);
        jsonObject1.put("name", "jack1");
        jsonObject1.put("gender", "male");
        array.add(jsonObject);
        array.add(jsonObject1);
        //System.out.println(array.toJSONString());
        List<Person> person = JSONObject.parseArray(array.toJSONString(), Person.class);

        System.out.println(person.toString());*/
        //方法2
        //官方示例！
       /*String json = "[{},...]";
       Type listType = new TypeReference<List<Model>>() {}.getType();
        List<Model> modelList = JSON.parseObject(json, listType);*/


        //[{"name":"marry","id":"2","gender":"female"},{"name":"lucy","id":"3","gender":"female"}]
        String jsonArr = "[" + json1 + ',' + json2 + "]";
        Type listTrype = new TypeReference<List<Person>>() {
        }.getType();
        List<Person> personList = JSON.parseObject(jsonArr, listTrype);
        for (Person person1 : personList) {
            System.out.println(person1);
        }


    }
}
