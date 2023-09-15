package com.example.Tiendaenlinea.controllers;

import com.example.Tiendaenlinea.entities.Address;
import com.example.Tiendaenlinea.entities.Clothes;
import com.example.Tiendaenlinea.entities.ShoppingHistory;
import com.example.Tiendaenlinea.entities.Users;
import com.example.Tiendaenlinea.exceptions.ServicesException;
import com.example.Tiendaenlinea.services.AddressService;
import com.example.Tiendaenlinea.services.ClothesService;
import com.example.Tiendaenlinea.services.ShoppingHistoryService;
import com.example.Tiendaenlinea.services.UserService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClothesService clothesService;

    @Autowired
    private ShoppingHistoryService shoppingHistoryService;

    @GetMapping("/shipping")
    public String getShippingInfo(
            @RequestParam("ids") List<Integer> ids, ModelMap model, HttpSession session) {
        Users user = (Users) session.getAttribute("userSession");

        Address savedAddress = addressService.findByUser(user);

        if (savedAddress != null) {
            model.addAttribute("streetAndNumber", savedAddress.getStreetAndNumber());
            model.addAttribute("city", savedAddress.getCity());
            model.addAttribute("country", savedAddress.getCountry());
            model.addAttribute("postalCode", savedAddress.getPostalCode());
        }

        model.addAttribute("ids", ids);
        return "address.html";
    }

    @PostMapping("/shipping")
    public String updateShippingInfo(@RequestParam("ids") List<Integer> ids,
            //@ModelAttribute Address newAddress,
            @RequestParam("streetAndNumber") String streetAndNumber,
            @RequestParam("city") String city,
            @RequestParam("country") String country,
            @RequestParam("postalCode") String postalCode,
            ModelMap model, RedirectAttributes redirectAttributes, HttpSession session) throws ServicesException {
        Users user = (Users) session.getAttribute("userSession");

        Address existingAddress = addressService.findByUser(user);

        if (existingAddress == null) {
            existingAddress = new Address();
            existingAddress.setUser(user);
        }

        existingAddress.setStreetAndNumber(streetAndNumber);
        existingAddress.setCity(city);
        existingAddress.setCountry(country);
        existingAddress.setPostalCode(postalCode);

        addressService.saveOrUpdate(existingAddress);

        //return reviewOrder(ids, model, session);
        redirectAttributes.addAttribute("ids", ids);
        return "redirect:/checkout/paymentReview";
    }

    @GetMapping("/paymentReview")
    public String reviewOrder(
            @RequestParam() List<Integer> ids,
            @RequestParam(required = false, name = "error") String error,
            //List<Integer> ids,
            ModelMap model, HttpSession session) throws ServicesException {
        BigDecimal total = BigDecimal.ZERO;

        List<Clothes> clothes = new ArrayList<>();
        for (Integer id : ids) {
            clothes.add(clothesService.findById(id));
        }

        for (Clothes item : clothes) {
            total = total.add(item.getPrice());
        }

        model.addAttribute("clothes", clothes);
        model.addAttribute("total", total);
        model.addAttribute("ids", ids);

        return "paymentReview.html";
    }

    @PostMapping("/paymentReviewPost")
    public String reviewOrderPost(
            @RequestParam("ids") List<Integer> ids,
            @RequestParam(required = false) String error,
            @RequestParam(required = false, name = "success") String success,
            ModelMap model,
            RedirectAttributes redirectAttributes, HttpSession session) throws ServicesException {

        Users user = (Users) session.getAttribute("userSession");

        for (Integer id : ids) {
            Clothes ropa = clothesService.findById(id);

            if (ropa.getStockQuantity() < 1) {
                redirectAttributes.addAttribute("ids", ids.remove(id));
                redirectAttributes.addAttribute("error", "El producto " + ropa.getProductName() + " está fuera de stock.");
                return "redirect:/checkout/paymentReview";
            }

            ropa.setStockQuantity(ropa.getStockQuantity() - 1);
            clothesService.update(ropa);
        }
        ShoppingHistory shoppingHistory = shoppingHistoryService.findByUser(user);

        List<Integer> productsIds = ids;

        if (shoppingHistory.getProductIds() != null) {
            for (Integer productsId : shoppingHistory.getProductIds()) {
                productsIds.add(productsId);
            }
            shoppingHistory.setProductIds(productsIds);

            shoppingHistoryService.update(shoppingHistory);

        }
        return "redirect:/checkout/confirmation";
    }

    @GetMapping("/confirmation")
    public String confirmationPage(
            @RequestParam(required = false, name = "error") String error,
            @RequestParam(required = false, name = "success") String success
    ) {
        // En caso de concretarse la compra se avisará que esta fue exitosa
        //Se podría ir al inicio

        return "confirmation.html";
    }

    /*@GetMapping("/purchaseHistory")
    public String purchaseHistoryPage(
            @RequestParam("clothes") List<Clothes> clothes,
            @RequestParam(required = false) String error,
            @RequestParam(required = false, name = "success") String success) {
        // Se mostrará una lista de los productos que se hayan comprado

        return "confirmation.html";
    }*/
}
