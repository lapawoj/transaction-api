package com.example.transactionapi.api.query;

import com.example.transactionapi.projection.model.ImportStatusDocument;
import com.example.transactionapi.projection.repository.ImportStatusRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/imports")
public class ImportStatusQueryController {

    private final ImportStatusRepository repository;

    public ImportStatusQueryController(ImportStatusRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{importId}/status")
    public ImportStatusDocument getStatus(@PathVariable String importId) {
        return repository.findById(importId)
            .orElseThrow(() -> new RuntimeException("Import not found"));
    }
}

