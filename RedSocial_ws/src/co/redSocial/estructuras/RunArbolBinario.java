package co.redSocial.estructuras;

//import parcial2F.Persona;

/**
 * Clase aplicaci�n destinada a probar los m�todos de la clase �rbol
 * estaVacio
 * Agregar
 * Inorden
 * Preorden
 * PostOrden
 * existe
 * obtenerPeso
 * obtenerAltura
 * obtenerNivel
 * contarHojas
 * obtenerMenor
 * imprimirAmplitud
 * Eliminar
 * contarHojas
 * obtenerAltura
 * obtenerNodoMayor
 * obtenerNodoMenor
 */
public class RunArbolBinario {

    public static void main(String[] args) {


        ArbolBinario<Integer> arbolBinario = new ArbolBinario<>();


        arbolBinario.add(8);
        arbolBinario.add(6);
        arbolBinario.add(7);
        arbolBinario.add(10);
        arbolBinario.add(12);
        arbolBinario.add(3);
        arbolBinario.add(2);

//		int altura = arbolBinario.obtenerAltura();
//		System.out.println(altura);

        arbolBinario.eliminar(2);


//		arbolBinario.printAmplitude();
//		arbolBinario.printHorizontal(arbolBinario.getRoot(), 0);
//
//		ArbolBinario<String> arbolBinario2 = new ArbolBinario<>();
//		arbolBinario2.agregar("hola");
//		arbolBinario2.agregar("h");
//		arbolBinario2.agregar("mundo");
//		arbolBinario2.agregar("mu");
//		arbolBinario2.agregar("mun");
//		arbolBinario2.agregar("mund");
//		arbolBinario2.agregar("hol");
//		arbolBinario2.imprimirAmplitud();
//
//
//		Persona persona  = new Persona();
//		persona.setEdad(22);
//		persona.setNombre("oscar");
//		persona.setSexo("m");


//		arbolBinario3.agregar("hola");
//		arbolBinario3.agregar("h");
//		arbolBinario3.agregar("mundo");
//		arbolBinario3.agregar("mu");
//		arbolBinario3.agregar("mun");
//		arbolBinario3.agregar("mund");
//		arbolBinario3.agregar("hol");
//		arbolBinario3.imprimirAmplitud();


//		System.out.println( sonIdenticos(arbolBinario.getRaiz(), arbolBinario2.getRaiz()) );

    }

    /**
     * Verifica si dos �rboles son id�nticos
     *
     * @param nodoRaiz1 Nodo ra�z �rbol 1
     * @param nodoRaiz1 Nodo ra�z �rbol 2
     * @return true si son iguales
     */
    public static boolean sonIdenticos(NodoArbol<Integer> nodoRaiz1, NodoArbol<Integer> nodoRaiz2) {

        if (nodoRaiz1 != null && nodoRaiz2 != null) {
            int comparacion = nodoRaiz1.getElemento().compareTo(nodoRaiz2.getElemento());
//		    if( nodoRaiz1.getElemento().compareTo(nodoRaiz2.getElemento()) == 0 )
            if (comparacion == 0) {

                boolean izq = sonIdenticos(nodoRaiz1.getIzquierdo(), nodoRaiz2.getIzquierdo());
                boolean der = sonIdenticos(nodoRaiz1.getDerecho(), nodoRaiz2.getDerecho());

                return izq && der;
            }

        } else if (nodoRaiz1 == null && nodoRaiz2 == null) {
            return true;
        }

        return false;
    }

}
