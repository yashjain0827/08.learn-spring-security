package com.yashtojava.learn_spring_security.resources;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoResource {

	private static final List<Todo> TODOS_LIST = List.of(new Todo("yash", "learn DevOps"),
			new Todo("yash", "learn AWS"));

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/todos")
	public List<Todo> retrieveAllTodos() {
		return TODOS_LIST;
	}

	@GetMapping("/user/{username}/todos")
	public Todo retrieveTodoForSpecificUser(@PathVariable("username") String username) {
		return TODOS_LIST.get(0);
	}

	@PostMapping("/user/{username}/todos")
	public ResponseEntity<Void> createTodoForSpecificUser(@PathVariable("username") String username, @RequestBody Todo todo) {

		logger.info("Create {} for {}", todo, username);
		return ResponseEntity.ok().build();
	}
}

record Todo(String username, String description) {
}
