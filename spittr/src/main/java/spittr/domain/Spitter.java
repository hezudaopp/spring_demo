package spittr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import spittr.service.SpitterAuthorityService;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

/**
 * Created by 273cn on 16/12/15.
 */
@Entity
public class Spitter implements UserDetails {
    @Id
    @Null
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    private String username;

    @NotNull
    @Size(min = 6, max = 16)
    private String password;

    @NotNull
    @Size(min = 2, max = 30)
    private String realName;

    @NotNull
    @Pattern(regexp = "^1[34578][0-9]{9}$")
    private String mobileNo;

    private Long createdTime;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public Spitter() {}

    public Spitter(String username, String password, String realName, String mobileNo, Long createdTime) {
        this(null, username, password, realName, mobileNo, createdTime);
    }

    public Spitter(Long id, String username, String password, String realName, String mobileNo, Long createdTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.mobileNo = mobileNo;
        this.createdTime = createdTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Spitter spitter = (Spitter) o;

        if (!id.equals(spitter.id)) return false;
        if (!username.equals(spitter.username)) return false;
        if (!password.equals(spitter.password)) return false;
        if (!realName.equals(spitter.realName)) return false;
        if (!mobileNo.equals(spitter.mobileNo)) return false;
        return createdTime.equals(spitter.createdTime);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + realName.hashCode();
        result = 31 * result + mobileNo.hashCode();
        result = 31 * result + createdTime.hashCode();
        return result;
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
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
