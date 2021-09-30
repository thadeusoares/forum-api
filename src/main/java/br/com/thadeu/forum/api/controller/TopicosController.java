package br.com.thadeu.forum.api.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.thadeu.forum.api.controller.dto.DetalhesTopicoDto;
import br.com.thadeu.forum.api.controller.dto.TopicoDto;
import br.com.thadeu.forum.api.controller.form.AtualizacaoTopicoForm;
import br.com.thadeu.forum.api.controller.form.TopicoForm;
import br.com.thadeu.forum.api.modelo.Topico;
import br.com.thadeu.forum.api.repository.CursoRepository;
import br.com.thadeu.forum.api.repository.TopicoRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/topicos")
@ApiImplicitParams({
    @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "Pagina a ser carregada", defaultValue = "0"),
    @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "Quantidade de registros", defaultValue = "5"),
    @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
            value = "Ordenacao dos registros")
})
public class TopicosController {

	@Autowired
	private TopicoRepository topicoRepository;

	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	@Cacheable(value = "listaDeTopicos") 	//Não é bom utilizar cache em tabelas que são altamente utilizadas
	public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso,
			@PageableDefault(sort = "id", direction = Direction.DESC, page=0, size = 10)  @ApiIgnore  Pageable paginacao) {
		/*
		 * URL de invocação
		 * http://localhost:8080/topicos?page=0&size=10&sort=dataCriacao,desc -->
		 * acumulado &sort=id,desc
		 */
		// Pageable paginacao = PageRequest.of(pagina, qtd);

		if (nomeCurso == null) {
			Page<Topico> topicos = topicoRepository.findAll(paginacao);
			return TopicoDto.converter(topicos);
		} else {
			Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
			return TopicoDto.converter(topicos);
		}
	}

	@Transactional
	@PostMapping
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDto> cadastrar(@Valid @RequestBody TopicoForm topicoForm,
			UriComponentsBuilder uriBuilder) {
		Topico topico = topicoForm.converter(cursoRepository);
		topicoRepository.save(topico);

		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}

	@GetMapping("/{id}")
	public ResponseEntity<DetalhesTopicoDto> detalhar(@PathVariable Long id) {
		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			return ResponseEntity.ok(new DetalhesTopicoDto(optional.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@Transactional
	@PutMapping("/{id}")
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id,
			@Valid @RequestBody AtualizacaoTopicoForm topicoForm) {
		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			Topico topico = topicoForm.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDto(topico));
		}
		return ResponseEntity.notFound().build();
	}

	@Transactional
	@DeleteMapping("/{id}")
	@CacheEvict(value = "listaDeTopicos", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

}
