import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * @ClassName:ETCUtil
 * @author: zhengkw
 * @description: 数据清洗工具类
 * @date: 20/03/28下午 7:07
 * @version:1.0
 * @since: jdk 1.8
 */
public class ETCUtil {


    public static boolean vilidateStartLogs(String str) {
        //判断合法性
        if (StringUtils.isBlank(str)) {
            return false;
        }
        //去前后空格
        String trimStr = str.trim();
        //判断是否是{开头，}结尾 json格式！
        if (trimStr.startsWith("{") && trimStr.endsWith("}")) {
            return true;
        }
        return false;
    }

    public static boolean validateEventLogs(String str) {
        //判空
        if (StringUtils.isBlank(str)) {

            return false;

        }
        //去前后空格
        String trimStr = str.trim();
        String[] split = trimStr.split("\\|");
        //判断切割以后是否是2段，如果不是数据则有问题
        //数据格式xxxxx|{}
        if (split.length != 2) {
            return false;
        }
        //判断时间戳是否合法 a) 长度复合13位 b)都是是数字
        // NumberUtils.isDigits(): 要求字符串中必须全部为数字符号0-9
        // NumberUtils.isNumbers(): 只要是java支持的数字类型即可
        if ((split[0].length() != 13) || NumberUtils.isDigits(split[0])) {
            return false;
        }
        if (split[1].startsWith("{") && split[1].endsWith("}")) {

            return true;

        }
        return false;
    }
}
