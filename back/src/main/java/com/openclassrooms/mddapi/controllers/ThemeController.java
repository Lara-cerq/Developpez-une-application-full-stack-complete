package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.ThemeDto;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.ThemeService;
import com.openclassrooms.mddapi.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/theme")
@Log4j2
public class ThemeController {

    @Autowired
    ThemeService themeService;

    @Autowired
    UserService userService;

    /*
    requette permettant d'envoyer au front tous les thèmes présents dans la DB
    ainsi que d'ajouter si follow true ou false en fonction si l'user est abonné ou non
     */
    @GetMapping()
    public ResponseEntity<?> findAll() {
        List<Theme> themeList = this.themeService.findAll();
        List<ThemeDto> themeListDto= new ArrayList<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.findByEmail(email);

        List<Theme> userThemes = user.getThemes();
        List<Long> listIds = userThemes.stream().map(t -> t.getThemeId()).collect(Collectors.toList());

        for(Theme theme : themeList) {
            ThemeDto themeDto= new ThemeDto();
            themeDto.setThemeId(theme.getThemeId());
            themeDto.setDescription(theme.getDescription());
            themeDto.setTitre(theme.getTitre());

            // pour la fonctionnalité s'abonner ou se désabonner => ajout de champ permettant de savoir facilement si user est abonné ou non
            if(listIds.contains(theme.getThemeId())) {
                themeDto.setFollow(true);
            } else {
                themeDto.setFollow(false);
            }

            themeListDto.add(themeDto);
        }

        return ResponseEntity.ok().body(themeListDto);
    }
}
