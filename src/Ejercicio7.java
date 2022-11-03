/* Alejandro Tasistro, 269430  */

import static java.lang.System.out;
import static Utils.Inputs.inputEj7;
import ClasesAuxiliares.PasesMatriz;

public class Ejercicio7 {

    public static void main(String[] args) {
        Integer[][] matriz = inputEj7();
        out.print(PasesMatriz.pasesMatriz(matriz));
    }
}
