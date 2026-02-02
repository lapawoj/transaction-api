package com.example.transactionapi.projection.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "statistics_by_category")
public class StatisticsByCategoryDocument {
    @Id
    private String id;
    private String category;
    private String month;
    private BigDecimal totalAmount;
}


