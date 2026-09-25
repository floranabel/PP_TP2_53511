package modelo.actividades;

public class Charla extends Actividad {
    private String disertante;

    public Charla(int id, String titulo, String disertante, int cupo) {
        super(id, titulo, cupo);
        this.disertante = disertante;
    }

    public String getDisertante() {
        return disertante;
    }

    public void setDisertante(String disertante) {
        if (disertante == null || disertante.isBlank()) {
            return;
        }
        this.disertante = disertante;
    }

    @Override
    public double calcularCostoMateriales() {
        /* Método polimórfico */
        return 0.0;
    }

    @Override
    public String getTipo() {
        /* Método polimórfico */
        return this.getClass().getName();
    }
}
