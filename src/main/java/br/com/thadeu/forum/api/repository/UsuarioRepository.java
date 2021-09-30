package br.com.thadeu.forum.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.thadeu.forum.api.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByEmail(String email);

}
