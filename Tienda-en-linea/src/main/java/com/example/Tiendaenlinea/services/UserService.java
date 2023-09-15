package com.example.Tiendaenlinea.services;

import com.example.Tiendaenlinea.entities.Users;
import com.example.Tiendaenlinea.exceptions.ServicesException;
import com.example.Tiendaenlinea.repositories.UserRepository;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email);
        if (null == user) {
            throw new UsernameNotFoundException("No se ha encontrado el usuario");
        }
        List<GrantedAuthority> permissions = new ArrayList();
        GrantedAuthority grantedAuth = new SimpleGrantedAuthority("ROLE_" + user.getRol().toString());
        permissions.add(grantedAuth);

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute("userSession", user);
        return new User(user.getEmail(), user.getPassword(), permissions);
    }

    //create
    @Transactional
    public void createUser(Users user) {
        user.setCreationDate(new Date());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    //delete
    @Transactional
    public void delete(@NotNull Integer id) throws ServicesException {
        Users user = findById(id);
        userRepository.delete(user);
    }

    //update
    @Transactional
    public void update(Users user) {
        userRepository.save(user);
    }

    //read
    @Transactional(readOnly = true)
    public Users findById(@NotNull Integer id) throws ServicesException {
        return userRepository.findById(id).orElseThrow(() -> new ServicesException("No se ha encontrado un usuario con el id ingresado"));
    }

    @Transactional(readOnly = true)
    public Users findByUsername(String username) throws ServicesException {
        return userRepository.findByUserName(username);
    }

    @Transactional(readOnly = true)
    public Users findByEmail(@NotNull String email) throws ServicesException {
        return userRepository.findByEmail(email);
    }
}
