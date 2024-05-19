export interface School {
    id: number,
    nombre: string,
    provincia: string,
    localidad: string,
    denominacion: string,
    naturaleza: string,
    comedor: boolean,
    concierto: boolean,
    email: string,
    web: string,
    descripcion: string | null,
    direccion: string,
    telefono: string,
    localizacion: string | null 
    codigo: string,
    activo: boolean
    desactivaciones: number,
    mediaValoraciones: string | null
}   