package com.company.storeapi.services.pet.impl;

import com.company.storeapi.core.mapper.PetMapper;
import com.company.storeapi.model.entity.Breed;
import com.company.storeapi.model.entity.Customer;
import com.company.storeapi.model.entity.Pet;
import com.company.storeapi.model.entity.Species;
import com.company.storeapi.model.payload.request.pet.RequestAddPetDTO;
import com.company.storeapi.model.payload.request.pet.RequestUpdatePetDTO;
import com.company.storeapi.model.payload.response.pet.ResponsePetDTO;
import com.company.storeapi.repositories.breed.facade.BreedRepositoryFacade;
import com.company.storeapi.repositories.customer.facade.CustomerRepositoryFacade;
import com.company.storeapi.repositories.pet.facade.PetRepositoryFacade;
import com.company.storeapi.repositories.species.facade.SpeciesRepositoryFacade;
import com.company.storeapi.services.pet.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepositoryFacade petRepositoryFacade;
    private final SpeciesRepositoryFacade speciesRepositoryFacade;
    private final BreedRepositoryFacade breedRepositoryFacade;
    private final CustomerRepositoryFacade customerRepositoryFacade;
    private final PetMapper petMapper;

    @Override
    public List<ResponsePetDTO> getAllPet() {
        List<Pet> veterinaries = petRepositoryFacade.getAllPet();
        return veterinaries.stream().map(petMapper::toPetDto).collect(Collectors.toList());
    }

    @Override
    public ResponsePetDTO validateAndGetPetById(String id) {
        return petMapper.toPetDto(petRepositoryFacade.validateAndGetPetById(id));
    }

    @Override
    public ResponsePetDTO savePet(RequestAddPetDTO requestAddPetDTO) {
        Species specie = speciesRepositoryFacade.validateAndGetById(requestAddPetDTO.getSpecies());
        Breed breed = breedRepositoryFacade.validateAndGetBreedById(requestAddPetDTO.getBreed());
        Customer customer = customerRepositoryFacade.validateAndGetCustomerById(requestAddPetDTO.getCustomer());

        Pet pet = new Pet();
        pet.setName(requestAddPetDTO.getName());
        pet.setSpecies(specie);
        pet.setBreed(breed);
        pet.setColor(requestAddPetDTO.getColor());
        pet.setSex(requestAddPetDTO.getSex());
        pet.setDateBirth(requestAddPetDTO.getDateBirth());
        pet.setAge(requestAddPetDTO.getAge());
        pet.setParticularSigns(requestAddPetDTO.getParticularSigns());
        pet.setOrigin(requestAddPetDTO.getOrigin());
        pet.setCustomer(customer);
        pet.setCreateAt(new Date());
        pet.setUpdateAt(new Date());

        return petMapper.toPetDto(petRepositoryFacade.savePet(pet));
    }

    @Override
    public ResponsePetDTO updatePet(RequestUpdatePetDTO requestUpdatePetDTO) {

        Species specie = speciesRepositoryFacade.validateAndGetById(requestUpdatePetDTO.getSpecies());
        Breed breed = breedRepositoryFacade.validateAndGetBreedById(requestUpdatePetDTO.getBreed());
        Customer customer = customerRepositoryFacade.validateAndGetCustomerById(requestUpdatePetDTO.getCustomer());

        Pet pet = petRepositoryFacade.validateAndGetPetById(requestUpdatePetDTO.getId());
        pet.setName(defaultIfNull(requestUpdatePetDTO.getName().trim(), pet.getName()));
        pet.setSpecies(specie);
        pet.setBreed(breed);
        pet.setColor(defaultIfNull(requestUpdatePetDTO.getColor(), pet.getColor()));
        pet.setSex(defaultIfNull(requestUpdatePetDTO.getSex(),pet.getSex()));
        pet.setDateBirth(defaultIfNull(requestUpdatePetDTO.getDateBirth(),pet.getDateBirth()));
        pet.setAge(defaultIfNull(requestUpdatePetDTO.getAge(),pet.getAge()));
        pet.setParticularSigns(defaultIfNull(requestUpdatePetDTO.getParticularSigns(),pet.getParticularSigns()));
        pet.setOrigin(defaultIfNull(requestUpdatePetDTO.getOrigin(),pet.getOrigin()));
        pet.setCustomer(customer);
        pet.setCreateAt(pet.getCreateAt());
        pet.setUpdateAt(new Date());
        return petMapper.toPetDto(petRepositoryFacade.savePet(pet));
    }

    @Override
    public void deletePet(String id) {
        petRepositoryFacade.deletePet(id);
    }
}
