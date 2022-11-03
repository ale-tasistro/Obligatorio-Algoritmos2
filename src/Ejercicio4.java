/* Alejandro Tasistro, 269430  */

import Utils.Inputs;
import Grafos.GrafoListas;
import static java.lang.System.out;

public class Ejercicio4 {

    public static void main(String[] args) {
        GrafoListas grafoListas = new GrafoListas(new Inputs.GrInput(true, false));
        out.print(grafoListas.tieneCiclos() ? "1" : "0");
    }
}
