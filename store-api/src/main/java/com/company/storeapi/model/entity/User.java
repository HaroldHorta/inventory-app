package com.company.storeapi.model.entity;

import com.company.storeapi.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document("user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;

    private String username;

    private String email;

    private String password;

    private Set<Role> roles;


}
