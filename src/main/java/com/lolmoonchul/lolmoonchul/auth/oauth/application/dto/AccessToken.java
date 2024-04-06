package com.lolmoonchul.lolmoonchul.auth.oauth.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AccessToken(@JsonProperty("access_token") String accessToken) {

}
