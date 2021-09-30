package br.com.thadeu.forum.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.thadeu.forum.api.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long>{
	//Utilizar esse underline força ele a buscar abaixo da entidade
	//List<Topico> findByCurso_Nome(String nomeCurso);
	Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao);
	
}
