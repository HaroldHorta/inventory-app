package com.company.storeapi.repositories.user.facade;

import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.model.entity.User;

import java.util.List;

public interface UserRepositoryFacade {

    List<User> getAllUser();

    User saveUser(User user);

    void deleteUser(String id) throws ServiceException;

    User validateAndGetUserById(String id);

    User findByUsername (String username);

    Boolean existsByUsername (String username);

    Boolean existsByEmail (String email);
}
