package com.skillnest.userservice.data.model;


import com.skillnest.userservice.data.enums.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Document(collection = "user")
public class User implements UserDetails{
    @Id
    private String id;
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String profilePicturePath;
    private LocalDateTime registrationDate;
    private String location;
    private boolean active;
    private Role role;
    private LocalDateTime updatedAt;
    private boolean isGoogleUser;
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of( new SimpleGrantedAuthority(role.toString()) );
    }


    @Override
    public String getUsername() {
        return this.email;
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

}
