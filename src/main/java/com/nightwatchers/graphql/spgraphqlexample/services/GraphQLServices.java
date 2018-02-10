package com.nightwatchers.graphql.spgraphqlexample.services;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.nightwatchers.graphql.spgraphqlexample.model.Book;
import com.nightwatchers.graphql.spgraphqlexample.repository.BookRepository;
import com.nightwatchers.graphql.spgraphqlexample.services.datafetcher.BookDataFetcher;
import com.nightwatchers.graphql.spgraphqlexample.services.datafetcher.AllBooksDataFetcher;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.GraphQLSchema;
import graphql.GraphQL;


@Service
public class GraphQLServices {
	@Autowired
	BookRepository bookRepository;
	
	@Value("classpath:books.graphql")
	Resource resource;
	
	private GraphQL graphQL;
	@Autowired
	private AllBooksDataFetcher allBooksDataFetcher;
	@Autowired
	private BookDataFetcher bookDataFetcher;
	
	
	@PostConstruct
	private void loadSchema() throws IOException{
		
		// Load books into the Books repository
		loadDataIntoHSQL();
		//get Schema
		File schemaFile = resource.getFile();
		//parse Schema
		TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
		RuntimeWiring wiring = buildRuntimeWriring();
		GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
		graphQL = GraphQL.newGraphQL(schema).build();
	}

	private void loadDataIntoHSQL() {
		Stream.of(
			new Book("123", "Book of Clouds", "Kindle Edition",
					new String[] {
					"Chloe Aridjis" 
					}, "Nov 2017"),
			new Book("124" , "Cloud architeccture and engg.", "Orielly",
					new String[] {
					"Peter", "Sam"
					}, "Jan 2015"),
			new Book("125", "Java 9 Programming", "Orielly",
					new String[] {
					"Venkat", "Ram"
					}, "Dec 2016")				
		).forEach(book -> {
			bookRepository.save(book);
		});		
	}

	private RuntimeWiring buildRuntimeWriring() {
		return RuntimeWiring.newRuntimeWiring()
				.type("Query", typeWiring -> typeWiring
						.dataFetcher("allBooks", allBooksDataFetcher)
						.dataFetcher("book", bookDataFetcher))
				.build();			
	}
	
	public GraphQL getGraphQL() {
		return graphQL;
	}
}
