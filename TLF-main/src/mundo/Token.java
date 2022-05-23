/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$
 * Universidad del Quind�o (Armenia - Colombia)
 * Programa de Ingenier�a de Sistemas y Computaci�n
 *
 * Asignatura: Teor�a de Lenguajes Formales
 * Ejercicio: AnalizadorLexico
 * Dise�o original por: Leonardo A. Hern�ndez R. - Agosto 2008 - Marzo 2009
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
    final public static String OPERADORASIGNACION = "Operador de asignaci�n";
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
    final public static String DESICION = "Palabra reservada de desici�n";
    final public static String CLASE = "Palabra reservada clase";
    final public static String SEPARADORSENTENCIA = "Separador de sentencia";
    final public static String TERMINAL = "Terminal";
    final public static String CADENA = "Cadena de caracteres";
    final public static String SALTOLINEA = "Salto de linea";
    final public static String TABULACION = "Tabulaci�n";
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
     * posici�n del siguiente lexema
     */
    private int indiceSiguiente;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------
    /**
     * Constructor de un token
     * @param elLexema - cadena - laCadena != null
     * @param elTipo - tipo del token - elTipo != null
     * @param elIndiceSiguiente - posici�n del siguiente token - laPosicionSiguiente > 0
     */
    public Token( String elLexema, String elTipo, int elIndiceSiguiente )
    {
        lexema = elLexema;
        tipo = elTipo;
        indiceSiguiente = elIndiceSiguiente;
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Entrega la informaci�n del token
     * @return Descripci�n del token
     */
    public String darDescripcion( )
    {
        return "Token: " + lexema + "     Tipo: " + tipo + "     �ndice del siguiente: " + indiceSiguiente;
    }

    /**
     * M�todo que retorna el lexema del token
     * @return el lexema del token
     */
    public String darLexema( )
    {
        return lexema;
    }

    /**
     * M�todo que retorna la posici�n del siguiente lexema
     * @return posici�n del siguiente token
     */
    public int darIndiceSiguiente( )
    {
        return indiceSiguiente;
    }

    /**
     * M�todo que retorna el tipo del token
     * @return el tipo del token
     */
    public String darTipo( )
    {
        return tipo;
    }




}
