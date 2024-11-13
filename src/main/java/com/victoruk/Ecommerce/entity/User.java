package com.victoruk.Ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "username",  nullable = false)
    private String name;
    
    @Column(name = "email" , nullable = false, unique = true)
    private String email;

    @Column(name = "password" , nullable = false)
    private String password;

    @Column(name = "phone_number" , nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreatedDate
    @Column(name = "created_at" , nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Initialize with current time


    @LastModifiedDate
    @Column(name = "updated_at" , nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now(); // Initialize with current time


    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Cart cart;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();


    @PrePersist
    private void onCreate(){
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        updatedAt = createdAt;

    }

    @PreUpdate
    private void onUpdate(){
        createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
