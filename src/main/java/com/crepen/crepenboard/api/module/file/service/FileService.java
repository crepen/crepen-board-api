package com.crepen.crepenboard.api.module.file.service;

import com.crepen.crepenboard.api.module.file.model.entity.FileEntity;
import com.crepen.crepenboard.api.module.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public List<FileEntity> findAll(){
        return fileRepository.findAll();
    }

    public void save(FileEntity fileEntity){
        fileRepository.save(fileEntity);
    }
}

