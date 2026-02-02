package com.example.transactionapi.importservice.command;

import org.springframework.web.multipart.MultipartFile;

public record ImportFileCommand(String importId, MultipartFile file) {}
