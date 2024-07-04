import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../interface/user.interface';
import { Observable } from 'rxjs';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private pathService = 'api/user';

  constructor(private httpClient : HttpClient) { }

  // récupération User correspondant à l'id
  public getById(id: string): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/${id}`);
  }

  // permet de faire l'update des infos de l'user
  public update(id: string, user: User): Observable<User> {
    return this.httpClient.put<User>(`${this.pathService}/${id}`, user);
  }

  // envoi requette pour s'abonner
  public follow(themeId: string, userId: string): Observable<void> {
    return this.httpClient.post<void>(`${this.pathService}/${themeId}/follow/${userId}`, null);
  }

  // envoi de requette pour de désabonner
  public unFollow(themeId: string, userId: string): Observable<void> {
    return this.httpClient.delete<void>(`${this.pathService}/${themeId}/follow/${userId}`);
  }

}
