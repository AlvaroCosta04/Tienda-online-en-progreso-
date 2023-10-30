/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Tiendaenlinea.controllers;

import com.example.Tiendaenlinea.entities.Cart;
import com.example.Tiendaenlinea.entities.Clothes;
import com.example.Tiendaenlinea.entities.Image;
import com.example.Tiendaenlinea.entities.ImageData;
import com.example.Tiendaenlinea.entities.Users;
import com.example.Tiendaenlinea.exceptions.ServicesException;
import com.example.Tiendaenlinea.services.CartService;
import com.example.Tiendaenlinea.services.CategoryService;
import com.example.Tiendaenlinea.services.ClothesService;
import com.example.Tiendaenlinea.services.ColorService;
import com.example.Tiendaenlinea.services.ImageDataService;
import com.example.Tiendaenlinea.services.ImageService;
import com.example.Tiendaenlinea.services.SizeService;
import com.example.Tiendaenlinea.services.UserService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/catalogue")
public class ClothesController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClothesService clothesService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private ImageService imageService;

    /*@Autowired
    private ImageDataService imageDataService;*/
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
            List<String> images = new ArrayList<>();

            for (Clothes apparel : clothes) {
                List<Integer> imageIds = apparel.getImage_id();

                if (imageIds != null && !imageIds.isEmpty()) {
                    Integer imageId = imageIds.get(0);
                    try {
                        String base64Image = imageService.findById(imageId);
                        images.add(base64Image);
                    } catch (Exception e) {
                        images.add(null);
                    }
                } else {
                    images.add(null);
                }
            }

            model.put("clothes", clothes);
            model.put("images", images);
        } catch (ServicesException ex) {
        }

        return "catalogue.html";
    }

    @GetMapping("/create")
    public String showCreateClothesForm(Model model) throws ServicesException {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("colors", colorService.findAll());
        model.addAttribute("sizes", sizeService.findAll());
        model.addAttribute("clothes", new Clothes());
        return "publish.html";
    }

    /*@PostMapping("/createPost")
    public String createClothes(@ModelAttribute("clothes") @Valid Clothes clothes,
            RedirectAttributes redirectAttributes,
            @RequestParam(required = false, name = "images") List<MultipartFile> images) throws IOException {

        ArrayList<ImageData> imageIds = new ArrayList<>();

        try {
            for (MultipartFile picture : images) {
                byte[] imageBytes = picture.getBytes();
                String imageString = Base64.getEncoder().encodeToString(imageBytes);
                Image image = new Image();
                image.setImage(imageString);

                imageService.createImage(image);

                ImageData imageData = new ImageData();
                imageData.setImageId(image.getId());
                imageIds.add(imageData);
            }

            clothes.setImages(imageIds);

            clothesService.createClothe(clothes);

        } catch (IOException ioe) {
            redirectAttributes.addAttribute("error", "Ha ocurrido un error al publicar el producto");
            return "redirect:/catalogue/create";
        }

        redirectAttributes.addAttribute("success", "Se ha publicado exitosamente el producto");
        return "redirect:/home";
    }*/
    @PostMapping("/createPost")
    public String createClothes(@ModelAttribute("clothes") @Valid Clothes clothes,
            RedirectAttributes redirectAttributes,
            @RequestParam(required = false, name = "images[]") MultipartFile[] imagesMultipartFile) throws IOException {

        ArrayList<Integer> images_id = new ArrayList<>();

        try {
            for (MultipartFile picture : imagesMultipartFile) {
                byte[] imageBytes = picture.getBytes();
                String imageString = Base64.getEncoder().encodeToString(imageBytes);
                Image image = new Image();
                image.setImage(imageString);

                imageService.createImage(image);

                images_id.add(image.getId());
            }

            clothes.setImage_id(images_id);

            clothesService.createClothe(clothes);

        } catch (IOException ioe) {
            redirectAttributes.addAttribute("error", "Ha ocurrido un error al publicar el producto");
            return "redirect:/catalogue/create";
        }

        redirectAttributes.addAttribute("success", "Se ha publicado exitosamente el producto");
        return "redirect:/home";
    }

    /*@GetMapping("/product-details-{id}")
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
                    model.addAttribute("removeFromCart", "a");
                    break;
                } else {
                    model.addAttribute("addToCart", "a");
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
    }*/
    @GetMapping("/product-details-{id}")
    public String getProductDetails(
            @RequestParam(required = false, name = "succes") String succes,
            @PathVariable Integer id, HttpSession session,
            ModelMap model
    ) {
        Users user = (Users) session.getAttribute("userSession");
        try {
            Clothes apparel = clothesService.findById(id);

            List<Integer> imagesId = apparel.getImage_id();
            List<String> images = new ArrayList<>();

            /*if (!imagesId.isEmpty()) {
                for (Integer idImage : imagesId) {
                    String image = imageService.findById(idImage);
                    images.add(image);
                }
            }*/

            /*
             */
                List<Integer> imageIds = apparel.getImage_id();

                if (imageIds != null && !imageIds.isEmpty()) {
                    Integer imageId = imageIds.get(0);
                    try {
                        String base64Image = imageService.findById(imageId);
                        images.add(base64Image);
                    } catch (Exception e) {
                        images.add(null);
                    }
                } else {
                    images.add(null);
                }

            model.put("images", images);
            /*
             */

            Cart cart = cartService.findByUser(user);
            List<Integer> productsIds = cart.getProductIds();

            String addToCart = "1";

            for (Integer productsId : productsIds) {
                if (productsId == id) {
                    addToCart = "0";
                    break;
                }
            }
            if (succes != null) {
                model.addAttribute("succes", succes);
            }

            //model.addAttribute("clothes", apparel);
            // model.addAttribute("idClothes", id);
            // model.addAttribute("addToCart", addToCart);
            //model.addAttribute("images", images);
            //return "productDetails.html";
            return "aAa.html";
        } catch (ServicesException ex) {
            return "redirect:/catalogue?error=Error al obtener los detalles del producto";
        }
    }

}
