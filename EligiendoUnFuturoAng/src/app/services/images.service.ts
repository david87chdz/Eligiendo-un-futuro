import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ImagesService {

  constructor(private http: HttpClient) { }



  private apiUrl = 'http://localhost:8083/api/imagenes/id';



  /**
   * Uploads an image file to the server.
   * @param image - The image file to upload.
   * @param email - The email associated with the image.
   * @returns An Observable that emits the response from the server.
   */
  uploadImageFile(image: File, email: any): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('images', image, image.name);

    return this.http.post(`http://localhost:8083/api/imagenes/uploadFile/${email}`, formData, { responseType: 'text' });
  }

  /**
   * Retrieves images by school ID.
   * @param {number} id - The ID of the school.
   * @returns An Observable that emits an array of strings representing the images.
   */
  getImagesBySchoolId(id: number): Observable<string[]> {
    return this.http.get<string[]>(`http://localhost:8083/api/imagenes/id/${id}`);
  }


  /**
   * Retrieves all images from the server.
   * @returns An Observable that emits the response from the server.
   */
  getImages(): Observable<any> {
    return this.http.get('http://localhost:8083/api/imagenes/all');
  }

  /**
   * Retrieves the URL of images for a given school ID.
   * @param id - The ID of the school.
   * @returns An Observable that emits the response containing the image URL.
   */
  getImagesUrlByIdSchool(id: number): Observable<any> {
    return this.http.get(`http://localhost:8083/api/imagenes//first/${id}`);
  }

  /**
   * Deletes an image with the specified ID.
   * @param id - The ID of the image to delete.
   * @returns An Observable that emits the response from the server.
   */
  deleteImage(id: number): Observable<any> {
    return this.http.delete(`http://localhost:8083/api/imagenes/delete/${id}`, { responseType: 'text' });
  }

  /**
   * Reactivates an image with the specified ID.
   * @param id - The ID of the image to reactivate.
   * @returns An Observable that emits the response from the server.
   */
  reactivateImage(id: number): Observable<any> {
    return this.http.put(`http://localhost:8083/api/imagenes/reactivate/${id}`, {});
  }

  /**
   * Deactivates an image with the specified ID.
   * @param id - The ID of the image to deactivate.
   * @returns An Observable that emit the response from the server.
   */
  deactivateImage(id: number): Observable<any> {
    return this.http.put(`http://localhost:8083/api/imagenes/deactivate/${id}`, {});
  }
}
