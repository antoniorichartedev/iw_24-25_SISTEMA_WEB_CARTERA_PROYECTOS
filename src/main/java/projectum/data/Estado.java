package projectum.data;


/*

    Aquí definimos los estados de un proyecto.
        * en_desarrollo -> proyecto que se está desarrollando.
        * sin_avalar -> proyecto que todavia no lo ha avalado un promotor.
        * completado -> proyecto realizado.

 */
public enum Estado {
    en_desarrollo, en_valoracion, valorado, sin_avalar, completado, rechazado, valoradoCIO, valoradoOT
}
