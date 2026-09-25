package modelo.actividades;

import modelo.Estudiante;
import modelo.certificacion.Certificable;

/**
 * modelo.actividades.Actividad concreta que representa un taller universitario.
 * Se ejemplifica el uso de herencia y polimorfismo, ya que modelo.actividades.Taller extiende de la clase abstracta modelo.actividades.Actividad
 * y proporciona implementaciones específicas para los métodos abstractos.
 */
public class Taller extends Actividad implements Certificable  {
    private boolean requiereNotebook;

    public Taller(int id, String titulo, boolean requiereNotebook, int cupo) {
        super(id, titulo,cupo);
        this.requiereNotebook = requiereNotebook;
    }

    public boolean isRequiereNotebook() {
        return requiereNotebook;
    }

    public void setRequiereNotebook(boolean requiereNotebook) {
        this.requiereNotebook = requiereNotebook;
    }

    @Override
    public double calcularCostoMateriales() {
        /* Método polimórfico */
        if (requiereNotebook) {
            return 5000.0;
        }
        return 2000.0;
    }

    @Override
    public String getTipo() {
        /* Método polimórfico */
        return this.getClass().getName();
    }

    /**
     * Implementa el protocolo Certificable.
     * Taller extiende Actividad e implementa Certificable, mostrando que
     * Java permite heredar implementación de una clase y heredar tipo
     * desde una o más interfaces.
     */
    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "Certificado emitido por " + ENTIDAD_EMISORA
                + ": se deja constancia de que " + estudiante.getNombre()
                + " participó en el taller \"" + getTitulo() + "\".";
    }
}