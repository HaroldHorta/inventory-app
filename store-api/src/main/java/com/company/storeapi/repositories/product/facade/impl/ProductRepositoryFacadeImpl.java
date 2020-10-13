package com.company.storeapi.repositories.product.facade.impl;

import com.company.storeapi.core.constants.MessageError;
import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.Product;
import com.company.storeapi.repositories.product.ProductRepository;
import com.company.storeapi.repositories.product.facade.ProductRepositoryFacade;
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
public class ProductRepositoryFacadeImpl implements ProductRepositoryFacade {


    private final ProductRepository repository;


    @Override
    public List<Product> getAllProduct() {
        try {
            return Optional.of(repository.findAll())
                    .orElseThrow(()-> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND,"No se encontraron productos registrados"));

        }catch (IllegalArgumentException ie){
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, MessageError.NO_SE_HA_ENCONTRADO_LA_ENTIDAD);
        }catch (DataAccessException er){
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD,er);
        }
    }

    @Override
    public Product saveProduct(Product product) {
        try {
            return repository.save(product);
        }catch (IllegalArgumentException ie){
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_CORRUPT,"Error al guardar el producto");
        }catch (DataAccessException er){
            throw new DataNotFoundPersistenceException(LogRefServices.LOG_REF_SERVICES, MessageError.ERROR_EN_EL_ACCESO_LA_ENTIDAD,er);
        }
    }

    @Override
    public Product validateAndGetProductById(String id) {
            return repository.findById(id).orElseThrow(()-> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND, "NO se encontraron productos con el id " + id));
    }

  @Override
    public List<Product> findProductByCategory(String id) {
      return Optional.of(repository.findProductByCategory_Description(id))
          .orElseThrow(()-> new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_NOT_FOUND,"No se encontraron productos registrados con la categoria" +id));
    }
}
