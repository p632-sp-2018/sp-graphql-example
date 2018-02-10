package com.nightwatchers.graphql.spgraphqlexample.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nightwatchers.graphql.spgraphqlexample.services.GraphQLServices;

import graphql.ExecutionResult;

@RestController
@RequestMapping("/rest/books")
public class BookResource {

	@Autowired
	GraphQLServices graphQLService;
	
	//@RequestMapping(value="/books",method = RequestMethod.POST)
	@PostMapping
	public ResponseEntity<Object> getAllBooks (@RequestBody String query) {
		ExecutionResult execute = graphQLService.getGraphQL().execute(query);
		
		return new ResponseEntity<>(execute, HttpStatus.OK);
	}
}
