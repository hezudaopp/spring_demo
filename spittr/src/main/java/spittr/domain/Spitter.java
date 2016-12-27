package spittr.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;

/**
 * Created by 273cn on 16/12/15.
 */
@Entity
@Table(indexes = { @Index(name = "idx_username", columnList = "username", unique = true) })
public class Spitter implements UserDetails {
    @Id
    @Null
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    private String username;

    @NotNull
    @Size(min = 6, max = 128)
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
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that, "username", "password", "realName", "mobileNo", "createdTime");
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "username", "password", "realName", "mobileNo", "createdTime");
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
