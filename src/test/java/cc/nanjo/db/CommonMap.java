package cc.nanjo.db;

import java.util.HashMap;
import java.util.Map;

public class CommonMap {
    /**
     * 状态编码转换
     */
    public static Map<String, String> javaTypeMap = new HashMap<String, String>();

    static {
        initJavaTypeMap();
    }

    /**
     * 返回状态映射
     */
    public static void initJavaTypeMap() {
        javaTypeMap.put("tinyint", "Integer");
        javaTypeMap.put("smallint", "Integer");
        javaTypeMap.put("mediumint", "Integer");
        javaTypeMap.put("int", "Integer");
        javaTypeMap.put("integer", "integer");
        javaTypeMap.put("bigint", "Long");
        javaTypeMap.put("float", "Float");
        javaTypeMap.put("double", "Double");
        javaTypeMap.put("decimal", "Double");
        // javaTypeMap.put("decimal", "BigDecimal");
        javaTypeMap.put("bit", "Boolean");
        javaTypeMap.put("char", "String");
        javaTypeMap.put("varchar", "String");
        javaTypeMap.put("tinytext", "String");
        javaTypeMap.put("text", "String");
        javaTypeMap.put("mediumtext", "String");
        javaTypeMap.put("longtext", "String");
        javaTypeMap.put("time", "LocalDateTime");
        javaTypeMap.put("date", "LocalDate");
        javaTypeMap.put("datetime", "LocalDateTime");
        javaTypeMap.put("timestamp", "LocalDateTime");

        javaTypeMap.put("tablePrefix", "");// 前缀
        javaTypeMap.put("package", "com.waterelephant.common");
        javaTypeMap.put("author", "HUANGJIN");
        javaTypeMap.put("jdk", "1.8");
        javaTypeMap.put("version", "1.0");
    }
}
