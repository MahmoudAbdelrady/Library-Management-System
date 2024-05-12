package com.maidscc.librarymanagementsystem.ControllersTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maidscc.librarymanagementsystem.controller.PatronController;
import com.maidscc.librarymanagementsystem.dto.Patron.PatronDTO;
import com.maidscc.librarymanagementsystem.dto.Patron.PatronRegisterDTO;
import com.maidscc.librarymanagementsystem.service.PatronService;
import com.maidscc.librarymanagementsystem.util.Response.ResponseMaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PatronController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PatronControllerTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private PatronRegisterDTO patronRegisterDTO;
    private PatronDTO patronDTO;

    @MockBean
    private PatronService patronService;

    @Autowired
    public PatronControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    public void init() {
        patronRegisterDTO = new PatronRegisterDTO("tester", "tester", "testing@mail.com", "test@123T", "01234567891");

        patronDTO = new PatronDTO(UUID.fromString("cd8edf2e-3bc1-4b76-8a4c-b4a629dc9d49"),
                "tester", "test", "test@mail.com", "01117332086", "Cairo, Egypt", "2002-07-21");
    }

    @Test
    public void GetAllPatronsTest() throws Exception {
        List<PatronDTO> patronDTOS = List.of(patronDTO);
        when(patronService.getAllPatrons(0)).thenReturn(ResponseEntity.ok(ResponseMaker.successRes("Patrons retrieved successfully", patronDTOS)));

        mockMvc.perform(get("/api/patrons").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void GetPatronByIdTest() throws Exception {
        when(patronService.getPatronById(UUID.fromString("cd8edf2e-3bc1-4b76-8a4c-b4a629dc9d49")))
                .thenReturn(ResponseEntity.ok(ResponseMaker.successRes("Patron retrieved successfully", patronDTO)));

        mockMvc.perform(get("/api/patrons/cd8edf2e-3bc1-4b76-8a4c-b4a629dc9d49").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void RegisterPatronTest() throws Exception {
        when(patronService.registerPatron(patronRegisterDTO))
                .thenReturn(ResponseEntity.ok(ResponseMaker.successRes("Patron Registered successfully", "No data")));

        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patronRegisterDTO)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void UpdatePatronTest() throws Exception {
        PatronDTO updatedPatron = patronDTO;
        updatedPatron.setName("edited");

        when(patronService.updatePatron(UUID.fromString("cd8edf2e-3bc1-4b76-8a4c-b4a629dc9d49"), updatedPatron))
                .thenReturn(ResponseEntity.ok(ResponseMaker.successRes("Patron updated successfully", updatedPatron)));

        mockMvc.perform(put("/api/patrons/cd8edf2e-3bc1-4b76-8a4c-b4a629dc9d49")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPatron)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void DeletePatronTest() throws Exception {
        when(patronService.deletePatron(UUID.fromString("cd8edf2e-3bc1-4b76-8a4c-b4a629dc9d49"))
        ).thenReturn(ResponseEntity.ok(ResponseMaker.successRes("Patron deleted successfully", "No data")));

        mockMvc.perform(delete("/api/patrons/cd8edf2e-3bc1-4b76-8a4c-b4a629dc9d49")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
