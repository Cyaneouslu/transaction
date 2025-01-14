package com.xiaolu.transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateSuccess() throws Exception {
        createTransactionId123();
    }

    private void createTransactionId123() throws Exception {
        String body = "{\n" +
                "    \"transactionId\": \"123\",\n" +
                "    \"account\": \"wangxiaolu\",\n" +
                "    \"amount\": 100\n" +
                "}";
        this.mockMvc.perform(post("/transaction/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(body, false));
    }

    @Test
    void shouldListSuccess() throws Exception {
        // create first
        createTransactionId123();
        this.mockMvc.perform(get("/transaction/list").param("account", "wangxiaolu"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("transactionId\":\"123")))
                .andExpect(content().string(containsString("account\":\"wangxiaolu")))
                .andExpect(content().string(containsString("amount\":100")));
    }

    @Test
    void shouldListEmpty() throws Exception {
        this.mockMvc.perform(get("/transaction/list").param("account", "wangxiaolu"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }


    @Test
    void shouldModifySuccess() throws Exception {
        // create first
        createTransactionId123();
        // modify
        String modifyBody = "{\n" +
                "    \"account\": \"wangxiaolu\",\n" +
                "    \"amount\": 1000\n" +
                "}";
        this.mockMvc.perform(put("/transaction/modify/{transactionId}", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(modifyBody))
                .andDo(print())
                .andExpect(status().isOk());
        // transaction has been modify
        this.mockMvc.perform(get("/transaction/list").param("account", "wangxiaolu"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("transactionId\":\"123")))
                .andExpect(content().string(containsString("account\":\"wangxiaolu")))
                .andExpect(content().string(containsString("amount\":1000")));
    }

    @Test
    void shouldDeleteSuccess() throws Exception {
        // create first
        createTransactionId123();
        // delete
        this.mockMvc.perform(delete("/transaction/delete/{transactionId}", "123"))
                .andDo(print())
                .andExpect(status().isOk());

        // list should be empty
        this.mockMvc.perform(get("/transaction/list").param("account", "wangxiaolu"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void shouldCreateFailForParamError() throws Exception {
        String body = "{\n" +
                "    \"account\": \"wangxiaolu\",\n" +
                "    \"amount\": 100\n" +
                "}";
        this.mockMvc.perform(post("/transaction/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString("params lack")));
    }

    @Test
    void shouldCreateFailForDuplicate() throws Exception {
        // create once
        createTransactionId123();
        // duplicate create with same transactionId but different other param
        String body = "{\n" +
                "    \"transactionId\": \"123\",\n" +
                "    \"account\": \"wangxiaolu2\",\n" +
                "    \"amount\": 1002\n" +
                "}";
        this.mockMvc.perform(post("/transaction/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString("transaction exist")));
    }

    @Test
    void shouldModifyFailForNotExist() throws Exception {
        String body = "{\n" +
                "    \"transactionId\": \"123\",\n" +
                "    \"account\": \"wangxiaolu2\",\n" +
                "    \"amount\": 1002\n" +
                "}";
        this.mockMvc.perform(put("/transaction/modify/{transactionId}", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString("transaction not exist")));
    }

    @Test
    void shouldModifyFailForParamError() throws Exception {
        // create first
        createTransactionId123();
        String body = "{\n" +
                "    \"transactionId\": \"123\",\n" +
                "    \"account\": \"wangxiaolu2\",\n" +
                "    \"amount\": 1002\n" +
                "}";
        this.mockMvc.perform(put("/transaction/modify/{transactionId}", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString("can not modify account")));
    }

    @Test
    void shouldDeleteFailForNotExist() throws Exception {
        // delete
        this.mockMvc.perform(delete("/transaction/delete/{transactionId}", "123"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(containsString("transaction not exist")));
    }

    @BeforeEach
    void deleteData() throws Exception {
        // delete data before test
        this.mockMvc.perform(delete("/transaction/delete/{transactionId}", "123"));
    }
}
