package com.company.storeapi.repositories.finances.creditCapital.facade;

import com.company.storeapi.model.entity.CreditCapital;

import java.util.List;

public interface CreditCapitalRepositoryFacade {

    List<CreditCapital> findCreditCapitalByTicket(String idTicket);

    CreditCapital saveCreditCapital (CreditCapital creditCapital);

}
