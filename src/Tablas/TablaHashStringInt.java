package Tablas;

/* Alejandro Tasistro, 269430  */

import Lineales.Lista;

import java.util.Optional;
import Utils.Utils.STPair;
import static Utils.Utils.*;
import Utils.Utils.KeyValuePair;



public class TablaHashStringInt {

    private int capacidad;
    private int size = 0;
    private Lista<KeyValuePair<String, Integer>>[] array;
    private final Lista<String> keys = new Lista<>();


                                             /* CONSTRUCTOR */

    public TablaHashStringInt(int unaCapacidad) {
        capacidad = nextPrime(unaCapacidad);
        array = (Lista<KeyValuePair<String, Integer>>[]) new Lista[capacidad];
        for (int i = 0; i < capacidad; i++) array[i] = new Lista<>();
    }

                                               /* QUERIES */

    public int getSize() {
        return size;
    }

    public float getLoadFactor() {
        float sizeFloat = size;
        float capFloat = capacidad;
        return sizeFloat/capFloat;
    }

    public Lista<String> getKeys() {
        return keys;
    }

    public Optional<Integer> fetch(String key) {
        int hash = hash(key, capacidad);
        Lista<KeyValuePair<String, Integer>>.Iterador iter = array[hash].iterator();
        while (!iter.isNull())
            if (iter.info().key().equals(key))
                return Optional.of(iter.info().value());
            else
                iter.next();
        // Si sale no hay ningun int asociado a la key
        return Optional.empty();
    }


                                               /* UPDATE */

    public void update(String key, Integer value) {
        int hash = hash(key, capacidad);
        Lista<KeyValuePair<String, Integer>>.Iterador iter = array[hash].iterator();
        boolean found = false;
        while (!iter.isNull() && !found)
            if (iter.info().key().equals(key)) {
                found = true;
                iter.info().updateValue(value);
        } else
            iter.next();
        // Si no hay ningún int asociado a la key lo agrega
        if (!found) {
            array[hash].addBehind(new KeyValuePair<>(key, value));
            keys.addBehind(key);
            size++;
        }
    }

                                               /* REMOVE */

    public boolean remove(String key) {
        int hash = hash(key, capacidad);
        Lista<KeyValuePair<String, Integer>>.Iterador iter = array[hash].iterator();
        boolean found = false;
        while (!iter.isNull() && !found)
            if (iter.info().key().equals(key)) {
                found = true;
                iter.remove();
                keys.remove(key);
                size--;
            } else
                iter.next();
        return found;
    }


                                               /* REHASH */

    public void rehash() {
        // agrandando capacidad
        int nuevaCap = nextPrime(2*capacidad + 1);
        // inicializando nuevo array
        Lista<KeyValuePair<String, Integer>>[] nuevo = (Lista<KeyValuePair<String, Integer>>[]) new Lista[nuevaCap];
        for (int i = 0; i < nuevaCap; i++)
            nuevo[i] = new Lista<>();
        // copiando contenido de array a nuevo
        Lista<String>.Iterador iter = keys.iterator();
        while (!iter.isNull()) {
            String key = iter.info();
            int hash = hash(key, nuevaCap);
            int value = fetch(key).get();
            KeyValuePair<String, Integer> par = new KeyValuePair<>(key, value);
            nuevo[hash].addBehind(par);
            iter.next();
        }
        // cambiando array y capacidad
        array = nuevo;
        capacidad = nuevaCap;
    }

                                                /* HASH */

    private static int hash(String key, int modulo) {
        // transforma el string en un Integer[]
        int largo = key.length();
        Integer[] charCodes = new Integer[largo];
        char[] keyToCharArray = key.toCharArray();
        for (int i = 0; i < largo; i++)
            charCodes[i] = (int) keyToCharArray[i];

        // lo pasa a las tres funciones de hash
        int hashChars = sumCharsHash(charCodes, modulo);
        int hashDAC = hashDAC(charCodes, modulo);
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
    // con el codigo del ultimo elemento multiplicado por 19 appendeado al final,
    // modulo la capacidad
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
        STPair<Integer[]> split = splitInHalf(array);
        Integer hash1 = hashDAC(split.getLeft(), modulo);
        Integer hash2 = hashDAC(split.getRight(), modulo);
        return mergeHash(hash1, hash2, array[0], array[array.length-1], modulo);
    }

    // appendea hash2 a hash1 en string 1, apendea el codigo ascii de ultimo al de primero en string2,
    // parsea string1 a long y hace modulo capacidad, parsea string2 a int, y retorna el producto ambos
    // modulo capacidad
    private static Integer mergeHash(Integer hash1, Integer hash2, int primeroCode, int ultimoCode, int modulo) {
        String string1 = String.valueOf(hash1) + hash2;
        long numString1 = Long.parseLong(string1) % modulo;
        String string2 = String.valueOf(primeroCode) + ultimoCode;
        long numString2 = Long.parseLong(string2) % modulo;
        long res = (numString1 * numString2) % modulo;
        return (int) res;
    }
}