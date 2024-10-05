package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Sort;

import java.util.List;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> findAll(@RequestParam(value = "min", required = false) Long min,
                                 @RequestParam(value = "max", required = false) Long max) {
        Sort sort = Sort.by("price").ascending();
        if (min == null && max == null) {
            return productRepository.findAll(sort);
        }
        if (min != null && max != null) {
            return productRepository.findByPriceBetween(min, max, sort);
        }
        if (min != null) {
            return productRepository.findByPriceGreaterThanEqual(min, sort);
        }
        return productRepository.findByPriceLessThanEqual(max, sort);
    }
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product =  productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
