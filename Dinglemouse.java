import java.util.*;
public class Dinglemouse {
    public static int[] theLift(final int[][] queues, final int capacity) {
        int floors = queues.length;
        //Crear el AL donde iremos guardando el resultado
        ArrayList<Integer> floorsList = new ArrayList<Integer>();
        floorsList.add(0); //El elevador siempre inicia en el piso 0
        
        int direction = 1; //1 va para arriva, -1 para abajo
        int currentFloor = 0;
        int stopsFloor = 0;
        int highestFloorWithPeople = 0;
        int peopleInLift = 0;
        int availability = capacity;
        /*Deacuerdo a las reglas, lo primero que hace el elevador es subir hasta 
        el piso maximo donde hay personas esperando. 
        */
        //Buscar los pisos donde hay personas esperando y cuantas
       HashMap<Integer, List<Integer>> floorsWithPeople = new HashMap<Integer, List<Integer>>();
            for(int i = 0; i < floors; i++){
                // Convert array to list
                List<Integer> people = new ArrayList<>();
                    for(int p : queues[i]) {
                        people.add(p);
                    }
                    floorsWithPeople.put(i, people);
                   if(!people.isEmpty() && i > highestFloorWithPeople) {
                    highestFloorWithPeople = i;
                }
            }
            
            /*for(Map.Entry<Integer, List<Integer>> entry : floorsWithPeople.entrySet()) {
                int floor = entry.getKey();
                List<Integer> people = entry.getValue();
                System.out.println("Floor " + floor + ": " + people.size() + " people waiting - " + people);
            }*/
         
         System.out.println(floorsWithPeople);
        //El piso más alto seria la segunda parada 
        floorsList.add(highestFloorWithPeople);
        //Check every floor with people, we must consider the capacity
       
       
        floorsList.add(0); //El elevador siempre termina en el piso 0
        // Transform from arraylist to int[]
        int[] result = floorsList.stream().mapToInt(i -> i).toArray();
    
        return result;
    }
    public static void main(String args[]) {
        int capacity = 5; 
        int[][] queues = {{},{6,5,2},{4},{},{0,0,0},{},{},{3,6,4,5,6},{},{1,10,2},{1,4,3,2}};
        System.out.println(Arrays.toString(theLift(queues, capacity)));
      }
}
