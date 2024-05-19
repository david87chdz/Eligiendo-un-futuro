import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Answers } from '../interfaces/Answers';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AnswersService {

  constructor(private http: HttpClient) { }


  /**
   * Retrieves the comments for a specific answer by its ID.
   * @param id - The ID of the answer.
   * @returns An Observable that emits the comments for the answer.
   */
  getAnswersCommentsId(id: number): Observable<any> {
    return this.http.get<Answers>(`http://localhost:8083/api/respuestas/comentario/${id}`);
  }


  /**
   * Retrieves the answers from the server.
   * @returns An Observable that emits the response from the server.
   */
  getAnswers(): Observable<any> {
    return this.http.get('http://localhost:8083/api/respuestas');
  };


  /**
   * Saves an answer for a comment.
   * @param {number} commentId - The ID of the comment.
   * @param {string} contenido - The content of the answer.
   * @param {number} colegioId - The ID of the school.
   * @returns An Observable that represents the HTTP POST request.
   */
  saveAnswer(commentId: number, contenido: string, colegioId: number,): Observable<any> {
    let answer = {
      contenido: contenido,
      colegioId: colegioId,
      comentarioId: commentId
    };
    return this.http.post('http://localhost:8083/api/respuestas', answer);
  }


  /**
   * Deletes an answer by its ID.
   * @param {number} id - The ID of the answer to delete.
   * @returns An Observable that emit the deleted answer.
   */
  deleteAnswer(id: number): Observable<any> {
    return this.http.delete(`http://localhost:8083/api/respuestas/${id}`);
  }


  /**
   * Updates an answer with the specified ID.
   * @param {number} id - The ID of the answer to update.
   * @param {string} contenido - The new content for the answer.
   * @returns An Observable that emit the updated answer.
   */
  updateAnswer(id: number, contenido: string): Observable<any> {
    const answer = { contenido: contenido };
    return this.http.put(`http://localhost:8083/api/respuestas/${id}`, answer);
  }

}
