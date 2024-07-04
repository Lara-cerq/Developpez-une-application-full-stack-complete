package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    /*
    methode permettant de récupérer de la DB les articles triés par date décroissante
    */
    public List<Article> findAllDesc() {
        return this.articleRepository.findByOrderByCreatedAtDesc();
    }

    /*
    methode permettant de récupérer de la DB les articles triés par date croissante
    */
    public List<Article> findAllAsc() {
        return this.articleRepository.findByOrderByCreatedAtAsc();
    }

    /*
    methode permettant de récupérer de la DB l'article triés correspondant à l'id
    */
    public Article findById(Long id) {
        return this.articleRepository.findById(id).orElse(null);
    }

    /*
    methode permettant d'ajouter dans la DB un nouvel article
    */
    @Transactional
    public Article addArticle(Article article) {
        return this.articleRepository.save(article);
    }
}
