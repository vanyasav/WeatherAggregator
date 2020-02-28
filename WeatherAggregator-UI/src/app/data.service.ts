import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import {  throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import {BehaviorSubject} from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class DataService {
  private REST_API_SERVER = 'http://localhost:8080/weathers/list';

  private REST_API_CITIES_SERVER = 'http://localhost:8080/weathers/listcustom';

  private messageSource = new BehaviorSubject<string>('default message');
  currentMessage = this.messageSource;

  changeMessage(message: string) {
    this.messageSource.next(message);
  }

  constructor(private httpClient: HttpClient) { }

  handleError(error: HttpErrorResponse) {
    let errorMessage = 'Unknown error!';
    if (error.error instanceof ErrorEvent) {
      // Client-side errors
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side errors
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    window.alert(errorMessage);
    return throwError(errorMessage);
  }

  public sendGetRequest() {
    return this.httpClient.get(this.REST_API_SERVER).pipe(retry(3), catchError(this.handleError));
  }

  public sendGetCitiesRequest() {
    return this.httpClient.get(this.REST_API_CITIES_SERVER).pipe(retry(3), catchError(this.handleError));
  }
}
