package com.example.Tiendaenlinea.services;

import com.example.Tiendaenlinea.entities.ShoppingHistory;
import com.example.Tiendaenlinea.entities.Users;
import com.example.Tiendaenlinea.repositories.ShoppingHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShoppingHistoryService {

    @Autowired
    private ShoppingHistoryRepository shoppingHistoryRepository;

    @Transactional
    public void create(ShoppingHistory shoppingHistory) {
        shoppingHistoryRepository.save(shoppingHistory);
    }

    @Transactional
    public void update(ShoppingHistory shoppingHistory) {
        shoppingHistoryRepository.save(shoppingHistory);
    }

    public ShoppingHistory findByUser(Users user) {
        return shoppingHistoryRepository.findByUser(user);
    }

}
