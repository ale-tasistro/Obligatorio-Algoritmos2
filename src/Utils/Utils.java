package Utils;

/* Alejandro Tasistro, 269430  */

import java.util.Arrays;
import ColasDePrioridad.ColaDePrioridad;

/* clase para funciones y clases auxiliares */
public class Utils {

    /* CLASES */

    /* clase que implementa duplas */
    public static class Pair<T1, T2> {

        protected T1 left;
        protected T2 right;

        public Pair(T1 leftInfo, T2 rightInfo) {
            this.left = leftInfo;
            this.right = rightInfo;
        }

        public void setLeft(T1 left) { this.left = left; }

        public void setRight(T2 right) { this.right = right; }

        public T1 getLeft() { return left; }

        public T2 getRight() { return right; }

        @Override
        public String toString() {
            return "Pair{ " +
                    "left = " + left +
                    ", right = " + right +
                    " }";
        }
    }

    /* clase para simplificar duplas de un solo tipo */
    public static class STPair<T> extends Pair<T, T> {
        public STPair(T leftInfo, T rightInfo) { super(leftInfo, rightInfo); }
    }

    /* clase que extiende Pair */
    public static class KeyValuePair<K extends Comparable<K>, V>
            extends Pair<K,V> {

        public KeyValuePair(K key, V value) { super(key, value); }

        public int keyCompare(KeyValuePair<K,V> pair) { return this.left.compareTo(pair.left); }

        public K key() { return super.getLeft(); }

        public V value() { return super.getRight(); }

        public void updateValue(V newValue) { this.right = newValue; }
    }

    /* clase que implementa tripletas de un solo tipo */
    public static class Triple<T> {

        public final T first;
        public final T second;
        public final T third;

        public Triple(T firstInfo, T secondInfo, T thirdInfo) {
            this.first = firstInfo;
            this.second = secondInfo;
            this.third = thirdInfo;
        }


        @Override
        public String toString() {
            return "Triple{ " +
                    "first = " + first +
                    ", second = " + second +
                    ", third = " + third +
                    " }";
        }
    }

    /* ------------------------------------------------------------------------------------ */

    /* FUNCIONES DE ENTEROS */

    /* funcion para determinar si un entero >= 0 es primo */
    public static boolean isPrime(int n) { // O(n^(1/2))
        if (n < 2) return false;
        boolean foundDiv = false;
        int i = 2;
        int sqrtN = (int) Math.floor(Math.sqrt(n));
        while (i <= sqrtN && !foundDiv) {
            foundDiv = n % i == 0;
            i++;
        }
        return !foundDiv;
    }

    /* funcion que dado un numero n retorna el siguiente primo m tal que m >= n */
    public static int nextPrime(int n) {
        if (isPrime(n)) return n;
        else {
            int i = n+1;
            while (!isPrime(i)) i++;
            return i;
        }
    }

    /* funcion que retorna la sumatoria de los elementos de array */
    public static int sumIntArray(Integer[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) sum += array[i];
        return sum;
    }

    /* ------------------------------------------------------------------------------------ */

    /* FUNCIONES DE MATRICES */

    public static STPair<Integer> arriba(int i, int j) {
        return new STPair<>(i - 1, j);
    }

    public static STPair<Integer> abajo(int i, int j) {
        return new STPair<>(i + 1, j);
    }

    public static STPair<Integer> izquierda(int i, int j) {
        return new STPair<>(i, j - 1);
    }

    public static STPair<Integer> derecha(int i, int j) {
        return new STPair<>(i, j + 1);
    }

    public static STPair<Integer>[] vecinos(int i, int j) {
        return new STPair[] {arriba(i, j), abajo(i, j), izquierda(i, j), derecha(i, j)};
    }

    public static boolean isValidCoord(STPair<Integer> coord, int filas, int columnas) {
        return (0 <= coord.getLeft() && coord.getLeft() < filas)
                && (0 <= coord.getRight() && coord.getRight() < columnas);
    }

    /* ------------------------------------------------------------------------------------ */

    /* FUNCIONES DE T =< COMPARABLE */

    /* funcion que dado un array no vacio devuelve la coordenada del minimo */
    // PRE: array.length > 0
    public static <T extends Comparable<T>> int arrayMinCoord(T[] array) {
        int res = 0;
        T min = array[0];
        for (int i = 0; i < array.length; i++)
            if (array[i].compareTo(min) < 0) {
                min = array[i];
                res = i;
            }
        return res;
    }

    /* funcion que dado un array no vacio devuelve el minimo */
    // PRE: array.length > 0
    public static <T extends Comparable<T>> T arrayMin(T[] array) {
        T min = array[0];
        for (T t : array)
            if (t.compareTo(min) < 0) {
                min = t;
            }
        return min;
    }

    /* funcion que devuelve el maximo de dos T =< Comparable */
    public static <T extends Comparable<T>> T max(T a, T b) { return a.compareTo(b) > 0 ? a : b; }

    /* funcion que devuelve el minimo de dos T =< Comparable */
    public static <T extends Comparable<T>> T min(T a, T b) { return a.compareTo(b) < 0 ? a : b; }

    /* funcion que ordena un array de T =< Comparable de menor a mayor*/
    public static <T extends Comparable<T>> void sortArray(T[] array) {
        ColaDePrioridad<T, T> colaDePrioridad = new ColaDePrioridad<>(array.length);
        for (T t : array) colaDePrioridad.add(t, t);
        int i = 0;
        while (!colaDePrioridad.isEmpty()) {
            array[i] = colaDePrioridad.popFirst();
            i++;
        }
    }

    /* ------------------------------------------------------------------------------------ */

    /* FUNCIONES DE ARRAYS */

    /* funcion que retorna true si array contiene item y false si no */
    public static <T extends Comparable<T>> boolean contains(T item, T[] array) {
        boolean cont = false;
        for (int i = 0; (i < array.length) && !cont; i++)
            cont = array[i] == item;
        return cont;
    }

    /* funcion que devuelve un par con la primer mitad de array en left y la segunda en right */
    public static <T> STPair<T[]> splitInHalf(T[] array) {
        if (array.length < 2) return new STPair<>((T[]) new Object[0], array);
        boolean parLength = array.length % 2 == 0;
        int halfLength = array.length/2;
        T[] firstHalf;
        T[] secondHalf;
        firstHalf = Arrays.copyOfRange(array, 0, halfLength-1);
        secondHalf = Arrays.copyOfRange(array, halfLength, array.length-1);
        return new STPair<>(firstHalf, secondHalf);
    }
}