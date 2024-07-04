package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    /*
    méthode permettant l'ajout d'un commentaire
    */
    @Transactional
    public Comment addComment(Comment comment) {
        return this.commentRepository.save(comment);
    }

}

