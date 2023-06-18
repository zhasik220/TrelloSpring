package com.example.bitlab.spring.SpringTrello.services;

import com.example.bitlab.spring.SpringTrello.models.Comments;
import com.example.bitlab.spring.SpringTrello.models.Tasks;
import com.example.bitlab.spring.SpringTrello.repositories.CommentsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentsRepository commentsRepository;

    public List<Comments> allCommentsByFolderId(Long id){
        return commentsRepository.allCommentsByFolder(id);
    }

    public void addCommentToTask(Comments comment){
        commentsRepository.save(comment);
    }

    @Transactional
    public void deleteCommentsThatBelongsToTasks(List<Long> taskIds){
        commentsRepository.deleteByTaskIds(taskIds);
    }

    public List<Comments> allCommentsIdByTask(Tasks task){
        return commentsRepository.findAllByTaskIs(task);
    }

    @Transactional
    public void deleteCommentsThatBelongsToTask(Long taskId){
        commentsRepository.deleteByTaskId(taskId);
    }
}
