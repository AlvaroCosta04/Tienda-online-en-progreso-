package com.example.Tiendaenlinea.services;

import com.example.Tiendaenlinea.entities.Address;
import com.example.Tiendaenlinea.entities.Users;
import com.example.Tiendaenlinea.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService {

    /*private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }*/
    
    @Autowired
    private AddressRepository addressRepository;

    public Address findByUser(Users user) {
        return addressRepository.findByUser(user);
    }

    @Transactional
    public void saveOrUpdate(Address address) {
        addressRepository.save(address);
    }
    
    @Transactional
    public void delete (Address address) {
        addressRepository.delete(address);
    }

}

