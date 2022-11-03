/* Alejandro Tasistro, 269430  */

import Grafos.GrafoListas;
import Utils.Inputs.GrInput;
import static java.lang.System.out;

public class Ejercicio8 {

    public static void main(String[] args) {
        GrafoListas grafoListas = new GrafoListas(new GrInput(false, false));
        out.print(grafoListas.cantComponentesConexas());
    }
}
