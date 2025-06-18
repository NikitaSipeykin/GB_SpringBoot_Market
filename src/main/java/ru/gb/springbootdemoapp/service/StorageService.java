package ru.gb.springbootdemoapp.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
  void store(MultipartFile file);
}
