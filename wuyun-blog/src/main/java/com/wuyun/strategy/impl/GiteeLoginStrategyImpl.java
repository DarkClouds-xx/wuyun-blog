package com.wuyun.strategy.impl;

import com.alibaba.fastjson2.JSON;
import com.wuyun.config.properties.GiteeProperties;
import com.wuyun.exception.ServiceException;
import com.wuyun.model.dto.GitDTO;
import com.wuyun.model.vo.GitUserInfoVO;
import com.wuyun.model.vo.SocialTokenVO;
import com.wuyun.model.vo.SocialUserInfoVO;
import com.wuyun.model.vo.TokenVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.wuyun.constant.SocialLoginConstant.*;
import static com.wuyun.enums.LoginTypeEnum.GITEE;


/**
 * Gitee登录策略
 *
 * @author DarkClouds
 * @date 2023/05/22
 */
@Service("giteeLoginStrategyImpl")
@RequiredArgsConstructor
public class GiteeLoginStrategyImpl extends AbstractLoginStrategyImpl {

    private final GiteeProperties giteeProperties;

    private final RestTemplate restTemplate;

    @Override
    public SocialTokenVO getSocialToken(String data) {
        GitDTO gitDTO = JSON.parseObject(data, GitDTO.class);
        // 获取Gitee的Token
        TokenVO giteeToken = getGiteeToken(gitDTO.getCode());
        // 返回Gitee的Token信息
        return SocialTokenVO.builder()
                .accessToken(giteeToken.getAccess_token())
                .loginType(GITEE.getLoginType())
                .build();
    }

    @Override
    public SocialUserInfoVO getSocialUserInfo(SocialTokenVO socialToken) {
        Map<String, String> dataMap = new HashMap<>(1);
        // 请求参数
        dataMap.put(ACCESS_TOKEN, socialToken.getAccessToken());
        // Gitee用户信息
        GitUserInfoVO gitUserInfoVO = restTemplate.getForObject(giteeProperties.getUserInfoUrl(), GitUserInfoVO.class, dataMap);
        // 返回用户信息
        return SocialUserInfoVO.builder()
                .avatar(Objects.requireNonNull(gitUserInfoVO).getAvatar_url())
                .id(gitUserInfoVO.getId())
                .nickname(gitUserInfoVO.getName()).build();
    }

    /**
     * 获取Gitee的Token
     *
     * @param code 第三方code
     * @return {@link TokenVO} Gitee的Token
     */
    private TokenVO getGiteeToken(String code) {
        // 根据code换取accessToken
        MultiValueMap<String, String> giteeData = new LinkedMultiValueMap<>();
        // Gitee的Token请求参数
        giteeData.add(CLIENT_ID, giteeProperties.getClientId());
        giteeData.add(CLIENT_SECRET, giteeProperties.getClientSecret());
        giteeData.add(GRANT_TYPE, giteeProperties.getGrantType());
        giteeData.add(REDIRECT_URI, giteeProperties.getRedirectUrl());
        giteeData.add(CODE, code);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(giteeData, null);
        try {
            return restTemplate.exchange(giteeProperties.getAccessTokenUrl(),
                    HttpMethod.POST,
                    requestEntity,
                    TokenVO.class).getBody();
        } catch (Exception e) {
            throw new ServiceException("Gitee登录错误");
        }
    }
}