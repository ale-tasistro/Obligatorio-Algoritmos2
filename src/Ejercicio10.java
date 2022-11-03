/* Alejandro Tasistro, 269430  */

import Utils.Inputs;
import Utils.Utils.Pair;
import Utils.Utils.Triple;
import ClasesAuxiliares.Mochila.MochilaTabulacion;

import static java.lang.System.out;

public class Ejercicio10 {

    public static void main(String[] args) {
        Pair<Triple<Integer>, Triple<Integer[]>> input = Inputs.inputEj10y11();
        // input = ((capS, capL, cantObjs), (pesoS, pesoL, valor))
        Integer[][][] matriz = MochilaTabulacion.mochila(input);
        Triple<Integer> cantidades = input.getLeft();
        out.print(matriz[cantidades.third-1][cantidades.first-1][cantidades.second-1]);
    }
}
