Hay parte de la tarea parcial 1, la verdad creo que lo coloque mal, pero como no la entregue lo deje ahi (aviso por si acaso).

Ahora si el readme:

## Entrega final 1

## Organización del código
El proyecto sigue el patrón arquitectónico MVC solicitado.
- El modelo agrupa todas las entidades base del juego, las cuales fueron estructuradas utilizando traits y clases abstractas.
- El controlador contiene al TurnScheduler, cuya responsabilidad es gestionar el estado de la batalla y mandar las entidades del modelo, manteniendo separadas las lógicas de datos y de flujo de juego.

## Decisiones de diseño
Para la implementación del TurnScheduler, se tomó la decisión de utilizar Arreglos  y recursividad pura. Se evitó deliberadamente el uso de ciclos iterativos clásicos (for, while). El filtrado de unidades listas y el ordenamiento por excedente se construyeron de forma manual recursiva. Esto garantiza un control algorítmico estricto.

## Patrones de diseño
El código se sustenta fuertemente en el polimorfismo. El programador de turnos no conoce clases concretas, sino que interactúa exclusivamente con la abstracción Entity. Esto respeta el principio de diseño Abierto-Cerrado (Open-Closed Principle, cabe aclarar que lo use de las últimas clases, supuse que se podía), permitiendo que en el futuro se puedan agregar nuevos tipos de unidades sin tener que modificar el código interno del TurnScheduler.

## Bonificaciones aplicadas (debido a que no entregue las tareas parciales)
- Inglés: Todo el código fuente, nombres de variables, métodos, y la documentación interna (ScalaDoc) están 100% en inglés.
- Uso de Git: Se registraron pequeños cambios progresivos utilizando la nomenclatura de Conventional Commits.
