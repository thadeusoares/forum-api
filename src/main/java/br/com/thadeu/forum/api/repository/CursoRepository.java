package br.com.thadeu.forum.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.thadeu.forum.api.modelo.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{

	Curso findByNome(String nome);

}
