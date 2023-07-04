package com.service.book.service.impl;

import com.service.book.entities.Book;
import com.service.book.repository.BookRepository;
import com.service.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book create(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(long id) {
        return bookRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public Book update(long id, Book book) {
        Book foundBook = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        boolean needUpdate = false;

        if (StringUtils.hasLength(book.getName())) {
            foundBook.setName(book.getName());
            needUpdate = true;
        }
        if (StringUtils.hasLength(book.getAuthor())) {
            foundBook.setAuthor(book.getAuthor());
            needUpdate = true;
        }

        if (needUpdate) {
            bookRepository.save(foundBook);
        }

        return foundBook;
    }

    @Override
    public void delete(long id) {
        bookRepository.deleteById(id);
    }
}
