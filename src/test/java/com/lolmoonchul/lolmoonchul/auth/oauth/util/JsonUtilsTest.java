package com.lolmoonchul.lolmoonchul.auth.oauth.util;

import static org.assertj.core.api.Assertions.assertThat;

import com.lolmoonchul.lolmoonchul.auth.oauth.application.dto.OauthMember;
import com.lolmoonchul.lolmoonchul.auth.oauth.application.dto.Properties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JsonUtilsTest {

    @Autowired
    private JsonUtils jsonUtils;

    @Test
    @DisplayName("oauth 파싱 테스트")
    void shouldOauthMemberParsing() {
        //given
        final String oauthMemberJson = " {\"id\":2550061694,\"connected_at\":\"2022-11-25T15:23:51Z\",\"properties\":{\"nickname\":\"John\",\"profile_image\":\"http://t1.kakaocdn.net/account_images/default_profile.jpeg.twg.thumb.R640x640\",\"thumbnail_image\":\"http://t1.kakaocdn.net/account_images/default_profile.jpeg.twg.thumb.R110x110\"},\"kakao_account\":{\"profile_nickname_needs_agreement\":false,\"profile_image_needs_agreement\":false,\"profile\":{\"nickname\":\"John\",\"thumbnail_image_url\":\"http://t1.kakaocdn.net/account_images/default_profile.jpeg.twg.thumb.R110x110\",\"profile_image_url\":\"http://t1.kakaocdn.net/account_images/default_profile.jpeg.twg.thumb.R640x640\",\"is_default_image\":true,\"is_default_nickname\":false}}}\nz";
        final String expectedValue = "http://t1.kakaocdn.net/account_images/default_profile.jpeg.twg.thumb.R110x110";

        //when
        OauthMember oauthMember = jsonUtils.extractOauthMember(oauthMemberJson);

        OauthMember oauthMember1 = new OauthMember(1L, new Properties("axa", expectedValue));

        //then
        assertThat(oauthMember.properties().thumbnailImage()).isEqualTo(expectedValue);
    }
}