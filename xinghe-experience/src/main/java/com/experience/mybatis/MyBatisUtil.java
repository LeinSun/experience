package com.experience.mybatis;

import com.experience.tree.bean.BuzApiOutput;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: sunlei
 * @date: 2022/4/18
 * @description:
 */
public class MyBatisUtil {
    // 获取bean的属性 根据属性评价 resultMap
    public static String getResultMap(Class<?> cls) throws Exception {
        String str = "";
        // 每一行字符串 <result column="BID_SECTION_CODE" property="BID_SECTION_CODE"
        // jdbcType="VARCHAR" />
        String linestr = "";
        Field[] declaredFields = cls.getDeclaredFields();
        for (Field field : declaredFields) {
//            if (field.getType().getName().equals("java.lang.String")) {
//                linestr = "<result column=\"" + field.getName() + "\" property=\"" + field.getName()
//                        + "\" jdbcType=\"VARCHAR\" />";
//            } else if ((field.getType().getName().equals("java.lang.Long"))){
//                linestr = "<result column=\"" + field.getName() + "\" property=\"" + field.getName()
//                        + "\" jdbcType=\"BIGINT\" />";
//            }else {
//                linestr = "<result column=\"" + field.getName() + "\" property=\"" + field.getName()
//                        + "\" jdbcType=\"INTEGER\" />";
//            }
            linestr = "<result column=\"" + field.getName() + "\" property=\"" + field.getName() + " />";
            System.out.println(linestr);
        }

        return str;
    }

    // 获取bean的属性 根据属性评价 resultMap
    // 并将驼峰修改为'_'
    public static String getResultMapNew(Class<?> cls) throws Exception {
        String str = "";
        // 头部 <resultMap id="BaseResultMap" type="com.huajie.entity.sys.SysMenuinfo">
        str = "<resultMap id=\"" + cls.getSimpleName() + "ResultMap\" type=\"" + cls.getName() + "\"> \r\n";
        // 每一行字符串
        String linestr = "";
//		Field[] declaredFields = cls.getDeclaredFields();
        List<Field> fieldList = new ArrayList<>();
        while (cls != null){
            fieldList.addAll(new ArrayList<>(Arrays.asList(cls.getDeclaredFields())));
            cls = cls.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        Field[] declaredFields = fieldList.toArray(fields);
        for (Field field : declaredFields) {
            if (field.getType().getName().equals("java.lang.String")) {
                linestr = "<result column=\"" + getUpCaseReplace(field.getName()) + "\" property=\"" + field.getName()
                        + "\" jdbcType=\"VARCHAR\" />";
            } else {
                linestr = "<result column=\"" + getUpCaseReplace(field.getName()) + "\" property=\"" + field.getName()
                        + "\" jdbcType=\"INTEGER\" />";
            }
            linestr += "\r\n";
            str += linestr;
        }
        str+="</resultMap>";
        return str;
    }

    // 获取Base_Column_List sql语句字段
    public static String getColumnList(Class<?> cls) throws Exception {
        // 每一行字符串 <result column="BID_SECTION_CODE" property="BID_SECTION_CODE"
        // jdbcType="VARCHAR" />
        String linestr = "";
        Field[] declaredFields = cls.getDeclaredFields();
        for (Field field : declaredFields) {
            linestr = linestr + field.getName() + ",";
        }
        String str = linestr.substring(0, linestr.length() - 1);
        System.out.println(str);
        return str;
    }

    /**
     * 将字符串中的驼峰写法替换成'_'
     *
     * @param str
     * @return
     */
    private static String getUpCaseReplace(String str) {
        List<String> listChar = getUpCaseList(str);
        for (int i = 0; i < listChar.size(); i++) {
            str = str.replace(listChar.get(i), "_" + listChar.get(i).toLowerCase());
        }
        return str;
    }

    /**
     * @Description: 输出字符串中的大写字母
     * @param str
     */
    private static List<String> getUpCaseList(String str) {
        List<String> listChar = new ArrayList<String>();
        // 转为char数组
        char[] ch = str.toCharArray();
        // 得到大写字母
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] >= 'A' && ch[i] <= 'Z') {
                listChar.add(String.valueOf(ch[i]));
            }
        }
        return listChar;
    }

    /**
     * @Description: 输出字符串中的大写字母
     * @param cls
     */
    private static String getColumnListNew(Class<?> cls) throws Exception {
        // 每一行字符串 <result column="BID_SECTION_CODE" property="BID_SECTION_CODE"
        // jdbcType="VARCHAR" />
        String linestr = "";
        Field[] declaredFields = cls.getDeclaredFields();
        for (Field field : declaredFields) {
            linestr = linestr + getUpCaseReplace(field.getName()) + ",";
        }
        String str = linestr.substring(0, linestr.length() - 1);
        System.out.println(str);
        return str;
    }

    public static void main(String[] args) throws Exception {
        BuzApiOutput input = new BuzApiOutput();
        System.out.println(getResultMapNew(input.getClass()));
        // getColumnListNew(a.getClass());
    }

}
