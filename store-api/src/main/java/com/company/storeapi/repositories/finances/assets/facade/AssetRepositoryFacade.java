package com.company.storeapi.repositories.finances.assets.facade;

import com.company.storeapi.core.exceptions.base.ServiceException;
import com.company.storeapi.model.entity.Customer;
import com.company.storeapi.model.entity.finance.Assets;

import java.util.List;

public interface AssetRepositoryFacade {

    List<Assets> getAllCustomers();

    Assets saveCustomer(Assets assets);

}
