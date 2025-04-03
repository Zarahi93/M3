import java.util.*;
public class lift {
  /**
   * Dirección del movimiento del elevador.
   * UP = subiendo, DOWN = bajando.
   */
  enum Mode {
    UP,
    DOWN
  }

  /**
   * Simula el movimiento del elevador hasta llevar a todas las personas a su destino.
   *
   * @param queues matriz que representa las colas de espera en cada piso.
   *               Cada subarreglo contiene los pisos a los que quieren ir las personas de ese piso.
   * @param capacity número máximo de personas que caben en el elevador.
   * @return arreglo de enteros representando los pisos donde se detuvo el elevador, en orden.
   */
  public static int[] theLift(final int[][] queues, final int capacity) {
    int currentFloor = 0; // El piso actual donde se encuentra el elevador.
    Mode mode = Mode.UP; // Dirección actual del elevador (subiendo por defecto).
    List<Integer> lift = new ArrayList<>(); // Personas dentro del elevador (solo se guarda su destino).
    List<Integer> stops = new ArrayList<>(); // Pisos donde el elevador se detiene.

    // Convierte el arreglo de colas a una estructura más flexible (List<List<Integer>>)
    List<List<Integer>> floorQueues = convertToQueueList(queues);

    int nextFloor = 0; // Próximo piso a visitar

    // Ciclo principal: se ejecuta mientras haya personas esperando o dentro del elevador
    do {
      // Actualiza la dirección del elevador según el próximo piso
      if (nextFloor > currentFloor) {
        mode = Mode.UP;
      } else if (nextFloor < currentFloor) {
        mode = Mode.DOWN;
      }

      currentFloor = nextFloor; // Mover el elevador al siguiente piso

      // Procesar el piso actual: bajar, subir personas y registrar parada
      processCurrentFloor(currentFloor, mode, lift, floorQueues, capacity, stops);

      // Determina el siguiente piso que debe visitar
      nextFloor = getNextStop(currentFloor, mode, lift, floorQueues);

    } while (nextFloor != -1); // Repite hasta que no haya nadie esperando ni dentro del elevador

    // Si el elevador no terminó en planta baja, regresa
    if (currentFloor != 0) {
      stops.add(0);
    }

    // Convertir la lista de paradas a un arreglo de enteros
    return stops.stream().mapToInt(i -> i).toArray();
  }

  /**
   * Convierte el arreglo de colas de piso a una lista de listas para facilitar su manipulación.
   *
   * @param queues matriz original de colas por piso.
   * @return lista de listas de enteros representando las colas de cada piso.
   */
  private static List<List<Integer>> convertToQueueList(int[][] queues) {
    List<List<Integer>> result = new ArrayList<>();
    for (int[] floor : queues) {
      List<Integer> q = new ArrayList<>();
      for (int dest : floor) {
        q.add(dest);
      }
      result.add(q);
    }
    return result;
  }

  /**
   * Decide cuál es el siguiente piso que el elevador debe visitar.
   *
   * @param currentFloor piso actual.
   * @param mode dirección actual (subiendo o bajando).
   * @param lift personas dentro del elevador.
   * @param floorQueues personas esperando por piso.
   * @return número de piso a visitar o -1 si ya no hay nada por hacer.
   */
  private static int getNextStop(
      int currentFloor,
      Mode mode,
      List<Integer> lift,
      List<List<Integer>> floorQueues) {

    int maxFloor = floorQueues.size() - 1;

    // Si el elevador está vacío, buscar inteligentemente el piso con personas
    if (lift.isEmpty()) {
      if (mode == Mode.UP) {
        // Buscar de arriba hacia abajo a alguien que quiera bajar
        for (int i = maxFloor; i > currentFloor; i--) {
          for (int dest : floorQueues.get(i)) {
            if (dest < i) {
              return i;
            }
          }
        }
      } else {
        // Buscar de abajo hacia arriba a alguien que quiera subir
        for (int i = 0; i < currentFloor; i++) {
          for (int dest : floorQueues.get(i)) {
            if (dest > i) {
              return i;
            }
          }
        }
      }

      // Si no encontramos a nadie, buscar cualquier piso con personas
      for (int i = 0; i < floorQueues.size(); i++) {
        if (!floorQueues.get(i).isEmpty()) {
          return i;
        }
      }

      return -1; // No hay nadie esperando
    }

    // Si está en un extremo, cambiar de dirección
    Mode newMode = mode;
    if (mode == Mode.UP && currentFloor == maxFloor) {
      newMode = Mode.DOWN;
    } else if (mode == Mode.DOWN && currentFloor == 0) {
      newMode = Mode.UP;
    }

    // Buscar el próximo destino según la dirección
    int next;
    if (newMode == Mode.UP) {
      next = getNextUpStop(currentFloor, lift, floorQueues);
      if (next == -1) {
        next = getNextDownStop(currentFloor, lift, floorQueues);
      }
    } else {
      next = getNextDownStop(currentFloor, lift, floorQueues);
      if (next == -1) {
        next = getNextUpStop(currentFloor, lift, floorQueues);
      }
    }

    return next;
  }

