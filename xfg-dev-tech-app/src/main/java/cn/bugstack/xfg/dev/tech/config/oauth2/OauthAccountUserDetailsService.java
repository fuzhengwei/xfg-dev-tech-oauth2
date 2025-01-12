package cn.bugstack.xfg.dev.tech.config.oauth2;


import cn.bugstack.xfg.dev.tech.infrastructure.dao.IOauthAccountDao;
import cn.bugstack.xfg.dev.tech.infrastructure.dao.po.OauthAccount;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.BadClientCredentialsException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Service
public class OauthAccountUserDetailsService implements UserDetailsService {

    @Resource
    private IOauthAccountDao oauthAccountDao;
    @Resource
    private JdbcClientDetailsService jdbcClientDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;

    private final BasicAuthenticationConverter authenticationConverter = new BasicAuthenticationConverter();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String clientId;
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                User clientUser = (User) principal;
                clientId = clientUser.getUsername();
            } else if (principal instanceof OauthAccountUserDetails) {
                getClientIdByRequest();
                return (OauthAccountUserDetails) principal;
            } else {
                throw new UnsupportedOperationException();
            }
        } else {
            clientId = getClientIdByRequest();
        }

        // 校验用户 - 直接从数据库查询
        OauthAccount account = oauthAccountDao.loadUserByUsername(clientId, username);

        if (account == null || !account.getAccountNonDeleted()) {
            throw new UsernameNotFoundException("err user is not found!");
        }

        return new OauthAccountUserDetails(account, new ArrayList<>());
    }

    public String getClientIdByRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) throw new UnsupportedOperationException();
        HttpServletRequest request = attributes.getRequest();
        UsernamePasswordAuthenticationToken client = authenticationConverter.convert(request);
        if (client == null) {
            throw new UnauthorizedClientException("unauthorized client");
        }
        ClientDetails clientDetails = jdbcClientDetailsService.loadClientByClientId(client.getName());
        if (!passwordEncoder.matches((String) client.getCredentials(), clientDetails.getClientSecret())) {
            throw new BadClientCredentialsException();
        }
        return clientDetails.getClientId();
    }

}
