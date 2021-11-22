package com.sap.cap.bookstore.service.impl;

import cds.gen.booksservice.Books;
import com.sap.cap.bookstore.repository.BookRepository;
import com.sap.cap.bookstore.service.BookService;
import com.sap.cds.services.ErrorStatuses;
import com.sap.cds.services.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Books findById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorStatuses.NOT_FOUND, "Book does not exist"));
    }

    @Override
    public Books updateWithId(String id, Books data) {
        return bookRepository.updateWithId(id, data);
    }
}
