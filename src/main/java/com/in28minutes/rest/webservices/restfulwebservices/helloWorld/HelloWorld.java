package com.in28minutes.rest.webservices.restfulwebservices.helloWorld;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class HelloWorld {
	
	@RequestMapping(method=RequestMethod.GET, path="/hello-world-API")
	public String helloWorld()
	{
		return "Hello World API";
	}
	
	@GetMapping(path="/hello-world-Get")
	public String helloWorldGet()
	{
		return "Hello world return using get mapping method";
	}
	
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldBean()
	{
		return new HelloWorldBean("Bean check with JSON");
	}
	
	@GetMapping(path="/hello-world/{name}")
	public HelloWorldBean helloWorldBeanWithVariable(@PathVariable String name)
	{
		return new HelloWorldBean(String.format("My Name is , %s" , name));
	}

}
