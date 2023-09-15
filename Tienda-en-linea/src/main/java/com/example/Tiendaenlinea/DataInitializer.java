package com.example.Tiendaenlinea;

import com.example.Tiendaenlinea.entities.Category;
import com.example.Tiendaenlinea.entities.Clothes;
import com.example.Tiendaenlinea.entities.Color;
import com.example.Tiendaenlinea.entities.ImageData;
import com.example.Tiendaenlinea.entities.Size;
import com.example.Tiendaenlinea.repositories.CategoryRepository;
import com.example.Tiendaenlinea.repositories.ClothesRepository;
import com.example.Tiendaenlinea.repositories.ColorRepository;
import com.example.Tiendaenlinea.repositories.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ClothesRepository clothesRepository;
    private final CategoryRepository categoryRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;

    @Autowired
    public DataInitializer(
            ClothesRepository clothesRepository,
            CategoryRepository categoryRepository,
            ColorRepository colorRepository,
            SizeRepository sizeRepository) {
        this.clothesRepository = clothesRepository;
        this.categoryRepository = categoryRepository;
        this.colorRepository = colorRepository;
        this.sizeRepository = sizeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (clothesRepository.count() < 1) {
            createCategories();
            createColors();
            createSizes();
            generateClothes();
            System.out.println("Inicialización concluida, las entidades se han creado de manera exitosa");
        }
    }

    private void createCategories() {
        List<String> categoryNames = List.of("Camisetas", "Pantalones", "Vestidos", "Chaquetas", "Faldas",
                "Sudaderas", "Camisas", "Jeans", "Ropa deportiva", "Accesorios");
        for (String categoryName : categoryNames) {
            Category category = new Category();
            category.setCategoryName(categoryName);
            categoryRepository.save(category);
        }
    }

    private void createColors() {
        List<String> colorNames = List.of("Rojo", "Azul", "Verde", "Amarillo", "Negro", "Blanco", "Rosa", "Gris");
        for (String colorName : colorNames) {
            Color color = new Color();
            color.setColorName(colorName);
            colorRepository.save(color);
        }
    }

    private void createSizes() {
        List<String> sizeNames = List.of("S", "M", "L", "XL");
        for (String sizeName : sizeNames) {
            Size size = new Size();
            size.setSizeName(sizeName);
            sizeRepository.save(size);
        }
    }

    private void generateClothes() {
        Random random = new Random();

        List<Category> categories = categoryRepository.findAll();
        List<Color> colors = colorRepository.findAll();
        List<Size> sizes = sizeRepository.findAll();

        for (int i = 0; i < 50; i++) {
            Clothes clothes = new Clothes();
            clothes.setProductName("Producto " + (i + 1));
            clothes.setDescription("Descripción del producto " + (i + 1));
            clothes.setPrice(BigDecimal.valueOf(random.nextInt(100) + 20));
            clothes.setCategory(categories.get(i % categories.size()));

            List<Color> selectedColors = new ArrayList<>();
            selectedColors.add(colors.get(random.nextInt(colors.size())));
            selectedColors.add(colors.get(random.nextInt(colors.size())));
            clothes.setColors(selectedColors);

            List<Size> selectedSizes = new ArrayList<>();
            selectedSizes.add(sizes.get(random.nextInt(sizes.size())));
            selectedSizes.add(sizes.get(random.nextInt(sizes.size())));
            clothes.setSizes(selectedSizes);

            List<ImageData> images = new ArrayList<>();
            images.add(new ImageData("url1", null));
            images.add(new ImageData("url2", null));
            images.add(new ImageData("url3", null));
            images.add(new ImageData("url4", null));
            clothes.setImages(images);

            clothes.setStockQuantity(random.nextInt(50) + 10);
            clothesRepository.save(clothes);
        }
    }
}
