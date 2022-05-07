package com.experience.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: sunlei
 * @date: 2022/5/7
 * @description:
 */
public class DynamicGenerateJson {
    static Pattern arrayPattern = Pattern.compile("\\[(\\d+)]");
    static Configuration configuration = Configuration.builder().options(Option.DEFAULT_PATH_LEAF_TO_NULL).build();
    /**
     * <p/>将map中的key（如：x.y.z）转换成多层级的JSONObject, 也支持 x[0].y 的形式<p/>
     * 例如 ("data.id","1"),("list[0].id","20");
     * 转换成:
     * <p/>
     * <pre>
     * {"data":{
     *      "id": "1"
     *  },
     *  "list":[
     *      {
     *          "id": "20"
     *      }
     *  ]
     * }
     * </pre>
     * @param paramMap
     * @return
     */
    public static JSONObject mapToJsonObject(Map<String, String> paramMap){
        JSONObject result = new JSONObject();
        Matcher matcher = null;
        Iterator<Map.Entry<String, String>> iterator = paramMap.entrySet().iterator();
        ParseContext parser = JsonPath.using(configuration);
        while (iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();
            String[] sources = key.replace("out.", "").split("\\.");
            StringBuffer buffer = new StringBuffer("$");
            for (int j = 0; j < sources.length - 1; j++) {
                String source = sources[j];
                matcher = arrayPattern.matcher(source);
                DocumentContext context = parser.parse(result);
                if (!matcher.find()) {
                    JSONObject curObject = context.read(new StringBuffer(buffer).append(".").append(source).toString());
                    if (curObject == null){
                        result = context.put(buffer.toString(),source,new JSONObject()).json();
                    }
                } else {
                    String p = source.substring(0, source.indexOf("["));
                    JSONArray subArray = context.read(new StringBuffer(buffer).append(".").append(p).toString());
                    if(subArray == null){
                        subArray = new JSONArray();
                    }
                    int arrayIndex = Integer.parseInt(matcher.group(1));
                    for (int i = subArray.size(); i <= arrayIndex; i++){
                        subArray.add(new JSONObject());
                    }
                    result = context.put(buffer.toString(),p,subArray).json();
                }
                buffer.append(".").append(source);
            }
            if (result != null) {
                result = parser.parse(result).put(buffer.toString(),sources[sources.length - 1],value).json();
            }
        }
        return result;
    }

    public void testMapToJsonObject(){
        Map<String, String> request = new HashMap<>();
        request.put("name","徽园");
        request.put("data.page.id","1");
        request.put("list[1].id","20");
        request.put("list[1].name","test");
        request.put("data.list[1].array[1].id","20");
        mapToJsonObject(request);
        System.out.printf("");
    }
}
