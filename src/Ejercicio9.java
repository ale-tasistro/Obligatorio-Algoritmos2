/* Alejandro Tasistro, 269430  */

import Lineales.Lista;
import static java.lang.System.out;
import static Utils.Inputs.inputEj9;
import ClasesAuxiliares.Edificios.CambioDeAltura;
import static ClasesAuxiliares.Edificios.avlSort;
import static ClasesAuxiliares.Edificios.edificiosDAC;

public class Ejercicio9 {

    public static void main(String[] args) {
        Lista<CambioDeAltura> silueta = edificiosDAC(inputEj9());
        avlSort(silueta);
        while (!silueta.isEmpty()) {
            CambioDeAltura head = silueta.popHead();
            out.print(head.getLeft() + " " + head.getRight() + "\n");
        }
    }
}
