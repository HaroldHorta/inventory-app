package com.company.storeapi.repositories.clinichistory.facade;

import com.company.storeapi.model.entity.ClinicHistory;

import java.util.List;

public interface ClinicHistoryRepositoryFacade {

    ClinicHistory validateAndGetClinicHistoryById(String id);

    ClinicHistory saveClinicHistory(ClinicHistory clinicHistory) ;

    List<ClinicHistory> findClinicHistoryByCustomer(String nroDocument);

}
