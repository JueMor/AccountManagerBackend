package com.example.accountmanager.model.account.authorized;

import com.example.accountmanager.model.account.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity(name = "authorized")
@Table(name = "authorized")
public class Authorized {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;
    @NotNull
    private Boolean accountNonExpired;
    @NotNull
    private Boolean accountNonLocked;
    @NotNull
    private Boolean credentialsNonExpired;
    @NotNull
    private Boolean enabled;

    public Authorized(
    ){
        accountNonExpired = true;
        accountNonLocked = true;
        credentialsNonExpired = true;
        enabled = true;
    }

    public Authorized(boolean accountNonExpired,
                      boolean accountNonLocked,
                      boolean credentialsNonExpired,
                      boolean enabled){

        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
