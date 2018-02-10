package com.nightwatchers.graphql.spgraphqlexample.services.datafetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nightwatchers.graphql.spgraphqlexample.model.Book;
import com.nightwatchers.graphql.spgraphqlexample.repository.BookRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class BookDataFetcher implements DataFetcher<Book> {

	@Autowired
	BookRepository bookRepository;
	
	@Override
	public Book get(DataFetchingEnvironment dataFetchingEnvironment) {
		
		String isn = dataFetchingEnvironment.getArgument("id");
		return bookRepository.getOne(isn);
	}
	
}
