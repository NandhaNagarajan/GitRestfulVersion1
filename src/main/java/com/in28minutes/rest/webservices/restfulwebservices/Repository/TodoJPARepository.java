package com.in28minutes.rest.webservices.restfulwebservices.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.in28minutes.rest.webservices.restfulwebservices.Entity.Todo;

@Repository
public interface TodoJPARepository extends JpaRepository<Todo, Long> {
	
	@Query (value="select * from todo where username= ?1",nativeQuery = true)
	List<Todo> findByUsername(String username);

}
