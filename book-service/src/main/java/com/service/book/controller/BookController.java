package com.service.book.controller;

import com.service.book.common.ResponseObject;
import com.service.book.entities.Book;
import com.service.book.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BookController {
    @Value("${server.port}")
    private int port;
    private final BookService bookService;

    @GetMapping("/find-all")
    public List<Book> findAll() {
        log.info("Load balancing in port: " + port);
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public Book findById(@PathVariable long id) {
        log.info("Load balancing in port: " + port);
        return bookService.findById(id);
    }

    @PostMapping("/create")
    public Book create(@RequestBody Book book) {
        log.info("Load balancing in port: " + port);
        return bookService.create(book);
    }

    @PatchMapping("/update/{id}")
    public Book update(@PathVariable long id, @RequestBody Book book) {
        log.info("Load balancing in port: " + port);
        return bookService.update(id, book);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        log.info("Load balancing in port: " + port);
        bookService.delete(id);
        return ResponseEntity.ok(new ResponseObject("delete ok", HttpStatus.OK, "Load balancing in port " + port));
    }
}
