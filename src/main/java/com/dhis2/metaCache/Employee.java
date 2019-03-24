package com.dhis2.metaCache;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Employee {

  private @Id @GeneratedValue Long id;
  private String name;
  private String role;

  Employee(String name, String role) {
    this.name = name;
    this.role = role;
  }
  
}