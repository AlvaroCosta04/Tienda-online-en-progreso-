package com.example.Tiendaenlinea.services;

import com.example.Tiendaenlinea.entities.Size;
import com.example.Tiendaenlinea.exceptions.ServicesException;
import com.example.Tiendaenlinea.repositories.SizeRepository;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SizeService {
     @Autowired
    private SizeRepository sizeRepository;
     
     @Transactional
    public void create(Size size) {
        sizeRepository.save(size);
    }

    //delete
    @Transactional
    public void delete(@NotNull Integer id) throws ServicesException {
        Size size = findById(id);
        sizeRepository.delete(size);
    }

    //update
    @Transactional
    public void update(Size size) {
        sizeRepository.save(size);
    }
    
     @Transactional(readOnly = true)
    public Size findById(@NotNull Integer id) throws ServicesException {
        return sizeRepository.findById(id).orElseThrow(() -> new ServicesException("No se ha encontrado un talle con el id ingresado"));
    }

    @Transactional(readOnly = true)
    public List<Size> findAll() throws ServicesException {
        List<Size> sizeList = sizeRepository.findAll();
        if (sizeList.isEmpty()) {
            throw new ServicesException("No se han encontrado talles de ropa");
        }
        return sizeList;
    }
}
