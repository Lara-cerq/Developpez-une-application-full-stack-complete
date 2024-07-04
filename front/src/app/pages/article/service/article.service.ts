import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Article } from '../interface/article';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private pathService = 'api/article';

  constructor(private httpClient : HttpClient) { 
  }

  // récupération liste articles par ordre décroissante de date de création
  public allDesc(): Observable<Article[]> {
    return this.httpClient.get<Article[]>(this.pathService);
  }

    // récupération liste articles par ordre croissante de date de création
  public allAsc(): Observable<Article[]> {
    return this.httpClient.get<Article[]>(`${this.pathService}/asc`);
  }

  // création de nouvel article
  public create(article : Article): Observable<Article> {
    return this.httpClient.post<Article>(this.pathService, article);
  }

  // récupération de article à partir de l'id
  public getById(id: string): Observable<Article> {
    return this.httpClient.get<Article>(`${this.pathService}/${id}`);
  }
}
