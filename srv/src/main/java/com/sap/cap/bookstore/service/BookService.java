package com.sap.cap.bookstore.service;

import cds.gen.booksservice.Books;

public interface BookService {
    Books findById(String id);

    Books updateWithId(String id, Books data);
}
