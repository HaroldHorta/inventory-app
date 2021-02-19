package com.company.storeapi.services.pet;


import com.company.storeapi.model.payload.request.clinichistory.RequestFeeding;
import com.company.storeapi.model.payload.request.clinichistory.RequestHabitat;
import com.company.storeapi.model.payload.request.clinichistory.RequestReproductiveStatus;
import com.company.storeapi.model.payload.request.pet.*;
import com.company.storeapi.model.payload.response.pet.ResponsePetDTO;

import java.util.List;

public interface PetService {

    List<ResponsePetDTO> getAllPet();

    List<ResponsePetDTO> findPetByCustomerNroDocument(String nroDocument);

    ResponsePetDTO validateAndGetPetById(String id);

    ResponsePetDTO savePet(RequestAddPetDTO entity);

    ResponsePetDTO updatePet(RequestUpdatePetDTO requestUpdatePetDTO);

    void deletePet(String id);

    ResponsePetDTO updateVaccination(String id, RequestPatientHistoryVaccinations requestPatientHistory);

    ResponsePetDTO updateDewormingInternal(String id, RequestPatientHistoryDeworming requestPatientHistory);

    ResponsePetDTO updateDewormingExternal(String id, RequestPatientHistoryDeworming requestPatientHistory);

    ResponsePetDTO updateFeeding(String id, RequestFeeding requestPatientHistory);

    ResponsePetDTO updateReproductiveStatus(String id, RequestReproductiveStatus requestPatientHistory);

    ResponsePetDTO updatePreviousIllnesses(String id, String requestPatientHistory);

    ResponsePetDTO updateSurgeries(String id, String requestPatientHistory);

    ResponsePetDTO updateAllergy (String id, String requestPatientHistory);

    ResponsePetDTO updateFamilyBackground(String id, String requestPatientHistory);

    ResponsePetDTO updateHabitat(String id, RequestHabitat requestPatientHistory);


}
