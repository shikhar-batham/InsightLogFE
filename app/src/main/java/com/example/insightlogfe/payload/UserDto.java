package com.example.insightlogfe.payload;



import java.util.HashSet;
import java.util.Set;





public class UserDto {

    private Integer id;

    private String name;

    private String lastName;

    private String email;

    private String password;

    private String userUniqueName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserUniqueName() {
        return userUniqueName;
    }

    public void setUserUniqueName(String userUniqueName) {
        this.userUniqueName = userUniqueName;
    }


}
