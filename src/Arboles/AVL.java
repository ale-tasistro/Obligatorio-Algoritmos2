package Arboles;

/* Alejandro Tasistro, 269430  */

import Lineales.Lista;
import Utils.Utils;
import Utils.Utils.Pair;


public class AVL<T extends Comparable<T>> extends ABB<T> {

    private static final Integer[] BALANCEADO_ARRAY = {-1, 0, 1};

    public boolean isDisbalanced(Nodo<T> nodo) {
        return !Utils.contains(nodo.getBalanceFactor(), BALANCEADO_ARRAY);
    }


                                                    /* CONSTRUCTORES */

    public AVL() {
        super();
    }

                                                        /* ADD */

    @Override
    public void add(T unInfo) {
        this.raiz = addAVL(unInfo, raiz);
    }

    private Nodo<T> addAVL(T unInfo, Nodo<T> nodo) {
        if (nodo == null) {
            nodo = new Nodo<>(unInfo);
        } else {
            int comp = unInfo.compareTo(nodo.info);
            if (comp == 0) nodo.increaseCont();
            else {
                if (comp < 0) nodo.left = addAVL(unInfo, nodo.left);
                else nodo.right = addAVL(unInfo, nodo.right);
                nodo.updateHeight();
                if (isDisbalanced(nodo)) {
                    int balance = nodo.getBalanceFactor();

                    // DERECHA
                    // derecha - derecha
                    if (balance > 1 && nodo.right.info.compareTo(unInfo) < 0) {
                        counterCwTurn(nodo);
                    }
                    // derecha - izquierda
                    else if (balance > 1) {
                        nodo.right = clockwiseTurn(nodo.right);
                        counterCwTurn(nodo);
                    }
                    // IZQUIERDA
                    // izquierda - izquierda
                    if (balance < -1 && nodo.left.info.compareTo(unInfo) > 0) {
                        clockwiseTurn(nodo);
                        // izquierda - derecha
                    } else if (balance < -1) {
                        nodo.left = counterCwTurn(nodo.left);
                        clockwiseTurn(nodo);
                    }
                }
            }
        }
        return nodo;
    }

                                                     /* RECORRER */

    public void inOrderPrint() {
        Lista<Pair<T, Integer>>.Iterador it = inOrder(raiz).iterator();
        while (!it.isNull()) {
            for (int i = 0; i < it.info().getRight(); i++)
                System.out.println(it.info().getLeft());
            it.next();
        }
    }

    public Lista<Pair<T, Integer>> inOrderLista() {
        return inOrder(raiz);
    }

    private Lista<Pair<T, Integer>> inOrder(Nodo<T> node) {
        if (node != null) {

            Lista<Pair<T, Integer>> res = new Lista<>();
            res.append(inOrder(node.left));
            Pair<T, Integer> pair = new Pair<>(node.info, node.getCont());
            res.addBehind(pair);
            res.append(inOrder(node.right));
            return res;
        } else {
            return new Lista<>();
        }
    }

                                                    /* BALANCEAR */

    private Nodo<T> clockwiseTurn(Nodo<T> nodo) {
        Nodo<T> hijo = nodo.left;
        Nodo<T> nieto = hijo.right;

        // Perform rotation
        hijo.right = nodo;
        nodo.left = nieto;

        // Update heights
        nodo.updateHeight();
        hijo.updateHeight();

        // Return new root
        return hijo;
    }

    private Nodo<T> counterCwTurn(Nodo<T> nodo) {
        Nodo<T> hijo = nodo.right;
        Nodo<T> nieto = hijo.left;

        // Perform rotation
        hijo.left = nodo;
        nodo.right = nieto;

        // Update heights
        nodo.updateHeight();
        hijo.updateHeight();

        // Return new root
        return hijo;
    }
}
