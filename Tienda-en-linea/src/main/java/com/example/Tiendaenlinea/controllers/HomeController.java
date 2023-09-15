package com.example.Tiendaenlinea.controllers;

import com.example.Tiendaenlinea.entities.Cart;
import com.example.Tiendaenlinea.entities.PasswordToken;
import com.example.Tiendaenlinea.entities.ShoppingHistory;
import com.example.Tiendaenlinea.entities.Users;
import com.example.Tiendaenlinea.enums.Rol;
import com.example.Tiendaenlinea.exceptions.ServicesException;
import com.example.Tiendaenlinea.services.CartService;
import com.example.Tiendaenlinea.services.MailService;
import com.example.Tiendaenlinea.services.PasswordTokenService;
import com.example.Tiendaenlinea.services.ShoppingHistoryService;
import com.example.Tiendaenlinea.services.UserService;
import java.io.IOException;
import java.util.Base64;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;
    
    @Autowired
    private ShoppingHistoryService shoppingHistoryService;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordTokenService passwordTokenService;

    @GetMapping
    public String getIndex(HttpSession session, ModelMap model,
            @RequestParam(required = false, name = "success") String success,
            @RequestParam(required = false, name = "error") String error) {
        Users user = (Users) session.getAttribute("userSession");

        if (success != null) {
            model.addAttribute("success", success);
        }
        if (error != null) {
            model.addAttribute("error", error);
        }

        return "index.html";
    }

    @GetMapping("/home")
    public String getHome(HttpSession session, ModelMap model,
            @RequestParam(required = false, name = "success") String success,
            @RequestParam(required = false, name = "error") String error) {
        Users user = (Users) session.getAttribute("userSession");

        if (success != null) {
            model.addAttribute("success", success);
        }
        if (error != null) {
            model.addAttribute("error", error);
        }

        return "home.html";
    }

    @GetMapping("/login")
    public String getLoginForm(@RequestParam(required = false) String error,
            @RequestParam(required = false, name = "email") String email,
            @RequestParam(required = false, name = "success") String success,
            ModelMap model) {

        if (error != null) {
            model.put("error", "Usuario o contraseña invalida");
        }

        if (success != null) {
            model.addAttribute("success", success);
        }

        if (email != null) {
            model.addAttribute("email", email);
        }

        return "login.html";
    }

    @GetMapping("/signup/user")
    public String getFormUser(
            @RequestParam(required = false) String error,
            ModelMap model,
            HttpSession session) {

        if (error != null) {
            model.addAttribute("error", error);
        }
        return "createProfile.html";
    }

    @PostMapping("/singup/user-post")
    public String postFormUser(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("userName") String userName,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam(required = false, name = "description") String description,
            @RequestParam(required = false, name = "address") String address,
            @RequestParam(required = false, name = "city") String city,
            @RequestParam(required = false, name = "postalCode") String postalCode,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        try {
            if (userService.findByEmail(email) != null) {
                redirectAttributes.addAttribute("error", "El correo electrónico ya está registrado.");
                return "redirect:/signup/user";
            }

            if (userService.findByUsername(userName) != null) {
                redirectAttributes.addAttribute("error", "El nombre de usuario ya está registrado.");
                return "redirect:/signup/user";
            }

            if (!password.equals(confirmPassword)) {
                redirectAttributes.addAttribute("error", "Las contraseñas no coinciden");
                return "redirect:/signup/user";
            }

            mailService.sendEmail(
                    email,
                    "Tienda Online",
                    "El código para crear su cuenta es: " + passwordTokenService.generateCode(email));

            session.setAttribute("name", firstName);
            session.setAttribute("last_name", lastName);
            session.setAttribute("email", email);
            session.setAttribute("user_name", userName);
            session.setAttribute("password", password);
            session.setAttribute("description", description);
            session.setAttribute("address", address);
            session.setAttribute("city", city);
            session.setAttribute("postalCode", postalCode);
            session.setAttribute("stepCode", "stepCode");

        } catch (ServicesException ex) {
        }

        return "redirect:/code-signup/user";
    }

    @GetMapping("/code-signup/user")
    public String codeSignUpUser(
            @RequestParam(required = false, name = "error") String error,
            ModelMap model,
            HttpSession session) {

        if (error != null) {
            model.addAttribute("error", error);
        }
        model.addAttribute("email", (String) session.getAttribute("email"));
        return "codeSingUp.html";
    }

    @GetMapping("/image/user")
    public String imageUser(
            @RequestParam(name = "verificationCode") String verificationCode,
            HttpSession session, RedirectAttributes redirectAttributes) {

        String name = (String) session.getAttribute("name");
        String last_name = (String) session.getAttribute("last_name");
        String email = (String) session.getAttribute("email");
        String user_name = (String) session.getAttribute("user_name");
        String password = (String) session.getAttribute("password");
        String description = (String) session.getAttribute("description");
        String address = (String) session.getAttribute("address");
        String city = (String) session.getAttribute("city");
        String postalCode = (String) session.getAttribute("postalCode");

        System.out.println("codigo introducido: " + verificationCode);
        PasswordToken token = passwordTokenService.findByCode(verificationCode);

        if (token == null || !token.getUserMail().equals(email)) {

            session.setAttribute("name", name);
            session.setAttribute("last_name", last_name);
            session.setAttribute("email", email);
            session.setAttribute("user_name", user_name);
            session.setAttribute("password", password);
            session.setAttribute("description", description);
            session.setAttribute("address", address);
            session.setAttribute("city", city);
            session.setAttribute("postalCode", postalCode);
            //session.setAttribute("image", "image");
            redirectAttributes.addAttribute("error", "El código ingresado es incorrecto");
            return "redirect:/code-signup/user";
        }

        session.setAttribute("name", name);
        session.setAttribute("last_name", last_name);
        session.setAttribute("email", email);
        session.setAttribute("user_name", user_name);
        session.setAttribute("password", password);
        session.setAttribute("description", description);
        session.setAttribute("address", address);
        session.setAttribute("city", city);
        session.setAttribute("postalCode", postalCode);
        //session.setAttribute("image", "image");
        return "imageUser.html";
    }

    @PostMapping("/signup-post/user")
    public String signUpUser(
            @RequestParam(value = "imageFile", required = false) MultipartFile image,
            HttpSession session,
            RedirectAttributes redirectAttributes, ModelMap model) {

        Users user = new Users();
        user.setName((String) session.getAttribute("name"));
        user.setLast_name((String) session.getAttribute("last_name"));
        user.setEmail((String) session.getAttribute("email"));
        user.setUser_name((String) session.getAttribute("user_name"));
        user.setPassword((String) session.getAttribute("password"));
        user.setDescription((String) session.getAttribute("description"));

        try {
            setImage(user, image);
        } catch (IOException ioe) {
            redirectAttributes.addAttribute("error", "No se ha podido cargar la imagen, por favor ingrese otra");
            return "redirect:/image/user";
        }

        try {
            passwordTokenService.delete(passwordTokenService.findByMail(user.getEmail()).getId());
        } catch (ServicesException ex) {
        }

        return createUser(user, redirectAttributes, model);
    }

    private void setImage(Users user, MultipartFile image) throws IOException {
        byte[] imageBytes = image.getBytes();
        String Image = Base64.getEncoder().encodeToString(imageBytes);
        user.setImage(Image);
    }

    private String createUser(Users user, RedirectAttributes redirectAttributes, ModelMap model) {
        
        user.setRol(Rol.USER);
        userService.createUser(user);

        Cart cart = new Cart();
        cart.setUser(user);
        
        ShoppingHistory shoppingHistory = new ShoppingHistory();
        shoppingHistory.setUser(user);

        shoppingHistoryService.create(shoppingHistory);
        cartService.create(cart);

        redirectAttributes.addAttribute("success", "¡Su cuenta ha sido creada exitosamente!");
        redirectAttributes.addAttribute("email", user.getEmail());
        return "redirect:/login";
    }
}
