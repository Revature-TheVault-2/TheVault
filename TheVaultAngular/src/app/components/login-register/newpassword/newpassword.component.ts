import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { RoutingAllocatorService } from 'src/app/_services/app_control/routing-allocator.service';
import { PostReset } from 'src/app/models/reset/responses/post-reset';
import { NewUser } from 'src/app/models/users/new-user.model';
import { PostProfile } from 'src/app/models/users/responses/post-profile';
import Validation from 'src/app/utils/validation';
import { UserHandlerService } from 'src/app/_services/user/user-handler.service';
import { LoginUser } from 'src/app/models/users/login-user.model';
import { resetPassword } from 'src/app/models/reset/reset-password.model';

@Component({
  selector: 'app-newpassword',
  templateUrl: './newpassword.component.html',
  styleUrls: ['./newpassword.component.css']
})
export class NewpasswordComponent implements OnInit {

  constructor(
    private routingAllocator: RoutingAllocatorService,
    private userHandler: UserHandlerService,
    private formBuilder: FormBuilder
    
  ) { }

  error:boolean = false;
  errorMessage: string = "Error, please check your Reset Token and passwords and try again.";
  loginUser!: LoginUser
  resetPassword!: resetPassword


  success:boolean = false;
  successMessage: string = "Success!";

  form: FormGroup = new FormGroup({
    
    password: new FormControl(''),
    confirmPassword: new FormControl(''),
    token: new FormControl(''),
  });
  submitted = false;
  posts: any;

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm():void{
    this.form = this.formBuilder.group(
      {        
        password: [
          '',
          [
            Validators.required,
            Validators.minLength(4),
            Validators.maxLength(25)
          ]
        ],
        confirmPassword: [
          '',
          [
            Validators.required,
            Validators.minLength(4),
            Validators.maxLength(25)
          ]
        ],
        token: [
          '',
          [
            Validators.required
          ]
        ]
      },
      {
        validators: [Validation.match('password', 'confirmPassword')]
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
    this.submitted = true;

  /* istanbul ignore next */
    if (this.form.invalid) {
      return;
    }
    
    //gets password and token value from form
    let passW = this.form.get('password')?.value;
    let tokenValue = this.form.get('token')?.value;
    if (passW != null && tokenValue != null) {

     
      //sets resetPassword model with gathered values, sends to newPassword
      this.resetPassword = new resetPassword(passW,tokenValue);
      this.newPassword()
    }
  }
  /**
   * utilizes user-handler service to build the request, including endpoint and body
   */
  newPassword(){
    this.userHandler.newPassword(this.resetPassword.password, this.resetPassword.token).subscribe(this.loginObserver);
  }
  
  loginObserver = {
    next: (data: boolean) => {
      //if it returns false, it will reset the form and display an error message
      if (data == false){
        this.form.reset();
        this.error = true;

      }else{
        //if it returns true, it will give the success message at the top of the page
        this.routingAllocator.login();
        this.success = true;

      }

      
    },
     
    
    complete: () => console.log("Nothing to see here. Move along.")
  }

}
