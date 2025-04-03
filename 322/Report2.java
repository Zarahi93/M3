import java.io.*;
import java.util.*;


public class Report2 {
    public static void main(String args[]) {
        String file = "input.txt";
        List<Integer> input = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(file))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine().trim();
                try {
                    int numero = Integer.parseInt(linea);
                    input.add(numero);
                } catch (NumberFormatException e) {
                    System.err.println("Línea ignorada (no es un número válido): " + linea);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Archivo no encontrado - " + e.getMessage());
        }
        Collections.sort(input);
        int result = 0;
        for(int i = 0; i < input.size()-2; i++){
            int left = i+1;
            int right = input.size() - 1;
            while (left < right) {
                int sum = input.get(i) + input.get(left) + input.get(right);
                if (sum == 2020) {
                    result = input.get(i) * input.get(left) * input.get(right);
                    break;
                } else if (sum < 2020) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        System.out.println(result);
    }
}
