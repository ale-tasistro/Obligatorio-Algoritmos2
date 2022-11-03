package Utils;

import Arboles.AVL;
import Lineales.Lista;
import Utils.Utils.Pair;
import java.util.Scanner;
import java.util.Optional;
import Utils.Utils.Triple;
import Tablas.TablaHashStringInt;
import static java.lang.System.in;
import ColasDePrioridad.ColaDePrioridad;



/* clase que para leer el input de los ejercicios */
public class Inputs {


    /* clase para leer el input a partir del cual se crea un grafo */
    public static class GrInput {

        private final Integer[] aristasOrigen;
        private final Integer[] aristasDestino;
        private final Integer[] aristasCosto;
        private final int v;
        private final int e;
        private final boolean dir;

        public GrInput(boolean dirigido, boolean ponderado) {
            dir = dirigido;
            Scanner scan = new Scanner(in);
            v = scan.nextInt();
            scan.nextLine();
            e = scan.nextInt();
            scan.nextLine();
            aristasOrigen = new Integer[e];
            aristasDestino = new Integer[e];
            aristasCosto = new Integer[e];
            for (int i = 0; i < e; i++) {
                String input = scan.nextLine();
                String[] split = input.split(" ");
                int origen = Integer.parseInt(split[0]);
                int destino = Integer.parseInt(split[1]);
                aristasOrigen[i] = origen;
                aristasDestino[i] = destino;
                aristasCosto[i] = ponderado ? Integer.parseInt(split[2]) : 1;
            }
        }

        public int getE() { return e; }

        public int getV() { return v; }

        public boolean isDir() { return dir; }

        public Integer[] getAristasOrigen() { return aristasOrigen; }

        public Integer[] getAristasDestino() { return aristasDestino; }

        public Integer[] getAristasCosto() { return aristasCosto; }
    }

    /* funcion que lee el input para el ejercicio 1 y lo retorna como un AVL */
    public static AVL<Integer> inputEj1() {
        Scanner scan = new Scanner(in);
        int cantNumeros = scan.nextInt();
        AVL<Integer> avl = new AVL<>();
        for (int i = 0; i < cantNumeros; i++) {
            scan.nextLine();
            int num = scan.nextInt();
            avl.add(num);
        }
        return avl;
    }

    /* funcion que lee el input para el ejercicio 2 y lo retorna como TablaHashStringInt */
    public static TablaHashStringInt inputEj2() {
        Scanner scan = new Scanner(in);
        int cantPalabras = scan.nextInt();
        TablaHashStringInt tabla = new TablaHashStringInt(cantPalabras);
        scan.nextLine();
        for (int i = 0; i < cantPalabras; i++) {
            String palabra = scan.nextLine();
            Optional<Integer> valPalabra = tabla.fetch(palabra);
            if (valPalabra.isPresent()) tabla.update(palabra, valPalabra.get() + 1);
            else tabla.update(palabra, 1);
        }
        return tabla;
    }

    /* funcion que lee el input para el ejercicio 3 y lo retorna como ColaDePrioridad */
    public static ColaDePrioridad<Integer, Lista<Integer>> inputEj3() {
         Scanner scan = new Scanner(in);
         int cantListas = scan.nextInt();
        ColaDePrioridad<Integer,Lista<Integer>> res = new ColaDePrioridad<>(cantListas);
        for (int i = 0; i < cantListas; i++) {
            scan.nextLine();
            int largoLista = scan.nextInt();
            Lista<Integer> lista = new Lista<>();
            for (int j = 0; j < largoLista; j++) {
                scan.nextLine();
                lista.addBehind(scan.nextInt());
            }
            if (!lista.isEmpty()) res.add(lista, lista.head());
        }
        return res;
    }

    /* funcion que lee el input para el ejercicio 5 y lo retorna como ColaDePrioridad */
    public static ColaDePrioridad<Integer,Integer> inputEj5() {
        Scanner scan = new Scanner(in);
        int cantVertices = scan.nextInt();
        scan.nextLine();
        ColaDePrioridad<Integer,Integer> res = new ColaDePrioridad<>(cantVertices);
        for (int i = 1; i < cantVertices+1; i++) {
            int vertice = scan.nextInt();
            res.add(vertice, vertice);
            scan.nextLine();
        }
        return res;
    }

    /* funcion que lee el input para el ejercicio 7 y lo retorna como Integer[][] */
    public static Integer[][] inputEj7() {
        Scanner scan = new Scanner(in);
        int filas = scan.nextInt();
        scan.nextLine();
        int columnas = scan.nextInt();
        scan.nextLine();
        Integer[][] matriz = new Integer[filas][columnas];
        for (int i = 0; i < filas; i++) {
            String line = scan.nextLine();
            String[] lineArray = line.split(" ");
            for (int j = 0; j < columnas; j++) matriz[i][j] = Integer.parseInt(lineArray[j]);
        }
        return matriz;
    }

    /* funcion que lee el input para el ejercicio 9 y lo retorna como array de tripletas de Integer */
    public static Lista<Triple<Integer>> inputEj9() {
        Scanner scan = new Scanner(in);
        int cantEdificios = scan.nextInt();
        Lista<Triple<Integer>> lista = new Lista<>();
        scan.nextLine();
        for (int i = 0; i < cantEdificios; i++) {
            String[] edificioStrings = scan.nextLine().split(" ");
            Triple<Integer> edificio = new Triple<>(
                    Integer.parseInt(edificioStrings[0]),   // I
                    Integer.parseInt(edificioStrings[1]),   // F
                    Integer.parseInt(edificioStrings[2])    // H
            );
            lista.addBehind(edificio);
        }
        return lista;
    }

    /* funcion que lee el input para el ejercicio 10 y lo retorna como par de triples */
    // retorna ((capS, capL, cantObjs), (pesoS, pesoL, valor))
    public static Pair<Triple<Integer>, Triple<Integer[]>> inputEj10y11() {
        // leyendo cantidad de objetos y capacidades
        Scanner scan = new Scanner(in);
        int cantObjetos = scan.nextInt();
        scan.nextLine();
        int capacidadS = scan.nextInt();
        scan.nextLine();
        int capacidadL = scan.nextInt();
        scan.nextLine();

        // guardando información de objetos en arrays
        Integer[] pesoS = new Integer[cantObjetos];
        Integer[] pesoL = new Integer[cantObjetos];
        Integer[] valor = new Integer[cantObjetos];
        for (int i = 0; i < cantObjetos; i++) {
            String line = scan.nextLine();
            String[] lineArray = line.split(" ");
            pesoS[i] = Integer.parseInt(lineArray[0]);
            pesoL[i] = Integer.parseInt(lineArray[1]);
            valor[i] = Integer.parseInt(lineArray[2]);
        }

        // return
        Triple<Integer> capacidades = new Triple<>(capacidadS, capacidadL, cantObjetos);
        Triple<Integer[]> arrays = new Triple<>(pesoS, pesoL, valor);
        return new Pair<>(capacidades, arrays);
    }
}
