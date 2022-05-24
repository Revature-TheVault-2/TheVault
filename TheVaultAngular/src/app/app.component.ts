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

    // let cardBody= document.getElementById('accountCard'); // Some issues with this one right now
    // cardBody?.classList.toggle('light-theme');

    let tableBody = document.getElementById('transactionTable');
    tableBody?.classList.toggle('light-theme');

    let accountHeader = document.getElementById('accountHeader');
    accountHeader?.classList.toggle('light-theme');
    let accountBackground = document.getElementById('accountBackground');
    accountBackground?.classList.toggle('light-theme');

    let navbar = document.getElementById('navbar')
    navbar?.classList.toggle('light-theme')

  }

 
}
