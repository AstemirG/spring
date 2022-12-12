package ru.kata.spring.boot_security.demo.model;


import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "users")
public class User implements UserDetails {

   @Column
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column
   private String name;

   @Column
   private String last_name;

   @Column
   private String email;

   @Column
   private String login;

   @Column
   private String password;

   @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinTable(
           name="user_role",
           joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="id")},
           inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="id")})
   private List<Role> roles;

   public User() {

   }

   public User(Long id, String name, String last_name, String email, String login, String password, List<Role> roles) {
      this.id = id;
      this.name = name;
      this.last_name = last_name;
      this.email = email;
      this.login = login;
      this.password = password;
      this.roles = roles;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getLast_name() {
      return last_name;
   }

   public void setLast_name(String last_name) {
      this.last_name = last_name;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getLogin() {
      return login;
   }

   @Override
   public String getUsername() {
      return getLogin();
   }

   public void setUsername(String login) {
      this.login = login;
   }

   @Override
   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public List<Role> getRoles() {
      return roles;
   }

   public void setRoles(List<Role> roles) {
      this.roles = roles;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
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

