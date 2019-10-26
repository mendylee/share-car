package com.jinzhun.common.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUser extends SysUser implements SocialUserDetails {

	private static final long serialVersionUID = -3685249101751401211L;
	
	private Set<String> permissions;
    
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
        return getEnabled();
    }

    @Override
    public String getUserId() {
        return getOpenId();
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new HashSet<>();
		return collection;
	}
}
