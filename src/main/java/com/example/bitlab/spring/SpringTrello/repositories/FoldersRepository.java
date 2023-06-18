package com.example.bitlab.spring.SpringTrello.repositories;

import com.example.bitlab.spring.SpringTrello.models.Folders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoldersRepository extends JpaRepository<Folders, Long> {
}
