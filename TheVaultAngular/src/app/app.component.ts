import { Component, Inject, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent {
  title = 'TheVault';
 buttonText: String = "Light Mode";

  toggleTheme(): void{
    document.body.classList.toggle('light-theme');
    let cardBody= document.getElementById('accountCard');
    console.log(cardBody);
    cardBody?.classList.toggle('light-theme');
  }

 
}
