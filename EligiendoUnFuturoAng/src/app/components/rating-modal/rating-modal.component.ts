import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-rating-modal',
  templateUrl: './rating-modal.component.html',
  styleUrls: ['./rating-modal.component.scss']
})
export class RatingModalComponent {
  stars: number[] = [1, 2, 3, 4, 5];
  selectedStar: number = 0;

  constructor(public dialogRef: MatDialogRef<RatingModalComponent>) { }


  /**
   * Sets the selected star rating and closes the modal.
   * @param star The selected star rating.
   */
  rate(star: number) {
    this.selectedStar = star;
    this.closeModal();
  }

  /**
   * Closes the modal and passes the selected star rating to the mat dialog.
   */
  closeModal() {
    this.dialogRef.close(this.selectedStar);
  }
}
