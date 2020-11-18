package com.company.storeapi.repositories.finances.cashRegister;

import com.company.storeapi.model.entity.finance.CashRegister;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CashRegisterRepository extends MongoRepository<CashRegister, String> {
}
