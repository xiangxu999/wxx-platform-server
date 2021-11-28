package com.xu.wxxplatformserver.security;

import com.xu.wxxplatformserver.pojo.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Description 自定义UserDetails
 * Date 2021/11/9 22:51
 * Version 1.0.1
 *
 * @author Wen
 */
public class MyUserDetails implements UserDetails {

    /**
     * 用户对象
     */
    private SysUser sysUser;

    /**
     * 用户的权限
     */
    private String userAuthorityInfo;


    public MyUserDetails(SysUser sysUser, String userAuthorityInfo) {
        this.sysUser = sysUser;
        this.userAuthorityInfo = userAuthorityInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(userAuthorityInfo);
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return sysUser.getStatus().equals(1);
    }
}
