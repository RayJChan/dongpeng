package com.dpmall.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonToFile {

    /**
     * 解析地址
     *address
     * @return
     * @author lin
     */
    public static void addressResolution() throws IOException {
        List<String> list = JsonToFile.readFile02("C:/Users/Administrator/Desktop/test111.txt");
        StringBuffer sb=new StringBuffer();
        for (String json : list){
            List l = new ArrayList<String>();
            JSONObject jsonObject = JSON.parseObject(json);
            Map<Object, Object> map = new LinkedHashMap<>();
            map = (Map)jsonObject;
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
//                System.out.println(entry.getKey() + ":" +map.entrySet());
                sb.append(entry.getValue()).append(";");
            }
            sb.append(" ;\r\n");
//            System.out.println(sb.toString());
        }
        File file = new File("C:/Users/Administrator/Desktop/text333.txt");
        FileOutputStream out=new FileOutputStream(file,false);
        out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
        out.flush();
    }

    public static List<String> readFile02(String path) throws IOException {
        // 使用一个字符串集合来存储文本中的路径 ，也可用String []数组
        List<String> list = new ArrayList<String>();
        FileInputStream fis = new FileInputStream(path);
        // 防止路径乱码   如果utf-8 乱码  改GBK     eclipse里创建的txt  用UTF-8，在电脑上自己创建的txt  用GBK
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        while ((line = br.readLine()) != null) {
            // 如果 t x t文件里的路径 不包含---字符串       这里是对里面的内容进行一个筛选
            if (line.lastIndexOf("---") < 0) {
                list.add(line);
            }
        }
        br.close();
        isr.close();
        fis.close();
        return list;
    }


    public static void main(String args[]){
        try {
            JsonToFile.addressResolution();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
