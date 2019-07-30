package com.xiaoyan.community.community.controller;

import com.xiaoyan.community.community.dto.AccessTokenDTO;
import com.xiaoyan.community.community.dto.GithubUser;
import com.xiaoyan.community.community.provider.GithubProcider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProcider githubProcider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("clientId");
        accessTokenDTO.setClient_secret("clientSecret");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("redirectUri");
        accessTokenDTO.setState(state);

        String accessToken = githubProcider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProcider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}


