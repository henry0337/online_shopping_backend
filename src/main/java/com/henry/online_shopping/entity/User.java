package com.henry.online_shopping.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User implements UserDetails {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Getter
    @Column(name = "display_name", nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @Getter
    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @Getter
    @ColumnDefault("0")
    @Builder.Default
    private short isAccountExpired = 0;

    @Getter
    @JsonIgnore
    @ColumnDefault("0")
    @Builder.Default
    private short isAccountLocked = 0;

    @Getter
    @JsonIgnore
    @ColumnDefault("0")
    @Builder.Default
    private short isCredentialsExpired = 0;

    @Getter
    @JsonIgnore
    @ColumnDefault("1")
    @Builder.Default
    private short canAuthenticate = 1;

    @Getter
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(updatable = false, nullable = false)
    @Builder.Default
    private Date createdAt = new Date();

    @Getter
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(insertable = false)
    @Builder.Default
    private Date updatedAt = new Date();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountExpired == 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountLocked == 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsExpired == 0;
    }

    @Override
    public boolean isEnabled() {
        return canAuthenticate == 1;
    }
}
