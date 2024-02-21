package com.example.insightlogfe.payload;



import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Integer id;

    private String name;

    private String lastName;

    private String email;

    private String password;

    private String userUniqueName;

    private Set<RoleDto> roles = new HashSet<>();
}
