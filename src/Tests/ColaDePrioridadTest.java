package Tests;

/* Alejandro Tasistro, 269430  */

import ColasDePrioridad.ColaDePrioridad;

import java.util.Scanner;

class ColaDePrioridadTest {

    private static final ColaDePrioridad<Integer, String> COLA = new ColaDePrioridad<>(10);

    static void isEmpty() {
        System.out.print("cola.isEmpty: " + COLA.isEmpty() + "\n");
    }

    static void isFull() {
        System.out.print("cola.isFull: " + COLA.isFull() + "\n");
    }

    static void capacity() {
        System.out.print("cola.capacity: " + COLA.capacity() + "\n");
    }

    static void size() {
        System.out.print("cola.size: " + COLA.size() + "\n");
    }

    static void first() {
        if (notEmpty()) System.out.print("cola.first: " + COLA.first() + "\n");
    }

    static void lowestPriority() {
        if (notEmpty()) System.out.print("cola.lowestPriority: " + COLA.lowestPriority() + "\n");
    }

    static void add() {
        System.out.print("Ingrese un string seguido de un entero, para agregar a la cola de prioridad.\n");
        System.out.print("El string es el valor, y el entero la prioridad.\n");
        Scanner scanner = new Scanner(System.in);
        String respuesta = scanner.nextLine().toLowerCase().trim();
        String[] array = respuesta.split(" ");
        int prioridad = Integer.parseInt(array[0]);
        String valor = array[1];
        System.out.print("cola.add( " + valor + ", " + prioridad + " ): "
                + COLA.add(valor, prioridad) + "\n");
    }

    static void popFirst() {
        if (notEmpty()) System.out.print("cola.popFirst: " + COLA.popFirst() + "\n");
    }

    private static void opciones() {
        System.out.print("Ingrese una opcion de la lista:\n");
        System.out.print("'A': isEmpty\n");
        System.out.print("'B': isFull\n");
        System.out.print("'C': capacity\n");
        System.out.print("'D': size\n");
        System.out.print("'E': first\n");
        System.out.print("'F': lowestPriority\n");
        System.out.print("'G': add\n");
        System.out.print("'H': popFirst\n");
        System.out.print("'I': Imprimir a lo bestia el array del heap\n");
        System.out.print("'X': terminar el programa\n");
    }

    private static boolean notEmpty() {
        while (COLA.isEmpty()) {
            System.out.print("No se puede usar esta funcion con una cola vacia\n");
            System.out.print("¿Quiere ingresar datos y continuar? (y/n)\n");
            Scanner scanner = new Scanner(System.in);
            String respuesta = scanner.nextLine().toLowerCase().trim();
            if (respuesta.equals("y")) {
                add();
            } else if (respuesta.equals("n")) return false;
        }
        return !COLA.isEmpty();
    }



    public static void main(String[] args) {
        boolean salir = false;
        opciones();
        Scanner scan = new Scanner(System.in);
        while (!salir) {
            String respuesta = scan.nextLine().trim().toUpperCase();
            switch (respuesta) {
                case "A":
                    isEmpty();
                    System.out.print("---------------------------\n");
                    opciones();
                    break;
                case "B":
                    isFull();
                    System.out.print("---------------------------\n");
                    opciones();
                    break;
                case "C":
                    capacity();
                    System.out.print("---------------------------\n");
                    opciones();
                    break;
                case "D":
                    size();
                    System.out.print("---------------------------\n");
                    opciones();
                    break;
                case "E":
                    first();
                    System.out.print("---------------------------\n");
                    opciones();
                    break;
                case "F":
                    lowestPriority();
                    System.out.print("---------------------------\n");
                    opciones();
                    break;
                case "G":
                    add();
                    System.out.print("---------------------------\n");
                    opciones();
                    break;
                case "H":
                    popFirst();
                    System.out.print("---------------------------\n");
                    opciones();
                    break;
                case "I":
                    COLA.imprimirArray();
                    System.out.print("\n---------------------------\n");
                    opciones();
                    break;
                case "X":
                    salir = true;
                    break;
                default:
                    System.out.print("Ingrese una opción de la lista por favor\n");
            }
        }
    }


}