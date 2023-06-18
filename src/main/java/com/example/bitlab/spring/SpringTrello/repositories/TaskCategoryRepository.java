package com.example.bitlab.spring.SpringTrello.repositories;

import com.example.bitlab.spring.SpringTrello.models.TaskCategories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskCategoryRepository extends JpaRepository<TaskCategories, Long> {
}
