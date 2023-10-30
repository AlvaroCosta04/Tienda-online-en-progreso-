package com.example.Tiendaenlinea.services;

import com.example.Tiendaenlinea.entities.Category;
import com.example.Tiendaenlinea.exceptions.ServicesException;
import com.example.Tiendaenlinea.repositories.CategoryRepository;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public void create(Category category) {
        categoryRepository.save(category);
    }

    //delete
    @Transactional
    public void delete(@NotNull Integer id) throws ServicesException {
        Category category = findById(id);
        categoryRepository.delete(category);
    }

    //update
    @Transactional
    public void update(Category category) {
        categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public Category findById(@NotNull Integer id) throws ServicesException {
        return categoryRepository.findById(id).orElseThrow(() -> new ServicesException("No se ha encontrado una categoría con el id ingresado"));
    }

    @Transactional(readOnly = true)
    public List<Category> findAll() throws ServicesException {
        List<Category> categoryList = categoryRepository.findAll();
        if (categoryList.isEmpty()) {
            throw new ServicesException("No se han encontrado categorías de ropa");
        }
        return categoryList;
    }
}
