package com.maidscc.librarymanagementsystem.ControllersTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maidscc.librarymanagementsystem.controller.BookController;
import com.maidscc.librarymanagementsystem.dto.BookDTO;
import com.maidscc.librarymanagementsystem.service.BookService;
import com.maidscc.librarymanagementsystem.util.Response.ResponseMaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BookControllerTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private BookDTO createdBook;
    private BookDTO bookDTO;

    @MockBean
    private BookService bookService;

    @Autowired
    public BookControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    public void init() throws ParseException {
        createdBook = new BookDTO("Testing book", "someone", 2019, "978-0-306-40615-7", BigDecimal.valueOf(125.5));

        bookDTO = new BookDTO(UUID.fromString("d835cc2a-ff0e-4601-9a97-6bca26ac66f2"), "new book"
                , "someone", 2020, "978-0-306-40615-7",
                BigDecimal.valueOf(125.5), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").parse("2024-05-11 22:09:08.690000"));
    }

    @Test
    public void GetAllBooksTest() throws Exception {
        List<BookDTO> bookDTOS = List.of(bookDTO);
        when(bookService.getAllBooks(0))
                .thenReturn(ResponseEntity.ok(ResponseMaker.successRes("Books retrieved successfully", bookDTOS)));

        mockMvc.perform(get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void GetBookByIdTest() throws Exception {
        UUID bookId = UUID.fromString("d835cc2a-ff0e-4601-9a97-6bca26ac66f2");
        when(bookService.getBookById(bookId))
                .thenReturn(ResponseEntity.ok(ResponseMaker.successRes("Book retrieved successfully", bookDTO)));

        mockMvc.perform(get("/api/books/d835cc2a-ff0e-4601-9a97-6bca26ac66f2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void BookNotFoundTest() throws Exception {
        UUID bookId = UUID.fromString("d835cc2a-ff3c-4601-9a97-6bca26ac66f2");
        when(bookService.getBookById(bookId))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMaker.failRes("Book not found")));

        mockMvc.perform(get("/api/books/d835cc2a-ff3c-4601-9a97-6bca26ac66f2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CreateBookTest() throws Exception {
        when(bookService.createBook(createdBook))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(ResponseMaker.successRes("Book created successfully", createdBook)));

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdBook)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void UpdateBookTest() throws Exception {
        UUID bookId = UUID.fromString("d835cc2a-ff0e-4601-9a97-6bca26ac66f2");
        BookDTO updatedBookInfo = bookDTO;
        updatedBookInfo.setTitle("edited book");
        updatedBookInfo.setPublicationYear(2020);

        when(bookService.updateBook(bookId, updatedBookInfo))
                .thenReturn(ResponseEntity.ok(ResponseMaker.successRes("Book updated successfully", updatedBookInfo)));

        mockMvc.perform(put("/api/books/d835cc2a-ff0e-4601-9a97-6bca26ac66f2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBookInfo)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void DeleteBookTest() throws Exception {
        UUID bookId = UUID.fromString("d835cc2a-ff0e-4601-9a97-6bca26ac66f2");

        when(bookService.deleteBook(bookId))
                .thenReturn(ResponseEntity.ok(ResponseMaker.successRes("Book deleted successfully", "No data")));

        mockMvc.perform(delete("/api/books/d835cc2a-ff0e-4601-9a97-6bca26ac66f2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }
}
