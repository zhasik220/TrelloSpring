package com.example.bitlab.spring.SpringTrello.services;

import com.example.bitlab.spring.SpringTrello.models.Folders;
import com.example.bitlab.spring.SpringTrello.models.Tasks;
import com.example.bitlab.spring.SpringTrello.repositories.TasksRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TasksRepository tasksRepository;

    public List<Tasks> allTasksById(Folders folder){
        return tasksRepository.findAllByFolderIs(folder);
    }

    public void saveTask(Tasks task){
        tasksRepository.save(task);
    }

    public void deleteTask(Long id){
        tasksRepository.deleteById(id);
    }

    @Transactional
    public void deleteTasks(List<Long> ids){
        tasksRepository.deleteTasksThatBelongsToFolder(ids);
    }

    public Tasks getTask(Long taskId){
        return tasksRepository.findById(taskId).orElseThrow();
    }
}
