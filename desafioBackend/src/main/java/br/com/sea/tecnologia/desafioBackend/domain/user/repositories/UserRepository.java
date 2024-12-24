package br.com.sea.tecnologia.desafioBackend.domain.user.repositories;

import br.com.sea.tecnologia.desafioBackend.domain.user.entities.User;
import br.com.sea.tecnologia.desafioBackend.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     @Query(nativeQuery = true, value = """
    SELECT tb_user.cpf AS username, tb_user.password, tb_role.id AS roleId, tb_role.authority
    FROM tb_user
    INNER JOIN tb_user_role ON tb_user.id = tb_user_role.user_id
    INNER JOIN tb_role ON tb_role.id = tb_user_role.role_id
    WHERE tb_user.cpf = :cpf
""")
     List<UserDetailsProjection> searchUserAndRolesByCpf(String cpf);

     boolean existsByCpf(String cpf);
}
