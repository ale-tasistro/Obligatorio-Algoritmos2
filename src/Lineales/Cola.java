package Lineales;

/* Alejandro Tasistro, 269430  */

public class Cola<T> extends Lista<T> {

    public Cola() { super(); }

    public void enqueue(T info) { this.addBehind(info); }

    public T dequeue() {
        T res = this.head();
        this.removeHead();
        return res;
    }
}
