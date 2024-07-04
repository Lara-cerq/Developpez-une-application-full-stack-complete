package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThemeRepository themeRepository;

    /*
    méthode permettant de faire l'update de l'utilisateur dans la DB
   */
    @Transactional
    public User update(Long user_id, User user) {
        user.setUserId(user_id);
        return userRepository.save(user);
    }

    /*
    méthode permettant de récupérer l'utilisateur dans la DB correspondant à l'id
    */
    public User findById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    /*
     méthode permettant de récupérer l'utilisateur dans la DB correspondant à l'email
     */
    public User findByEmail(String email) {
        User user = this.userRepository.findByEmail(email);
        if (user==null) {
            throw new NotFoundException();
        }
        return user;
    }

    /*
     méthode permettant d'ajouter le theme dans la liste des thèmes de l'utilisateur dans la DB
     ce qui correspond à l'abonnement de l'utilisateur à un thème
     */
    @Transactional
    public void follow(Long themeId, Long userId) {
        Theme theme = this.themeRepository.findById(themeId).orElse(null);
        User user = this.userRepository.findById(userId).orElse(null);

        if (theme == null || user == null) {
            throw new NotFoundException();
        }

        boolean alreadyFollow = user.getThemes().stream().anyMatch(o -> o.getThemeId().equals(themeId));
        if(alreadyFollow) {
            throw new BadRequestException();
        }

        user.getThemes().add(theme);

        this.userRepository.save(user);
    }

    /*
     méthode permettant d'enlever le theme dans la liste des thèmes de l'utilisateur dans la DB
     ce qui correspond au désabonnement de l'utilisateur à un thème
     */
    @Transactional
    public void unFollow(Long themeId, Long userId) {
        User user = this.userRepository.findById(userId).orElse(null);
        Theme theme = this.themeRepository.findById(themeId).orElse(null);

        if (user == null || theme == null) {
            throw new NotFoundException();
        }

        boolean alreadyFollow = user.getThemes().stream().anyMatch(o -> o.getThemeId().equals(themeId));
        if(!alreadyFollow) {
            throw new BadRequestException();
        }

        user.setThemes(user.getThemes().stream().filter(themeUp -> !themeUp.getThemeId().equals(themeId)).collect(Collectors.toList()));

        this.userRepository.save(user);
    }
}
