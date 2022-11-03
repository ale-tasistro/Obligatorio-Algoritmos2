/* Alejandro Tasistro, 269430  */

import Arboles.AVL;
import Utils.Inputs;

public class Ejercicio1 {

    public static void main(String[] args) {
        AVL<Integer> avl = Inputs.inputEj1();
        avl.inOrderPrint();
    }
}
