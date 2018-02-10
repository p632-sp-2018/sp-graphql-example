package com.nightwatchers.graphql.spgraphqlexample.services.datafetcher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nightwatchers.graphql.spgraphqlexample.model.Book;
import com.nightwatchers.graphql.spgraphqlexample.repository.BookRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component	
public class AllBooksDataFetcher implements DataFetcher<List<Book>>{

	@Autowired
	BookRepository bookRepository;
	@Override
	public List<Book> get(DataFetchingEnvironment arg0) {
		return bookRepository.findAll();
	}
}
