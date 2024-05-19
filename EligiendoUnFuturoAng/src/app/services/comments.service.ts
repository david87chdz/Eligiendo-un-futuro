import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comments } from '../interfaces/Comment';
import { json } from 'express';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {

  constructor(private http: HttpClient) { }


  /**
   * Retrieves the comments for a specific school ID.
   * @param {number} id - The ID of the school.
   * @returns An Observable that emits an array of Comments.
   */
  getCommentsSchoolId(id: number): Observable<Comments[]> {
    return this.http.get<Comments[]>(`http://localhost:8083/api/comentarios/colegio/${id}`);
  }

  /**
   * Adds a comment to the server.
   * @param comment - The comment to be added.
   * @returns An Observable that emits the added comment.
   */
  addComment(comment: any): Observable<Comments> {
    const json = JSON.stringify(comment);
    return this.http.post<Comments>('http://localhost:8083/api/comentarios', comment);
  }

  /**
   * Retrieves the comments from the server.
   * @returns {Observable<Comments[]>} An observable that emits an array of comments.
   */
  getComments() {
    return this.http.get<Comments[]>('http://localhost:8083/api/comentarios');
  }


  /**
   * Retrieves the comments for a specific user.
   * @param {number} id - The ID of the user.
   * @returns An Observable that emits an array of Comments.
   */
  getUserComments(id: number) {
    return this.http.get<Comments[]>(`http://localhost:8083/api/comentarios/usuario/${id}`);
  }

  /**
   * Deactivates a comment with the specified ID.
   * @param {number} id - The ID of the comment to deactivate.
   * @returns A promise that resolves when the comment is deactivated.
   */
  deactivateComment(id: number) {
    return this.http.put(`http://localhost:8083/api/comentarios/${id}/deactivate`, null);
  }

  /**
   * Modifies a comment with the specified ID.
   * @param {number} id - The ID of the comment to modify.
   * @param {string} contenido - The new content of the comment.
   * @returns A Promise that resolves to the modified comment.
   */
  modifyComment(id: number, contenido: string) {
    const body = { contenido };
    return this.http.put(`http://localhost:8083/api/comentarios/${id}`, body);
  }


  /**
   * Deletes a comment by its ID.
   * @param id - The ID of the comment to delete.
   * @returns An Observable that emits the response from the server.
   */
  deleteComment(id: number): Observable<any> {
    return this.http.delete(`http://localhost:8083/api/comentarios/${id}`);
  }


  /**
   * Reactivates a comment with the specified ID.
   * @param id - The ID of the comment to reactivate.
   * @returns An Observable that emits the reactivated comment.
   */
  reactivateComment(id: number): Observable<any> {
    return this.http.put(`http://localhost:8083/api/comentarios/${id}/reactivate`, null);
  }
}
