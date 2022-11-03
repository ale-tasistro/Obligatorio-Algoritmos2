package ClasesAuxiliares;

/* Alejandro Tasistro, 269430  */

import Utils.Utils.STPair;
import static Utils.Utils.vecinos;
import static Utils.Utils.isValidCoord;

public class PasesMatriz {

    public static int pasesMatriz(Integer[][] matriz) {
        // inicialización de matriz, y declaración de banderas
        int filas = matriz.length;
        int columnas = (filas == 0) ? 0 : matriz[0].length;
        Boolean[][] cambios = new Boolean[filas][columnas];
        boolean huboCambios;
        int cont = 0;
        // Algoritmo:
        /* Se recorre la matriz, para cada valor positivo que no haya
            sido cambiado en esta pasada se toman sus vecinos negativos
            y se los invierte. Esto se hace iterativamente hasta haber
            una recorrida en que ninguna celda cambia de valor          */
        do {
            // inicialización de banderas:
            for (int i = 0; i < cambios.length; i++)
                for (int j = 0; j < cambios[i].length; j++)
                    cambios[i][j] = false;
            huboCambios = false;
            // recorriendo la matriz
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    // se procesan los valores positivos no cambiados en esta pasada
                    if (matriz[i][j] > 0 && !cambios[i][j]) {
                        STPair<Integer>[] vecinos = vecinos(i, j);
                        // se recorren los vecinos
                        for (STPair<Integer> vecino : vecinos) {
                            // se procesan los vecinos válidos
                            if (isValidCoord(vecino, filas, columnas)) {
                                int vecinoi = vecino.getLeft();
                                int vecinoj = vecino.getRight();
                                // se cambia el valor de los negativos y se los marca como cambiados
                                if (matriz[vecinoi][vecinoj] < 0) {
                                    matriz[vecinoi][vecinoj] *= -1;
                                    cambios[vecinoi][vecinoj] = true;
                                    // se cambia la bandera marcar que hubo cambios
                                    huboCambios = true;
                                }
                            }
                        }
                    }
                }
            }
            if (huboCambios) cont++;
        } while (huboCambios);
        return cont;
    }
}
