package cn.bugstack.xfg.dev.tech.config.oauth2;

import cn.bugstack.xfg.dev.tech.infrastructure.dao.po.OauthAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class OauthAccountUserDetails implements UserDetails {

    private final OauthAccount oauthAccount;

    private final Collection<? extends GrantedAuthority> authorities;

    public OauthAccountUserDetails(OauthAccount oauthAccount, Collection<? extends GrantedAuthority> authorities) {
        this.oauthAccount = oauthAccount;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return oauthAccount.getPassword();
    }

    @Override
    public String getUsername() {
        return oauthAccount.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return oauthAccount.getAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return oauthAccount.getAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return oauthAccount.getCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return oauthAccount.getEnabled();
    }

    public OauthAccount getOauthAccount() {
        return oauthAccount;
    }

}
