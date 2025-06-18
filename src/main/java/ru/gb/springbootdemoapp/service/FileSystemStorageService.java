package ru.gb.springbootdemoapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageService implements StorageService{
  @Value("${location}")
  private String location;
  private Path rootLocation;

  @PostConstruct
  void init(){
    rootLocation = Paths.get(location);
  }

  @Override
  public void store(MultipartFile file) {
    try {
      if (file.isEmpty()){
        throw new RuntimeException("File is empty");
      }
      Path destinationFile = rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();
      if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())){
        throw new RuntimeException("Can't save file out of directory");
      }
      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
      }
    }catch (IOException e){
      throw new RuntimeException("File saving error");
    }
  }
}
