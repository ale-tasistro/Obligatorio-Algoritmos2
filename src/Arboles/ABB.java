package Arboles;

/* Alejandro Tasistro, 269430  */

import static Utils.Utils.max;
import static java.lang.System.out;

public class ABB<T extends Comparable<T>> {

    /* NODO */
    protected static class Nodo<T extends Comparable<T>> {

        protected T info;
        protected Nodo<T> left;
        protected Nodo<T> right;
        protected int height = 1;
        private int cont = 1;

        public Nodo(T unInfo) {
            this.info = unInfo;
            this.left = null;
            this.right = null;
        }

        public boolean isLeaf() { return ((left == null) && (right == null)); }

        public int updateHeight() {
            if (this.isLeaf())
                return 1;
            else {
                int rightH = this.right != null ? this.right.height : 0;
                int leftH = this.left != null ? this.left.height : 0;
                return (1 + max(rightH, leftH));
            }
        }

        public int getBalanceFactor() {
            if (this.isLeaf()) return 0;
            else if (this.left == null)
                return this.right.height;
            else if (this.right == null)
                return this.left.height;
            else return this.right.height - this.left.height;
        }

        public void decreaseCont() {
            cont--;
        }

        public void increaseCont() {
            cont++;
        }

        public int getCont() {
            return cont;
        }
    }

    /* ATRIBUTOS */

    protected Nodo<T> raiz;

    /* CONSTRUCTORES */

    public ABB() { raiz = null; }

    public ABB(Nodo<T> unRoot) { raiz = unRoot; }

    /* QUERIES */

    public boolean isEmpty() { return raiz == null; }

    public int size() {
        if (this.isEmpty()) return 0;
        else return (1 + this.left().size() + this.right().size());
    }

    public int height() {
        if (this.isEmpty()) return 0;
        else return this.raiz.height;
    }

    // PRE: !this->isEmpty()
    public T root() { return raiz.info; }

    // PRE: !this->isEmpty()
    public ABB<T> left() { return new ABB<>(raiz.left); }

    // PRE: !this->isEmpty()
    public ABB<T> right() { return new ABB<>(raiz.right); }

    public boolean contains(T unInfo) {
        // todo: iterador
        if (this.isEmpty()) return false;
        else {
            Nodo<T> i = this.raiz;
            int comp;
            do {
                comp = unInfo.compareTo(i.info);
                if (comp > 0) i = i.right;
                else if (comp < 0) i = i.left;
            } while (i != null && comp != 0);
            return comp == 0;
        }
    }

    /* METODOS */

    /* ADD */

    public void add(T unInfo) { this.raiz = add(unInfo, raiz); }

    /* Metodo auxiliar para ingresar datos a un abb */
    private Nodo<T> add(T unInfo, Nodo<T> n) {
        if (n == null) {
            n = new Nodo<>(unInfo);
        } else {
            int comp = unInfo.compareTo(n.info);
            if (comp == 0) n.increaseCont();
            else {
                if (comp < 0) n.left = add(unInfo, n.left);
                else n.right = add(unInfo, n.right);
                n.updateHeight();
            }
        }
        return n;
    }

    /* REMOVE */

    /* funcion auxiliar para popRoot y makeEmpty
        popea el maximo del subarbol de raiz nodo
        en padre se pasa el padre de nodo
        si padre es null, nodo se asume que es la raiz */
    // PRE: nodo != null
    private T popMax(Nodo<T> nodo, Nodo<T> padre, boolean isRightChild) {
        if (nodo.right == null) { // nodo es el max
            T res = nodo.info;
            if (padre != null)
                if (isRightChild) padre.right = nodo.left;
                else padre.left = nodo.left;
            else
                this.raiz = nodo.left;
            return res;
        } else {
            return popMax(nodo.right, nodo, true);
        }
    }

    // PRE: !this.isEmpty()
    public T popRoot() {
        T res = this.root();
        if (this.raiz.left == null)
            this.raiz = this.raiz.right;
        else this.raiz.info = popMax(this.raiz.left, this.raiz, false);
        return res;
    }

    public void removeRoot() { if (!this.isEmpty()) this.popRoot(); }

    /* Metodo auxiliar para remover datos de un abb
        en padre se pasa el padre de origen
        si padre es null, origen se asume que es la raiz */
    private boolean remove(T unInfo, Nodo<T> nodo, Nodo<T> padre, boolean isRightChild) {
        if (nodo == null) return false;
        else {
            // comparando
            int comp = unInfo.compareTo(nodo.info);
            // llamada recursiva segun comp
            if (comp < 0) return remove(unInfo, nodo.left, nodo, false);
            else if (comp > 0) return remove(unInfo, nodo.right, nodo, true);
                // caso ==
            else {
                if (padre == null) popRoot();
                else if (nodo.left != null) nodo.info = popMax(nodo.left, nodo, false);
                else if (isRightChild) padre.right = nodo.right;
                else padre.left = nodo.right;
                return true;
            }
        }
    }

    public boolean remove(T info) {
        //rightChild no incide con padre == null
        return remove(info, raiz, null, true);
    }

    public void makeEmpty() {
        while (!this.isEmpty()) {
            //rightChild no incide con padre == null
            popMax(raiz, null, true);
        }
    }

    /* READ-ONLY */

    /* Metodo auxiliar para copiar datos de un abb */

    private Nodo<T> copyNodo(Nodo<T> origen) {
        if (origen == null) return null;
        else {
            Nodo<T> copia = new Nodo<>(origen.info);
            copia.left = copyNodo(origen.left);
            copia.right = copyNodo(origen.right);
            return copia;
        }
    }

    public ABB<T> copyTree() {
        if (this.isEmpty()) return new ABB<>();
        else return new ABB<>(copyNodo(this.raiz));
    }

    public void print() {
        out.print("\n");
        StringBuilder sb = new StringBuilder();
        printFormatter(sb, "", "", this);
        out.print(sb);
        out.print("\n");
    }

    private void printFormatter(StringBuilder res, String pad, String childFlag, ABB<T> abb) {
        if (!abb.isEmpty()) {
            res.append(pad);
            res.append(childFlag);
            res.append(abb.root());
            res.append("\n");

            String padding = pad + "│ ";
            String rightFlag = "R: ";
            String leftFlag = "L: ";

            printFormatter(res, padding, rightFlag, abb.right());
            printFormatter(res, padding, leftFlag, abb.left());
        }
    }
}
