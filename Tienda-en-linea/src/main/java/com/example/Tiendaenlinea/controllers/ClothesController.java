/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Tiendaenlinea.controllers;

import com.example.Tiendaenlinea.entities.Cart;
import com.example.Tiendaenlinea.entities.Clothes;
import com.example.Tiendaenlinea.entities.Users;
import com.example.Tiendaenlinea.exceptions.ServicesException;
import com.example.Tiendaenlinea.services.CartService;
import com.example.Tiendaenlinea.services.ClothesService;
import com.example.Tiendaenlinea.services.UserService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/catalogue")
public class ClothesController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClothesService clothesService;

    @Autowired
    private CartService cartService;

    @GetMapping
    public String getCatalogue(HttpSession session, ModelMap model,
            @RequestParam(required = false, name = "success") String success,
            @RequestParam(required = false, name = "error") String error) {
        Users user = (Users) session.getAttribute("userSession");

        if (success != null) {
            model.addAttribute("success", success);
        }
        if (error != null) {
            model.addAttribute("error", error);
        }

        try {
            List<Clothes> clothes = clothesService.findAll();
            model.put("clothes", clothes);
        } catch (ServicesException ex) {
        }

        return "catalogue.html";
    }

    @GetMapping("/product-details-{id}")
    public String getProductDetails(
            @RequestParam(required = false, name = "succes") String succes,
            @PathVariable Integer id, HttpSession session, ModelMap model) {
        Users user = (Users) session.getAttribute("userSession");
        try {
            Clothes clothes = clothesService.findById(id);

            Cart cart = cartService.findByUser(user);
            List<Integer> productsIds = cart.getProductIds();

            for (Integer productsId : productsIds) {
                if (productsId == id) {
                    model.addAttribute("cart", true);
                    break;
                }
            }
            if (succes != null) {
                model.addAttribute("succes", succes);
            }

            model.addAttribute("clothes", clothes);
            model.addAttribute("idClothes", id);
            return "productDetails.html";
        } catch (ServicesException ex) {
            return "redirect:/catalogue?error=Error al obtener los detalles del producto";
        }
    }

}
