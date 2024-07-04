package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
@Log4j2
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;
    /*
    requette permettant d'ajout d'un commentaire
    */
    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody CommentDto comment) {

        Article article = new Article();
        if(comment.getArticleId() != null) {
            // appel au service pour récupérer l'article par l'id
            article = articleService.findById(Long.parseLong(comment.getArticleId()));
        }

        User user = new User();
        if(comment.getUserId() != null) {
            // appel au service pour récupérer l'user par l'id
            user = userService.findById(Long.parseLong(comment.getUserId()));
        }
        Comment newComment = new Comment();
        newComment.setCommentaire(comment.getCommentaire());
        //pour pouvoir afficher comment dans le bon article
        newComment.setArticle(article);
        // pour pouvoir afficher le nom de la personne qui l'a écrit
        newComment.setUser(user);
        newComment.setAuteur(user.getFirstName());

        commentService.addComment(newComment);

        return ResponseEntity.ok().body(newComment);
    }
    }
