import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'date'
})
export class DatePipe implements PipeTransform {

  /**
   * Transforms a string value representing a date into a formatted date string.
   * @param value - The string value representing the date.
   * @returns The formatted date string in the format "dd/mm/yyyy".
   */
  transform(value: string): string {
    let fecha = new Date(value);
    let dia = fecha.getDate();
    let mes = fecha.getMonth() + 1;
    let año = fecha.getFullYear();
    return `${dia}/${mes}/${año}`;
  }

}
