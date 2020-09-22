package com.company.storeapi.model.payload.request.user;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class RequestAddUserDTO {

    @NotBlank
    @Size(min = 3, max = 20)
    @Schema(example = "admin_name")
    private String username;

    @NotBlank
    @Size(max = 50)
    @Schema(example = "admin_name@inventory.com")
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    @Schema(example = "password")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

}
