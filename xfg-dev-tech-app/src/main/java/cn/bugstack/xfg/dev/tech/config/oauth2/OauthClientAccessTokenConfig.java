package cn.bugstack.xfg.dev.tech.config.oauth2;

import cn.bugstack.xfg.dev.tech.config.valobj.UserAccountVO;
import cn.bugstack.xfg.dev.tech.infrastructure.dao.po.OauthAccount;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class OauthClientAccessTokenConfig {

    private static final String SIGNING_KEY = "re0wr8e09w8r0ewjodijiodosjf23093820909";

    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService(DataSource dataSource) {
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    public TokenStore tokenStore(DataSource dataSource) {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }

    @Bean
    public TokenEnhancer additionalInformationTokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> information = new HashMap<>(8);
            Authentication userAuthentication = authentication.getUserAuthentication();
            if (userAuthentication instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) userAuthentication;
                Object principal = token.getPrincipal();
                if (principal instanceof OauthAccountUserDetails) {
                    OauthAccountUserDetails userDetails = (OauthAccountUserDetails) token.getPrincipal();
                    OauthAccount oauthAccount = userDetails.getOauthAccount();

                    information.put("account_info", UserAccountVO.builder()
                            .id(oauthAccount.getId())
                            .clientId(oauthAccount.getClientId())
                            .username(oauthAccount.getUsername())
                            .mobile(oauthAccount.getMobile())
                            .email(oauthAccount.getEmail())
                            .build());

                    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(information);
                }
            }
            return accessToken;
        };
    }

    @Bean
    public TokenEnhancerChain tokenEnhancerChain() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter(), additionalInformationTokenEnhancer()));
        return tokenEnhancerChain;
    }

    @Bean(name = "tokenServices")
    public AuthorizationServerTokenServices tokenServices(JdbcClientDetailsService jdbcClientDetailsService, TokenStore tokenStore, TokenEnhancerChain tokenEnhancerChain) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setClientDetailsService(jdbcClientDetailsService);
        // 允许支持 refreshToken
        tokenServices.setSupportRefreshToken(true);
        // refreshToken 不重用策略
        tokenServices.setReuseRefreshToken(false);
        // 设置Token存储方式
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setTokenEnhancer(tokenEnhancerChain);
        return tokenServices;
    }

}
