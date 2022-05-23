/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$
 * Universidad del Quindío (Armenia - Colombia)
 * Programa de Ingeniería de Sistemas y Computación
 *
 * Asignatura: Teoría de Lenguajes Formales
 * Ejercicio: AnalizadorLexico
 * Diseño original por: Leonardo A. Hernández R. - Agosto 2008 - Marzo 2009
 * Modificado y usado por: Claudia E. Quiceno R- Julio 2021
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package mundo;

/**
 * Clase que modela un token
 */

public class Token {
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------
    /**
     * Constantes para modelar los posibles tipos de token que se van a analizar
     */
    final public static String ENTERO = "Entero";
    final public static String OPERADORADITIVO = "Operador aditivo";
    final public static String OPERADORASIGNACION = "Operador de asignación";
    final public static String IDENTIFICADORMETODO = "Identificador de metodo";
    final public static String IDENTIFICADORVARIABLE = "Identificador de variable";
    final public static String IDENTIFICADORCLASE = "Identificador de clase";
    final public static String OPERADORLOGICO = "Operador Logico";
    final public static String SIMBOLOABRIR = "Simbolo Abrir";
    final public static String SIMBOLOCERRAR = "Simbolo Cerrar";
    final public static String OPERADORRELACIONAL = "Operador Relacional";
    final public static String NORECONOCIDO = "No reconocido";
    final public static String TIPODATO = "Palabra reservada ";
    final public static String METODOPRINCIPAL = "Metodo principal";
    final public static String BUCLE = "Palabra reservada bucle";
    final public static String DESICION = "Palabra reservada de desición";
    final public static String CLASE = "Palabra reservada clase";
    final public static String SEPARADORSENTENCIA = "Separador de sentencia";
    final public static String TERMINAL = "Terminal";
    final public static String CADENA = "Cadena de caracteres";
    final public static String SALTOLINEA = "Salto de linea";
    final public static String TABULACION = "Tabulación";
    final public static String ARROBA = "arroba";
    
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------
    /**
     * Lexema
     */
    private String lexema;

    /**
     * tipo
     */
    private String tipo;

    /**
     * posición del siguiente lexema
     */
    private int indiceSiguiente;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------
    /**
     * Constructor de un token
     * @param elLexema - cadena - laCadena != null
     * @param elTipo - tipo del token - elTipo != null
     * @param elIndiceSiguiente - posición del siguiente token - laPosicionSiguiente > 0
     */
    public Token( String elLexema, String elTipo, int elIndiceSiguiente )
    {
        lexema = elLexema;
        tipo = elTipo;
        indiceSiguiente = elIndiceSiguiente;
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Entrega la información del token
     * @return Descripción del token
     */
    public String darDescripcion( )
    {
        return "Token: " + lexema + "     Tipo: " + tipo + "     Índice del siguiente: " + indiceSiguiente;
    }

    /**
     * Método que retorna el lexema del token
     * @return el lexema del token
     */
    public String darLexema( )
    {
        return lexema;
    }

    /**
     * Método que retorna la posición del siguiente lexema
     * @return posición del siguiente token
     */
    public int darIndiceSiguiente( )
    {
        return indiceSiguiente;
    }

    /**
     * Método que retorna el tipo del token
     * @return el tipo del token
     */
    public String darTipo( )
    {
        return tipo;
    }




}
