package com.xpotify.auth.oauth;

import com.xpotify.auth.oauth.user.OAuth2UserInfo;
import com.xpotify.auth.oauth.user.OAuth2UserInfoFactory;
import com.xpotify.entity.User;
import com.xpotify.exception.OAuth2AuthenticationProcessingException;
import com.xpotify.service.UserService;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final UserService userService;

    public OAuth2UserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuthUser(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User processOAuthUser(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.retrieveOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        User user = userService.findByEmail(oAuth2UserInfo.getEmail());
        if (user == null) {
            user = createNewUser(oAuth2UserRequest, oAuth2UserInfo);
        } else {
            if (!user.getAuthProvider().equals(oAuth2UserRequest.getClientRegistration().getRegistrationId())) {
                throw new OAuth2AuthenticationProcessingException("Looks like you've already signed up with " +
                        user.getAuthProvider() + " account. Please use your " + user.getAuthProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        }
        return user;
    }

    private User updateExistingUser(User user, OAuth2UserInfo oAuth2UserInfo) {
        user.setName(oAuth2UserInfo.getName());
        return userService.save(user);
    }

    private User createNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();
        user.setAuthProvider(oAuth2UserRequest.getClientRegistration().getRegistrationId());
        user.setProviderId(oAuth2UserInfo.getId());
        user.setName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        return userService.save(user);
    }
}
