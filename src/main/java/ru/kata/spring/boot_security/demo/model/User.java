package ru.kata.spring.boot_security.demo.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
@Table(name = "users")
public class User {

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
   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", name='" + name + '\'' +
              ", surName='" + surName + '\'' +
              ", email='" + email + '\'' +
              '}';
   }
}
