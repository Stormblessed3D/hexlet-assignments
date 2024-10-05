package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import exercise.model.Product;

import org.springframework.data.domain.Sort;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // BEGIN
    List<Product> findByPriceBetween(Long min, Long max, Sort sort);

    List<Product> findByPriceGreaterThanEqual(Long min, Sort sort);

    List<Product> findByPriceLessThanEqual(Long max, Sort sort);
    // END
}
