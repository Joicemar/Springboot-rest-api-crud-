package br.com.springboot.curso_jdev_treinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_treinamento.repository.UsuarioRepository;
import br.com.springboot.curso_jdev_treinamento_model.Usuario;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController // intercepta todas as requisições, seja navegador, celular etc
public class GreetingsController {

	@Autowired /* IC/CD ou CDI - Injeção de dependencia */
	private UsuarioRepository usuarioRepository;

	/**
	 *
	 * @param name the name to greet
	 * @return greeting text
	 */
	@RequestMapping(value = "/com/{name}", method = RequestMethod.GET) // mapeia por parâmetro get da url
	@ResponseStatus(HttpStatus.OK) // retorna ok para o navegador
	public String greetingText(@PathVariable String name) {
		// pega uma variavel da url e junta com Hello + name
		return "Primeira aplicação spring boot. Hello " + name + "!";
	}

	@RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String retornaOlaMundo(@PathVariable String nome) {

		Usuario usuario = new Usuario();
		usuario.setNome(nome);

		usuarioRepository.save(usuario); // Grava dados no banco

		return "Ola mundo " + nome;

	}

	@GetMapping(value = "/listatodos")
	@ResponseBody /* retorna dados para o corpo da resposta */
	public ResponseEntity<List<Usuario>> listarUsuario() {

		List<Usuario> usuarios = usuarioRepository.findAll();// Executa a consulta no banco de dados
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);/* Retorna a lista em JSON */

	}

	@PostMapping(value = "/salvar") /* Maperia esta url */
	@ResponseBody /* Descrição da resposta */
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {/* Recebe os dados Json para salvar */

		Usuario user = usuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/deletar") /* Maperia esta url */
	@ResponseBody /* Descrição da resposta */
	public ResponseEntity<String> delete(@RequestParam Long idUser) {/* @RequestParam Notação para parametros */

		usuarioRepository.deleteById(idUser);

		return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);
	}

	@GetMapping(value = "/buscaUserId") /* Maperia esta url */
	@ResponseBody /* Descrição da resposta */
	public ResponseEntity<Usuario> findById(@RequestParam(name = "idUser") Long idUser) {/* Notação para parametros */

		Usuario user = usuarioRepository.findById(idUser).get();

		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}

	@GetMapping(value = "/bucaPorNome") /* Maperia esta url */
	@ResponseBody /* Descrição da resposta */
	public ResponseEntity<List<Usuario>> findByName(
			@RequestParam(name = "name") String name) {/* Notação para parametros */

		List<Usuario> user = usuarioRepository.buscarPorNome(name.trim().toUpperCase());

		return new ResponseEntity<List<Usuario>>(user, HttpStatus.OK);
	}
	
	@PutMapping(value = "/atualizar") /* Maperia esta url */
	@ResponseBody /* Descrição da resposta */
	public ResponseEntity<?> update(@RequestBody Usuario user) {

		if (usuarioRepository.findById(user.getId()).isPresent()) {
			return ResponseEntity.ok(usuarioRepository.saveAndFlush(user));
		}
		return new ResponseEntity<String>("Id inválido para atualização.", HttpStatus.OK);
	}

//	@PutMapping(value = "/atualizar") /* Maperia esta url */
//	@ResponseBody /* Descrição da resposta */
//	public ResponseEntity<?> update(@RequestBody Usuario user) {
//		Usuario usuario;
//
//		if (usuarioRepository.findById(user.getId()).isPresent()) {
//			usuario = usuarioRepository.saveAndFlush(user);
//			return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
//		}
//		return new ResponseEntity<String>("Id inválido para atualização.", HttpStatus.OK);
//	}



}


















