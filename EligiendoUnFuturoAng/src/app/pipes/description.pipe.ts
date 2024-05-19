import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'description'
})
export class DescriptionPipe implements PipeTransform {

  /**
   * Transforms the input value based on the provided arguments.
   * If the value doesn´t existr, it returns the default description "Centro de enseñanza de la JCYL".
   * @param value - The value to be transformed.
   * @param args - Additional arguments.
   * @returns The transformed value.
   */
  transform(value: unknown, ...args: unknown[]): unknown {
    if (!value) {
      return "Centro de enseñanza de la JCYL";
    }
    return value;
  }

}
