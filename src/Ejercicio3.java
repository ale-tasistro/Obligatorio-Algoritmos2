/* Alejandro Tasistro, 269430  */

import Lineales.Lista;
import static java.lang.System.out;
import ColasDePrioridad.ColaDePrioridad;

import static Utils.Inputs.inputEj3;

public class Ejercicio3 {

    public static void main(String[] args) {
        ColaDePrioridad<Integer,Lista<Integer>> cp = inputEj3();
        while (!cp.isEmpty()) { // P log K
            Lista<Integer> lista = cp.popFirst(); // log P
            int head = lista.popHead(); // 1
            if (!lista.isEmpty()) cp.add(lista, lista.head()); // log P peor caso
            out.print(head + "\n"); //1
        }
    }
}
