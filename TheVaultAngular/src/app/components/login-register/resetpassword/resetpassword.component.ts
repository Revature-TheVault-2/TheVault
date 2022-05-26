import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { RoutingAllocatorService } from 'src/app/_services/app_control/routing-allocator.service';


import { PostLogin } from 'src/app/models/login/responses/post-login';
import { NewUser } from 'src/app/models/users/new-user.model';
import { PostProfile } from 'src/app/models/users/responses/post-profile';
import Validation from 'src/app/utils/validation';
import { UserHandlerService } from 'src/app/_services/user/user-handler.service';


@Component({
  selector: 'app-resetpassword',
  templateUrl: './resetpassword.component.html',
  styleUrls: ['./resetpassword.component.css']
})
export class ResetpasswordComponent implements OnInit {

  constructor(
    private routingAllocator: RoutingAllocatorService,
    private userHandler: UserHandlerService,
    private formBuilder: FormBuilder
  ) { }

  error:boolean = false;
  errorMessage: string = "Error";

  success:boolean = false;
  successMessage: string = "Success!";

  form: FormGroup = new FormGroup({
    username: new FormControl('')
    
  });
  submitted = false;
  posts: any;

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm():void{
    this.form = this.formBuilder.group(
      {        
        username: [
          '',
          [
            Validators.required,
            Validators.minLength(4),
            Validators.maxLength(25)
          ]
        ]
      }
    );
  }

  goToLogin(): void {
    this.routingAllocator.login();
  }

  get f(): { [key: string]: AbstractControl } {
    return this.form.controls;
  }

  onReset(): void {
    this.submitted = false;
    this.form.reset();
  }

  onSubmit(): void {
    this.error = false;
    this.errorMessage = "Error";
    this.submitted = true;

  /* istanbul ignore next */
    if (this.form.invalid) {
      return;
    }
    let userN = this.form.get('username')?.value;
    
    if (userN != null) {

      // this.newUser = new NewUser(userN, firstN, lastN, email, addr, phoneN, passW);
      // this.registerUser();
    }
  }

}