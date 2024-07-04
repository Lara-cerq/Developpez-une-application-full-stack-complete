import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Comment } from 'src/app/pages/comment/interface/comment.interface';
import { CommentService } from 'src/app/pages/comment/service/comment.service';
import { ArticleService } from '../../../service/article.service';
import { Article } from '../../../interface/article';
import { Theme } from 'src/app/pages/theme/interface/theme';
import { ThemeService } from 'src/app/pages/theme/service/theme.service';
import { SessionService } from 'src/app/services/session.service';
import { UserService } from 'src/app/pages/user/service/user.service';
import { User } from 'src/app/pages/user/interface/user.interface';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  public commentForm!: FormGroup;

  public articleId : string;

  public themeId!: string;

  public userId!: string;

  public article : Article | undefined;

  public user : User | undefined;

  public comments: Comment[] | undefined;

  public themeByArticle : Theme | undefined;

  constructor(   
    private articleService: ArticleService, 
    private commentService : CommentService,
    private sessionService : SessionService,
    private userService : UserService,
    private matSnackBar: MatSnackBar,
    private fb: FormBuilder,
    private router : Router,
    private route: ActivatedRoute,
    ) {
      this.articleId = this.route.snapshot.paramMap.get('id')!;
     }

  ngOnInit(): void {
    // appel au service pour récupérer l'article pour afficher détails
    // récupération des commentaires appartennant à l'user a écrit
    this.articleService.getById(this.articleId).subscribe((article: Article) => {
      this.article = article;
      this.comments = article.commentaires;
    });

    //récupération de l'user par les informations de connection
    this.userService.getById(this.sessionService.sessionInformation!.id.toString()).subscribe((user : User) => {
      this.user = user;
    })
    this.initForm();
  }

  public back() {
    window.history.back();
  }

  private initForm(): void {
    this.commentForm = this.fb.group({
      commentaire: ['', [Validators.required, Validators.min(10)]]
    });
  }

  public submit(): void {
    const commentaire = {
      commentaire: this.commentForm?.value.commentaire,
      auteur: this.user?.firstName,
      articleId: this.articleId,
      userId: this.sessionService.sessionInformation?.id.toString()
    } as Comment;
    // appel au service pour ajout de commentaire
      this.commentService
        .create(commentaire)
        .subscribe((comment: Comment) => {this.exitPage('Commentaire ajouté !'), console.log(comment.userId)});
      }

  private exitPage(message: string): void {
    this.matSnackBar.open(message, 'Close', { duration: 3000 });
    this.router.navigate(['article/list']);
  }
  
}
