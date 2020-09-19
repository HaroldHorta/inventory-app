package com.company.storeapi.services.user;


import com.company.storeapi.model.enums.Status;
import com.company.storeapi.model.payload.request.user.LoginRequest;
import com.company.storeapi.model.payload.request.user.RequestAddUserDTO;
import com.company.storeapi.model.payload.response.user.JwtResponse;
import com.company.storeapi.model.payload.response.user.ResponseUserDTO;

import java.util.List;

public interface UserServices {

    List<ResponseUserDTO> getAllUser();

    ResponseUserDTO saveUser(RequestAddUserDTO requestAddUserDTO);

    JwtResponse getToken (LoginRequest loginRequest);

    void deleteUser(String id);

    ResponseUserDTO updateUser(String id, RequestAddUserDTO requestUpdateCustomerDTO);

    ResponseUserDTO validateAndGetUserById(String id);

    ResponseUserDTO updateStatus(String id, Status status);
}
