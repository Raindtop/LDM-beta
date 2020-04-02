package com.raindrop.security.UserDetails;

import com.raindrop.common.Model.Account.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CurrentAccount extends Account implements UserDetails {

    List<GrantedAuthority> userAuthority;

    public CurrentAccount(Account account){
        this.setAccountId(account.getAccountId());
        this.setName(account.getName());
        this.setMobile(account.getMobile());
        this.setEmail(account.getEmail());
        this.setHeadImg(account.getHeadImg());
        this.setDepId(account.getDepId());
        this.setRoleId(account.getRoleId());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.getPassword();
    }

    @Override
    public String getUsername() {
        return this.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.getStatus() == 0 ? true : false;
    }
}
