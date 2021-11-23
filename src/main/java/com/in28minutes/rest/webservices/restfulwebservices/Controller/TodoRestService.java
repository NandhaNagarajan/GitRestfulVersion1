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
import com.in28minutes.rest.webservices.restfulwebservices.Service.TodoDataService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TodoRestService {

	@Autowired
	private TodoDataService todoDataService;

	@GetMapping(path = "/user/{username}/details")
	public List<Todo> getAllUsers(@PathVariable String username) {
		return todoDataService.getAllUsers(username);
	}

	@GetMapping(path = "/user/{username}/details/{id}")
	public Todo getbyId(@PathVariable String username, @PathVariable long id) {
		Todo todo = todoDataService.getTodoById(username, id);

		if (todo == null) {
			throw new RuntimeException("User not found");
		}

		return todo;
	}

	@PutMapping(path = "/user/{username}/details/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable String username, @PathVariable long id,
			@RequestBody Todo todo) {

		todoDataService.save(todo);

		return new ResponseEntity<Todo>(todo, HttpStatus.OK);
	}

	@DeleteMapping(path = "/user/{username}/details/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable String username, @PathVariable long id) {

		Todo todo = todoDataService.deleteById(username, id);

		if (todo != null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping(path = "/user/{username}/details")
	public ResponseEntity<Void> createTodo(@PathVariable String username, @RequestBody Todo todo) {

		todo.setId(0L);
		Todo todoCreated = todoDataService.save(todo);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(todoCreated.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

}
