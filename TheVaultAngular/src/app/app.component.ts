import { Component, Inject, ViewEncapsulation } from '@angular/core';
import { DOCUMENT } from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent {
  title = 'TheVault';


  constructor(@Inject(DOCUMENT) private document: Document) { }


  changeTheme() {
    let theme = this.document.getElementById('theme') as HTMLLinkElement;

    console.log(theme.getAttribute('href'))

    
    //to add another theme add another if statment
    if (theme.getAttribute('href') == '/src/DarkMode.css') {
      theme.href = '/src/LightMode.css';
    }else {
      theme.href = '/src/DarkMode.css';

    }

  }

}
