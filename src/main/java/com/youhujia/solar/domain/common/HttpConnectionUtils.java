package com.youhujia.solar.domain.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by ListenYoung on 2017/3/21.
 */
public class HttpConnectionUtils {
    /**
     * 通过java发送一个GET请求
     * @param url 请求的url
     * @param params 参数的集合，如"key=1&v=2",传null或者空字符串表示没有参数或者前一个参数url为带参数的完整url
     * @return 请求的返回结果，为StringBuffer对象
     */
    public static StringBuffer sendGETRequest(String url, String params){
        StringBuffer result = new StringBuffer();
        BufferedReader in = null;
        String urlString = null;
        if(params != null && !"".equals(params)){
            urlString = url + "?" + params;
        }else{
            urlString = url;
        }
        try {
            URL realUrl = new URL(urlString);
            //打开连接
            URLConnection connection = realUrl.openConnection();
            //设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //建立真正的连接
            connection.connect();
            //获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            //定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String temp;
            while ((temp = in.readLine()) != null){
                temp = new String(temp.getBytes(), "utf-8");
                result.append(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    in = null;
                }
            }
        }

        return result;
    }

    /**
     * 通过java代码发送一条post请求，params来传递参数
     * @param url 发送请求的url
     * @param params 参数列表，如"key=1&v=2"
     * @return 请求响应结果的StringBuffer对象
     */
    public static StringBuffer sendPOSTRequestWithParams(String url, String params){
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            //打开连接
            URLConnection connection = realUrl.openConnection();
            //设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //发送POST请求必须设置如下两行
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();
            //获取URLConnection对象的输出流
            out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            //发送请求参数
            out.write(params);
            out.flush();
            //获取输入流来读取Response
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String temp;
            while ((temp = in.readLine()) != null){
                temp = new String(temp.getBytes(), "utf-8");
                result.append(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    out = null;
                }
            }

            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    in = null;
                }
            }
        }

        return result;
    }

    /**
     * 通过java代码发送json body POST请求
     * @param url 请求的url地址
     * @param jsonString json字符串
     * @return 请求的response的StringBuffer对象
     */
    public static StringBuffer sendPOSTRequestWithJSON(String url, String jsonString){
        StringBuffer result = new StringBuffer();
        OutputStreamWriter out = null;
        BufferedReader reader = null;
        HttpURLConnection connection = null;
        try {
            URL realURL = new URL(url);
            connection = (HttpURLConnection)realURL.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.connect();
            //post请求
            out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            //发送请求参数
            out.write(jsonString);
            out.flush();
            //读取响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String temp;
            while((temp = reader.readLine()) != null){
                temp = new String(temp.getBytes(), "utf-8");
                result.append(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(connection != null){
                connection.disconnect();
            }

            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    reader = null;
                }
            }

            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    out = null;
                }
            }
        }

        return result;
    }
}
