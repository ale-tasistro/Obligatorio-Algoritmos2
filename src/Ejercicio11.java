/* Alejandro Tasistro, 269430  */

import Utils.Inputs;
import Utils.Utils.Pair;
import Utils.Utils.Triple;
import ClasesAuxiliares.Mochila.MochilaBacktracking;

import static java.lang.System.out;

public class Ejercicio11 {

    public static void main(String[] args) {
        Pair<Triple<Integer>, Triple<Integer[]>> input = Inputs.inputEj10y11();
        // input = ((capS, capL, cantObjs), (pesoS, pesoL, valor))
        MochilaBacktracking mb = new MochilaBacktracking(input);
        out.print(mb.mochila());
    }
}
