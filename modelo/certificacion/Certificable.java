package modelo.certificacion;

import modelo.Estudiante;

/**
 * Define el protocolo que debe cumplir cualquier elemento del sistema
 * capaz de emitir una constancia o certificado para un estudiante.
 *
 * Se modela como interface porque no representa una especialización
 * dentro de la jerarquía de Actividad, sino una capacidad transversal:
 * distintas clases no relacionadas por herencia podrían ser certificables.
 *
 * En Java, los atributos de una interface son public, static y final
 * de forma implícita. Los métodos declarados son public y abstract
 * mientras no se indique otra cosa.
 */
public interface Certificable {
    String ENTIDAD_EMISORA = "UTN - FRM";

    /**
     * Genera el texto de la constancia correspondiente al estudiante indicado.
     *
     * @param estudiante estudiante para quien se emite la constancia.
     * @return texto de la constancia generada.
     */
    String generarCertificado(Estudiante estudiante);
}
