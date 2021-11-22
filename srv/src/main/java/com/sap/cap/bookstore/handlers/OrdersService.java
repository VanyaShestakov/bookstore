package com.sap.cap.bookstore.handlers;

import cds.gen.booksservice.Books;
import cds.gen.booksservice.Books_;
import cds.gen.ordersservice.OrderItems;
import cds.gen.ordersservice.Orders;
import com.sap.cap.bookstore.service.BookService;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.Update;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.ql.cqn.CqnUpdate;
import com.sap.cds.services.ErrorStatuses;
import com.sap.cds.services.ServiceException;
import com.sap.cds.services.cds.CdsService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ServiceName("OrdersService")
public class OrdersService implements EventHandler {

    private final BookService bookService;

    @Autowired
    public OrdersService(BookService bookService) {
        this.bookService = bookService;
    }

    @Before(event = CdsService.EVENT_CREATE, entity = "OrdersService.OrderItems")
    public void validateBookAndDecreaseStock(List<OrderItems> items) {
        for (OrderItems item : items) {
            String bookId = item.getBookId();
            int amount = item.getAmount();
            Books book = bookService.findById(bookId);
            int stock  = book.getStock();
            if (stock < amount) {
                throw new ServiceException(ErrorStatuses.BAD_REQUEST, "Not enough books on stock");
            }
            book.setStock(stock - amount);
            bookService.updateWithId(bookId, book);
        }
    }

    @Before(event = CdsService.EVENT_CREATE, entity = "OrdersService.Orders")
    public void validateBookAndDecreaseStockViaOrders(List<Orders> orders) {
        orders.forEach(order -> {
            if (order.getItems() != null) {
                validateBookAndDecreaseStock(order.getItems());
            }
        });
    }
}
