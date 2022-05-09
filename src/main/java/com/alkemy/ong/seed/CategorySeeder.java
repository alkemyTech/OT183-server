package com.alkemy.ong.seed;

import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CategorySeeder implements CommandLineRunner {

    private final CategoryRepository repository;

    @Override
    public void run(String... args) throws Exception {
        if (repository.getCategoriesQuantity() == 0) {
            for (int i = 0; i < 39; i++) {
                createCategory(i);
            }
        }
    }

    public Category createCategory(int index) {
        return repository.save(new Category(getName(index)));
    }

    public String getName(int index) {
        return "Category " + (index + 1);
    }

}