  /**
   * Determina el próximo piso al subir.
   *
   * @param currentFloor piso actual.
   * @param lift personas dentro del elevador.
   * @param floorQueues personas esperando en pisos.
   * @return el piso más cercano hacia arriba donde debe detenerse.
   */
  private static int getNextUpStop(int currentFloor, List<Integer> lift, List<List<Integer>> floorQueues) {
    int nFloors = floorQueues.size();
    Integer nextPickup = null;

    // Buscar personas esperando para subir más arriba
    for (int i = currentFloor + 1; i < nFloors; i++) {
      for (int dest : floorQueues.get(i)) {
        if (dest > i) {
          nextPickup = i;
          break;
        }
      }
      if (nextPickup != null) break;
    }

    // Buscar personas dentro que quieran bajarse más arriba
    Integer nextDropoff = null;
    for (int dest : lift) {
      if (dest > currentFloor) {
        if (nextDropoff == null || dest < nextDropoff) {
          nextDropoff = dest;
        }
      }
    }

    if (nextPickup == null && nextDropoff == null) return -1;
    if (nextPickup != null && nextDropoff != null) return Math.min(nextPickup, nextDropoff);
    return nextPickup != null ? nextPickup : nextDropoff;
  }

  /**
   * Determina el próximo piso al bajar.
   *
   * @param currentFloor piso actual.
   * @param lift personas dentro del elevador.
   * @param floorQueues personas esperando en pisos.
   * @return el piso más cercano hacia abajo donde debe detenerse.
   */
  private static int getNextDownStop(int currentFloor, List<Integer> lift, List<List<Integer>> floorQueues) {
    Integer nextPickup = null;

    // Buscar personas esperando para bajar más abajo
    for (int i = currentFloor - 1; i >= 0; i--) {
      for (int dest : floorQueues.get(i)) {
        if (dest < i) {
          nextPickup = i;
          break;
        }
      }
      if (nextPickup != null) break;
    }

    // Buscar personas dentro que quieran bajarse más abajo
    Integer nextDropoff = null;
    for (int dest : lift) {
      if (dest < currentFloor) {
        if (nextDropoff == null || dest > nextDropoff) {
          nextDropoff = dest;
        }
      }
    }

    if (nextPickup == null && nextDropoff == null) return -1;
    if (nextPickup != null && nextDropoff != null) return Math.max(nextPickup, nextDropoff);
    return nextPickup != null ? nextPickup : nextDropoff;
  }

  /**
   * Procesa las acciones del elevador al llegar a un piso:
   * - Baja a las personas que llegaron a su destino.
   * - Sube a las personas que van en la dirección del elevador (o a cualquiera si está vacío).
   * - Registra el piso como parada.
   *
   * @param currentFloor piso actual.
   * @param mode dirección actual del elevador.
   * @param lift lista de destinos de personas dentro del elevador.
   * @param floorQueues lista de personas esperando en cada piso.
   * @param capacity capacidad máxima del elevador.
   * @param stops lista de pisos donde el elevador se ha detenido.
   */
  private static void processCurrentFloor(
      int currentFloor,
      Mode mode,
      List<Integer> lift,
      List<List<Integer>> floorQueues,
      int capacity,
      List<Integer> stops
  ) {
    // Registrar parada
    stops.add(currentFloor);

    // Bajar personas cuyo destino es este piso
    Iterator<Integer> it = lift.iterator();
    while (it.hasNext()) {
      if (it.next() == currentFloor) {
        it.remove();
      }
    }

    // Subir personas en el piso actual
    List<Integer> floorQueue = floorQueues.get(currentFloor);
    Iterator<Integer> fqIt = floorQueue.iterator();
    boolean liftIsEmpty = lift.isEmpty();

    while (fqIt.hasNext()) {
      int dest = fqIt.next();

      // Si el ascensor está vacío, puede subir cualquiera
      boolean sameDirection =
        liftIsEmpty ||
        (mode == Mode.UP && dest > currentFloor) ||
        (mode == Mode.DOWN && dest < currentFloor);

      if (sameDirection && lift.size() < capacity) {
        lift.add(dest);
        fqIt.remove();
      }
    }
  }

}
