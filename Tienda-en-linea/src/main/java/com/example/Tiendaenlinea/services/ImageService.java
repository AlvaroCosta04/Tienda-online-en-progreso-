package com.example.Tiendaenlinea.services;

import com.example.Tiendaenlinea.entities.Image;
import com.example.Tiendaenlinea.exceptions.ServicesException;
import com.example.Tiendaenlinea.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Transactional(readOnly = true)
    public Image findImageById(Integer imageId) {
        return imageRepository.findImageById(imageId);
    }

    @Transactional(readOnly = true)
    public String findById(Integer image_id) throws ServicesException {
        Optional<Image> optionalImage = imageRepository.findById(image_id);
        if (!optionalImage.isPresent()) {
            throw new ServicesException("No se ha encontrado una imagen con el id " + image_id);
        }
        Image image = optionalImage.get();
        return image.getImage();
    }

    @Transactional
    public Image createImage(Image image) {
        return imageRepository.save(image);
    }

    @Transactional
    public Image updateImage(Image image) {
        return imageRepository.save(image);
    }

    @Transactional
    public void deleteImage(Integer imageId) {
        imageRepository.deleteById(imageId);
    }

    @Transactional(readOnly = true)
    public List<Image> findAllImages() {
        return imageRepository.findAll();
    }
}
