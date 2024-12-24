package br.com.sea.tecnologia.desafioBackend.domain.product.controllers;

import br.com.sea.tecnologia.desafioBackend.domain.product.dto.ProductDto;
import br.com.sea.tecnologia.desafioBackend.domain.product.entities.Product;
import br.com.sea.tecnologia.desafioBackend.domain.product.repositories.ProductRepository;
import br.com.sea.tecnologia.desafioBackend.domain.product.services.ProductService;
import br.com.sea.tecnologia.desafioBackend.exceptions.ResourceNotFoundException;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

     private final ProductService productService;
     private final ProductRepository productRepository;

     @Autowired
     public ProductController(ProductService productService, ProductRepository productRepository) {
          this.productService = productService;
          this.productRepository = productRepository;
     }

     @PreAuthorize("hasRole('ROLE_ADMIN')")
     @PostMapping
     public ResponseEntity<ProductDto> create(@RequestBody @Valid ProductDto productDto) {
          ProductDto newProductDto = productService.create(productDto);
         return ResponseEntity
                  .created(ServletUriComponentsBuilder
                          .fromCurrentRequest()
                          .path("/{id}")
                          .buildAndExpand(newProductDto.getId())
                          .toUri())
                  .body(newProductDto);
     }

     @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
     @GetMapping(value = "/{id}")
     public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
          ProductDto productDto = productService.findById(id);
          return ResponseEntity.ok(productDto);
     }

     @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
     @GetMapping
     public ResponseEntity<Page<ProductDto>> findAll(Pageable pageable) {
          Page<ProductDto> productDtoPage = productService.findAll(pageable);
          return ResponseEntity.ok(productDtoPage);
     }

     @PreAuthorize("hasRole('ROLE_ADMIN')")
     @PutMapping(value = "/{id}")
     public ResponseEntity<ProductDto> update (@PathVariable Long id, @RequestBody @Valid ProductDto productDto) {
          ProductDto newProductDto = productService.update(id, productDto);
          return ResponseEntity.ok(newProductDto);
     }

     @PreAuthorize("hasRole('ROLE_ADMIN')")
     @DeleteMapping(value = "/{id}")
     public ResponseEntity<String> delete(@PathVariable Long id) {
          Product product = productRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));
          productService.delete(id);
          String successMessage = "Produto: ( " + product.getName() + " ) excluído com sucesso com o ID: " + id;
          return ResponseEntity.ok(successMessage);
     }
}
