package com.example.bitlab.spring.SpringTrello.repositories;

import com.example.bitlab.spring.SpringTrello.models.Folders;
import com.example.bitlab.spring.SpringTrello.models.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TasksRepository extends JpaRepository<Tasks, Long> {
    List<Tasks> findAllById(Long folderId);
    List<Tasks> findAllByFolderIs(Folders folder);


    @Modifying
    @Query(
            value = "DELETE from Tasks ts where ts.id IN :ids"
    )
    void deleteTasksThatBelongsToFolder(@Param("ids") List<Long> ids);
}
