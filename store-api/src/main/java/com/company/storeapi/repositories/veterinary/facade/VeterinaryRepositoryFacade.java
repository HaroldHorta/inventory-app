package com.company.storeapi.repositories.veterinary.facade;

import com.company.storeapi.model.entity.Veterinary;

import java.util.List;

public interface VeterinaryRepositoryFacade {

    List<Veterinary> getAllVeterinary() ;

    Veterinary validateAndGetVeterinaryById(String id);

    Veterinary findVeterinaryByTAndProfessionalCard(String professionalCard);

    Veterinary saveVeterinary(Veterinary entity) ;

    void deleteVeterinary(String  id) ;

    Boolean existsVeterinaryByProfessionalCard (String professionalCard);

}
