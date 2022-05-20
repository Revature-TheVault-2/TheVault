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


  constructor(@Inject(DOCUMENT) private document: Document) {}

  toggleStyle() {



    // let  = this.document.getElementById('dark-theme') as HTMLLinkElement;
      
    // if (!themeLink) {
    //   themeLink.href = 'DarkMode.css';
    // } else {
    //   themeLink.href = 'LightMode.css';

    // }
  }

}
