package com.youhujia.solar.wxQrcode;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.zxing.WriterException;
import com.youhujia.halo.util.LogInfoGenerator;
import com.youhujia.solar.common.SolarExceptionCodeEnum;
import com.youhujia.solar.util.HttpUtil;
import com.youhujia.solar.util.QRCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by dam0n on 2017/4/21.
 */

@Service
public class WechatQRCodeBO {

    private final String WxSceneCodeForDptIdPrefix = "departmentId_";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${wx.appid}")
    String wxAppid;

    @Value("${wx.secret}")
    String wxSecret;

    public String generateWxSubQRCodeBase64Image(Long departmentId) throws IOException, WriterException {
        ObjectMapper objectMapper = new ObjectMapper();
        String accessToken = getAccessToken();
        String wxUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken;

        ObjectNode sceneObjectNode = objectMapper.createObjectNode();
        sceneObjectNode.put("scene_str", WxSceneCodeForDptIdPrefix + departmentId);

        ObjectNode actionInfoNode = objectMapper.createObjectNode();
        actionInfoNode.set("scene", sceneObjectNode);

        ObjectNode actionNode = objectMapper.createObjectNode();
        actionNode.put("action_name", "QR_LIMIT_STR_SCENE");
        actionNode.set("action_info", actionInfoNode);

        String resp = HttpUtil.post(wxUrl, actionNode.toString());

        String url = getUrl(resp);
        return QRCodeUtils.getImageBase64Src(url, 250, 250, QRCodeUtils.PNG_FORMAT);
    }

    private String getAccessToken() {

        String where = "WechatQRCodeBO->getAccessToken";

        String urlTemplate = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

        String url = String.format(urlTemplate, wxAppid, wxSecret);

        String respJson = HttpUtil.getUrlAsStr(url);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(respJson);
            String token = jsonNode.get("access_token").asText();

//            logger.debug("get token:" + token);
            logger.info(LogInfoGenerator.generateCallInfo(where, "token", token));
            return token;
        } catch (IOException e) {
            logger.info(LogInfoGenerator.generateErrorInfo(where, SolarExceptionCodeEnum.UNKNOWN_ERROR, "exception","json", respJson, "message", e.getMessage()));
        }
        return null;
    }

    private String getUrl(String respJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(respJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = jsonNode.get("url").asText();
//        System.out.println("resp json:" + jsonNode.toString());

        return url;
    }
}
