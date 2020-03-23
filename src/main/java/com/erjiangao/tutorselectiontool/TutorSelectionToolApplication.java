package com.erjiangao.tutorselectiontool;

import com.erjiangao.tutorselectiontool.Repository.impl.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
@SpringBootApplication
public class TutorSelectionToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(TutorSelectionToolApplication.class, args);
    }

}
