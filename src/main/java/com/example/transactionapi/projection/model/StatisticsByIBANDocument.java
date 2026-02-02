package com.example.transactionapi.projection.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "statistics_by_iban")
public class StatisticsByIBANDocument {
    @Id
    private String id;
    private String iban;
    private String month;
    private BigDecimal totalAmount;
}
