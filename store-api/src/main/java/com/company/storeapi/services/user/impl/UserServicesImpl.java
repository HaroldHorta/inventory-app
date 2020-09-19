package com.company.storeapi.services.user.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.core.mapper.UserMapper;
import com.company.storeapi.core.security.jwt.JwtUtils;
import com.company.storeapi.core.security.service.UserDetailsImpl;
import com.company.storeapi.model.entity.User;
import com.company.storeapi.model.enums.Status;
import com.company.storeapi.model.payload.request.user.LoginRequest;
import com.company.storeapi.model.payload.request.user.RequestAddUserDTO;
import com.company.storeapi.model.payload.response.user.JwtResponse;
import com.company.storeapi.model.payload.response.user.ResponseUserDTO;
import com.company.storeapi.repositories.user.facade.UserRepositoryFacade;
import com.company.storeapi.services.user.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserServices {

    private final UserRepositoryFacade userRepositoryFacade;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public List<ResponseUserDTO> getAllUser() {
        List<User> customerList = userRepositoryFacade.getAllUser();
        return customerList.stream().map(userMapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    public ResponseUserDTO saveUser(RequestAddUserDTO requestAddUserDTO) {
        if(userRepositoryFacade.existsByUsername(requestAddUserDTO.getUsername())){
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT,"El nombre de usuario ya esta en uso");
        }else if(userRepositoryFacade.existsByEmail(requestAddUserDTO.getEmail())){
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT,"El email ya esta en uso");
        }
        else{
            return userMapper.toUserDto(userRepositoryFacade.saveUser(userMapper.toUser(requestAddUserDTO)));
        }

    }

    @Override
    public JwtResponse getToken(LoginRequest loginRequest) {

        JwtResponse jwtResponse = new JwtResponse();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        jwtResponse.setAccessToken(jwt);
        jwtResponse.setId(userDetails.getId());
        jwtResponse.setUsername(userDetails.getUsername());
        jwtResponse.setEmail(userDetails.getEmail());
        jwtResponse.setRoles(roles);


        return jwtResponse;
    }

    @Override
    public void deleteUser(String id) {

    }

    @Override
    public ResponseUserDTO updateUser(String id, RequestAddUserDTO requestUpdateCustomerDTO) {
        return null;
    }

    @Override
    public ResponseUserDTO validateAndGetUserById(String id) {
        return null;
    }

      @Override
    public ResponseUserDTO updateStatus(String id, Status status) {
        return null;
    }
}
