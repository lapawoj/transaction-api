package com.example.transactionapi.integration;

import com.example.transactionapi.projection.repository.ImportStatusRepository;
import com.example.transactionapi.projection.repository.StatisticsByCategoryRepository;
import com.example.transactionapi.projection.repository.StatisticsByIBANRepository;
import com.example.transactionapi.projection.repository.StatisticsByMonthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc

class ImportIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StatisticsByCategoryRepository categoryRepository;

    @Autowired
    private StatisticsByIBANRepository ibanRepository;

    @Autowired
    private StatisticsByMonthRepository monthRepository;

    @Autowired
    private ImportStatusRepository importStatusRepository;

    @BeforeEach
    void cleanDatabase() {
        categoryRepository.deleteAll();
        ibanRepository.deleteAll();
        monthRepository.deleteAll();
        importStatusRepository.deleteAll();
    }

    @Test
    void fullCsvImportFlow_generatesStatistics() throws Exception {
        String importId = mockMvc.perform(post("/api/imports"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        String csv = "IBAN,transactionDate,currency,category,amount\n" +
            "DE12345678901234567890,2026-01-30,EUR,GROCERIES,100\n" +
            "DE12345678901234567890,2026-01-30,EUR,ENTERTAINMENT,50";

        MockMultipartFile file = new MockMultipartFile(
            "file",
            "transactions.csv",
            "text/csv",
            csv.getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(multipart("/api/imports/" + importId + "/file")
                .file(file))
            .andExpect(status().isOk())
            .andExpect(content().string("File processed"));

        var groceries = categoryRepository.findById("GROCERIES_2026-01")
            .orElseThrow();
        assertThat(groceries.getTotalAmount()).isEqualByComparingTo("100");

        var entertainment = categoryRepository.findById("ENTERTAINMENT_2026-01")
            .orElseThrow();
        assertThat(entertainment.getTotalAmount()).isEqualByComparingTo("50");
    }
}

