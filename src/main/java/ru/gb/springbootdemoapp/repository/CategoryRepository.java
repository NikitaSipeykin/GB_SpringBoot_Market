package ru.gb.springbootdemoapp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gb.springbootdemoapp.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  Optional<Category> findByTitle(String title);

  @Query("FROM Category c LEFT JOIN FETCH c.products WHERE c.id = :id")
  Category findByIdFetchProducts(@Param("id") Long id);
}
