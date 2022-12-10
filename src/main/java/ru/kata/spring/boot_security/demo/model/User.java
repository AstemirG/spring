package ru.kata.spring.boot_security.demo.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "users")
public class User implements GrantedAuthority, UserDetails {

   @ManyToMany(mappedBy = "users")
   private List<Role> roles;

   @Id
   @Column(name = "ID")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   @Column(name = "name")
   private String name;

   @Column(name = "surname")
   private String surName;

   @Column(name = "email")
   private String email;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getName() {return name;}

   public void setName(String name) {this.name = name;}

   public String getSurName() {
      return surName;
   }

   public void setSurName(String surName) {
      this.surName = surName;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public User() {
      this.name = name;
      this.surName = surName;
      this.email = email;
   }

   public List<Role> getRoles() {
      return roles;
   }

   public void setRoles(List<Role> roles) {
      this.roles = roles;
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", name='" + name + '\'' +
              ", surName='" + surName + '\'' +
              ", email='" + email + '\'' +
              '}';
   }

   @Override
   public String getAuthority() {
      return null;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return null;
   }

   @Override
   public String getPassword() {
      return null;
   }

   @Override
   public String getUsername() {
      return null;
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
      return false;
   }
}
