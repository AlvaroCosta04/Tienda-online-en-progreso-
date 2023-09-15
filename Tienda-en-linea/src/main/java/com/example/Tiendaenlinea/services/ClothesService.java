package com.example.Tiendaenlinea.services;

import com.example.Tiendaenlinea.entities.Category;
import com.example.Tiendaenlinea.entities.Clothes;
import com.example.Tiendaenlinea.entities.Color;
import com.example.Tiendaenlinea.entities.Size;
import com.example.Tiendaenlinea.entities.Users;
import com.example.Tiendaenlinea.exceptions.ServicesException;
import com.example.Tiendaenlinea.repositories.ClothesRepository;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClothesService {

    @Autowired
    private ClothesRepository clothesRepository;

    //create
    @Transactional
    public void createClothe(Clothes clothes) {
        clothesRepository.save(clothes);
    }

    //delete
    @Transactional
    public void delete(@NotNull Integer id) throws ServicesException {
        Clothes clothes = findById(id);
        clothesRepository.delete(clothes);
    }

    //update
    @Transactional
    public void update(Clothes clothes) {
        clothesRepository.save(clothes);
    }

    //read
    @Transactional(readOnly = true)
    public Clothes findById(@NotNull Integer id) throws ServicesException {
        return clothesRepository.findById(id).orElseThrow(() -> new ServicesException("No se ha encontrado un usuario con el id ingresado"));
    }

    @Transactional(readOnly = true)
    public List<Clothes> findAll() throws ServicesException {
        List<Clothes> clothesList = clothesRepository.findAll();
        if (clothesList.isEmpty()) {
            throw new ServicesException("No se han encontrado prendas de ropa");
        }
        return clothesList;
    }

    @Transactional(readOnly = true)
    public List<Clothes> findByCategory(Category category) throws ServicesException {
        List<Clothes> clothesList = clothesRepository.findByCategory(category);
        if (clothesList.isEmpty()) {
            throw new ServicesException("No se han encontrado prendas de ropa en la categoría ingresada");
        }
        return clothesList;
    }

    @Transactional(readOnly = true)
    public List<Clothes> findByColorsIn(List<Color> colors) throws ServicesException {
        List<Clothes> clothesList = clothesRepository.findByColorsIn(colors);
        if (clothesList.isEmpty()) {
            throw new ServicesException("No se han encontrado prendas de ropa con los colores ingresados");
        }
        return clothesList;
    }

    @Transactional(readOnly = true)
    public List<Clothes> findBySizesIn(List<Size> sizes) throws ServicesException {
        List<Clothes> clothesList = clothesRepository.findBySizesIn(sizes);
        if (clothesList.isEmpty()) {
            throw new ServicesException("No se han encontrado prendas de ropa con los talles ingresados");
        }
        return clothesList;
    }

}
