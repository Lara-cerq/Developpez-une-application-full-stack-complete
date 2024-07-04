import { Component, OnInit } from '@angular/core';
import { Article } from '../../../interface/article';
import { Observable } from 'rxjs';
import { ArticleService } from '../../../service/article.service';
import { Router } from '@angular/router';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {

  public articles$: Observable<Article[]> = this.articleService.allDesc();

  public articlesAsc$: Observable<Article[]> = this.articleService.allAsc();

  public commentForm: FormGroup | undefined;

  public ascending : boolean | undefined;

  constructor(
    private articleService : ArticleService,
    private router : Router
  ) { }

  ngOnInit(): void {
  }

  // redirection du boutton permettant de créer nouvel article
  create() {
    this.router.navigateByUrl('article/create');
  }

  // pour button de 'trier par'
  onDownClick() {
    this.ascending=true;
  }

  onUpClick() {
    this.ascending=false;
  }
}
