/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: SortUtil
 * Author:   adm
 * Date:     2018/12/26 14:18
 * Description: 排序工具
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.java.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈排序工具〉
 *
 * @author adm
 * @create 2018/12/26
 * @since 1.0.0
 */
public class SortUtil {
    /**读取本地文件
     * @param fileName
     * @return
     */
    public static String getFile(String fileName){
        String res="";
        try {
            StringBuffer buffer = new StringBuffer();
            BufferedReader bf= null;
                bf = new BufferedReader(new FileReader(fileName));
            String s = null;
            while((s = bf.readLine())!=null){//使用readLine方法，一次读一行
                buffer.append(s.trim());
            }
            res = buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**根据jsonobject的某个字段排序，该字段为数字顺序
     * @param arrayStr
     * @return
     */
    public static String sortJsonArray(String arrayStr,String sortKey){
        JSONArray array=JSON.parseArray(arrayStr);
        JSONArray sortArray=new JSONArray();
        List<JSONObject> jsonValue=new ArrayList<JSONObject>();
        for(int i=0;i<array.size();i++){
            jsonValue.add(array.getJSONObject(i));
        }
        Collections.sort(jsonValue,new Comparator<JSONObject>() {
            private final String key=sortKey;
            public int compare(JSONObject a, JSONObject b) {
                Integer valA=a.getIntValue(key);
                Integer valB=b.getIntValue(key);
                return valA.compareTo(valB);
            }
        });
        for(int i=0;i<array.size();i++){
            sortArray.add(jsonValue.get(i));
        }
        return sortArray.toJSONString();
    }

    public static void main(String[] args) {
        String fileName="C:\\Users\\adm\\Desktop\\dept.txt";
    }
}


