package com.example.Tiendaenlinea.controllers;

import com.example.Tiendaenlinea.entities.Clothes;
import com.example.Tiendaenlinea.entities.ShoppingHistory;
import com.example.Tiendaenlinea.entities.Users;
import com.example.Tiendaenlinea.exceptions.ServicesException;
import com.example.Tiendaenlinea.services.CartService;
import com.example.Tiendaenlinea.services.ClothesService;
import com.example.Tiendaenlinea.services.ShoppingHistoryService;
import com.example.Tiendaenlinea.services.UserService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/history")
public class ShopingHistoryController {

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingHistoryService shoppingHistoryService;

    @Autowired
    private ClothesService clothesService;

    @GetMapping("/")
    public String History(
            ModelMap model, HttpSession session) throws ServicesException {
        Users user = (Users) session.getAttribute("userSession");

        ShoppingHistory shoppingHistory = shoppingHistoryService.findByUser(user);

        if (shoppingHistory != null) {
            List<Clothes> clothes = new ArrayList();
            for (Integer id : shoppingHistory.getProductIds()) {
                clothes.add(clothesService.findById(id));
            }
            model.put("clothes", clothes);
        }

        return "shoppingHistory.html";
    }
}
