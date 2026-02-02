package com.example.transactionapi.importservice.command;

import com.example.transactionapi.importservice.handler.ImportCommandHandler;
import com.example.transactionapi.importservice.handler.ImportFileCommandHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/imports")
public class ImportCommandController {

    private final ImportCommandHandler startImportHandler;
    private final ImportFileCommandHandler fileImportHandler;

    public ImportCommandController(ImportCommandHandler startImportHandler,
        ImportFileCommandHandler fileImportHandler) {
        this.startImportHandler = startImportHandler;
        this.fileImportHandler = fileImportHandler;
    }

    @PostMapping
    public String startImport() {
        String importId = UUID.randomUUID().toString();
        startImportHandler.handle(new StartImportCommand(importId));
        return importId;
    }

    @PostMapping("/{importId}/file")
    public String uploadFile(@PathVariable String importId, @RequestParam("file") MultipartFile file) {
        fileImportHandler.handle(new ImportFileCommand(importId, file));
        return "File processed";
    }
}