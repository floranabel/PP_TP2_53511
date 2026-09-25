README TP2
Consignas 
Ejercicio 1 - Excepciones y Persistencia:** Se implementó la excepción personalizada `CupoExcedidoException` para evitar que se inscriban más alumnos del límite permitido en una actividad. Además, se agregó la funcionalidad para guardar (persistir) y recuperar los datos del evento utilizando serialización de objetos.

Ejercicio 2 - Interfaces:** Se agregó la interfaz `Certificable` para poder emitir certificados únicamente a los estudiantes que asisten a Talleres o Cursos (las Charlas quedaron excluidas de esta certificación).

Ejercicio 3 - Genéricos y Wildcards:** Se agregaron métodos en la clase `EventoUniversitario` utilizando genéricos (`<T extends Actividad>`) para poder filtrar las listas de actividades por su tipo específico, y comodines (`? extends Actividad`) para calcular los costos de los materiales.

Ejercicio 4 - Hilos (Concurrencia):** Se implementó una clase interna `TicketDeAcceso` y un hilo independiente llamado `EnvioTicketsThread`. Esto permite simular el envío de tickets a los alumnos inscriptos en segundo plano, sin bloquear la ejecución del menú principal en la consola.

PARA EJECUTAR EL PROGRAMA
	Abrir clase App.Java

LEGAJO 53511
Florencia Anabel Sosa 
2K07