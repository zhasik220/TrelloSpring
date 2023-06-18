package com.example.bitlab.spring.SpringTrello.services;

import com.example.bitlab.spring.SpringTrello.models.TaskCategories;
import com.example.bitlab.spring.SpringTrello.repositories.TaskCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskCategoryService {
    private final TaskCategoryRepository taskCategoryRepository;

    public List<TaskCategories> getCategories(){
        return taskCategoryRepository.findAll();
    }

    public TaskCategories getCategory(Long categoryId){
        return taskCategoryRepository.findById(categoryId).orElseThrow();
    }
    public void saveTaskCategory(TaskCategories taskCategories){
        taskCategoryRepository.save(taskCategories);
    }
}
