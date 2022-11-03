package Grafos;

/* Alejandro Tasistro, 269430  */

import Lineales.Cola;
import Lineales.Pila;
import Lineales.Lista;
import Utils.Utils.Pair;
import Utils.Inputs.GrInput;
import Utils.Utils.STPair;
import static java.lang.Integer.*;
import static java.lang.System.out;
import ColasDePrioridad.ColaDePrioridad;

public class GrafoListas {

    public static class Arista {

        public final int origen;
        public final int destino;
        public final int costo;
        public Arista(int unOrigen, int unDestino, int unCosto) {
            origen = unOrigen;
            destino = unDestino;
            costo = unCosto;
        }

    }

    private static class DijkstraPrimInit {

        public final Boolean[] visitados;
        public final Integer[] costos;
        public final Integer[] vengo;

        public DijkstraPrimInit(GrafoListas grafo, int origen) {
            vengo = new Integer[grafo.v+1];
            costos = new Integer[grafo.v+1];
            visitados = new Boolean[grafo.v+1];
            for (int i = 0; i < grafo.v+1; i++) {
                vengo[i] = -1;
                costos[i] = MAX_VALUE;
                visitados[i] = false;
            }
            visitados[0] = true;
            vengo[0] = costos[0] = 0;
            costos[origen] = 0;
        }
    }

    private int v;
    private Lista<Arista>[] aristas; // aristas[i] tiene los nodos adyacentes a i con el costo correspondiente.
    private Integer[] gradoDeEntrada;
    private boolean dirigido;

    /* CONSTRUCTORES */

    public GrafoListas(GrInput input) {   // O(E)
        init(input.getV(), input.getE(), input.isDir(),
                input.getAristasOrigen(), input.getAristasDestino(), input.getAristasCosto());
    }

    private void init(int cantV, int cantAr, boolean dir,
                      Integer[] origenes, Integer[] destinos, Integer[] costos) {
        v = cantV;
        aristas = (Lista<Arista>[]) new Lista[v+1];
        gradoDeEntrada = new Integer[v+1];
        for (int i = 0; i < v + 1; i++) {
            aristas[i] = new Lista<>();
            gradoDeEntrada[i] = 0;
        }
        for (int i = 0; i < cantAr; i++) {
            int origen = origenes[i];
            int destino = destinos[i];
            int costo = costos[i];
            aristas[origen].addAhead(new Arista(origen, destino, costo));
            gradoDeEntrada[destino]++;
            if (!dir) {
                aristas[destino].addAhead(new Arista(destino, origen, costo));
                gradoDeEntrada[origen]++;
            }
        }
        dirigido = dir;
    }

    /* GETTERS */

    public int getV() { return v; }

    public Lista<Arista>[] getAristas() { return aristas; }

    /* QUERIES */

    public boolean isDirigido() { return dirigido; }

    // O(ordenTopologico() + V)
    public boolean tieneCiclos() { return ordenTopologico().length() < v; }

    public boolean hasNegativeCost() {
        boolean foundNegative = false;
        int currentV = 1;
        while (!foundNegative && currentV < v+1) {
            Lista<Arista>.Iterador iter = aristas[currentV].iterator();
            while (!foundNegative && !iter.isNull()) {
                foundNegative = iter.info().costo < 0;
                iter.next();
            }
            currentV++;
        }
        return foundNegative;
    }

    // PRE: !this.isDirigido()
    public int cantComponentesConexas() {
        // inicialización:
        int cant = 0;
        Lista<Integer>[] dfsArray = (Lista<Integer>[]) new Lista[v+1];
        Boolean[] componentesEncontradas = new Boolean[v+1];
        for (int i = 0; i < this.v + 1; i++) componentesEncontradas[i] = false;
        componentesEncontradas[0] = true;
        for (int i = 1; i < this.v + 1; i++) dfsArray[i] = dfs(i);
        // algoritmo:
        for (int v = 1; v < this.v + 1; v++) {
            if (!componentesEncontradas[v]) {
                componentesEncontradas[v] = true;
                cant++;
                Lista<Integer>.Iterador iter = dfsArray[v].iterator();
                while (!iter.isNull()) {
                    int w = iter.info();
                    componentesEncontradas[w] = true;
                    iter.next();
                }
            }
        }
        return cant;
    }

    public int gradoDeSalida(int vertice) { return aristas[vertice].length(); }

    public int gradoDeEntrada(int vertice) { return gradoDeEntrada[vertice]; }

    /* METODOS */

