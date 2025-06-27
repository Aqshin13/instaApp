package com.company.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "shares")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shares extends BaseEntity{


private String imageName;
@ManyToOne
private User user;




}
