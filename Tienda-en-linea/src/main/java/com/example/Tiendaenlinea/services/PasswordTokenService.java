package com.example.Tiendaenlinea.services;

import com.example.Tiendaenlinea.entities.PasswordToken;
import com.example.Tiendaenlinea.exceptions.ServicesException;
import com.example.Tiendaenlinea.repositories.PasswordTokenRepository;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PasswordTokenService {

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    /*public String generateCode(String mail) throws ServicesException {
        System.out.println("generar codigo");
        String CHARACTERS = "0123456789ABCDEFGHJKLMNOPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
        int CODE_LENGTH = 7;

        PasswordToken token = new PasswordToken();
        token.setUserMail(mail);
        token.setCreateDate(LocalDateTime.now());

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        token.setCode(sb.toString());
        createToken(token);
        return token.getCode();
    }*/
    public String generateCode(String mail) throws ServicesException {
        System.out.println("generar codigo");
        String CHARACTERS = "0123456789ABCDEFGHJKLMNOPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
        int CODE_LENGTH = 7;

        PasswordToken token = new PasswordToken();
        token.setUserMail(mail);
        token.setCreateDate(LocalDateTime.now());

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        token.setCode(sb.toString());
        createToken(token);

        String codeWithDashes = insertDashes(token.getCode(), 1);
        token.setCode(codeWithDashes);

        return codeWithDashes;
    }

    private String insertDashes(String text, int interval) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            result.append(text.charAt(i));
            if (i < text.length() - 1 && (i + 1) % interval == 0) {
                result.append('-');
            }
        }
        return result.toString();
    }

    @Transactional
    public void createToken(PasswordToken token) {
        if (findByMail(token.getUserMail()) == null) {
            passwordTokenRepository.save(token);
        } else {
            try {
                delete(findByMail(token.getUserMail()).getId());
                passwordTokenRepository.save(token);
            } catch (ServicesException ex) {
            }
        }
    }

    //delete
    @Transactional
    public void delete(Integer id) throws ServicesException {
        PasswordToken token = findById(id);
        passwordTokenRepository.delete(token);
    }

    public PasswordToken findById(Integer id) throws ServicesException {
        return passwordTokenRepository.findById(id).orElseThrow(() -> new ServicesException("No se ha encontrado un token con el id ingresado"));
    }

    @Transactional(readOnly = true)
    public PasswordToken findByMail(String mail) {
        Optional<PasswordToken> optionalToken = passwordTokenRepository.findByUserMail(mail);
        if (optionalToken.isPresent()) {
            return optionalToken.get();
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public PasswordToken findByCode(String code) {
        Optional<PasswordToken> optionalToken = passwordTokenRepository.findByCode(code);
        if (optionalToken.isPresent()) {
            return optionalToken.get();
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<PasswordToken> getAll() {
        return passwordTokenRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<PasswordToken> findTokensByMail(String mail) {
        return passwordTokenRepository.findTokensByUserMail(mail);
    }
}
