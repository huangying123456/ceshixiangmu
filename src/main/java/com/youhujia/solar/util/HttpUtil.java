package com.youhujia.solar.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youhujia.halo.common.YHJExceptionCodeEnum;
import com.youhujia.halo.util.LogInfoGenerator;
import com.youhujia.halo.util.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Created by hy on 2016/12/1.
 */
public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static String getUrlAsStr(String url) {
        String where = "HttpUtil->getUrlAsStr";
        try {
            URL website = new URL(url);
            URLConnection connection = website.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));

            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);

            in.close();

            return response.toString();
        } catch (Exception e) {
            logger.error(LogInfoGenerator.generateErrorInfo(where, YHJExceptionCodeEnum.THE_THIRD_PARTY_EXCEPTION, "exception", "url", url));

            return null;
        }
    }

    public static String post(String url, String payload) {

        String where = "HttpUtil->post";


        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);

        try {
            HttpEntity payloadEntity = new StringEntity(payload, Charset.forName("UTF-8"));
            httppost.setEntity(payloadEntity);

            //Execute and get the response.
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String str = EntityUtils.toString(entity);
                logger.debug(LogInfoGenerator.generateCallInfo(where, "url", url+str, "payload", payload));

                return str;
            }
        } catch (IOException e) {
            logger.error(LogInfoGenerator.generateErrorInfo(where, YHJExceptionCodeEnum.THE_THIRD_PARTY_EXCEPTION, "exception", "url", url));

            return null;
        }

        return null;
    }

    public static JsonNode getUrlAsJSON(String url){

        String where = "HttpUtil->getUrlAsJson";

        String content = getUrlAsStr(url);
        logger.info("getUrlAsJson()\nurl:" + url + "\ncontent:" + content);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(content);

            return jsonNode;
        } catch (IOException e) {
            logger.error(LogInfoGenerator.generateErrorInfo(where, YHJExceptionCodeEnum.THE_THIRD_PARTY_EXCEPTION, "exception", "url", url, "message", e.getMessage()));

            return objectMapper.createObjectNode();
        }
    }
}