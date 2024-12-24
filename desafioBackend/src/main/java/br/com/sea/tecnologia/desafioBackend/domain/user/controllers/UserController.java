package br.com.sea.tecnologia.desafioBackend.domain.user.controllers;

import br.com.sea.tecnologia.desafioBackend.domain.user.dto.UserDto;
import br.com.sea.tecnologia.desafioBackend.domain.user.entities.User;
import br.com.sea.tecnologia.desafioBackend.domain.user.repositories.UserRepository;
import br.com.sea.tecnologia.desafioBackend.domain.user.services.UserService;
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
@RequestMapping(value = "/users")
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
     public ResponseEntity<UserDto> findById(@PathVariable Long id) {
          UserDto userDto = userService.findById(id);
          return ResponseEntity.ok(userDto);
     }

     @PreAuthorize("hasRole('ROLE_ADMIN')")
     @GetMapping
     public ResponseEntity<Page<UserDto>> findAll(Pageable pageable) {
          Page<UserDto> userDtoPage = userService.findAll(pageable);
          return ResponseEntity.ok(userDtoPage);
     }

     @PreAuthorize("hasRole('ROLE_ADMIN')")
     @PutMapping(value = "/{id}")
     public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody @Valid UserDto userDto) {
          UserDto updateUserDto = userService.update(id, userDto);
          return ResponseEntity.ok(updateUserDto);
     }

     @PreAuthorize("hasRole('ROLE_ADMIN')")
     @DeleteMapping(value = "/{id}")
     public ResponseEntity<String> delete(@PathVariable Long id) {
          User user = userRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));
          userService.delete(id);
          String successMessage = "Usuário: ( " + user.getNome() + " ) excluído com sucesso com o ID: " + id;
          return ResponseEntity.ok(successMessage);
     }
}
