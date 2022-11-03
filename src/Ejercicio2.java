/* Alejandro Tasistro, 269430  */

import Utils.Inputs;
import Lineales.Lista;
import Tablas.TablaHashStringInt;
import static java.lang.System.out;


public class Ejercicio2 {

    public static void main(String[] args) {
        TablaHashStringInt tabla = Inputs.inputEj2(); // O(N)
        Lista<String> keys = tabla.getKeys();
        Lista<String>.Iterador iter = keys.iterator();
        int cont = 0;
        while (!iter.isNull()) {
            String key = iter.info();
            int cant = tabla.fetch(key).get();
            if (cant == 2) cont++;
            iter.next();
        }
        out.print(cont);
    }
}
