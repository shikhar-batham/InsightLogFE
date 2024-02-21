package com.example.insightlogfe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User  {
    private Integer id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String userUniqueName;
}
