package com.company.storeapi.repositories.category.facade.impl;

import com.company.storeapi.core.constants.MessageError;
import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.Category;
import com.company.storeapi.repositories.category.CategoryRepository;
import com.company.storeapi.repositories.category.facade.CategoryRepositoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The type Category repository.
 */

@RequiredArgsConstructor
@Component
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CategoryRepositoryFacadeImpl implements CategoryRepositoryFacade {

    private final CategoryRepository repository;

    @Override
    public List<Category> getAllCategory()  {
        try {
            return Optional.of(repository.findAll())
                    .orElseThrow(()-> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "No se encontraron registros de categorias"));
        }catch (EmptyResultDataAccessException er){
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        }catch (DataAccessException er){
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD,er);
        }
    }

    @Override
    public Category validateAndGetCategoryById(String id)  {

            return  repository.findById(id)
                    .orElseThrow(()-> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "Categoria con el id: "+ id + " no encontrada" ));

    }

    @Override
    public Category saveCategory(Category entity)  {
        return repository.save(entity);
    }

    @Override
    public void deleteCategory(String id)  {
            Optional<Category> category = repository.findById(id);
        if(category.isPresent()){
            repository.deleteById(id);
        }else {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        }

    }

    @Override
    public Boolean existsCategoryByDescription(String description) {
        return repository.existsCategoryByDescription(description);
    }
}
