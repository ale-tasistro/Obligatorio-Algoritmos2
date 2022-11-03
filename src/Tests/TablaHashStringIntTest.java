package Tests;

/* Alejandro Tasistro, 269430  */

import Utils.Utils;
import java.util.Scanner;
import java.util.Optional;
import static Utils.Utils.*;
import Tablas.TablaHashStringInt;


class TablaHashStringIntTest {


    private static final TablaHashStringInt TABLA = new TablaHashStringInt(2000);


    static void update() {
        System.out.print("Ingrese la cantidad de pares que va a ingresar/actualizar:\n");
        Scanner scanner = new Scanner(System.in);
        int cant = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese " + cant + " lineas, cada una con un string y un entero separados por un espacio.\n");
        String respuesta;
        getSize();
        for (int i = 0; i < cant; i++) {
            respuesta = scanner.nextLine().trim();
            if (!respuesta.equalsIgnoreCase("x")) {
                String[] aux = respuesta.split(" ");
                String key = aux[0];
                int value = Integer.parseInt(aux[1]);
                TABLA.update(key, value);
            }
        }
        getSize();
    }

    static void fetch() {
        System.out.print("Ingrese una string que quiera buscar en la tabla.\n");
        Scanner scanner = new Scanner(System.in);
        String respuesta = scanner.nextLine().trim();
        Optional<Integer> valor = TABLA.fetch(respuesta);
        System.out.print("tabla.fetch(" + respuesta + "): ");
        System.out.print((valor.isPresent() ?
                valor.get() : "No hay ningún valor asociado a " + respuesta) + "\n");

    }

    static void getSize() {
        System.out.print("tabla.getSize(): " + TABLA.getSize() + "\n");
    }

    static void getLoadFactor() {
        System.out.print("tabla.getLoadFactor(): " + TABLA.getLoadFactor() + "\n");
    }

    static void rehash() {
        getLoadFactor();
        System.out.print("tabla.rehash(): \n");
        TABLA.rehash();
        getLoadFactor();
    }

    static void remove() {
        System.out.print("Ingrese una string que quiera borrar de la tabla.\n");
        Scanner scanner = new Scanner(System.in);
        String respuesta = scanner.nextLine().trim();
        getSize();
        System.out.print("tabla.remove(" + respuesta + "): "+ TABLA.remove(respuesta) + " \n");
        getSize();

    }

    private static void opciones() {
        System.out.print("Ingrese una opcion de la lista:\n");
        System.out.print("'A': update\n");
        System.out.print("'B': fetch\n");
        System.out.print("'C': getSize\n");
        System.out.print("'D': getLoadFactor\n");
        System.out.print("'E': rehash\n");
        System.out.print("'F': remove\n");
        System.out.print("'G': probarHash\n");
        System.out.print("'X': terminar el programa\n");
    }



    public static void main(String[] args) {
        boolean salir = false;
        opciones();
        Scanner scan = new Scanner(System.in);
        while (!salir) {
            String respuesta = scan.nextLine().trim().toUpperCase();
            switch (respuesta) {
                case "A":
                    update();
                    System.out.print("---------------------------\n");
                    opciones();
                    break;
                case "B":
                    fetch();
                    System.out.print("---------------------------\n");
                    opciones();
                    break;
                case "C":
                    getSize();
                    System.out.print("---------------------------\n");
                    opciones();
                    break;
                case "D":
                    getLoadFactor();
                    System.out.print("---------------------------\n");
                    opciones();
                    break;
                case "E":
                    rehash();
                    System.out.print("---------------------------\n");
                    opciones();
                    break;
                case "F":
                    remove();
                    System.out.print("---------------------------\n");
                    opciones();
                    break;
                case "G":
                    probarHash();
                    System.out.print("---------------------------\n");
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

    private static void probarHash() {
        System.out.print("Ingrese la cantidad de Stings a hashear:\n");
        Scanner scanner = new Scanner(System.in);
        int cant = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese " + cant + " lineas, cada una con un string para hashear.\n");
        String respuesta;
        int CAP = nextPrime(2*cant+1);
        System.out.println("Modulo = " + CAP);
        for (int i = 0; i < cant; i++) {
            respuesta = scanner.nextLine().trim();
            if (!respuesta.equalsIgnoreCase("x"))
                System.out.println(respuesta + " -> " + hash(respuesta, CAP));
        }
    }


    private static int hash(String key, int modulo) {
        // transforma el string en un Integer[]
        int largo = key.length();
        char[] keyToCharArray = key.toCharArray();
        Integer[] charCodes = new Integer[largo];
        for (int i = 0; i < largo; i++)
            charCodes[i] = (int) keyToCharArray[i];

        // lo pasa a las tres funciones de hash
        int hashDAC = hashDAC(charCodes, modulo);
        int hashChars = sumCharsHash(charCodes, modulo);
        int hashFL = firstLastHash(charCodes, modulo);


        // appendea los hashes en una string y parsea el resultado a long
        String aux = String.valueOf(hashFL) + hashChars + hashDAC;
        long longAux = Long.parseLong(aux);
        longAux = longAux % modulo;

        // retorna el long modulo capacidad casteado a int
        return (int) longAux;
    }

    // multiplica el codigo ascii de cada Character en keyChars por su posicion
    // y devuelve el resultado de sumar todos esos modulo la capacidad
    private static Integer sumCharsHash(Integer[] charCodes, int modulo) {
        for (int i = 0; i < charCodes.length; i++) {
            long aux = (long) (modulo - (i + 1)) * charCodes[i];
            charCodes[i] = (int) (aux % modulo);
        }
        long res = sumIntArray(charCodes) % modulo;
        return (int) res;
    }

    // devuelve el codigo del primer elemento de keyChars multiplicado por 17
    // con el codigo del ultimo elemento multiplicado por 19 appendeado al final
    private static Integer firstLastHash(Integer[] charCodes, int modulo) {
        int codigoPrimero = 17*charCodes[0];
        int codigoUltimo = 19*charCodes[charCodes.length-1];
        String stringAux = String.valueOf(codigoPrimero) + codigoUltimo;
        long res = Long.parseLong(stringAux) % modulo;
        return (int) res;
    }

    // si array es vacío devuelve 0,
    // si tiene un elemento solo devuelve el código ascii del elemento
    // si no divide a la mitad y se llama recursivamente en las mitades
    // finalmente llama a mergeHash con el resultado de las llamadas recursivas,
    // el primer y el último caracter de array
    private static Integer hashDAC(Integer[] array, int modulo) {
        if (array.length == 0) return 0;
        if (array.length == 1) return array[0];
        Utils.STPair<Integer[]> split = splitInHalf(array);
        Integer hash1 = hashDAC(split.getLeft(), modulo);
        Integer hash2 = hashDAC(split.getRight(), modulo);
        return mergeHash(hash1, hash2, array[0], array[array.length-1], modulo);
    }

    // appendea hash2 a hash1 en string 1, apendea el codigo ascii de ultimo al de primero en string2,
    // parsea string1 a long y hace modulo capacidad, parsea string2 a int, y retorna el producto ambos
    // modulo capacidad
    private static Integer mergeHash(Integer hash1, Integer hash2, int primeroCode, int ultimoCode, int modulo) {
        String string1 = String.valueOf(hash1) + hash2;
        String string2 = String.valueOf(primeroCode) + ultimoCode;
        long numString1 = Long.parseLong(string1) % modulo;
        long numString2 = Long.parseLong(string2) % modulo;
        long res = (numString1 * numString2) % modulo;
        return (int) res;
    }

}