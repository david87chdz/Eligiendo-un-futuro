export interface Evaluations {
    id: number,
    usuario_id: number,
    colegio_id: number,
    puntuacion: number,
    fecha: Date
    usuario: {
        id: number;
        nombre: string;
        apellidos: string;
        email: string;
        password: string;
      };
      colegio: {
        id: number;
        nombre: string;
        provincia: string;
        localidad: string;
        denominacion: string;
        naturaleza: string;
        comedor: boolean;
        concierto: boolean;
        email: string;
        web: string;
        descripcion: string | null;
        direccion: string;
        telefono: string;
        localizacion: string;
      }
}