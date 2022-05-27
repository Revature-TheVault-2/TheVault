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
    // this.errorMessage = "Error";
    this.submitted = true;

  /* istanbul ignore next */
    if (this.form.invalid) {
      return;
    }
    
    let passW = this.form.get('password')?.value;
    let tokenValue = this.form.get('token')?.value;
    // console.log(tokenValue);
    if (passW != null && tokenValue != null) {

      // this.newUser = new NewUser(userN, firstN, lastN, email, addr, phoneN, passW);
      // this.registerUser();
      this.resetPassword = new resetPassword(passW,tokenValue);
      this.newPassword()
    }
  }
  newPassword(){
    this.userHandler.newPassword(this.resetPassword.password, this.resetPassword.token).subscribe(this.loginObserver);
  }
  
  loginObserver = {
    next: (data: boolean) => {
      if (data == false){
        console.log("This was false");
        this.form.reset();
        this.error = true;



      }else{
        
        this.routingAllocator.login();
        this.success = true;

      }

      
    },
    // error: (err: Error) => {
    //   /* istanbul ignore next */
    //     console.error("profile observer error: " + err);
    //     this.error = true;
    //   /* istanbul ignore next */
    //     this.onReset();},
  
    
    complete: () => console.log("Response lets you know what happened")
  }

}
