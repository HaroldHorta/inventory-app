package com.company.storeapi.repositories.veterinary.facade;

import com.company.storeapi.model.entity.Veterinary;

import java.util.List;

public interface VeterinaryRepositoryFacade {

    List<Veterinary> getAllVeterinary() ;

    Veterinary validateAndGetVeterinaryById(String id);

    Veterinary saveVeterinary(Veterinary entity) ;

    void deleteVeterinary(String  id) ;

    Boolean existsVeterinaryById(String id);

    Boolean existsVeterinaryByProfessionalCard (String professionalCard);

}
