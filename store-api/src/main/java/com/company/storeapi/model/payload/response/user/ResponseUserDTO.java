package com.company.storeapi.model.payload.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserDTO {

    private String username;

    private String email;

    private Set<String> role;


}
