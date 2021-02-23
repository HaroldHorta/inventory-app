package com.company.storeapi.repositories.finances.expenses;

import com.company.storeapi.model.entity.finance.Expenses;
import com.company.storeapi.model.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface  ExpensesRepository extends MongoRepository<Expenses, String> {

    List<Expenses> findAllByPageable (boolean pag, Pageable pageable);
    int countByPageable (boolean pag);

}
