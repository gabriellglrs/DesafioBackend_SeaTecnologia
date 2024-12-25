package br.com.sea.tecnologia.desafioBackend.domain.product.controllers;

import br.com.sea.tecnologia.desafioBackend.domain.product.dto.ProductDto;
import br.com.sea.tecnologia.desafioBackend.domain.product.entities.Product;
import br.com.sea.tecnologia.desafioBackend.domain.product.repositories.ProductRepository;
import br.com.sea.tecnologia.desafioBackend.domain.product.services.ProductService;
import br.com.sea.tecnologia.desafioBackend.exceptions.ResourceNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
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
     @Operation(summary = "Criar produto - ADMIN", description = "Permite criar um novo produto. Requer o papel ROLE_ADMIN.")
     @ApiResponses({
             @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
                     content = @Content(mediaType = "application/json",
                             schema = @Schema(implementation = ProductDto.class))),
             @ApiResponse(responseCode = "400", description = "Erro de validação", content = @Content)
     })
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
     @Operation(summary = "Buscar produto por ID - USER/ADMIN", description = "Retorna os detalhes de um produto com base no ID. Acesso permitido para ADMIN ou USER.")
     @ApiResponses({
             @ApiResponse(responseCode = "200", description = "Produto encontrado",
                     content = @Content(mediaType = "application/json",
                             schema = @Schema(implementation = ProductDto.class))),
             @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content)
     })
     public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
          ProductDto productDto = productService.findById(id);
          return ResponseEntity.ok(productDto);
     }

     @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
     @GetMapping
     @Operation(summary = "Listar todos os produtos - USER/ADMIN", description = "Retorna uma página com todos os produtos cadastrados. Acesso permitido para ADMIN ou USER.")
     @ApiResponses({
             @ApiResponse(responseCode = "200", description = "Lista de produtos retornada",
                     content = @Content(mediaType = "application/json",
                             schema = @Schema(implementation = Page.class)))
     })
     public ResponseEntity<Page<ProductDto>> findAll(Pageable pageable) {
          Page<ProductDto> productDtoPage = productService.findAll(pageable);
          return ResponseEntity.ok(productDtoPage);
     }

     @PreAuthorize("hasRole('ROLE_ADMIN')")
     @PutMapping(value = "/{id}")
     @Operation(summary = "Atualizar produto - ADMIN", description = "Atualiza as informações de um produto com base no ID. Requer o papel ROLE_ADMIN.")
     @ApiResponses({
             @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",
                     content = @Content(mediaType = "application/json",
                             schema = @Schema(implementation = ProductDto.class))),
             @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content)
     })
     public ResponseEntity<ProductDto> update (@PathVariable Long id, @RequestBody @Valid ProductDto productDto) {
          ProductDto newProductDto = productService.update(id, productDto);
          return ResponseEntity.ok(newProductDto);
     }

     @PreAuthorize("hasRole('ROLE_ADMIN')")
     @DeleteMapping(value = "/{id}")
     @Operation(summary = "Excluir produto - ADMIN", description = "Exclui um produto com base no ID. Requer o papel ROLE_ADMIN.")
     @ApiResponses({
             @ApiResponse(responseCode = "200", description = "Produto excluído com sucesso",
                     content = @Content(mediaType = "application/json")),
             @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content)
     })
     public ResponseEntity<String> delete(@PathVariable Long id) {
          Product product = productRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));
          productService.delete(id);
          String successMessage = "Produto: ( " + product.getName() + " ) excluído com sucesso com o ID: " + id;
          return ResponseEntity.ok(successMessage);
     }
}
