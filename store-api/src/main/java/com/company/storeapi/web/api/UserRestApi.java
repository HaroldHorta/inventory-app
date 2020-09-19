package com.company.storeapi.web.api;

import com.company.storeapi.model.payload.request.user.LoginRequest;
import com.company.storeapi.model.payload.request.user.RequestAddUserDTO;
import com.company.storeapi.model.payload.response.user.JwtResponse;
import com.company.storeapi.model.payload.response.user.ResponseUserDTO;
import com.company.storeapi.services.user.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserRestApi {

    private final UserServices userServices;

    @PostMapping("/signup")
    public ResponseEntity<ResponseUserDTO> registerUser(@Valid @RequestBody RequestAddUserDTO requestAddUserDTO) {
        ResponseUserDTO user = userServices.saveUser(requestAddUserDTO);
        return new ResponseEntity<>(user, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse token = userServices.getToken(loginRequest);
        return new ResponseEntity<>(token, new HttpHeaders(), HttpStatus.OK);
    }

}
