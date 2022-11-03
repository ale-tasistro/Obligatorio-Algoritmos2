/* Alejandro Tasistro, 269430  */

import Grafos.GrafoListas;
import Utils.Inputs.GrInput;
import Utils.Utils.STPair;
import static java.lang.System.out;
import static Utils.Inputs.inputEj5;
import ColasDePrioridad.ColaDePrioridad;

public class Ejercicio5 {

    public static void main(String[] args) {
        GrafoListas grafoListas = new GrafoListas(new GrInput(true, true));
        ColaDePrioridad<Integer, Integer> vertices = inputEj5();
        while (!vertices.isEmpty()) {
            int current = vertices.popFirst();
            STPair<Integer[]> currDijkstra = grafoListas.dijkstra(current); // <vengo, costos>
            Integer[] vengo = currDijkstra.getLeft();
            Integer[] costos = currDijkstra.getRight();
            for (int j = 1; j < vengo.length; j++) out.print((vengo[j] > 0 ? costos[j] : "-1") + "\n");
        }
    }
}
