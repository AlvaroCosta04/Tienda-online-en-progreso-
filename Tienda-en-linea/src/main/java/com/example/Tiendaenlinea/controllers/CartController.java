package com.example.Tiendaenlinea.controllers;

import com.example.Tiendaenlinea.entities.Cart;
import com.example.Tiendaenlinea.entities.Clothes;
import com.example.Tiendaenlinea.entities.Users;
import com.example.Tiendaenlinea.exceptions.ServicesException;
import com.example.Tiendaenlinea.services.CartService;
import com.example.Tiendaenlinea.services.ClothesService;
import com.example.Tiendaenlinea.services.UserService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ClothesService clothesService;

    @GetMapping("/")
    public String Cart(
            @RequestParam(required = false, name = "succes") String succes,
            @RequestParam(required = false, name = "itemID") Integer itemID,
            ModelMap model, HttpSession session) throws ServicesException {
        Users user = (Users) session.getAttribute("userSession");

        Cart cart = cartService.findByUser(user);
        List<Clothes> clothes = new ArrayList();

        for (Integer id : cart.getProductIds()) {
            clothes.add(clothesService.findById(id));
        }

        if (succes != null) {
            model.addAttribute("succes", succes);
            model.addAttribute("itemID", itemID);
        }
        model.put("clothes", clothes);

        return "cart.html";
    }

    @PostMapping("/add-item")
    public String CartAddItem(
            @RequestParam("itemID") Integer itemID,
            @RequestParam("from") String from,
            RedirectAttributes redirectAttributes, HttpSession session) throws ServicesException {

        Users user = (Users) session.getAttribute("userSession");

        Cart cart = cartService.findByUser(user);

        List<Integer> productsIds = cart.getProductIds();

        for (Integer productsId : productsIds) {
            if (productsId == itemID) {

                redirectAttributes.addAttribute("error", "Este producto ya se encuentra en su carrito");
                return "redirect:" + from;
            }
        }

        productsIds.add(itemID);

        cart.setProductIds(productsIds);

        cartService.update(cart);

        return "redirect:" + from;
    }

    @PostMapping("/delete-item")
    public String CartDeleteItem(
            @RequestParam("itemID") Integer itemID,
            @RequestParam("from") String from,
            RedirectAttributes redirectAttributes, HttpSession session) throws ServicesException {

        Users user = (Users) session.getAttribute("userSession");

        Cart cart = cartService.findByUser(user);

        List<Integer> productsIds = cart.getProductIds();
        List<Integer> newProductIds = new ArrayList<>();

        for (Integer productId : productsIds) {
            if (!productId.equals(itemID)) {
                newProductIds.add(productId);
            }
        }
        cart.setProductIds(newProductIds);
        cartService.update(cart);

        redirectAttributes.addAttribute("succes", "Se ha eliminado el producto del carrito");
        redirectAttributes.addAttribute("itemID", itemID);
        return "redirect:" + from;
    }

}
