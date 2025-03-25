import {ChangeDetectorRef, Component} from '@angular/core';
import {Router, RouterLink, RouterOutlet} from '@angular/router';
import {ApiService} from "./service/api.service";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,CommonModule, RouterLink],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'ims-app';

  constructor(
      private apiService: ApiService,
      private router: Router,
      private cdr: ChangeDetectorRef
  ) {}

  isAuth():boolean{
    return this.apiService.isAuthenticated();
  }

  isAdmin():boolean{
    return this.apiService.isAdmin();
  }

  logOut():void{
    this.apiService.logout();
    this.router.navigate(["/login"])
    this.cdr.detectChanges();
  }

}
