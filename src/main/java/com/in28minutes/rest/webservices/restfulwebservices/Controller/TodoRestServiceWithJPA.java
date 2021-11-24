package com.in28minutes.rest.webservices.restfulwebservices.Controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restfulwebservices.Entity.Todo;
import com.in28minutes.rest.webservices.restfulwebservices.Repository.TodoJPARepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TodoRestServiceWithJPA {

	@Autowired
	private TodoJPARepository todoJPARepository;

	@GetMapping(path = "/JPA/user/{username}/details")
	public List<Todo> getAllUserDetails(@PathVariable String username){
		return todoJPARepository.findAll();
	}

	@GetMapping(path = "/JPA/user/{username}/details/{id}")
	public Todo getAllUserDetails(@PathVariable String username, @PathVariable Long id) {
		return todoJPARepository.findById(id).get();
	}

	@GetMapping(path = "/JPA/user/{username}/detailsByUsername")
	public List<Todo> getUserDetailsByName(@PathVariable String username){
		return todoJPARepository.findByUsername(username);
	}

	@PostMapping(path = "/JPA/user/{username}/details")
	public ResponseEntity<Void> CreateTodo(@PathVariable String username, @RequestBody Todo todo) {
		todo.setId(0L);
		todo.setUsername(username);
		Todo todoCreated = todoJPARepository.save(todo);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(todoCreated.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@PutMapping(path = "/JPA/user/{username}/details/{id}")
	public ResponseEntity<Todo> UpdateTodo(@PathVariable String username, @PathVariable Long id,
			@RequestBody Todo todo) {

		List<Todo> checkExistingID = getAllUserDetails(username);
		int count = 0;

		for (Todo check : checkExistingID) {

			if (check.getId().longValue() == id.longValue()) {

				++count;
				break;
			}
		}

		if (count > 0) {
			todo.setId(id);
			todo.setUsername(username);
			todoJPARepository.save(todo);
		} else {
			throw new RuntimeException("ID Not Found");
		}

		return new ResponseEntity<Todo>(todo, HttpStatus.OK);
	}

	@DeleteMapping(path = "/JPA/user/{username}/details/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable String username, @PathVariable Long id) {
		todoJPARepository.deleteById(id);

		return ResponseEntity.noContent().build();

	}

}
