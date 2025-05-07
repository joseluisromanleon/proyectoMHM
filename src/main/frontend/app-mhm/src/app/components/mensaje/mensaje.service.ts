import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import {Observable, throwError} from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Mensaje } from '@features/mensaje';  //Importa la interface

@Injectable({
  providedIn: 'root'
})
export class MensajeService {

  private apiURL = "http://localhost:8080/v1/api/mensajes";
  // private apiURL = "/api/mensajes";

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private httpClient: HttpClient) { }

  //Genera la apy añadiendo la barra '/' y recogiendo el parametro id
  private getUrl(id?: number): string {
    return id ? `${this.apiURL}/${id}` : this.apiURL;
  }


  getAll(): Observable<Mensaje[]> {
    return this.httpClient.get<Mensaje[]>(this.apiURL)
      .pipe(
        catchError(this.errorHandler)
      )
  }

  findById(id: number): Observable<Mensaje> {
    return this.httpClient.get<Mensaje>(this.getUrl(id))
      .pipe(
          catchError(this.errorHandler));
  }


  create(mensaje: Mensaje): Observable<Mensaje> {
    return this.httpClient.post<Mensaje>(this.apiURL, mensaje, this.httpOptions)
      .pipe(
        catchError(this.errorHandler)
      )
  }
  // crearMensaje(mensaje: any) {
  //   return this.httpClient.post('/api/mensajes', mensaje);
  // }

  update(id: number, mensaje: Mensaje): Observable<Mensaje> {
    return this.httpClient.put<Mensaje>(this.getUrl(id), JSON.stringify(mensaje), this.httpOptions)
      .pipe(
        catchError(this.errorHandler)
      )
  }

  delete(id: number){
    return this.httpClient.delete<Mensaje>(this.getUrl(id),  this.httpOptions)

      .pipe(
        catchError(this.errorHandler)
      )
  }

  errorHandler(error: any) {

    let errorMessage = '';

    if(error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Error Code: ${error.status}\n Message: ${error.message}`;
    }

    return throwError(() => errorMessage);
  }

}