    public Lista<Integer> ordenTopologico() {
        // Inicializacion:
        Integer[] gradosAux = new Integer[v+1];
        if (v >= 0) System.arraycopy(gradoDeEntrada, 1, gradosAux, 1, v); // O(V)
        Cola<Integer> cola = new Cola<>();
        for (int i = 1; i < v + 1; i++) if (gradosAux[i] == 0) cola.enqueue(i);
        Lista<Integer> res = new Lista<>();
        // Algoritmo:
        while (!cola.isEmpty()) {
            int vertice = cola.dequeue(); // O(1)
            res.addBehind(vertice);
            // Procesado de adyacentes:
            // -1 a los grados de entrada de los adyacentes a vertice,
            // se encolan los que quedan en 0
            Lista<Arista> adyacentes = aristas[vertice];
            Lista<Arista>.Iterador iter = adyacentes.iterator();
            while (!iter.isNull()) {
                int current = iter.info().destino;
                gradosAux[current]--;
                if (gradosAux[current] == 0) cola.enqueue(current); // O(1)
                iter.next();
            }
        }
        return res;
    }

    // PRE: !hasNegativeCost()
    public STPair<Integer[]> dijkstra(int origen) {
        // Inicializacion:
        DijkstraPrimInit init = new DijkstraPrimInit(this, origen);
        ColaDePrioridad<Integer,Integer> cp = new ColaDePrioridad<>(v);
        cp.add(origen,0);
        Boolean[] visitados = init.visitados;
        Integer[] vengo = init.vengo;
        Integer[] costos = init.costos;
        // Algoritmo:
        while (!cp.isEmpty()) {
            int v = cp.popFirst(); // O(log V)
            // se procesan vertices no visitados
            if (!visitados[v]) {
                visitados[v] = true;
                // se procesan las aristas adyacentes a v
                Lista<Arista>.Iterador iter = aristas[v].iterator();
                while (!iter.isNull()) {    // O(E(v))
                    int w = iter.info().destino;
                    int costoVW = iter.info().costo;
                    int candidato = costos[v] + costoVW;
                    // se actualizan y encolan vertices adyacentes no visitados
                    // con costo registrado mayor al de ir desde v
                    if (!visitados[w] && (costos[w] > candidato)) {
                        costos[w] = candidato;
                        vengo[w] = v;
                        cp.add(w, candidato);
                    }
                    iter.next();
                }
            }
        }
        // se devuelven arrays de predecesores y de costos
        return new STPair<>(vengo, costos);
    }

    public Pair<Boolean[], Integer[]> prim(int origen) {
        // Inicializacion:
        DijkstraPrimInit init = new DijkstraPrimInit(this, origen);
        ColaDePrioridad<Integer,Integer> cp = new ColaDePrioridad<Integer, Integer>(v);
        cp.add(origen,0);
        Boolean[] visitados = init.visitados;
        Integer[] vengo = init.vengo;
        Integer[] costos = init.costos;
        // Algoritmo:
        while (!cp.isEmpty()) {
            int v = cp.popFirst(); // O(log V)
            // se procesan vertices no visitados
            if (!visitados[v]) {
                visitados[v] = true;
                // se procesan las aristas adyacentes a v
                Lista<Arista>.Iterador iter = aristas[v].iterator();
                while (!iter.isNull()) {
                    int w = iter.info().destino;
                    int costoVW = iter.info().costo;
                    // se actualizan y encolan vertices adyacentes no visitados
                    // con costo registrado mayor al de la arista que sale de v
                    if (!visitados[w] && (costos[w] > costoVW)) {
                        costos[w] = costoVW;
                        vengo[w] = v;
                        cp.add(w, costoVW);
                    }
                    iter.next();
                }
            }
        }
        // se devuelven arrays de visitados y de costos
        return new Pair<>(visitados, costos);
    }

    private Lista<Integer> dfs(int origen) {
        Lista<Integer> res = new Lista<>();
        Pila<Integer> pila = new Pila<>();
        Boolean[] visitados = new Boolean[v+1];
        for (int i = 0; i < v + 1; i++) visitados[i] = false;
        pila.push(origen);
        while (!pila.isEmpty()) {
            int vertice = pila.pop();
            if (!visitados[vertice]) {
                visitados[vertice] = true;
                res.addBehind(vertice);
                Lista<Arista>.Iterador iter = aristas[vertice].iterator();
                while (!iter.isNull()) {
                    pila.push(iter.info().destino);
                    iter.next();
                }
            }
        }
        return res;
    }

    public void print() {
        for (int i = 1; i < v + 1; i++) {
            Lista<Arista>.Iterador iter = aristas[i].iterator();
            out.print("Vertice " + i + ": ");
            while (!iter.isNull()) {
                out.print("Hacia " + iter.info().destino + " cuesta " + iter.info().costo + " - ");
                iter.next();
            }
            out.print("\n");
        }
    }
}
