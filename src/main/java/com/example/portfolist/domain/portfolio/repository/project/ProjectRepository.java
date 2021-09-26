package com.example.portfolist.domain.portfolio.repository.project;

import com.example.portfolist.domain.portfolio.entity.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {
}
