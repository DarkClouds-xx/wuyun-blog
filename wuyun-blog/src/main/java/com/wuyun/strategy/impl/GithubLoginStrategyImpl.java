package com.wuyun.strategy.impl;

import com.alibaba.fastjson2.JSON;
import com.wuyun.config.properties.GithubProperties;
import com.wuyun.exception.ServiceException;
import com.wuyun.model.dto.GitDTO;
import com.wuyun.model.vo.GitUserInfoVO;
import com.wuyun.model.vo.SocialTokenVO;
import com.wuyun.model.vo.SocialUserInfoVO;
import com.wuyun.model.vo.TokenVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.wuyun.constant.SocialLoginConstant.*;
import static com.wuyun.enums.LoginTypeEnum.GITHUB;


/**
 * Github登录策略
 *
 * @author DarkClouds
 * @date 2023/05/22
 */
@Service("githubLoginStrategyImpl")
public class GithubLoginStrategyImpl extends AbstractLoginStrategyImpl {

    @Autowired
    private GithubProperties githubProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public SocialTokenVO getSocialToken(String data) {
        GitDTO gitDTO = JSON.parseObject(data, GitDTO.class);
        // 获取Github的Token
        TokenVO githubToken = getGithubToken(gitDTO.getCode());
        // 返回Github的Token信息
        return SocialTokenVO.builder()
                .accessToken(githubToken.getAccess_token())
                .loginType(GITHUB.getLoginType())
                .build();
    }

    @Override
    public SocialUserInfoVO getSocialUserInfo(SocialTokenVO socialToken) {
        // 请求参数
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + socialToken.getAccessToken());
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(null, headers);
        // Gitee用户信息
        GitUserInfoVO gitUserInfoVO = restTemplate.exchange(githubProperties.getUserInfoUrl(),
                HttpMethod.GET,
                requestEntity,
                GitUserInfoVO.class).getBody();
        // 返回用户信息
        return SocialUserInfoVO.builder()
                .avatar(Objects.requireNonNull(gitUserInfoVO).getAvatar_url())
                .id(gitUserInfoVO.getId())
                .nickname(gitUserInfoVO.getLogin()).build();
    }

    /**
     * 获取Github的Token
     *
     * @param code 第三方code
     * @return {@link TokenVO} Github的Token
     */
    private TokenVO getGithubToken(String code) {
        // 根据code换取accessToken
        MultiValueMap<String, String> githubData = new LinkedMultiValueMap<>();
        // Github的Token请求参数
        githubData.add(CLIENT_ID, githubProperties.getClientId());
        githubData.add(CLIENT_SECRET, githubProperties.getClientSecret());
        githubData.add(REDIRECT_URI, githubProperties.getRedirectUrl());
        githubData.add(CODE, code);
        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mediaTypes = Collections.singletonList(MediaType.APPLICATION_JSON);
        headers.setAccept(mediaTypes);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(githubData, headers);
        try {
            return restTemplate.exchange(githubProperties.getAccessTokenUrl(),
                    HttpMethod.POST,
                    requestEntity,
                    TokenVO.class).getBody();
        } catch (Exception e) {
            throw new ServiceException("Github登录错误");
        }
    }
}