import { LoginCredential } from './login-credential.model';

describe('LoginCredential', () => {
  it('should create an instance', () => {
    let loginCredential: LoginCredential = new LoginCredential(
      1,
      "username",
      "password",
      "jwt_token"
    );
    expect(loginCredential).toBeTruthy();
  });
});
