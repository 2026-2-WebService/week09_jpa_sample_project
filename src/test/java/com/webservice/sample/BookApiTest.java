package com.webservice.sample;

import com.webservice.sample.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BookApiTest {
    @Autowired MockMvc mvc;
    @Autowired BookRepository repository;

    @AfterEach void cleanUp() { repository.deleteAll(); }

    @Test void createReadUpdateDelete() throws Exception {
        String book = """ 
                {"title":"Spring Boot","author":"Kim","price":30000,"category":"Programming"}
                """;
        String updated = """
                {"title":"Spring JPA","author":"Kim","price":35000,"category":"Programming"}
                """;
        String location = mvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON).content(book))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andReturn().getResponse().getHeader("Location");
        mvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Spring Boot"));
        mvc.perform(put(location).contentType(MediaType.APPLICATION_JSON).content(updated))
                .andExpect(status().isOk()).andExpect(jsonPath("$.price").value(35000));
        mvc.perform(get("/api/books")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        mvc.perform(delete(location)).andExpect(status().isNoContent());
        mvc.perform(get(location)).andExpect(status().isNotFound());
    }

    @Test void rejectsInvalidInput() throws Exception {
        mvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"title":"","author":"Kim","price":-1,"category":"Programming"}
                        """))
                .andExpect(status().isBadRequest());
    }
}
