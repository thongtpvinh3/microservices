package com.service.book.repository;

import com.service.book.entities.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface Book2Repo extends PagingAndSortingRepository<Book, Long> {
    List<Book> findAllByAuthor(String author, Pageable pageable);
}
