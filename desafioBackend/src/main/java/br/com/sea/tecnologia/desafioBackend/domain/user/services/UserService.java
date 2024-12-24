package br.com.sea.tecnologia.desafioBackend.domain.user.services;

import br.com.sea.tecnologia.desafioBackend.domain.user.dto.*;
import br.com.sea.tecnologia.desafioBackend.domain.user.entities.*;
import br.com.sea.tecnologia.desafioBackend.domain.user.enums.RoleType;
import br.com.sea.tecnologia.desafioBackend.domain.user.repositories.PhoneRepository;
import br.com.sea.tecnologia.desafioBackend.domain.user.repositories.RoleRepositories;
import br.com.sea.tecnologia.desafioBackend.domain.user.repositories.UserRepository;
import br.com.sea.tecnologia.desafioBackend.exceptions.ResourceAlreadyExistsException;
import br.com.sea.tecnologia.desafioBackend.exceptions.ResourceNotFoundException;

import br.com.sea.tecnologia.desafioBackend.projections.UserDetailsProjection;
import br.com.sea.tecnologia.desafioBackend.util.ApiViaCep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

     private final UserRepository userRepository;
     private final PhoneRepository phoneRepository;
     private final ApiViaCep apiViaCep;
     private final RoleRepositories roleRepositories;
     private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


     @Autowired
     public UserService(UserRepository userRepository, PhoneRepository phoneRepository, ApiViaCep apiViaCep, RoleRepositories roleRepositories) {
          this.userRepository = userRepository;
          this.phoneRepository = phoneRepository;
          this.apiViaCep = apiViaCep;
          this.roleRepositories = roleRepositories;
     }

     @Transactional
     public UserDto create(UserDto dto) {
          if (userRepository.existsByCpf(dto.getCpf())) {
               throw new ResourceAlreadyExistsException("CPF já cadastrado: ( " + dto.getCpf() + " )");
          }
          for (PhoneDto phoneDto : dto.getPhones()) {
               if (phoneRepository.existsByNumero(phoneDto.getNumero())) {
                    throw new ResourceAlreadyExistsException("Número de telefone já cadastrado: " + phoneDto.getNumero());
               }
          }
          validateCpfDto(dto);
          User user = mapDtoToUser(dto);
          user = userRepository.save(user);
          return new UserDto(user);
     }

     @Transactional(readOnly = true)
     public UserDto findById(Long id) {
          User user = userRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));
          return new UserDto(user);
     }

     @Transactional(readOnly = true)
     public Page<UserDto> findAll(Pageable pageable) {
          Page<User> users = userRepository.findAll(pageable);
          return users.map(UserDto::new);
     }

     @Transactional
     public UserDto update(Long id, UserDto dto) {
          User user = userRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));
          updateUserFromDto(user, dto);
          user = userRepository.save(user);
          return new UserDto(user);
     }

     @Transactional
     public void delete(Long id) {
          userRepository.deleteById(id);
     }

     private void validateCpfDto(UserDto dto) {
          if (!apiViaCep.isValidCep(dto.getAddress().getCep())) {
               throw new ResourceNotFoundException("CEP inválido ou não encontrado: ( " + dto.getAddress().getCep() + " )");
          }
     }

     @Override
     public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
          // Buscar os dados do usuário e suas roles no banco
          List<UserDetailsProjection> result = userRepository.searchUserAndRolesByCpf(cpf);

          // Verificar se o resultado está vazio
          if (result.isEmpty()) {
               throw new UsernameNotFoundException("Usuário não encontrado com CPF: " + cpf);
          }

          // Criar o objeto User
          User user = new User();
          user.setCpf(cpf); // Definindo o CPF
          user.setPassword(result.get(0).getPassword()); // Definindo a senha

          // Adicionando as roles (permissões) ao usuário
          for (UserDetailsProjection projection : result) {
               user.addRole(new Role(projection.getRoleId(), RoleType.valueOf(projection.getAuthority())));
          }

          // Retorna o usuário com todas as roles e senha definidas
          return user;
     }

     public User mapDtoToUser(UserDto dto) {
          User user = new User();
          user.setNome(dto.getNome());
          user.setCpf(dto.getCpf());
          user.setPassword(passwordEncoder.encode(dto.getPassword()));
          user.setAddress(mapDtoToAddress(dto.getAddress()));
          user.setPhones(mapDtoToPhones(dto.getPhones(), user));
          user.setEmails(mapDtoToEmails(dto.getEmails(), user));
          user.setRoles(mapDtoToRoles(dto.getRoles()));
          return user;
     }

     public void updateUserFromDto(User user, UserDto dto) {
          user.setNome(dto.getNome());
          user.setCpf(dto.getCpf());
          user.setPassword(passwordEncoder.encode(dto.getPassword()));
          updateAddressFromDto(user.getAddress(), dto.getAddress());
          user.getPhones().clear();
          user.getPhones().addAll(mapDtoToPhones(dto.getPhones(), user));
          user.getEmails().clear();
          user.getEmails().addAll(mapDtoToEmails(dto.getEmails(), user));
          user.setRoles(mapDtoToRoles(dto.getRoles()));
     }

     public Address mapDtoToAddress(AddressDto dto) {
          Address address = new Address();
          address.setCep(dto.getCep());
          address.setLogradouro(dto.getLogradouro());
          address.setBairro(dto.getBairro());
          address.setCidade(dto.getCidade());
          address.setUf(dto.getUf());
          address.setComplemento(dto.getComplemento());
          return address;
     }

     public void updateAddressFromDto(Address address, AddressDto dto) {
          address.setCep(dto.getCep());
          address.setLogradouro(dto.getLogradouro());
          address.setBairro(dto.getBairro());
          address.setCidade(dto.getCidade());
          address.setUf(dto.getUf());
          address.setComplemento(dto.getComplemento());
     }

     public Set<Phone> mapDtoToPhones(Set<PhoneDto> phoneDtos, User user) {
          Set<Phone> phones = new HashSet<>();
          for (PhoneDto phoneDto : phoneDtos) {
               Phone phone = new Phone();
               phone.setNumero(phoneDto.getNumero());
               phone.setPhoneType(phoneDto.getPhoneType());
               phone.setClient(user);
               phones.add(phone);
          }
          return phones;
     }

     public Set<Emaill> mapDtoToEmails(Set<EmailDto> emailDtos, User user) {
          Set<Emaill> emails = new HashSet<>();
          for (EmailDto emailDto : emailDtos) {
               Emaill email = new Emaill();
               email.setEmail(emailDto.getEmail());
               email.setClient(user);
               emails.add(email);
          }
          return emails;
     }

     public Set<Role> mapDtoToRoles(Set<RoleDto> roleDtos) {
          Set<Role> roles = new HashSet<>();
          for (RoleDto roleDto : roleDtos) {
               Role role = roleRepositories.getReferenceById(roleDto.getId());
               roles.add(role);
          }
          return roles;
     }
}