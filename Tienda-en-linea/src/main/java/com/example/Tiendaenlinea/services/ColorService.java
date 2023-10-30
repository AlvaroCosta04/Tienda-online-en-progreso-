package com.example.Tiendaenlinea.services;

import com.example.Tiendaenlinea.entities.Color;
import com.example.Tiendaenlinea.exceptions.ServicesException;
import com.example.Tiendaenlinea.repositories.ColorRepository;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ColorService {
     @Autowired
    private ColorRepository colorRepository;
     
     @Transactional
    public void create(Color color) {
        colorRepository.save(color);
    }

    //delete
    @Transactional
    public void delete(@NotNull Integer id) throws ServicesException {
        Color color = findById(id);
        colorRepository.delete(color);
    }

    //update
    @Transactional
    public void update(Color color) {
        colorRepository.save(color);
    }
    
     @Transactional(readOnly = true)
    public Color findById(@NotNull Integer id) throws ServicesException {
        return colorRepository.findById(id).orElseThrow(() -> new ServicesException("No se ha encontrado un color con el id ingresado"));
    }

    @Transactional(readOnly = true)
    public List<Color> findAll() throws ServicesException {
        List<Color> colorList = colorRepository.findAll();
        if (colorList.isEmpty()) {
            throw new ServicesException("No se han encontrado colores");
        }
        return colorList;
    }
}
