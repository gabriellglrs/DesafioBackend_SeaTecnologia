package br.com.sea.tecnologia.desafioBackend.domain.product.services;

import br.com.sea.tecnologia.desafioBackend.domain.product.dto.ProductDto;
import br.com.sea.tecnologia.desafioBackend.domain.product.entities.Product;
import br.com.sea.tecnologia.desafioBackend.exceptions.ResourceNotFoundException;
import br.com.sea.tecnologia.desafioBackend.domain.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

     private final ProductRepository productRepository;

     @Autowired
     public ProductService(ProductRepository productRepository) {
          this.productRepository = productRepository;
     }

     // Método para criar um novo produto
     @Transactional
     public ProductDto create(ProductDto productDto) {
          Product product = new Product();
          copyEntityDto(product, productDto);
          product = productRepository.save(product);
          return new ProductDto(product);
     }

     // Método para buscar um produto pelo ID
     @Transactional(readOnly = true)
     public ProductDto findById(Long id) {
          Product product = productRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + id));
          return new ProductDto(product);
     }

     // Método para buscar todos os produtos
     @Transactional(readOnly = true)
     public Page<ProductDto> findAll(Pageable pageable) {
          Page<Product> products = productRepository.findAll(pageable);
          return products.map(ProductDto::new);
     }

     // Método para atualizar um produto
     @Transactional
     public ProductDto update(Long id, ProductDto productDto) {
          Product product = productRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + id));
          copyEntityDto(product, productDto);
          product = productRepository.save(product);
          return new ProductDto(product);
     }

     // Método para deletar um produto
     @Transactional
     public void delete(Long id) {
          productRepository.deleteById(id);
     }

     private void copyEntityDto(Product product, ProductDto dto) {
          product.setName(dto.getName());
          product.setDescription(dto.getDescription());
          product.setPrice(dto.getPrice());
     }

}