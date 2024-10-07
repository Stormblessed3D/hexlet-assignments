package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.mapper.BookMapper;
import exercise.repository.AuthorRepository;
import exercise.repository.BookRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    // BEGIN
    @Autowired
    private BookMapper mapper;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public BookDTO create(BookCreateDTO dto) {
        var author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ConstraintViolationException("Task with id " + dto.getAuthorId() + " not found", null));
        var book = mapper.map(dto);
        bookRepository.save(book);
        var bookDTO = mapper.map(book);
        return bookDTO;
    }

    public List<BookDTO> getAll() {
        var books = bookRepository.findAll();
        return books.stream()
                .map(p -> mapper.map(p))
                .toList();
    }

    public BookDTO show(Long id) {
        var author = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not Found: " + id));
        var authorDTO = mapper.map(author);
        return authorDTO;
    }

    public BookDTO update(BookUpdateDTO dto, Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not Found: " + id));
        var author = authorRepository.findById(dto.getAuthorId().get())
                .orElseThrow(() -> new ConstraintViolationException("Task with id " + dto.getAuthorId() + " not found", null));
        mapper.update(dto, book);
        bookRepository.save(book);
        var bookDTO = mapper.map(book);
        return bookDTO;
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
    // END
}
