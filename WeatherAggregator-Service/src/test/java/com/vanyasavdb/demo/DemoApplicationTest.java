package com.vanyasavdb.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTest {

    @Autowired
    private MockMvc mockMvc;


    //Проверка
    @Test
    public void ListShouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/weathers/list")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void ListCustomShouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/weathers/listcustom")).andDo(print()).andExpect(status().isOk());
    }

}