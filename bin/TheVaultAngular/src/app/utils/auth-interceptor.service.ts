import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GlobalStorageService } from '../_services/global-storage.service';

@Injectable()
export class AuthInterceptorService implements HttpInterceptor{

  constructor(
    private globalStorage: GlobalStorageService
  ) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let authReq = req;
    return next.handle(authReq);
  }

}
