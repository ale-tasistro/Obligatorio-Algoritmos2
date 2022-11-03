package Lineales;

/* Alejandro Tasistro, 269430  */

public class Pila<T> extends Lista<T> {

    public Pila() { super(); }

    public void push(T info) { this.addAhead(info); }

    public T pop() {
        T res = this.head();
        this.removeHead();
        return res;
    }
}
