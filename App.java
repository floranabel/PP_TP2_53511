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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean continuar=true;
        int id=1;

        /* Se crean estudiantes } */
        List<Estudiante> estudiantes = new ArrayList<>();
//preg tipo salto como se hace
        System.out.println("Sistema de Registro Estudiantes");


        while (continuar){
            System.out.println("Ingrese el legajo del estudiante:");
            String legajo = scanner.nextLine();
            System.out.println("Ingrese el nombre y apellido del estudiante:");
            String apenomb = scanner.nextLine();
            estudiantes.add(new Estudiante(legajo, apenomb));
            System.out.println("Desea añadir otro estudiante  S/N?");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            continuar = (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí")) ? true : false;
        };
            System.out.println("\n\nRegistro de Eventos: ");

        continuar=true;
        while(continuar) {
            System.out.println("Ingese el titulo del evento:");
            String titulo = scanner.nextLine();
            System.out.println("Ingese el costo base del evento:");
            double costoBase = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("El evento tendra costo para los participantes S/N?");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            boolean esGratuito = true;
            if (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí")) {
                esGratuito = false;
            }

            EventoUniversitario evento = new EventoUniversitario(
                    "EVT-" + id,
                    titulo,
                    costoBase,
                    esGratuito
            );
            System.out.println("Ingese el nombre de la sala del evento:");
            String nombreSala = scanner.nextLine();
            Sala sala = new Sala(id, nombreSala);
            evento.asignarSala(sala);



            System.out.println("\n\nRegistro de Actividades para Eventos" + evento.getTitulo());
            int idActividad = 1;
            while (continuar) {
                System.out.println("Ingrese el título de la actividad:");
                String tituloActividad = scanner.nextLine();
                System.out.println("Ingrese el Cupo Máximo de estudiantes para la actividad:");
                int cupo = scanner.nextInt();
                scanner.nextLine();
                System.out.println("La actividad es una Charla, Taller o Curso?  (Charla/Taller/Curso)? ");
                String tipo = scanner.nextLine().trim().toLowerCase();
                evento.crearActividad(idActividad, tituloActividad, cupo, tipo);
                System.out.println("Desea crear otra actividad para el  evento" + evento.getTitulo() + " S/N?");
                respuesta = scanner.nextLine().trim().toLowerCase();
                continuar = (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí")) ? true : false;
                ++idActividad;
            }

            try {
                System.out.println("\n\nInscripcion de Estudiantes en Actividades de Evento" + evento.getTitulo());
                continuar = true;
                while (continuar) {
                    System.out.println("Ingrese el legajo del estudiante a inscribir:");
                    String legajo = scanner.nextLine();
                    System.out.println("Ingese ID de la Actividad:");
                    idActividad = scanner.nextInt();
                    scanner.nextLine(); //LO D LA LINEA
                    for (Estudiante estudiante : estudiantes) {
                        if (estudiante.getLegajo().equals(legajo)) {
                            evento.getActividades().get(--idActividad).inscribir(estudiante);
                        }
                    }
                    System.out.println("Desea generar otra inscripción  S/N?");
                    respuesta = scanner.nextLine().trim().toLowerCase();
                    continuar = (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí")) ? true : false;
                }
            } catch (CupoExcedidoException e) {
                System.out.println("Error al inscribir: " + e.getMessage());
            }

            try {

                evento.persistirEvento();

                System.out.println("\n\nDATOS DEL EVENTO");
                evento.mostrarDatos();

                List<Taller> talleres = evento.filtrarActividadesPorTipo(Taller.class);
                List<Curso> cursos = evento.filtrarActividadesPorTipo(Curso.class);
                List<Charla> charlas = evento.filtrarActividadesPorTipo(Charla.class);

//FILTRADAS POR TIPO USANDO METODO PARAMETRIZADO ACOTADO
                System.out.println("Actividades filtradas por tipo:");
                System.out.println("Charlas encontradas:" + charlas.size());
                System.out.println("Talleres encontrados:" + talleres.size());
                System.out.println("Cursos encontrados:" + cursos.size());

                System.out.println("Costo Materiales de talleres: $" + evento.calcularCostoMateriales(talleres));
                System.out.println("Costo Materiales de cursos: $" + evento.calcularCostoMateriales(cursos));
                System.out.println("Costo Materiales de todas las actividades: $" + evento.calcularCostoMateriales(evento.getActividades()));

            } catch (FileNotFoundException e) {

                System.out.println(
                        "Error 01: No se encontró el archivo del evento: "
                                + e.getMessage()
                );

            } catch (IOException e) {

                System.out.println(
                        "Se produjo un error de entrada o salida: "
                                + e.getMessage()
                );
            }

            for (Actividad actividad: evento.getActividades()){
                if (actividad instanceof Certificable certificable){
                    System.out.println("Certificados Emitidos para Actividad" + actividad.getTitulo());
                    for (Inscripcion inscripcion: actividad.getInscripciones()){
                        String certificado = certificable.generarCertificado(inscripcion.getEstudiante());
                        System.out.println(certificado);
                    }
                }
            }


            System.out.println("\n\nDesea crear otro evento  S/N?");
            respuesta = scanner.nextLine().trim().toLowerCase();
            continuar  = (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí")) ? true : false;
        } ;

        //cant total eventos cfeados
        System.out.println("\n\nTotal de Eventos Creados: " + EventoUniversitario.getCantidadEventos());
    }
}
