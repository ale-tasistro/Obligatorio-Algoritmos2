package Lineales;

/* Alejandro Tasistro, 269430  */

import static java.lang.System.out;

public class Lista<T> {

    /* NODO */
    protected class Nodo {
        final T info;
        Nodo next;
        Nodo prev;

        public Nodo(T unInfo) {
            this.info = unInfo;
            this.next = null;
            this.prev = null;
        }
    }

    /* ITERADOR */
    public class Iterador {

        private Nodo current;
        private final Lista<T> lista;

        public Iterador(Lista<T> unaLista) {
            this.lista = unaLista;
            toFirst();
        }

        public void toFirst() { current = lista.first; }

        public void toLast() { current = lista.last; }

        public boolean isNull() { return current == null; }

        public boolean isFirst() { return current == lista.first; }

        public boolean isLast() { return current == lista.last; }

        public boolean hasNext() { return !isNull() && current.next != null; }

        public boolean hasPrev() { return !isNull() && current.prev != null; }

        // PRE: !this.isNull()
        public void next() { current = current.next; }

        // PRE: !this.isNull()
        public void prev() { current = current.prev; }

        // PRE: !this.isNull()
        public T info() { return current.info; }

        // PRE: !this.isNull()
        // POS: this.next()
        public void remove() {
            if (this.isFirst()) lista.removeHead();
            else conectarNodos(this.current.prev, this.current.next);
            this.next();
        }
    }

    /* ATRIBUTOS */

    protected Nodo first;
    protected Nodo last;

    /* CONSTRUCTORES */

    public Lista() {
        first = null;
        last = null;
    }

    public Lista(Nodo unFirst, Nodo unLast) {
        first = unFirst;
        last = unLast;
    }

    /* QUERIES */

    public boolean isEmpty() { return first == null; }

    public int length() {
        int res = 0;
        if (!this.isEmpty()) {
            Nodo i = this.first;
            while (i != null) {
                res++;
                i = i.next;
            }
        }
        return res;
    }

    // PRE: !this->isEmpty()
    public T head() { return first.info; }

    // PRE: !this->isEmpty()
    public Lista<T> tail() {
        if (length() == 1) return new Lista<>();
        else return new Lista<>(this.first.next, this.last);
    }

    public boolean contains(T info) {
        if (this.isEmpty()) return false;
        else if (head() == info) return true;
        else return tail().contains(info);
    }

    /* METODOS */

    // Metodo auxiliar para manipulacion de nodos
    private void conectarNodos(Nodo nodo1, Nodo nodo2) {
        if (nodo1 != null) nodo1.next = nodo2;
        if (nodo2 != null) nodo2.prev = nodo1;
    }

    /* ADD */

    // i.isNull se interpreta como lista vacia
    public void addBefore(Iterador i, T info) {
        Nodo nuevo = new Nodo(info);
        if (i.isNull()) {
            this.last = nuevo;
            this.first = nuevo;
        } else {
            conectarNodos(i.current.prev, nuevo);
            conectarNodos(nuevo, i.current);
            if (i.isFirst()) this.first = nuevo;
        }
    }

    // i.isNull se interpreta como lista vacia
    public void addAfter(Iterador i, T info) {
        Nodo nuevo = new Nodo(info);
        if (i.isNull()) {
            this.last = nuevo;
            this.first = nuevo;
        } else {
            conectarNodos(nuevo, i.current.next);
            conectarNodos(i.current, nuevo);
            if (i.isLast()) this.last = nuevo;
        }
    }

    public void addAhead(T info) { addBefore(this.iterator(), info); }

    public void addBehind(T info) {
        Iterador i = this.iterator();
        i.toLast();
        addAfter(i, info);
    }

    /* REMOVE */

    // PRE: !this.isEmpty()
    public T popHead() {
        T head = this.head();
        removeHead();
        return head;
    }

    public void removeHead() {
        if (!this.isEmpty()) {
            if (this.length() == 1) this.last = null;
            conectarNodos(this.first.prev, this.first.next);
            Nodo aux = this.first;
            this.first = this.first.next;
            aux.prev = null;
            aux.next = null;
        }
    }

    public boolean remove(T info) {
        boolean found = false;
        Iterador iter = this.iterator();
        while (!iter.isNull() && !found)
            if (iter.info().equals(info))
                found = true;
            else
                iter.next();
        if (found)
            iter.remove();
        return found;
    }

    public void makeEmpty() { while (!this.isEmpty()) removeHead(); }

    /* MODIFICADORES */

    //POS: lista.isEmpty() == true
    public void append(Lista<T> lista) {
        while (!lista.isEmpty()) {
            addBehind(lista.head());
            lista.removeHead();
        }
    }

    public void appendArray(T[] array) {
        for (T t : array) addBehind(t);
    }

    public void reverse() {
        if (this.length() > 1) {

            // Se intercambian prevs y nexts de nodos de la lista
            Nodo i = this.first;
            Nodo aux;
            Nodo flag = this.last.next;
            while (i != flag) {
                aux = i.prev;
                i.prev = i.next;
                i.next = aux;
                i = i.prev;
            }

            // Se intercambian first y last de la lista
            aux = this.last;
            this.last = this.first;
            this.first = aux;

            // Se arregla next y prev de nodos por fuera de la lista si existen
            if (this.last.next != null)
                this.last.next.next = this.first;
            if (this.first.prev != null)
                this.first.prev.prev = this.last;

            // Se arregla next de last y prev de first por si existen
            aux = this.last.next;
            this.last.next = this.first.prev;
            this.first.prev = aux;
        }
    }


    /* READ-ONLY */

    public Lista<T> copyList()  {
        Lista<T> res = new Lista<>();
        Nodo index = this.first;
        while (index != null) {
            res.addBehind(index.info);
            index = index.next;
        }
        return res;
    }

    public void print() {
        Nodo index = this.first;
        while (index != null) {
            out.print(index.info);
            if (index.next != null) out.print(" <=> ");
            index = index.next;
        }
        out.print("\n");
    }

    /* ITERAR */

    public Iterador iterator() { return new Iterador(this); }

}