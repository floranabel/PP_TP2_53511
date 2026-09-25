package modelo.actividades;

import excepciones.CupoExcedidoException;
import modelo.Estudiante;
import modelo.Inscripcion;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * modelo.actividades.Actividad es una clase abstracta que representa una actividad genérica dentro de un evento universitario.
 */
public abstract  class Actividad  implements Serializable  {
    private int id;
    private String titulo;
    private int cupoMaximo;
    /* Aquí evidenciar cómo se puede mantener en un único lugar el código que es común a cualquier tipo de actividad
     * como por ejemplo el manejo de las inscripciones. Y luego en las clases especializadas manejar sólo el código
     * que implementa las diferencias entre cada tipo concreto de actividad.  */
    private List<Inscripcion> inscripciones ;

    public static final int CUPO_MINIMO ;

    static {
        /* Variable de clase: se fija un cupo mínimo de 5 personas para cualquier actividad. */
        CUPO_MINIMO = 5;
        System.out.println("Inicializador estático: se cargó la clase modelo.actividades.Actividad.");
    }


    /* Hacer notar que aunque la clase sea abstracta  igual pueden proveerse constructores que serán
     * activados desde las subclases con super() */
    public Actividad(int id, String titulo, int cupo) {
        this.id = id;
        this.titulo = titulo;
        /* Aquí no se lanza una excepción, porque se entiende que no es una situación de error.
         *  Simplemente, si se consideró un cupoMáximo inferior al mínimo,  se fija el cupo en el mínimo y se resuelve el problema. */
        this.cupoMaximo = (cupo > CUPO_MINIMO) ? cupo : CUPO_MINIMO;
        this.inscripciones = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            return;
        }
        this.titulo = titulo;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupo) {
        this.cupoMaximo = (cupo > CUPO_MINIMO) ? cupo : CUPO_MINIMO;
    }

    public Inscripcion inscribir(Estudiante estudiante) throws CupoExcedidoException {
        if (inscripciones.size() >= cupoMaximo) {
            throw new CupoExcedidoException("No se puede inscribir al estudiante " + estudiante.getNombre() + ". Cupo máximo alcanzado.");
        }
        Inscripcion inscripcion = new Inscripcion(this, estudiante, LocalDate.now(), "REGISTRADA");
        inscripciones.add( inscripcion);
        return inscripcion;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void mostrarInscripciones() {
        if (inscripciones.isEmpty()) {
            System.out.println("  Sin inscripciones registradas.");
            return;
        }
        System.out.println("   Inscripciones registradas:");
        for (Inscripcion inscripcion : inscripciones) {
            System.out.println("   " + inscripcion.getFecha()
                    +" - "+  inscripcion.getEstado()
                    + " - " + inscripcion.getEstudiante().getNombre()
                    + " (Legajo: " + inscripcion.getEstudiante().getLegajo() + ")");
        }
    }

    /**
     * Método final: las subclases no pueden redefinir la forma estándar de identificar una actividad.
     */
    public final void mostrarIdentificacion() {
        /* Aquí se evidencia el polimorfismo: cada subclase implementa su propia versión de getTipo() . Si este método no estuviese
         *  definido en la superclase, no se podría utilizar aquí. */
        System.out.println("- " + getTipo() + ": " + titulo + " (id=" + id + ")" + " - Cupo máximo: " + cupoMaximo);
    }

    /**
     * Método abstracto: cada tipo de actividad calcula su costo de materiales de manera diferente.
     */
    public abstract double calcularCostoMateriales();

    /**
     * Método abstracto usado para evidenciar polimorfismo en la salida del programa.
     */
    public abstract String getTipo();
}
