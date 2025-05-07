import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import {Observable, throwError} from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Categoria } from '@features/categoria';  //Importa la interface

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  private apiURL = "http://localhost:8080/v1/api/categorias";

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


  getAll(): Observable<Categoria[]> {
    return this.httpClient.get<Categoria[]>(this.apiURL)
      .pipe(
        catchError(this.errorHandler)
      )
  }

  findById(id: number): Observable<Categoria> {
    return this.httpClient.get<Categoria>(this.getUrl(id))
      .pipe(
          catchError(this.errorHandler));
  }


  create(categoria: Categoria): Observable<Categoria> {
    return this.httpClient.post<Categoria>(this.apiURL, JSON.stringify(categoria), this.httpOptions)
      .pipe(
        catchError(this.errorHandler)
      )
  }

  update(id: number, categoria: Categoria): Observable<Categoria> {
    return this.httpClient.put<Categoria>(this.getUrl(id), JSON.stringify(categoria), this.httpOptions)
      .pipe(
        catchError(this.errorHandler)
      )
  }

  delete(id: number){
    return this.httpClient.delete<Categoria>(this.getUrl(id),  this.httpOptions)

      .pipe(
        catchError(this.errorHandler)
      )
  }

  errorHandler(error: any) {

    let errorMessage = '';

    if(error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }

    return throwError(() => errorMessage);
  }

}
