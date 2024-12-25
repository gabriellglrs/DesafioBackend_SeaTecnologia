package br.com.sea.tecnologia.desafioBackend.domain.user.controllers;

import br.com.sea.tecnologia.desafioBackend.domain.user.dto.UserDto;
import br.com.sea.tecnologia.desafioBackend.domain.user.entities.User;
import br.com.sea.tecnologia.desafioBackend.domain.user.repositories.UserRepository;
import br.com.sea.tecnologia.desafioBackend.domain.user.services.UserService;
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
@RequestMapping(value = "/users")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UserController {
     private final UserService userService;
     private final UserRepository userRepository;

     @Autowired
     public UserController(UserService userService, UserRepository userRepository) {
          this.userService = userService;
          this.userRepository = userRepository;
     }

     @PreAuthorize("hasRole('ROLE_ADMIN')")
     @PostMapping
     @Operation(summary = "Criar usuário", description = "Permite criar um novo usuário. Requer o papel ROLE_ADMIN.")
     @ApiResponses({
             @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                     content = @Content(mediaType = "application/json",
                             schema = @Schema(implementation = UserDto.class))),
             @ApiResponse(responseCode = "400", description = "Erro de validação", content = @Content)
     })
     public ResponseEntity<UserDto> create(@RequestBody @Valid UserDto userDto) {
          UserDto createdUser = userService.create(userDto);
          return ResponseEntity
                  .created(ServletUriComponentsBuilder
                          .fromCurrentRequest()
                          .path("/{id}")
                          .buildAndExpand(createdUser.getId())
                          .toUri())
                  .body(createdUser);
     }

     @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
     @GetMapping(value = "/{id}")
     @Operation(summary = "Buscar usuário por ID", description = "Retorna os detalhes de um usuário com base no ID. Acesso permitido para ADMIN ou USER.")
     @ApiResponses({
             @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                     content = @Content(mediaType = "application/json",
                             schema = @Schema(implementation = UserDto.class))),
             @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
     })
     public ResponseEntity<UserDto> findById(@PathVariable Long id) {
          UserDto userDto = userService.findById(id);
          return ResponseEntity.ok(userDto);
     }

     @PreAuthorize("hasRole('ROLE_ADMIN')")
     @GetMapping
     @Operation(summary = "Listar todos os usuários", description = "Retorna uma página com os usuários cadastrados. Requer o papel ROLE_ADMIN.")
     @ApiResponses({
             @ApiResponse(responseCode = "200", description = "Lista de usuários retornada",
                     content = @Content(mediaType = "application/json",
                             schema = @Schema(implementation = Page.class)))
     })
     public ResponseEntity<Page<UserDto>> findAll(Pageable pageable) {
          Page<UserDto> userDtoPage = userService.findAll(pageable);
          return ResponseEntity.ok(userDtoPage);
     }

     @PreAuthorize("hasRole('ROLE_ADMIN')")
     @PutMapping(value = "/{id}")
     @Operation(summary = "Atualizar usuário", description = "Atualiza as informações de um usuário com base no ID. Requer o papel ROLE_ADMIN.")
     @ApiResponses({
             @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                     content = @Content(mediaType = "application/json",
                             schema = @Schema(implementation = UserDto.class))),
             @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
     })
     public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody @Valid UserDto userDto) {
          UserDto updateUserDto = userService.update(id, userDto);
          return ResponseEntity.ok(updateUserDto);
     }

     @PreAuthorize("hasRole('ROLE_ADMIN')")
     @DeleteMapping(value = "/{id}")
     @Operation(summary = "Excluir usuário", description = "Exclui um usuário com base no ID. Requer o papel ROLE_ADMIN.")
     @ApiResponses({
             @ApiResponse(responseCode = "200", description = "Usuário excluído com sucesso",
                     content = @Content(mediaType = "application/json")),
             @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
     })
     public ResponseEntity<String> delete(@PathVariable Long id) {
          User user = userRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));
          userService.delete(id);
          String successMessage = "Usuário: ( " + user.getNome() + " ) excluído com sucesso com o ID: " + id;
          return ResponseEntity.ok(successMessage);
     }
}
