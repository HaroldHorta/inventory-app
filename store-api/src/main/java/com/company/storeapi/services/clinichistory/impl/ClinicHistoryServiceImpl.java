package com.company.storeapi.services.clinichistory.impl;

import com.company.storeapi.core.mapper.ClinichistoryMapper;
import com.company.storeapi.model.entity.ClinicHistory;
import com.company.storeapi.model.entity.Customer;
import com.company.storeapi.model.entity.Pet;
import com.company.storeapi.model.entity.Veterinary;
import com.company.storeapi.model.payload.request.clinichistory.RequestAddClinicHistoryDTO;
import com.company.storeapi.model.payload.request.clinichistory.RequestUpdateClinicHistoryDTO;
import com.company.storeapi.model.payload.response.clinichistory.ResponseClinicHistoryDTO;
import com.company.storeapi.repositories.clinichistory.facade.ClinicHistoryRepositoryFacade;
import com.company.storeapi.repositories.customer.facade.CustomerRepositoryFacade;
import com.company.storeapi.repositories.pet.facade.PetRepositoryFacade;
import com.company.storeapi.repositories.veterinary.facade.VeterinaryRepositoryFacade;
import com.company.storeapi.services.clinichistory.ClinicHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClinicHistoryServiceImpl implements ClinicHistoryService {

    private final ClinicHistoryRepositoryFacade clinicHistoryRepositoryFacade;
    private final VeterinaryRepositoryFacade veterinaryRepositoryFacade;
    private final CustomerRepositoryFacade customerRepositoryFacade;
    private final PetRepositoryFacade petRepositoryFacade;
    private final ClinichistoryMapper clinichistoryMapper;

    @Override
    public List<ResponseClinicHistoryDTO> getClinicHistoryByCustomerNroDocument(String nroDocument) {
        List<ClinicHistory> clinicHistories = clinicHistoryRepositoryFacade.getClinicHistoryByCustomerNroDocument(nroDocument);
        return clinicHistories.stream().map(clinichistoryMapper::toClinichistoryDto).collect(Collectors.toList());
    }

    @Override
    public ResponseClinicHistoryDTO validateAndGetClinicHistoryById(String id) {
        return clinichistoryMapper.toClinichistoryDto(clinicHistoryRepositoryFacade.validateAndGetClinicHistoryById(id));
    }

    @Override
    public ResponseClinicHistoryDTO saveClinicHistory(RequestAddClinicHistoryDTO requestAddClinicHistoryDTO) {
        Veterinary veterinary = veterinaryRepositoryFacade.validateAndGetVeterinaryById(requestAddClinicHistoryDTO.getVeterinary());
        Customer customer = customerRepositoryFacade.validateAndGetCustomerById(requestAddClinicHistoryDTO.getCustomer());
        Pet pet = petRepositoryFacade.validateAndGetPetById(requestAddClinicHistoryDTO.getPet());

        ClinicHistory clinicHistory = new ClinicHistory();
        clinicHistory.setCreateAt(new Date());
        clinicHistory.setVeterinary(veterinary);
        clinicHistory.setCustomer(customer);
        clinicHistory.setPet(pet);
        clinicHistory.setReasonOfConsultation(requestAddClinicHistoryDTO.getReasonOfConsultation());
        clinicHistory.setAnamnesis(requestAddClinicHistoryDTO.getAnamnesis());
        clinicHistory.setRecipeBook(requestAddClinicHistoryDTO.getRecipeBook());


        return clinichistoryMapper.toClinichistoryDto(clinicHistoryRepositoryFacade.saveClinicHistory(clinicHistory));
    }

    @Override
    public ResponseClinicHistoryDTO updateClinicHistory(RequestUpdateClinicHistoryDTO requestUpdateClinicHistoryDTO) {
        return null;
    }
}
