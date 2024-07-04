import { Component, OnInit } from '@angular/core';
import { ThemeService } from '../../service/theme.service';
import { UserService } from 'src/app/pages/user/service/user.service';
import { SessionService } from 'src/app/services/session.service';
@Component({
  selector: 'app-theme',
  templateUrl: './theme.component.html',
  styleUrls: ['./theme.component.scss']
})
export class ThemeComponent implements OnInit {

  public themes  = this.themeService.all();

  private userId: string;

  constructor(
    private themeService : ThemeService,
    private userService : UserService,
    private sessionService : SessionService
    ) { 
      this.userId = this.sessionService.sessionInformation!.id.toString();
    }
  ngOnInit(): void {

  }

    public follow(themeId : any): void {
      this.userService.follow(themeId, this.userId).subscribe(_ => this.themes= this.themeService.all());
    }
  
    public unFollow(themeId : any): void {
      this.userService.unFollow(themeId, this.userId).subscribe(_ =>  this.themes= this.themeService.all());
    }

}
