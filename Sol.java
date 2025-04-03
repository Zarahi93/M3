class Sol{
    public static int[] solve(int a, int b) {
         if (a == 0 || b == 0) {
             return setArr(a, b);
         } else if (a >= 2 * b) {
             a = a - 2 * b;
             return setArr(a, b);
         } else if (b >= 2 * a) {  // Changed condition from b >= a to b >= 2 * a
             b = b - 2 * a;
             return setArr(a, b);
         }
         // Add a default return statement
         return setArr(a, b);
     }
 
     public static int[] setArr(int a, int b) {
         int[] solution = new int[2];
         solution[0] = a;
         solution[1] = b;
         return solution;
     }
     public static void main(String[] args){
        for(int num: solve(6, 7)){
            System.out.println(num);
        }
        
     }
 }