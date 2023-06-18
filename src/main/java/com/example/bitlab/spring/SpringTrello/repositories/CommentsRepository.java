package com.example.bitlab.spring.SpringTrello.repositories;

import com.example.bitlab.spring.SpringTrello.models.Comments;
import com.example.bitlab.spring.SpringTrello.models.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAllByTaskIs(Tasks task);

    @Query(
            value = "" +
                    "SELECT co " +
                    "FROM Comments co " +
                    "WHERE co.task.folder.id = :folderId"
    )
    List<Comments> allCommentsByFolder(@Param("folderId") Long folderID);

    @Modifying
    @Query("DELETE FROM Comments c WHERE c.task.id IN :taskIds")
    void deleteByTaskIds(@Param("taskIds") List<Long> taskIds);

    @Modifying
    @Query("DELETE FROM Comments c WHERE c.task.id = :taskId")
    void deleteByTaskId(@Param("taskId") Long taskId);


}
