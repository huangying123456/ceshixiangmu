package com.youhujia.solar.util;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mmliu on 04/11/2016.
 */
public class WxUtil {
    private static final Logger log = LoggerFactory.getLogger(WxUtil.class);


    public static UInfo getUserInfo(String code, String wxAppId, String wxSecret){
        String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
                wxAppId, wxSecret, code);

        JsonNode jsonObject = HttpUtil.getUrlAsJSON(url);

        JsonNode accessTokenNode = jsonObject.get("access_token");
        JsonNode refreshTokenNode = jsonObject.get("refresh_token");
        JsonNode openIdNode = jsonObject.get("openid");

        if(accessTokenNode == null || refreshTokenNode == null || openIdNode == null){
            return null;
        }

        String accessToken = accessTokenNode.asText();
        String refreshToken = refreshTokenNode.asText();
        String openId = openIdNode.asText();

        return getUserInfo(openId, accessToken);
    }

    public static String getOpenId(String code, String wxAppId, String wxSecret){
        String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
                wxAppId, wxSecret, code);

        JsonNode jsonObject = HttpUtil.getUrlAsJSON(url);

        JsonNode openIdNode = jsonObject.get("openid");

        if(openIdNode == null){
            return null;
        }

        return openIdNode.asText();
    }

    /**
     * 第四步：拉取用户信息(需scope为 snsapi_userinfo)
     *
     * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842&token=&lang=zh_CN
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public static UInfo getUserInfo(String openId, String accessToken){
        log.debug("going to get user info");

        String urlTemplate = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

        String url = String.format(urlTemplate, accessToken, openId);

        JsonNode jsonObject = HttpUtil.getUrlAsJSON(url);

        if(jsonObject.size() == 0 || jsonObject.has("errcode")){
            String errCode = jsonObject.get("errcode").asText();

            log.warn("errCode when fetch user info:" + errCode + ", complete resp:" + jsonObject.toString());

            return null;
        }else{
            String nickname = filterEmoji(jsonObject.get("nickname").asText());
            String sex = jsonObject.get("sex").asText();
            String headImgUrl = jsonObject.get("headimgurl").asText();

            return new UInfo(nickname, headImgUrl, openId);
        }
    }

    public static boolean hasSubscribed(String openId, String appkey, String secret){
        String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
        JsonNode jsonNode = HttpUtil.getUrlAsJSON(String.format(getAccessTokenUrl, appkey, secret));

        JsonNode tokenNode = jsonNode.get("access_token");
        String accessToken = tokenNode.asText();

        String uInfoUrlTemplate = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
        JsonNode jNode = HttpUtil.getUrlAsJSON(String.format(uInfoUrlTemplate, accessToken, openId));

        return jNode.get("subscribe").asInt() != 0;
    }

    public static class UInfo {
        public String nickname;
        public String headimgurl;
        public String openId;

        public UInfo(String nickname, String headimgurl, String openId){
            this.nickname = nickname;
            this.headimgurl = headimgurl;
            this.openId = openId;
        }
    }

    static String regexPattern = "[\uD83C-\uDBFF\uDC00-\uDFFF]+";
    static Pattern pattern = Pattern.compile(regexPattern);
    /**
     * http://stackoverflow.com/a/25858015/404145
     *
     * fail some case(bear): http://www.oschina.net/question/89964_105220
     *
     * @param source
     * @return
     */
    private static String filterEmoji(String source) {
        log.info("nickname before filter:" + source);

        Matcher matcher = pattern.matcher(source);

        String filtered = matcher.replaceAll("");

        log.info("nickname after filter:" + filtered);

        return filtered;
    }
}
