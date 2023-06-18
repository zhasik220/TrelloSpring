package com.example.bitlab.spring.SpringTrello.controllers;

import com.example.bitlab.spring.SpringTrello.models.Comments;
import com.example.bitlab.spring.SpringTrello.models.Folders;
import com.example.bitlab.spring.SpringTrello.models.TaskCategories;
import com.example.bitlab.spring.SpringTrello.models.Tasks;
import com.example.bitlab.spring.SpringTrello.services.TaskService;
import com.example.bitlab.spring.SpringTrello.services.CommentService;
import com.example.bitlab.spring.SpringTrello.services.FolderService;
import com.example.bitlab.spring.SpringTrello.services.TaskCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final FolderService folderService;
    private final CommentService commentService;
    private final TaskCategoryService taskCategoryService;
    private final TaskService taskService;
    @GetMapping(value = "/")
    public String homePage(Model model){
        model.addAttribute("folders", folderService.getFolders());
        return "index";
    }

    @GetMapping(value = "/details/{folderId}")
    public String folderDetails(
            @PathVariable(name = "folderId") Long id, Model model
    ){
        Folders folder = folderService.getFolder(id);
        model.addAttribute("folder", folder);

        List<TaskCategories> categories = taskCategoryService.getCategories();
        categories.removeAll(folder.getCategories());
        model.addAttribute("categories", categories);

        List<Tasks> tasksList = taskService.allTasksById(folder);
        model.addAttribute("tasks", tasksList);

        List<Comments> commentsList = commentService.allCommentsByFolderId(id);
        model.addAttribute("comments", commentsList);

        return "details";
    }

    @PostMapping(value = "/add-folder")
    public String addFolder(Folders folder){
        folderService.saveFolder(folder);
        return "redirect:/";
    }

    @PostMapping(value = "/assign-category")
    public String assignCategory(
        @RequestParam(name = "folder_id") Long folderId,
        @RequestParam(name = "category") Long categoryId
    ){
        folderService.assignCategory(folderId, categoryId);
        return "redirect:/details/" + folderId;
    }

    @PostMapping(value = "/unAssign-category")
    public String deleteCategory(
        @RequestParam(name = "folder_id") Long folderId,
        @RequestParam(name = "category_id") Long categoryId
    ){
        folderService.unAssignCategory(folderId, categoryId);
        return "redirect:/details/" + folderId;
    }

    @PostMapping(value = "/add-task")
    public String addTask(Tasks task){
        taskService.saveTask(task);
        return "redirect:/details/" + task.getFolder().getId();
    }

    @PostMapping(value = "/change-task")
    public String changeTask(Tasks task){
        taskService.saveTask(task);
        return "redirect:/details/" + task.getFolder().getId();
    }

    @PostMapping(value = "/delete-task")
    public String deleteTask(
            @RequestParam(name = "task_id") Long id,
            @RequestParam(name = "folder_id") Long folderId
    ){
        Tasks task = taskService.getTask(id);

        commentService.deleteCommentsThatBelongsToTask(id);
        taskService.deleteTask(id);
        return "redirect:/details/"+folderId;
    }

    @PostMapping(value = "/add-task-comment")
    public String addCommentToTask(
            Comments comment, @RequestParam(name = "folder_id") Long folderId
    ){
        commentService.addCommentToTask(comment);
        return "redirect:/details/"+folderId;
    }
    @PostMapping(value = "/delete-folder")
    public String deleteFolder(
            @RequestParam(name = "folder_id") Long id
    ){
        Folders folder = folderService.getFolder(id);

        List<Long> tasksListIds = taskService.allTasksById(folder)
                .stream().map(Tasks::getId)
                .collect(Collectors.toList());

        commentService.deleteCommentsThatBelongsToTasks(tasksListIds);
        taskService.deleteTasks(tasksListIds);
        folderService.deleteFolder(id);

        return "redirect:/";

    }

    @GetMapping(value = "/categories")
    public String AddCategoriesPage(){
        return "categories";
    }

    @PostMapping(value = "/Addcategories")
    public String AddCategory(TaskCategories taskCategories){
        taskCategoryService.saveTaskCategory(taskCategories);
        return "redirect:/categories";
    }


}
