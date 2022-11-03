/* Alejandro Tasistro, 269430  */

import Utils.Utils;
import Utils.Utils.Pair;
import Grafos.GrafoListas;
import Utils.Inputs.GrInput;
import static java.lang.System.out;

public class Ejercicio6 {

    public static void main(String[] args) {
        GrafoListas grafoListas = new GrafoListas(new GrInput(false, true));
        Pair<Boolean[], Integer[]> prim = grafoListas.prim(grafoListas.getV()); // <visitados, costos>
        out.print(Utils.contains(false, prim.getLeft()) ? -1 : Utils.sumIntArray(prim.getRight()));
    }
}
