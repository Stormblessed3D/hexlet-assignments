package exercise.controller;

import java.util.List;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.mapper.ProductMapper;
import exercise.repository.CategoryRepository;
import jakarta.validation.ConstraintViolationException;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.ProductRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    // BEGIN
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<ProductDTO> getAll() {
        var products = productRepository.findAll();
        return products.stream()
                .map(p -> productMapper.map(p))
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProductDTO create(@Valid @RequestBody ProductCreateDTO dto) {
        var category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ConstraintViolationException("Task with id " + dto.getCategoryId() + " not found", null));
        var product = productMapper.map(dto);
        productRepository.save(product);
        return productMapper.map(product);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ProductDTO get(@PathVariable Long id) {
        var product =  productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
        var productDTO = productMapper.map(product);
        return productDTO;

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ProductDTO update(@RequestBody @Valid ProductUpdateDTO dto, @PathVariable Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
        var category = categoryRepository.findById(dto.getCategoryId().get())
                .orElseThrow(() -> new ConstraintViolationException("Task with id " + dto.getCategoryId() + " not found", null));
        productMapper.update(dto, product);
        productRepository.save(product);
        return productMapper.map(product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
    // END
}