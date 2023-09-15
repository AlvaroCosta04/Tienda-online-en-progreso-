package com.example.Tiendaenlinea.services;

import com.example.Tiendaenlinea.entities.Cart;
import com.example.Tiendaenlinea.entities.Users;
import com.example.Tiendaenlinea.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public void create(Cart cart) {
        cartRepository.save(cart);
    }

    @Transactional
    public void update(Cart cart) {
        cartRepository.save(cart);
    }

    public Cart findByUser(Users user) {
        return cartRepository.findByUser(user);
    }

}
