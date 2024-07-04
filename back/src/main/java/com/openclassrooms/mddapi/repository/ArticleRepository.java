package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    /*
    récupére de la DB les articles par ordre décroissante de création
     */
    List<Article> findByOrderByCreatedAtDesc();

    /*
    récupére de la DB les articles par ordre décroissante de création
    */
    List<Article> findByOrderByCreatedAtAsc();
}
