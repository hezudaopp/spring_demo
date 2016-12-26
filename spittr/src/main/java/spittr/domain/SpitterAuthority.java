package spittr.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * Created by 273cn on 16/12/21.
 */
@Entity
@Table(indexes = { @Index(name = "idx_username", columnList = "username"),
    @Index(name = "idx_role_name", columnList = "roleName")})
public class SpitterAuthority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String roleName;

    public SpitterAuthority() {}

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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }
}