package com.in28minutes.rest.webservices.restfulwebservices.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.in28minutes.rest.webservices.restfulwebservices.Entity.Todo;

@Service
public class TodoDataService {

	private static List<Todo> todoData = new ArrayList<>();
	private static Long idCounter = 0L;

	static {
		todoData.add(new Todo(++idCounter, "Nandha", "learn spring boot", new Date(), false));
		todoData.add(new Todo(++idCounter, "Nandha", "do exercise", new Date(), false));
		todoData.add(new Todo(++idCounter, "Nandha", "learn git", new Date(), false));
	}

	public List<Todo> getAllUsers(String name) {

		return todoData;
	}
	
	public Todo deleteById(String username, long id)
	{
		Todo todo=getTodoById(username, id);
		
		if(todo==null) 
		{
			return null;
		}
		
		if(todoData.remove(todo))
		{
			return todo;
		}
		
		return null;
				
	}
	
	public Todo getTodoById(String username, long id) {
		
		for(Todo input: todoData)
		{
			if(input.getUsername().equals(username) && input.getId()==id)
			{
				return input;
			}
		}
		return null;
	}

	public Todo save(Todo todo) {
		
		if(todo.getId()==-1 || todo.getId()==0)
		{
			todo.setId(++idCounter);
			todoData.add(todo);
		}
		else
		{
			deleteById(todo.getUsername(), todo.getId());
			todoData.add(todo);
		}
		return todo;
	}

}
