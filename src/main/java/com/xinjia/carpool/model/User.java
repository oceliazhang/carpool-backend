package com.xinjia.carpool.model;


import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a User entity with basic information.
 */
@Entity
@Table
@Getter
@Setter
@Inheritance (strategy = InheritanceType.JOINED)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Column
  private String note;

  @Column
  private String phone;

  @Column(unique = true)
  private String username;

  @Column
  private String password;

  /**
   * Default constructor for the User class.
   */
  public User() {

  }
}

