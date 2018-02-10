package com.nightwatchers.graphql.spgraphqlexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nightwatchers.graphql.spgraphqlexample.model.Book;

public interface BookRepository extends JpaRepository<Book, String> {
	
}
