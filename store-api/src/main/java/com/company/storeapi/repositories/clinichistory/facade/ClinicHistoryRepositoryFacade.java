package com.company.storeapi.repositories.clinichistory.facade;

import com.company.storeapi.model.entity.ClinicHistory;

import java.util.List;

public interface ClinicHistoryRepositoryFacade {

    List<ClinicHistory> getClinicHistoryByCustomerNroDocument(String nroDocument);

    ClinicHistory validateAndGetClinicHistoryById(String id);

    ClinicHistory saveClinicHistory(ClinicHistory clinicHistory) ;

}
