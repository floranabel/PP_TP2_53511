import excepciones.CupoExcedidoException;
import modelo.Estudiante;
import modelo.EventoUniversitario;
import modelo.Inscripcion;
import modelo.Sala;
import modelo.actividades.Actividad;
import modelo.actividades.Charla;
import modelo.actividades.Curso;
import modelo.actividades.Taller;
import modelo.certificacion.Certificable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class AppAlternativo {
        public static void main(String[] args) {

            List<Estudiante> estudiantes = new ArrayList<>();

            estudiantes.add(new Estudiante("52342", "Florencia Sosa"));
            estudiantes.add(new Estudiante("50243", "Victoria Cicconofri"));
            estudiantes.add(new Estudiante("51878", "Marta Camargo"));

            EventoUniversitario evento = new EventoUniversitario("001", "Encuentro de Prog Orientada a Objetos",2000,true);

            Sala sala = new Sala(1, "Aula Magna");

            evento.asignarSala(sala);

            evento.crearActividad(1, "modelo.actividades.Taller de POO",3,"taller" );
            evento.crearActividad(2, "modelo.actividades.Charla de PL",30,"charla" );

            try {
                evento.getActividades().get(0).inscribir(estudiantes.get(0));
                evento.getActividades().get(0).inscribir(estudiantes.get(1));
                evento.getActividades().get(0).inscribir(estudiantes.get(2));

                evento.getActividades().get(1).inscribir(estudiantes.get(1));
                evento.getActividades().get(1).inscribir(estudiantes.get(2));
            } catch (CupoExcedidoException e) {
                    System.out.println("Error al Inscribir: " + e.getMessage());
            }

            evento.mostrarDatos();

            try { //PERSISTIR EVENTO
                System.out.println("Almacenando el evento ID " + evento.getId()  );
                evento.persistirEvento();
            }
            catch (FileNotFoundException e)
            {
                System.out.println("Imposible guardar el evento ID  " +  evento.getId() +"Error al guardar el archivo: "+ e.getMessage());
            }
            catch (IOException e)
            {
                System.out.println("Imposible guardar el evento ID  " + evento.getId() );
                e.printStackTrace();
            }

            try {
                EventoUniversitario copiaDesdeArchivo = evento.recuperarEvento(evento.getId());
                System.out.println("Recuperando y mostrando el evento ID " + evento.getId()  + " almacenado previamente.");
                copiaDesdeArchivo.mostrarDatos();
            }
            catch (ClassNotFoundException e)
            {
                System.out.println("No fue posible reconstruir el objeto almacenado: " + e.getMessage());
            }
            catch (FileNotFoundException e)
            {
                System.out.println("Imposible recuperar el evento ID  " + evento.getId() +". Error al guardar el archivo: "+ e.getMessage());
            }
            catch (IOException e)
            {
                System.out.println("Imposible recuperar el evento ID  " + evento.getId() );
                e.printStackTrace();
            }

            for (Actividad actividad: evento.getActividades()){
                if (actividad instanceof Certificable certificable){
                    System.out.println("Certificados Emitidos para la Actividad" + actividad.getTitulo());
                    for (Inscripcion inscripcion: actividad.getInscripciones()){
                        String certificado = certificable.generarCertificado(inscripcion.getEstudiante());
                        System.out.println(certificado);
                    }
                }
            }


            System.out.println("\n\n Filtrando la Lista de Actividades ");
            List<Taller> talleres = evento.filtrarActividadesPorTipo(Taller.class);
            List<Curso> cursos = evento.filtrarActividadesPorTipo(Curso.class);
            List<Charla> charlas = evento.filtrarActividadesPorTipo(Charla.class);


            System.out.println("Actividades filtradas por Tipo:");
            System.out.println("Charlas encontradas: " + charlas.size());
            System.out.println("Talleres encontrados: " + talleres.size());
            System.out.println("Cursos encontrados: " + cursos.size());

            System.out.println("\nCalculando costos de las listas filtradas");

            System.out.println("Costo Materiales de Talleres: $" + evento.calcularCostoMateriales(talleres));
            System.out.println("Costo Materiales de Cursos: $" + evento.calcularCostoMateriales(cursos));
            System.out.println("Costo Materiales de todas las aAtividades: $" + evento.calcularCostoMateriales(evento.getActividades()));

        }


}
