package ColasDePrioridad;

/* Alejandro Tasistro, 269430  */

import Utils.Utils.KeyValuePair;


public class ColaDePrioridad<K extends Comparable<K>, V> {

    private class Heap {
        private final KeyValuePair<K, V>[] array;
        private final int capacidad;
        private int next = 1;
        private int last = 0;

                /* CONSTRUCTOR */

        public Heap(int unaCapacidad) {
            capacidad = unaCapacidad;
            array = (KeyValuePair<K,V>[]) new KeyValuePair[capacidad+1];
        }

                /* QUERIES */

        public boolean isEmpty() {
            return next == 1;
        }

        public boolean isFull() {
            return last == capacidad;
        }

        // PRE: !isEmpty()
        public V rootValue() {
            return array[1].value();
        }

        public K rootKey() {
            return array[1].key();
        }

        public int size() {
            return last;
        }

        public int getCapacidad() {
            return capacidad;
        }

                /* ADD */

        public boolean add(V value, K key) {
            if (this.isFull()) return false;
            array[next] = new KeyValuePair<>(key, value);
            next++; last++;
            flotar(last);
            return true;
        }

        private void flotar(int indice) {
            if (indice > 1) {
                int padre = indice/2;
                // si no satisface la propiedad de orden:
                if (array[padre].keyCompare(array[indice]) > 0) {
                    swap(padre, indice);
                    flotar(padre);
                }
            }
        }

                /* REMOVE */

        // PRE: !this.isEmpty()
        public V popRoot() {
            V first = array[1].value();
            array[1] = array[last];
            next--; last--;
            hundir(1);
            return first;
        }

        private void hundir(int indice) {
            int hijoDer = indice*2 + 1;
            int hijoIzq = indice*2;
            // si tengo los dos hijos y soy más grande que alguno
            if (hijoDer < next && (
                    array[indice].keyCompare(array[hijoDer]) > 0
                            || array[indice].keyCompare(array[hijoIzq]) > 0
            )) {
                // swapeo con el indice del minimo hijo (a > b || a > c => a > min(b, c))
                int hijosComp = array[hijoDer].keyCompare(array[hijoIzq]);
                int minIndex = (hijosComp < 0) ? hijoDer : hijoIzq;
                swap(indice, minIndex);
                // sigo hundiendo con el nuevo índice
                hundir(minIndex);
            // si no, si tengo que hijo izquierdo y soy más grande que él
            } else if (hijoIzq < next
                    && array[indice].keyCompare(array[hijoIzq]) > 0) {
                // swapeo con él
                swap(indice, hijoIzq);
                // sigo hundiendo con el hijo izquierdo
                hundir(hijoIzq);
            }
        }

        private void swap(int indice1, int indice2) {
            KeyValuePair<K, V> pivot = array[indice1];
            array[indice1] = array[indice2];
            array[indice2] = pivot;
        }


        public void imprimirArray() {
            for (int i = 1; i < next; i++)
                System.out.print(array[i]);
        }
    }

    private final Heap heap;


                    /* CONSTRUCTOR */

    public ColaDePrioridad(int capacidad) {
        heap = new Heap(capacidad);
    }


                     /* QUERIES */

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public boolean isFull() {
        return heap.isFull();
    }

    public int size() {
        return heap.size();
    }

    public int capacity() {
        return heap.getCapacidad();
    }

    public V first() {
        return heap.rootValue();
    }

    public K lowestPriority() {
        return heap.rootKey();
    }

                    /* ADD */

    public boolean add(V item, K prioridad) {
        return heap.add(item, prioridad);
    }

                  /* REMOVE */

    public V popFirst() {
        return heap.popRoot();
    }

        /* IMPRIMIR A LO BESTIA EL HEAP */

    public void imprimirArray() {
        heap.imprimirArray();
    }



}
