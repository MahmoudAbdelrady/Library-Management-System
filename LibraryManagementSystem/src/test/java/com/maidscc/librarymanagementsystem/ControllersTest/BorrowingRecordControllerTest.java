package com.maidscc.librarymanagementsystem.ControllersTest;

import com.maidscc.librarymanagementsystem.controller.BorrowingRecordController;
import com.maidscc.librarymanagementsystem.service.BorrowingRecordService;
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

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BorrowingRecordController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BorrowingRecordControllerTest {
    private final MockMvc mockMvc;
    private UUID bookId;
    private UUID patronId;

    @MockBean
    private BorrowingRecordService borrowingRecordService;

    @Autowired
    public BorrowingRecordControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    public void init() {
        bookId = UUID.fromString("d835cc2a-ff0e-4601-9a97-6bca26ac66f2");
        patronId = UUID.fromString("cd8edf2e-3bc1-4b76-8a4c-b4a629dc9d49");
    }

    @Test
    public void BorrowBookTest() throws Exception {
        when(borrowingRecordService.borrowBook(bookId, patronId))
                .thenReturn(ResponseEntity.ok(ResponseMaker.successRes("Book borrowed successfully", null)));

        mockMvc.perform(post("/api/borrow/d835cc2a-ff0e-4601-9a97-6bca26ac66f2/patron/cd8edf2e-3bc1-4b76-8a4c-b4a629dc9d49")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void BookAlreadyBorrowedTest() throws Exception {
        when(borrowingRecordService.borrowBook(bookId, patronId))
                .thenReturn(ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ResponseMaker.failRes("Book already borrowed by this patron")));

        mockMvc.perform(post("/api/borrow/d835cc2a-ff0e-4601-9a97-6bca26ac66f2/patron/cd8edf2e-3bc1-4b76-8a4c-b4a629dc9d49")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void ReturnBookTest() throws Exception {
        when(borrowingRecordService.returnBook(bookId, patronId))
                .thenReturn(ResponseEntity.ok(ResponseMaker.successRes("Book returned successfully", null)));

        mockMvc.perform(put("/api/return/d835cc2a-ff0e-4601-9a97-6bca26ac66f2/patron/cd8edf2e-3bc1-4b76-8a4c-b4a629dc9d49")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void ReturnBookAlreadyReturnedTest() throws Exception {
        when(borrowingRecordService.returnBook(bookId, patronId))
                .thenReturn(ResponseEntity.badRequest().body(ResponseMaker.failRes("Book already returned")));

        mockMvc.perform(put("/api/return/d835cc2a-ff0e-4601-9a97-6bca26ac66f2/patron/cd8edf2e-3bc1-4b76-8a4c-b4a629dc9d49")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
}
