package com.example.bitlab.spring.SpringTrello.services;

import com.example.bitlab.spring.SpringTrello.models.Folders;
import com.example.bitlab.spring.SpringTrello.models.TaskCategories;
import com.example.bitlab.spring.SpringTrello.repositories.FoldersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {
    private final FoldersRepository foldersRepository;
    private final TaskCategoryService taskCategoryService;

    public List<Folders> getFolders(){
        return foldersRepository.findAll();
    }

    public Folders getFolder(Long id){
        return foldersRepository.findById(id).orElseThrow();
    }

    public void saveFolder(Folders folder){
        foldersRepository.save(folder);
    }


    public void assignCategory(Long folderId, Long categoryId){
        Folders folder = getFolder(folderId);
        TaskCategories category = taskCategoryService.getCategory(categoryId);

        if (folder.getCategories() != null && folder.getCategories().size() > 0){
            if (!folder.getCategories().contains(category)){
                folder.getCategories().add(category);
            }
        } else{
            List<TaskCategories> categories = new ArrayList<>();
            categories.add(category);
            folder.setCategories(categories);
        }

        foldersRepository.save(folder);
    }

    public void unAssignCategory(Long folderId, Long categoryId){
        Folders folder = getFolder(folderId);
        TaskCategories category = taskCategoryService.getCategory(categoryId);

        if (folder.getCategories() != null && folder.getCategories().size() > 0){
            folder.getCategories().remove(category);
        }

        foldersRepository.save(folder);
    }

    public void deleteFolder(Long id){
        foldersRepository.deleteById(id);
    }
}
