package com.company.storeapi.repositories.user.facade.impl;

import com.company.storeapi.core.constants.MessageError;
import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.User;
import com.company.storeapi.repositories.user.UserRepository;
import com.company.storeapi.repositories.user.facade.UserRepositoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UserRepositoryFacadeImpl  implements UserRepositoryFacade {

    private final UserRepository repository;
    @Override
    public List<User> getAllUser() {
        try {
            return Optional.of(repository.findAll())
                    .orElseThrow(()-> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND,"No se encontraron usuarios registrados"));
        }catch (IllegalArgumentException ie){
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        }catch (DataAccessException er){
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD,er);
        }
    }

    @Override
    public User saveUser(User user) {
        try {
            return repository.save(user);
        }catch (IllegalArgumentException ie){
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_CORRUPT,"Error al guardar el usuario");
        }catch (DataAccessException er){
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD,er);
        }
    }

    @Override
    public void deleteUser(String id) throws ServiceException {
        Optional<User> user = repository.findById(id);
        if(user.isPresent()){
            repository.deleteById(id);
        }else {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND,"No se encontraron datos a eliminar con el id" + id);
        }
    }

    @Override
    public User validateAndGetUserById(String id) {
        return repository.findById(id).orElseThrow(()-> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "NO se encontraron usuarios con el id " + id));
    }

    @Override
    public User findByUsername(String username) {
        return Optional.of(repository.findByUsername(username))
                .orElseThrow(()-> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "NO se encontraron usuarios con el id " + username));
    }

    @Override
    public Boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
