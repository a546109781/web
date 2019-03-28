package cc.nanjo.common.util;

import org.apache.commons.lang3.StringUtils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f"};

    /**
     * 转换字节数组为16进制字符串
     *
     * @param b 字节数组
     * @return 16进制字符串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append(byteToHexString(b[i]));
        }

        return sb.toString().toUpperCase();
    }

    /**
     * 转换字节数为16进制字符串
     *
     * @param b byte数值
     * @return 16进制字符串
     */
    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;

        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 将整数转为16进行数后并以指定长度返回（当实际长度大于指定长度时只返回从末位开始指定长度的值）
     *
     * @param val int 待转换整数
     * @param len int 指定长度
     * @return String
     */
    public static String Int2HexStr(int val, int len) {
        String result = Integer.toHexString(val).toUpperCase();
        int r_len = result.length();
        if (r_len > len) {
            return result.substring(r_len - len, r_len);
        }
        if (r_len == len) {
            return result;
        }
        StringBuffer strBuff = new StringBuffer(result);
        for (int i = 0; i < len - r_len; i++) {
            strBuff.insert(0, '0');
        }
        return strBuff.toString();
    }

    /**
     * 获取指定字符串的MD5编码
     *
     * @param original 字符串
     * @return MD5编码 （结果为大写！）
     */
    public static String MD5Encode(String original) {
        String ret = null;

        try {
            ret = new String(original);
            MessageDigest md = MessageDigest.getInstance("MD5");
            ret = byteArrayToHexString(md.digest(ret.getBytes()));
        } catch (Exception ex) {
            // empty
        }

        return ret;
    }

    /**
     * 获得0-9的随机数字符串
     *
     * @param length 返回字符串的长度
     * @return String
     */
    public static String getRandomNumber(int length) {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < length; i++) {
            buffer.append(random.nextInt(10));
        }
        return buffer.toString();
    }

    /**
     * 获得0-9,a-z,A-Z范围的随机字符串
     *
     * @param length 字符串长度
     * @return String
     */
    public static String getRandomChar(int length) {
        char[] chr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
                'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
                'Z'};

        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append(chr[random.nextInt(62)]);
        }

        return buffer.toString();
    }

    /**
     * 判断字符串数组中是否包含某字符串
     *
     * @param substring 某字符串
     * @param source    源字符串数组
     * @return 包含则返回true，否则返回false
     */
    public static boolean isContains(String substring, String[] source) {
        if (source == null || source.length == 0) {
            return false;
        }

        for (int i = 0; i < source.length; i++) {
            String aSource = source[i];
            if (aSource.equals(substring)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断字符是否为空
     *
     * @param str 某字符串
     * @return 为null或为空串则返回true，否则返回false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || "null".equals(str);
    }

    /**
     * 判断字符是否不为空
     *
     * @param str 某字符串
     * @return 不为null或不为空串则返回true，否则返回false
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断对象是否为空
     *
     * @param str 对象
     * @return 为空则为true，否则返回false
     */
    public static boolean isEmptyObject(Object str) {
        return str == null || ("NULL".equals(str.toString().toUpperCase()) || (str + "").length() == 0);
    }

    /**
     * 首字母大写
     *
     * @param str 字符串
     * @return 首字符大写后的字符串
     */
    public static String upFirstChar(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param str 字符串
     * @return 首字符大写后的字符串
     */
    public static String lowerFirstChar(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 字符串数组转成列表
     *
     * @param arr
     * @return
     */
    public static List<String> StringsToList(String[] arr) {
        List<String> strList = null;
        if (null == arr) {
            return strList;
        }
        strList = new ArrayList<String>();
        for (int i = 0; i < arr.length; i++) {
            strList.add(arr[i]);
        }
        return strList;
    }

    /**
     * inputstream解析成string
     *
     * @param in
     * @return
     */
    public static String inputStream2String(InputStream in) {
        try {
            StringBuffer out = new StringBuffer();
            byte[] b = new byte[4096];
            for (int n; (n = in.read(b)) != -1; ) {
                out.append(new String(b, 0, n));
            }
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 把null转换成空字符串
     *
     * @return
     */
    public static String changeNullToStr(String str) {
        return str == null ? "" : str;
    }

    /**
     * 将标准的BASE64编码格式中+ , = , /的字符替换成. , - , _, okpay需要
     *
     * @param str
     * @return
     */
    public static String toy64(String str) {
        String s = StringUtils.replace(str, "+", ".");
        s = StringUtils.replace(s, "/", "_");
        s = StringUtils.replace(s, "=", "-");

        return s;
    }

    /**
     * 将替换过+ , = , /的字符串转换成标准的BASE64编码格式, okpay需要
     *
     * @param str
     * @return
     */
    public static String from64(String str) {
        String s = StringUtils.replace(str, ".", "+");
        s = StringUtils.replace(s, "_", "/");
        s = StringUtils.replace(s, "-", "=");

        return s;
    }

    /**
     * 把16进制字符串转化为字节数组
     *
     * @param hex
     * @return
     */
    public static byte[] hexToBytes(String hex) {
        byte[] buffer = new byte[hex.length() / 2];
        String strByte;

        for (int i = 0; i < buffer.length; i++) {
            strByte = hex.substring(i * 2, i * 2 + 2);
            buffer[i] = (byte) Integer.parseInt(strByte, 16);
        }

        return buffer;
    }

    /**
     * 生成指定范围的随机数字
     *
     * @param from
     * @param to
     * @return
     */
    public static int randomInt(int from, int to) {
        return from + new Random().nextInt(to - from);
    }

    /**
     * 生成指定长度的随机数字
     *
     * @param length 不限制长度
     * @return
     */
    public static String randomStr(int length) {
        StringBuffer sb = new StringBuffer();
        // 生成随机数字串
        for (int i = 0; i < length; i++) {
            sb.append(randomInt(0, 10));
        }

        return sb.toString();
    }

    /**
     * 生成指定长度的随机数字，并将数字转化为ascii
     *
     * @param length 不限制长度
     * @return ascii字符串
     */
    public static String randomStr2Ascii(int length) {
        StringBuffer sb = new StringBuffer();

        char[] chars = randomStr(length).toCharArray();
        for (int i = 0; i < chars.length; i++) {
            sb.append((int) chars[i]);
        }

        return sb.toString();
    }

    /**
     * 生成指定长度的随机数字，并将数字转化为ascii
     *
     * @param length 1-8位
     * @return ascii字符串
     */
    public static String generalStringToAscii(int length) {

        int num = 1;
        for (int i = 0; i < length; i++) {
            num *= 10;
        }

        Random rand = new Random((new Date()).getTime());
        String strRandom = Integer.toString(rand.nextInt(num));

        strRandom = StringUtils.leftPad(strRandom, length, '0');

        StringBuffer sb = new StringBuffer();
        char[] chars = strRandom.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            sb.append((int) chars[i]);
        }

        return sb.toString();
    }

    /**
     * 补位
     *
     * @param src         源
     * @param length      补充到的长度
     * @param paddingType 1:补0 2:补空格
     * @param leftOrRight 1:左补 2:右补
     * @return
     */
    public static String padding(String src, int length, int paddingType, int leftOrRight) {
        String padddingStr = null;
        StringBuffer buffer = new StringBuffer(src);
        if (paddingType == 1) {
            padddingStr = "0";
        } else {
            padddingStr = " ";
        }

        while (buffer.length() < length) {
            if (leftOrRight == 1) {
                buffer.insert(0, padddingStr);
            } else {
                buffer.append(padddingStr);
            }
        }
        return buffer.toString();
    }

    /**
     * 去除字符串左边的0
     *
     * @param src
     * @return
     */
    public static String removeZero(String src) {
        int j = 0;
        for (int i = 0; i < src.length(); i++) {
            if (src.charAt(i) == '0') {
                j++;
            } else {
                break;
            }
        }
        return src.substring(j);
    }

    /**
     * 生成固定的随机颜色代码
     *
     * @param count 生成的个数
     * @return
     */
    public static String createColorCode(int count) {
        Random random = new Random(5);
        String retVal = "";
        for (int i = 0; i < count; i++) {
            String r = Integer.toHexString(random.nextInt(256)).toUpperCase();
            String g = Integer.toHexString(random.nextInt(256)).toUpperCase();
            String b = Integer.toHexString(random.nextInt(256)).toUpperCase();
            r = r.length() == 1 ? "0" + r : r;
            g = g.length() == 1 ? "0" + g : g;
            b = b.length() == 1 ? "0" + b : b;
            retVal += "#" + r + g + b + ",";
        }
        if (retVal.length() > 0) {
            retVal = retVal.substring(0, retVal.length() - 1);
        }
        return retVal;
    }

    public static String getStrByArray(String[] array) {
        if (array == null || array.length == 0) {
            return "";
        }
        String retVal = "";
        for (int i = 0; i < array.length; i++) {
            retVal += array[i] + ",";
        }
        if (retVal.length() > 0) {
            retVal = retVal.substring(0, retVal.length() - 1);
        }
        return retVal;
    }

    public static String getStrByArray(Object[] array) {
        if (array == null || array.length == 0) {
            return "";
        }
        String retVal = "";
        for (int i = 0; i < array.length; i++) {
            retVal += array[i] + ",";
        }
        if (retVal.length() > 0) {
            retVal = retVal.substring(0, retVal.length() - 1);
        }
        return retVal;
    }

    /**
     * 返回BCD码表示的指定长度数字字符串
     *
     * @param number
     * @param width
     * @return
     */
    public static String getFormatedNumBcdString(int number, int width) {
        String bcdString = Integer.toString(number);

        int len = width - bcdString.length();

        for (int i = 0; i < len; i++) {
            bcdString = "0" + bcdString;
        }

        return bcdString;
    }

    /**
     * 返回右边不足位添加F的制定长度的字符串
     *
     * @param number
     * @param width
     * @return
     */
    public static String getRightFString(String number, int width) {

        int len = width - number.length();

        for (int i = 0; i < len; i++) {
            number = number + "F";
        }

        return number;
    }

    /**
     * 返回左边，多余截取，不足补，的定长字符串
     *
     * @param str     源
     * @param width   长度
     * @param padding 补的字符
     * @return
     */
    public static String getLeftString(String str, int width, char padding) {
        if (str == null)
            str = "";
        if (width < str.length()) {
            str = str.substring(0, width);
        } else {
            StringBuffer sb = new StringBuffer(str);
            while (width > sb.length()) {
                sb.append(padding);
            }
            str = sb.toString();
        }
        return str;
    }

    /**
     * 返回右边，多余截取，不足补，的定长字符串
     *
     * @param str     源
     * @param width   长度
     * @param padding 补的字符
     * @return
     */
    public static String getRightString(String str, int width, char padding) {
        if (str == null)
            str = "";
        if (width < str.length()) {
            str = str.substring(str.length() - width, str.length());
        } else {
            StringBuffer sb = new StringBuffer(str);
            while (width > sb.length()) {
                sb.insert(0, padding);
            }
            str = sb.toString();
        }
        return str;
    }

    public static String byteToHex(byte[] buffer) {
        StringBuffer hexString = new StringBuffer();
        String hex;
        int iValue;

        for (int i = 0; i < buffer.length; i++) {
            iValue = buffer[i];
            if (iValue < 0)
                iValue += 256;

            hex = Integer.toString(iValue, 16);
            if (hex.length() == 1)
                hexString.append("0" + hex);
            else
                hexString.append(hex);
        }

        return hexString.toString().toUpperCase();
    }

    /**
     * 转化时间格式
     *
     * @param src     原格式
     * @param target  目标格式
     * @param dateStr 时间字符串
     * @return
     */
    public static String formatDateStr(String src, String target, String dateStr) {
        if (isEmpty(src) || isEmpty(target) || isEmpty(dateStr)) {
            return null;
        }
        SimpleDateFormat srcDateFormat = new SimpleDateFormat(src);
        SimpleDateFormat targetDateFormat = new SimpleDateFormat(target);
        Date date = null;
        try {
            date = srcDateFormat.parse(dateStr);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return targetDateFormat.format(date);
    }

    public static String[] formatDateStr(String src, String target, String dateStr[]) {
        if (isEmpty(src) || isEmpty(target) || dateStr == null || dateStr.length == 0) {
            return null;
        }
        String retVal[] = new String[dateStr.length];
        for (int i = 0; i < dateStr.length; i++) {
            retVal[i] = formatDateStr(src, target, dateStr[i]);
        }
        return retVal;
    }

    /**
     * 这里共有2个时间段（b1-----e1）【b2-----e2】，4个时间点； 相当于两条线段(b代表起点，e代表端点，b<=e)，4个端点。 可分3种情况：
     * 1.不相交。（b1-----e1）【b2-----e2】（b1-----e1）。if(e1<b2||b1>e2)此时，重合天数为零。 返回x 2.相交。 情况一：（b1---【b2---e1）----e2】
     * if(b1<b2&&e1<e2&&e1>b2) 情况二：【b2---(b1---e2】----e1) if(b1>b2&&b1<e2&&e2<e1) 3.包含：计算较短的时间段日期长度。
     * （b1---【b2-----e2】--e1） if(b1<b2&&e1>e2) 【b2---（b1-----e1）--e2】 if(b1>b2&&e1<e2)
     *
     * @param begindate1     开始日期
     * @param enddate1       结束日期
     * @param enddate2       结束日期
     * @return
     */
    public static String getDayCoincidence(Date begindate1, Date enddate1, Date begindate2, Date enddate2) {
        long b1 = begindate1.getTime();
        long e1 = enddate1.getTime();
        long b2 = begindate2.getTime();
        long e2 = enddate2.getTime();
        assert (b1 < e1 && b2 < e2);
        String coincidenceday;
        if (b1 <= b2 && e1 >= e2) {// （b1---【b2-----e2】--e1）
            // System.out.println("1包含2");
            coincidenceday = getDayDifference(enddate2, begindate2);
        } else if (b1 >= b2 && e1 <= e2) {// 【b2---（b1-----e1）--e2】
            // System.out.println("2包含1");
            coincidenceday = getDayDifference(enddate1, begindate1);
        } else if (b1 >= b2 && b1 <= e2 && e2 <= e1) {// 【b2---(b1---e2】----e1)
            // System.out.println("相交");
            coincidenceday = getDayDifference(enddate2, begindate1);
        } else if (b1 <= b2 && e1 <= e2 && e1 >= b2) {// （b1---【b2---e1）----e2】
            // System.out.println("相交");
            coincidenceday = getDayDifference(enddate1, begindate2);
        } else if (e1 <= b2 || b1 >= e2) {
            coincidenceday = "x";
        } else {
            coincidenceday = "";
            // System.out.println("意料外的日期组合，无法计算重合天数！");
        }
        // System.out.println("重合天数为["+coincidenceday+"]天。");
        return coincidenceday;
    }

    /**
     * 计算两个日期的相差天数(d1-d2)
     *
     * @param d1
     * @param d2
     * @return
     */
    public static String getDayDifference(Date d1, Date d2) {
        StringBuffer ds = new StringBuffer();
        try {
            long num = (d1.getTime() - d2.getTime()) / 1000;
            long days = num / (3600 * 24);
            if (days >= 0)
                ds.append(days);
        } catch (Exception e) {
            ds = new StringBuffer("");
            e.printStackTrace();
        }
        return ds.toString();
    }

    /**
     * 比较2个时间段是否重叠
     *
     * @param start    第一个时间段的开始时间
     * @param end      第一个时间段的结束时间
     * @param comStart 第二个时间段的开始时间
     * @param comEnd   第二个时间段的结束时间
     * @return 重叠返回true 不重叠返回false
     */
    public static boolean checkDate(String start, String end, String comStart, String comEnd) {

        // 将时间加上年月日
        start = "1970-1-1 " + start;
        end = "1970-1-1 " + end;
        comStart = "1970-1-1 " + comStart;
        comEnd = "1970-1-1 " + comEnd;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String retVal = "";
        try {
            retVal = getDayCoincidence(dateFormat.parse(start), dateFormat.parse(end), dateFormat.parse(comStart),
                    dateFormat.parse(comEnd));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 结果为x证明不重叠
        if (retVal.equals("x")) {
            return false;
        }
        return true;

    }

    /**
     * java去除字符串中的空格、回车、换行符、制表符
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {

            Pattern p = Pattern.compile("\\s*|\t|\r|\n");

            Matcher m = p.matcher(str);

            dest = m.replaceAll("");

        }

        return dest;
    }

    /**
     * ArrayList转json字符串
     *
     * @param list
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String list2JsonStr(List list) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (list != null && list.size() > 0) {
            for (Object obj : list) {
                json.append(objectToJson(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    public static String objectToJson(Object object) {
        StringBuilder json = new StringBuilder();
        if (object == null) {
            json.append("\"\"");
        } else if (object instanceof String || object instanceof Integer) {
            json.append("\"").append(object.toString()).append("\"");
        } else {
            json.append(beanToJson(object));
        }
        return json.toString();
    }

    public static String beanToJson(Object bean) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        PropertyDescriptor[] props = null;
        try {
            props = Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors();
        } catch (IntrospectionException e) {
        }
        if (props != null) {
            for (int i = 0; i < props.length; i++) {
                try {
                    String name = objectToJson(props[i].getName());
                    String value = objectToJson(props[i].getReadMethod().invoke(bean));
                    json.append(name);
                    json.append(":");
                    json.append(value);
                    json.append(",");
                } catch (Exception e) {
                }
            }
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        return json.toString();
    }

    /**
     * 移除给定字符串结尾处的特定字符。
     *
     * @param value 要修剪的字符串。
     * @param chars 要移除的字符。
     * @return 修剪后的字符串。
     */
    public static String trimEnd(final String value, final char... chars) {
        if (null == value || value.length() == 0) {
            return value;
        }
        Arrays.sort(chars);
        int endIndex = value.length() - 1;
        boolean flag = containsChar(chars, value.charAt(endIndex));
        while (flag) {
            endIndex--;
            flag = containsChar(chars, value.charAt(endIndex));
        }
        if (0 >= endIndex) {
            return "";
        }
        return value.substring(0, endIndex + 1);
    }

    /**
     * 把obect转化成字符串，及时对象不是String类型的，也转换对应的字符串
     *
     * @param obj 任何对象
     * @return
     */
    public static String trimStr(Object obj) {
        if (null == obj || "".equals(obj)) {
            return "";
        } else if (obj instanceof String) {
            return obj.toString().trim();
        } else {
            return obj.toString();
        }
    }

    /**
     * 拼接字符串
     *
     * @return 字符串
     */
    public static String concat(Object... objarr) {
        StringBuffer sbf = new StringBuffer("");
        for (Object object : objarr) {

            sbf.append(trimStr(object));
        }
        return sbf.toString();
    }

    /**
     * 判断object对象是否为空
     */
    public static boolean isEmpty(Object obj) {
        return isEmpty(trimStr(obj));
    }

    /**
     * 判断object对象是否不为空
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(trimStr(obj));
    }

    /**
     * 判断给定的字符数组（已排序）中是否包含给定的字符。
     *
     * @param chars 已经排序的字符数组。
     * @param ch    要检查的字符。
     * @return 如果 ch 存在于 chars 中则返回 true；否则返回 false。
     */
    public static final boolean containsChar(final char[] chars, final char ch) {
        return Arrays.binarySearch(chars, ch) >= 0;
    }

    /**
     * 判断手机号是否合格
     *
     * @param phonenumber
     * @return
     */
    public static boolean isPhoneValid(String phonenumber) {
        try {
            if (StringUtils.isBlank(phonenumber)) {
                return false;
            }
            if (!phonenumber.matches("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$")) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String removeEnd(String str, String end) {
        if (!isEmpty(str)) {
            str = str.endsWith(end) ? str.substring(0, str.length() - 1) : str;
        }
        return str;
    }

    public static String toString(Object obj) {
        if (null != obj) {
            return obj.toString().trim();
        }
        return "";
    }

    public static Integer toInteger(Object obj) {
        if (StringUtil.isNotEmpty(obj)) {
            return Integer.parseInt(obj.toString());
        } else {
            return 0;
        }
    }

    public static Long toLong(Object obj) {
        return Long.parseLong(StringUtil.toString(obj));
    }

    public static String substring(String str, int start, int end) {
        int length = str.length();
        if (length > end) {
            str = str.substring(start, end);
        }
        return str;
    }

    /**
     * 下划线转驼峰
     *
     * @param underline
     * @return
     */
    public static String underline2Camel(String underline) {
        Pattern pattern = Pattern.compile("[_]\\w");
        String camel = underline.toLowerCase();
        Matcher matcher = pattern.matcher(camel);
        while (matcher.find()) {
            String w = matcher.group().trim();
            camel = camel.replace(w, w.toUpperCase().replace("_", ""));
        }
        return camel;
    }

    /**
     * 隐藏中间的字符串，默认用*替换
     *
     * @param str
     * @param replaceStr
     * @param startLength
     * @param endLength
     * @return
     */
    public static String hideStringMiddle(String str, String replaceStr, int startLength, int endLength) {
        if (isEmpty(str) || str.length() <= (startLength + endLength)) {
            return str;
        }
        if (isEmpty(replaceStr)) {
            replaceStr = "*";
        }
        StringBuilder replaceStrSB = new StringBuilder();
        for (int i = 0; i < (str.length() - startLength - endLength); i++) {
            replaceStrSB.append(replaceStr);
        }
        StringBuilder newStrSB = new StringBuilder();
        newStrSB.append(str.substring(0, startLength)).append(replaceStrSB)
                .append(str.substring(str.length() - endLength, str.length()));
        return newStrSB.toString();
    }

    public static String getPrefix(String str, String SEP) {
        if (str == null || SEP == null) return str;

        int i = str.lastIndexOf(SEP);

        return (i < 0) ? str : str.substring(0, i);
    }

    public static String getSuffix(String str, String SEP) {
        if (str == null || SEP == null) return str;

        int i = str.lastIndexOf(SEP);

        return (i < 0) ? null : str.substring(i + SEP.length());
    }

}
