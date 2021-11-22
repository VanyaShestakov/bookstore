package com.sap.cap.bookstore.repository;

import cds.gen.booksservice.Books;

import java.util.Optional;

public interface BookRepository {
    Optional<Books> findById(String id);

    Books updateWithId(String id, Books data);

}
