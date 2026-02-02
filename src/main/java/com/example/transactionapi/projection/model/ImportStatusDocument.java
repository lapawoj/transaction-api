package com.example.transactionapi.projection.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "import_status")
public class ImportStatusDocument {
    @Id
    private String importId;
    private int totalImported;
    private int totalRejected;
    private boolean completed;
}

