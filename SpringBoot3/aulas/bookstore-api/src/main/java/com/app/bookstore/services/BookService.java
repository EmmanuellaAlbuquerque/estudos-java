package com.app.bookstore.services;

import com.app.bookstore.dtos.BookRecordDto;
import com.app.bookstore.models.Book;
import com.app.bookstore.models.Review;
import com.app.bookstore.repositories.AuthorRepository;
import com.app.bookstore.repositories.BookRepository;
import com.app.bookstore.repositories.PublisherRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    @Transactional
    public Book saveBook(BookRecordDto bookRecordDto) {
        Book book = new Book();
        book.setTitle(bookRecordDto.title());
        // TODO: tratativas try catch
        book.setPublisher(publisherRepository.findById(bookRecordDto.publisherId()).get());
        book.setAuthors(authorRepository.findAllById(bookRecordDto.authorIds()).stream().collect(Collectors.toSet()));

        Review review = new Review();
        review.setComment(bookRecordDto.reviewComment());
        review.setBook(book);
        book.setReview(review);

        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public void deleteBook(UUID id){
        bookRepository.deleteById(id);
    }
}
