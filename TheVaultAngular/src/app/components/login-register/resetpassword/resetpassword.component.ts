import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup } from '@angular/forms';
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
  ) { }

  error:boolean = false;
  errorMessage: string = "Error";

  success:boolean = false;
  successMessage: string = "Success!";

  form: FormGroup = new FormGroup({
    username: new FormControl(''),
    firstname: new FormControl(''),
    lastname: new FormControl(''),
    email: new FormControl(''),
    address: new FormControl(''),
    phoneNumber: new FormControl(''),
    password: new FormControl(''),
    confirmPassword: new FormControl('')
  });
  submitted = false;
  posts: any;

  ngOnInit(): void {
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
    // this.error = false;
    // this.errorMessage = "Error";
    this.submitted = true;

  /* istanbul ignore next */
    if (this.form.invalid) {
      return;
    }
    let userN = this.form.get('username')?.value;
    let firstN = this.form.get('firstName')?.value;
    let lastN = this.form.get('lastName')?.value;
    let email = this.form.get('email')?.value;
    let addr = this.form.get('address')?.value;
    let phoneN = this.form.get('phoneNumber')?.value;
    let passW = this.form.get('password')?.value;
    if (userN != null && firstN != null && lastN != null && email != null && addr != null 
       && phoneN != null && passW != null) {
         
      // this.newUser = new NewUser(userN, firstN, lastN, email, addr, phoneN, passW);
      // this.registerUser();
    }
  }

}
