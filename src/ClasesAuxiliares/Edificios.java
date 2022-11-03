package ClasesAuxiliares;

/* Alejandro Tasistro, 269430  */

import Arboles.AVL;
import Lineales.Lista;
import Utils.Utils.Pair;
import Utils.Utils.Triple;
import static Utils.Utils.min;
import static Utils.Utils.max;
import Utils.Utils.STPair;
import Utils.Utils.KeyValuePair;

import static java.lang.Integer.MAX_VALUE;


public class Edificios {

    public static class CambioDeAltura extends KeyValuePair<Integer, Integer>
            implements Comparable<CambioDeAltura> {

        public CambioDeAltura(Integer key, Integer value) {
            super(key, value);
        }

        @Override
        public int compareTo(CambioDeAltura that) {
            return this.key().compareTo(that.key());
        }
    }

    /* funcion recursiva que procesa arrays de tamaño 1 */
    public static Lista<CambioDeAltura> edificiosDAC(Lista<Triple<Integer>> edificios) { //O(N log N)
        if (edificios.length() == 0) return new Lista<>();               // O(1)
        if (edificios.length() == 1) return siluetaEdificio(edificios.head()); // O(1)

        STPair<Lista<Triple<Integer>>> split = splitListaInHalf(edificios); // O(N)
        Lista<CambioDeAltura> recCallLeft = edificiosDAC(split.getLeft()); // O(N/2)
        Lista<CambioDeAltura> recCallRight = edificiosDAC(split.getRight()); // O(N/2)
        return mergeSiluetas(recCallLeft, recCallRight); // O(N)
    }

    // O(1)
    /* funcion para convertir un edificio de tripleta a dos pares */
    private static Lista<CambioDeAltura> siluetaEdificio(Triple<Integer> edificio) { // O(1)
        CambioDeAltura primero = new CambioDeAltura(edificio.first, edificio.third);
        CambioDeAltura segundo = new CambioDeAltura(edificio.second, 0);
        Lista<CambioDeAltura> res = new Lista<>();
        res.addBehind(primero);
        res.addBehind(segundo);
        return res;
    }

    // retorna dos listas correspondientes a la primera y segunda mitad
    // de una lista de tripletas de enteros
    private static STPair<Lista<Triple<Integer>>> splitListaInHalf( // O(N)
            Lista<Triple<Integer>> lista) {
        Lista<Triple<Integer>> copia = lista.copyList();
        int largo = lista.length();
        Lista<Triple<Integer>> primera = new Lista<>();
        Lista<Triple<Integer>> segunda = new Lista<>();
        for (int i = 0; i < largo; i++)
            if (i < largo / 2)
                primera.addBehind(copia.popHead());
            else
                segunda.addBehind(copia.popHead());

        return new STPair<>(primera, segunda);
    }

    // O(N)
    /* funcion para mergear dos arrays de siluetas expresadas como pares */
    private static Lista<CambioDeAltura> mergeSiluetas( // O(N), N = izq.length() + der.length()
            Lista<CambioDeAltura> izq, Lista<CambioDeAltura> der) {
        // Indices para ir recorriendo los arrays
        Lista<CambioDeAltura>.Iterador indiceIzq = izq.iterator();
        Lista<CambioDeAltura>.Iterador indiceDer = der.iterator();
        // Referencias para no agregar falsos puntos a la silueta
        int ultimaAltura = 0;
        int ultimaAlturaIzq = 0;
        int ultimaAlturaDer = 0;
        // Lista
        Lista<CambioDeAltura> lista = new Lista<>();

        // Algoritmo
        while (!indiceIzq.isNull() || !indiceDer.isNull()) { // O(N), N = finIzq.length+finDer.length

            /* Información del par que corresponde en cada array,
               si alguno de los arrays se terminó de recorrer
                se pone x = MAX_VALUE y altura = 0 para que no incida */
            int xIzq;
            int hIzq;
            if (!indiceIzq.isNull()) {
                xIzq = indiceIzq.info().getLeft();
                hIzq = indiceIzq.info().getRight();
            } else {
                ultimaAlturaIzq = 0;
                xIzq = MAX_VALUE;
                hIzq = 0;
            }

            int xDer;
            int hDer;
            if (!indiceDer.isNull()) {
                xDer = indiceDer.info().getLeft();
                hDer = indiceDer.info().getRight();
            } else {
                ultimaAlturaDer = 0;
                xDer = MAX_VALUE;
                hDer = 0;
            }

            /* Se comparan xIzq y xDer,
                si son iguales se avanzan ambos índices y se actualizan ambas alturas,
                si no se avanza el indice y se actualiza la altura del menor */
            if (xIzq <= xDer) {
                ultimaAlturaIzq = hIzq;
                indiceIzq.next();
            }
            if (xDer <= xIzq) {
                ultimaAlturaDer = hDer;
                indiceDer.next();
            }

            /* Se guarda el minimo de xIzq y de xDer, y la altura maxima de izquierda y derecha,
                si alturaTentativa es distinta de ultimaAltura se actualiza el valor de ultimaAltura
                 y se agrega (xTentativo, alturaTentativa) a la lista */
            int xTentativo = min(xIzq, xDer);
            int alturaTentativa = max(ultimaAlturaIzq, ultimaAlturaDer);
            if (alturaTentativa != ultimaAltura) {
                ultimaAltura = alturaTentativa;
                lista.addBehind(new CambioDeAltura(xTentativo, alturaTentativa));
            }
        }

        return lista;
    }

                                            /* SORT LISTA */

    public static void avlSort(Lista<CambioDeAltura> lista) { // O(N log N)
        AVL<CambioDeAltura> avl = new AVL<>();
        while (!lista.isEmpty()) avl.add(lista.popHead());
        Lista<Pair<CambioDeAltura, Integer>> aux = avl.inOrderLista();
        while (!aux.isEmpty()) {
            int cantReps = aux.head().getRight();
            for (int i = 0; i < cantReps; i++) {
                lista.addBehind(aux.head().getLeft());
            }
            aux.popHead();
        }
    }

}
