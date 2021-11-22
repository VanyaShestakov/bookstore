package com.sap.cap.bookstore.repository.impl;

import cds.gen.adminservice.Products_;
import cds.gen.booksservice.Books;
import cds.gen.booksservice.Books_;
import com.sap.cap.bookstore.repository.BookRepository;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.Update;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.ql.cqn.CqnUpdate;
import com.sap.cds.services.ErrorStatuses;
import com.sap.cds.services.ServiceException;
import com.sap.cds.services.persistence.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final PersistenceService service;

    @Autowired
    public BookRepositoryImpl(PersistenceService service) {
        this.service = service;
    }

    @Override
    public Optional<Books> findById(String id) {
        CqnSelect select = Select.from(Books_.class).where(b -> b.ID().eq(id));
        return service.run(select).first(Books.class);
    }

    @Override
    public Books updateWithId(String id, Books data) {
        CqnUpdate update = Update.entity(Books_.class).data(data).where(b -> b.ID().eq(id));
        service.run(update);
        return data;
    }

}
