package ClasesAuxiliares;

/* Alejandro Tasistro, 269430  */

import Utils.Utils.Pair;
import Utils.Utils.Triple;

import static Utils.Utils.max;

public class Mochila {

    public static class MochilaTabulacion {

        public static Integer[][][] mochila(Pair<Triple<Integer>, Triple<Integer[]>> input) {
            // input = ((capS, capL, cantObjs), (pesoS, pesoL, valor))
            Integer[] pesoS = input.getRight().first;
            Integer[] pesoL = input.getRight().second;
            Integer[] valor = input.getRight().third;
            int capacidadS = input.getLeft().first;
            int capacidadL = input.getLeft().second;
            int cantidadObjetos = input.getLeft().third;
            Integer[][][] matriz = new Integer[cantidadObjetos][capacidadS][capacidadL];
            // Algoritmo:
        /* Se construye la tabla de abajo hacia arriba,
            comienza con únicamente el objeto 0, capacidadS = 0, y capacidadL = 0
            luego, a medida que sube, construye el resultado de cada nueva celda
            a partir de los valores guardados en las anteriores, y el del objeto actual */
            for (int objActual = 0; objActual < cantidadObjetos; objActual++) { // recorriendo array de objetos
                for (int capSActual = 0; capSActual < capacidadS; capSActual++) { // recorriendo capacidadesS
                    for (int capLActual = 0; capLActual < capacidadL; capLActual++) { // recorriendo capacidadesL
                        int objPrev = objActual - 1;
                        // solo se consideran objetos que entran con las capacidades actuales
                        if ((pesoS[objActual] <= capSActual) && (pesoL[objActual] <= capLActual)) {
                        /* Se compara el valor de usar el objeto actual con el valor de no usarlo
                                (o sea, el valor del objeto actual sumado al valor de la mochila con
                                capacidadS -= pesoS[obj. actual] y capacidadL -= pesoS[obj. actual],
                                con el valor de la matriz en el objeto anterior).
                           En el caso del objeto 0 el valor anterior es 0.
                           Se guarda en la matriz el mayor de los valores comparados.             */
                            int valorObjPrev = 0;
                            int valorUsando = valor[objActual];
                            if (objActual != 0) {
                            int capSUsando = capSActual - pesoS[objActual]; // >= 0
                            int capLUsando = capLActual - pesoL[objActual]; // >= 0
                                valorUsando += matriz[objPrev][capSUsando][capLUsando];
                                valorObjPrev = matriz[objPrev][capSActual][capLActual];
                            }
                            matriz[objActual][capSActual][capLActual] = max(valorUsando, valorObjPrev);
                        } else {
                            matriz[objActual][capSActual][capLActual] =
                                    (objActual == 0) ? Integer.valueOf(0)
                                            : matriz[objPrev][capSActual][capLActual];
                        }
                    }
                }
            }
            return matriz;
        }
    }

    public static class MochilaBacktracking {

        public MochilaBacktracking(Pair<Triple<Integer>, Triple<Integer[]>> input) {
            // input = ((capS, capL, cantObjs), (pesoS, pesoL, valor))
            CAPACIDAD_S = input.getLeft().first;
            CAPACIDAD_L = input.getLeft().second;
            CANTIDAD_OBJS = input.getLeft().third;
            pesoS = input.getRight().first;
            pesoL = input.getRight().second;
            valor = input.getRight().third;
            currS = CAPACIDAD_S;
            currL = CAPACIDAD_L;
        }

        private final Integer[] pesoS;
        private final Integer[] pesoL;
        private final Integer[] valor;
        private final int CAPACIDAD_S;
        private final int CANTIDAD_OBJS;
        private final int CAPACIDAD_L;

        private int currS;
        private int currL;
        private int currVal = 0;
        private int maxVal = 0;

        public int mochila() {
            backtracking(0);
            return maxVal;
        }

        private void backtracking(int currObj) {
            for(int i = currObj; i < CANTIDAD_OBJS; i++) {
                if (puedoPoner(i)) {
                    pongo(i);
                    if (currVal > maxVal)
                        maxVal = currVal;
                    backtracking(i + 1);
                    saco(i);
                }
            }
        }

        private boolean puedoPoner(int currObj) {
            return currObj < CANTIDAD_OBJS
                    && pesoS[currObj] <= currS
                    && pesoL[currObj] <= currL;
        }

        private void pongo(int currObj) {
            currVal +=  valor[currObj];
            currS -=  pesoS[currObj];
            currL -= pesoL[currObj];
        }

        private void saco(int currObj) {
            currVal -=  valor[currObj];
            currS +=  pesoS[currObj];
            currL += pesoL[currObj];
        }
    }
}
