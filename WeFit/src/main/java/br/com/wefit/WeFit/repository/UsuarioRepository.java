package br.com.wefit.WeFit.repository;

import br.com.wefit.WeFit.Domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCpf(String cpf);

    Optional<Usuario> findByCnpj(String cnpj);

    Optional<Usuario> findByEmail(String email);

}
