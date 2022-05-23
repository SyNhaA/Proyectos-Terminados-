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

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.plaf.multi.MultiSliderUI;

import interfaz.InterfazAnalizadorLexico;

/**
 * Clase que modela un analizador l�xico
 */

public class AnalizadorLexico {

	// -----------------------------------------------------------------
	// M�todos
	// -----------------------------------------------------------------

	private InterfazAnalizadorLexico analizador;

	/**
	 * Extrae los tokens de un c�digo fuente dado
	 * 
	 * @param cod - c�digo al cual se le van a extraer los tokens - !=null
	 * @return vector con los tokens
	 */
	public ArrayList extraerTokens(String cod, InterfazAnalizadorLexico analizador) {
		Token token;
		ArrayList vectorTokens = new ArrayList();
		this.analizador = analizador;

		// El primer token se extrae a partir de posici�n cero
		int i = 0;

		// Ciclo para extraer todos los tokens
		while (i < cod.length()) {
			// Extrae el token de la posici�n i
			token = extraerSiguienteToken(cod, i);
			vectorTokens.add(token);
			i = token.darIndiceSiguiente();
		}
		return vectorTokens;
	}

	/**
	 * Extrae el token de la cadena cod a partir de la posici�n i, bas�ndose en el
	 * Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a extraer un token - codigo!=null
	 * @param i   - posici�n a partir de la cual se va a extraer el token - i>=0
	 * @return token que se extrajo de la cadena
	 */
	public Token extraerSiguienteToken(String cod, int i) {
		Token token;

		// Intenta extraer un entero
		token = extraerEntero(cod, i);
		if (token != null)
			return token;

		// Intenta extraer un operador aditivo
		token = extraerOperadorAditivo(cod, i);
		if (token != null)
			return token;

		// Intenta extraer un operador Relacional
		token = extraerOperadorRelacional(cod, i);
		if (token != null)
			return token;

		// Intenta extraer un operador Logico
		token = extraerOperadorLogico(cod, i);
		if (token != null)
			return token;

		// Intenta extraer un operador de asignaci�n
		token = extraerOperadorAsignacion(cod, i);
		if (token != null)
			return token;

		// Intenta extraer un identificador
		token = extraerIdentificadorClase(cod, i);
		if (token != null)
			return token;

		// Intenta extraer un identificador
		token = extraerIdentificadorVariable(cod, i);
		if (token != null)
			return token;

		// Intenta extraer un identificador
		token = extraerSimboloAbrir(cod, i);
		if (token != null)
			return token;

		// Intenta extraer un identificador
		token = extraerSimboloCerrar(cod, i);
		if (token != null)
			return token;


		// Intenta extraer un identificador
		token = extraerIdentificadorMetodo(cod, i);
		if (token != null)
			return token;

		// Intenta extraer un identificador
		token = extraerPalabraEntero(cod, i);
		if (token != null)
			return token;

		// Intenta extraer un identificador
		token = extraerPalabraReales(cod, i);
		if (token != null)
			return token;

		// Intenta extraer un identificador
		token = extraerPalabraCaracteres(cod, i);
		if (token != null)
			return token;

		// Intenta extraer una palabra de cdena de caracteres
		token = extraerPalabraCadenaCaracteres(cod, i);
		if (token != null)
			return token;

		// Intenta extraer palabra reservada main
		token = extraerPalabraMain(cod, i);
		if (token != null)
			return token;

		// Intenta extraer palabra reservada bucle
		token = extraerPalabraBucle(cod, i);
		if (token != null)
			return token;

		// Intenta extraer palabra reservada desici�n
		token = extraerPalabraDesicion(cod, i);
		if (token != null)
			return token;

		// Intenta extraer palabra reservada clase
		token = extraerPalabraClase(cod, i);
		if (token != null)
			return token;

		// Intenta extraer el separador sentencia
		token = extraerSeparadorSentencia(cod, i);
		if (token != null)
			return token;

		// Intenta extraer el terminal
		token = extraerTerminal(cod, i);
		if (token != null)
			return token;

		// Intenta extraer una cadenaa de caracteres
		token = extraerCadenaCaracteres(cod, i);
		if (token != null)
			return token;

		// Intenta extraer el salto de l�nea
		token = extraerSaltoLinea(cod, i);
		if (token != null)
			return token;

		// Intenta extraer el arroba
		token = extraerArroba(cod, i);
		if (token != null)
			return token;

		// Intenta extraer un identificador
		token = extraerTabulacion(cod, i);
		if (token != null)
			return token;


		// Extrae un token no reconocido
		token = extraerNoReconocido(cod, i);
		return token;
	}

	/**
	 * Intenta extraer un entero de la cadena cod a partir de la posici�n i,
	 * bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer un entero -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer un entero
	 *            - 0<=indice<codigo.length()
	 * @return el token entero o NULL, si el token en la posici�n dada no es un
	 *         entero. El Token se compone de el lexema, el tipo y la posici�n del
	 *         siguiente lexema.
	 */

	// Este m�todo usa el m�todo substring(), que se explica a continuaci�n:
	// x.substring( i, j ) retorna una nueva cadena que es una subcadena de la
	// cadena x.
	// La subcadena comienza en la posici�n i y
	// se extiende hasta el car�cter en la posici�n j-1.
	// Ejemplo: "universidad".substring(3,6) retorna "ver",

	public Token extraerEntero(String cod, int i) {

		int j;
		String lex;
		if (cod.charAt(i) == '[') {
			j = i + 1;
			if (j < cod.length() && esDigito(cod.charAt(j))) {
				do {
					if (!esDigito(cod.charAt(j))) {
						return null;
					}
					j++;
				} while (j < cod.length() && cod.charAt(j) != ']');
				try {
					j++;
					lex = cod.substring(i, j);
					Token token = new Token(lex, Token.ENTERO, j);
					return token;
				} catch (Exception e) {
					analizador.lanzarAlerta("Debe cerrar los corchetes");
				}
			}
		}

		return null;
	}

	/**
	 * Intenta extraer un entero de la cadena cod a partir de la posici�n i,
	 * bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer un entero -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer un entero
	 *            - 0<=indice<codigo.length()
	 * @return el token entero o NULL, si el token en la posici�n dada no es un
	 *         entero. El Token se compone de el lexema, el tipo y la posici�n del
	 *         siguiente lexema.
	 */

	// Este m�todo usa el m�todo substring(), que se explica a continuaci�n:
	// x.substring( i, j ) retorna una nueva cadena que es una subcadena de la
	// cadena x.
	// La subcadena comienza en la posici�n i y
	// se extiende hasta el car�cter en la posici�n j-1.
	// Ejemplo: "universidad".substring(3,6) retorna "ver",

	public Token extraerSimboloAbrir(String cod, int i) {

		int j;
		String lex;
		j = i + 1;
		if (j <= cod.length() && (cod.charAt(i) == '\'' || cod.charAt(i) == '\"' || cod.charAt(i) == '�')) {

			lex = cod.substring(i, j);
			Token token = new Token(lex, Token.SIMBOLOABRIR, j);
			return token;
		}

		return null;
	}

	/**
	 * Intenta extraer un entero de la cadena cod a partir de la posici�n i,
	 * bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer un entero -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer un entero
	 *            - 0<=indice<codigo.length()
	 * @return el token entero o NULL, si el token en la posici�n dada no es un
	 *         entero. El Token se compone de el lexema, el tipo y la posici�n del
	 *         siguiente lexema.
	 */

	// Este m�todo usa el m�todo substring(), que se explica a continuaci�n:
	// x.substring( i, j ) retorna una nueva cadena que es una subcadena de la
	// cadena x.
	// La subcadena comienza en la posici�n i y
	// se extiende hasta el car�cter en la posici�n j-1.
	// Ejemplo: "universidad".substring(3,6) retorna "ver",

	public Token extraerSimboloCerrar(String cod, int i) {

		int j;
		String lex;
		j = i + 1;
		if (j <= cod.length() && (cod.charAt(i) == '\'' || cod.charAt(i) == '\"' || cod.charAt(i) == '?')) {

			lex = cod.substring(i, j);
			Token token = new Token(lex, Token.SIMBOLOCERRAR, j);
			return token;
		}

		return null;
	}


	/**
	 * Intenta extraer un operador aditivo de la cadena cod a partir de la posici�n
	 * i, bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer el operador aditivo -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer el
	 *            operador aditivo - 0<=i<codigo.length()
	 * @return el token operador aditivo o NULL, si el token en la posici�n dada no
	 *         es un operador aditivo.El Token se compone de el lexema, el tipo y la
	 *         posici�n del siguiente lexema.
	 */
	public Token extraerOperadorAditivo(String cod, int i) {
		if (cod.charAt(i) == 's' || cod.charAt(i) == 'r' || cod.charAt(i) == 'm' || cod.charAt(i) == 'd'
				|| cod.charAt(i) == 'M' || cod.charAt(i) == 'P' || cod.charAt(i) == 'R') {
			int j = i + 1;
			String lex = cod.substring(i, j);
			Token token = new Token(lex, Token.OPERADORADITIVO, j);
			return token;
		}
		return null;
	}

	/**
	 * Intenta extraer un operador de asignaci�n de la cadena cod a partir de la
	 * posici�n i, bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer el operador de
	 *            asignaci�n - codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer el
	 *            operador de asingaci�n - 0<=i<codigo.length()
	 * @return el token operador asignaci�n o NULL, si el token en la posici�n dada
	 *         no es un operador de asignaci�n. El Token se compone de el lexema, el
	 *         tipo y la posici�n del siguiente lexema.
	 */
	public Token extraerOperadorAsignacion(String cod, int i) {
		if (cod.charAt(i) == ':') {
			int j = i + 1;

			String lex = cod.substring(i, j);
			Token token = new Token(lex, Token.OPERADORASIGNACION, j);
			return token;
		}

		return null;
	}

	/**
	 * Intenta extraer un operador de asignaci�n de la cadena cod a partir de la
	 * posici�n i, bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer el operador de
	 *            asignaci�n - codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer el
	 *            operador de asingaci�n - 0<=i<codigo.length()
	 * @return el token operador asignaci�n o NULL, si el token en la posici�n dada
	 *         no es un operador de asignaci�n. El Token se compone de el lexema, el
	 *         tipo y la posici�n del siguiente lexema.
	 */
	public Token extraerOperadorLogico(String cod, int i) {
		if (cod.charAt(i) == '!' || cod.charAt(i) == '�') {
			int j = i + 1;

			String lex = cod.substring(i, j);
			Token token = new Token(lex, Token.OPERADORLOGICO, j);
			return token;
		}

		return null;
	}




	/**
	 * Intenta extraer un operador Relacional de la cadena cod a partir de la posici�n
	 * i, bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posici�n dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posici�n del siguiente lexema.
	 */
	public Token extraerOperadorRelacional(String cod, int i) {
		int j;
		String lex;
		if (cod.charAt(i) == '|' || cod.charAt(i) == ':') {
			j = i + 1;
			if (j < cod.length() && cod.charAt(j) == ':') {
				j++;
				lex = cod.substring(i, j);
				Token token = new Token(lex, Token.OPERADORRELACIONAL, j);
				return token;
			}
		} else if (cod.charAt(i) == '/' || cod.charAt(i) == '\\') {
			j = i + 1;
			if (j < cod.length() && cod.charAt(j) == ':') {
				j++;
				lex = cod.substring(i, j);
				Token token = new Token(lex, Token.OPERADORRELACIONAL, j);
				return token;
			}
			lex = cod.substring(i, j);
			Token token = new Token(lex, Token.OPERADORRELACIONAL, j);
			return token;

		}

		return null;
	}

	/**
	 * Intenta extraer un identificador de clase de la cadena cod a partir de la
	 * posici�n i, bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer un identificador -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer un
	 *            identificador - 0<=indice<codigo.length()
	 * @return el token identificaror o NULL, si el token en la posici�n dada no es
	 *         un identificador. El Token se compone de el lexema, el tipo y la
	 *         posici�n del siguiente lexema.
	 */

	public Token extraerIdentificadorClase(String cod, int i) {

		int j;
		String lex;
		if (cod.charAt(i) == '%') {
			j = i + 1;
			if (j < cod.length()) {
				do {

					j++;
				} while (j < cod.length() && cod.charAt(j) != '%');
				try {
					j++;
					lex = cod.substring(i, j);
					Token token = new Token(lex, Token.IDENTIFICADORCLASE, j);
					return token;
				} catch (Exception e) {
					analizador.lanzarAlerta("Debe cerrar el signo");
				}
			}
		}

		return null;
	}


	/**
	 * Intenta extraer un operador Relacional de la cadena cod a partir de la posici�n
	 * i, bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posici�n dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posici�n del siguiente lexema.
	 */
	public Token extraerCadenaCaracteres(String cod, int i) {

		int j;
		String lex;
		if (cod.charAt(i) == '*') {
			j = i + 1;
			if (j < cod.length()) {
				do {

					j++;
				} while (j < cod.length() && cod.charAt(j) != '*');
				try {
					j++;
					lex = cod.substring(i, j);
					Token token = new Token(lex, Token.CADENA, j);
					return token;
				} catch (Exception e) {
					analizador.lanzarAlerta("Debe cerrar el signo");
				}
			}
		}

		return null;
	}

	/**
	 * Intenta extraer un identificador de variable de la cadena cod a partir de la
	 * posici�n i, bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer un identificador -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer un
	 *            identificador - 0<=indice<codigo.length()
	 * @return el token identificaror o NULL, si el token en la posici�n dada no es
	 *         un identificador. El Token se compone de el lexema, el tipo y la
	 *         posici�n del siguiente lexema.
	 */

	public Token extraerIdentificadorMetodo(String cod, int i) {

		int j;
		String lex;
		if (cod.charAt(i) == '$') {
			j = i + 1;
			if (j < cod.length()) {
				do {

					j++;
				} while (j < cod.length() && cod.charAt(j) != '$');
				try {
					j++;
					lex = cod.substring(i, j);
					Token token = new Token(lex, Token.IDENTIFICADORMETODO, j);
					return token;
				} catch (Exception e) {
					analizador.lanzarAlerta("Debe cerrar el signo");
				}
			}
		}

		return null;
	}

	/**
	 * Intenta extraer un identificador de clase de la cadena cod a partir de la
	 * posici�n i, bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer un identificador -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer un
	 *            identificador - 0<=indice<codigo.length()
	 * @return el token identificaror o NULL, si el token en la posici�n dada no es
	 *         un identificador. El Token se compone de el lexema, el tipo y la
	 *         posici�n del siguiente lexema.
	 */

	public Token extraerIdentificadorVariable(String cod, int i) {

		int j;
		String lex;
		if (cod.charAt(i) == '#') {
			j = i + 1;
			if (j < cod.length()) {
				do {

					j++;
				} while (j < cod.length() && cod.charAt(j) != '#');
				try {
					j++;
					lex = cod.substring(i, j);
					Token token = new Token(lex, Token.IDENTIFICADORVARIABLE, j);
					return token;
				} catch (Exception e) {
					analizador.lanzarAlerta("Debe cerrar el signo");
				}
			}
		}

		return null;
	}

	/**
	 * Extraer un lexema no reconocido de la cadena cod a partir de la posici�n i.
	 * Antes de utilizar este m�todo, debe haberse intentado todos los otros m�todos
	 * para los otros tipos de token
	 * 
	 * @param cod - c�digo al cual se le va a extraer el token no reconocido -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a extraer el token no
	 *            reconocido - 0<=indice<codigo.length()
	 * @return el token no reconocido. El Token se compone de lexema, el tipo y la
	 *         posici�n del siguiente lexema.
	 * 
	 */
	public Token extraerNoReconocido(String cod, int i) {
		String lexema = cod.substring(i, i + 1);
		int j = i + 1;
		Token token = new Token(lexema, Token.NORECONOCIDO, j);
		return token;
	}

	/**
	 * Intenta extraer la palabra reservada de los enteros de la cadena cod a partir de la posici�n
	 * i, bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posici�n dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posici�n del siguiente lexema.
	 */
	public Token extraerPalabraEntero(String cod, int i) {

		int j = i;
		if (cod.charAt(j)=='e') {
			j++;
			if (cod.charAt(j)=='t') {
				j++;
				if (cod.charAt(j)=='s') {
					j++;
					String lexema = cod.substring(i, j); 
					Token token = new Token(lexema, Token.TIPODATO, j);
					return token;
				}
			}

		}
		return null;
	}

	/**
	 * Intenta extraer la palabra reservada de os rales de la cadena cod a partir de la posici�n
	 * i, bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posici�n dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posici�n del siguiente lexema.
	 */
	public Token extraerPalabraReales(String cod, int i) {

		int j = i;
		if (cod.charAt(j)=='r') {
			j++;
			if (cod.charAt(j)=='l') {
				j++;
				if (cod.charAt(j)=='s') {
					j++;
					String lexema = cod.substring(i, j); 
					Token token = new Token(lexema, Token.TIPODATO, j);
					return token;
				}
			}

		}
		return null;
	}

	/**
	 * Intenta extraer una palabra caracter de la cadena cod a partir de la posici�n
	 * i, bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posici�n dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posici�n del siguiente lexema.
	 */
	public Token extraerPalabraCaracteres(String cod, int i) {

		int j = i;
		if (cod.charAt(j)=='c') {
			j++;
			if (cod.charAt(j)=='r') {
				j++;
				if (cod.charAt(j)=='t') {
					j++;
					String lexema = cod.substring(i, j); 
					Token token = new Token(lexema, Token.TIPODATO, j);
					return token;
				}
			}

		}
		return null;
	}

	/**
	 * Intenta extraer una cadena de caraceres de la cadena cod a partir de la posici�n
	 * i, bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posici�n dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posici�n del siguiente lexema.
	 */
	public Token extraerPalabraCadenaCaracteres(String cod, int i) {

		int j = i;
		if (cod.charAt(j)=='c') {
			j++;
			if (cod.charAt(j)=='d') {
				j++;
				if (cod.charAt(j)=='n') {
					j++;
					String lexema = cod.substring(i, j); 
					Token token = new Token(lexema, Token.TIPODATO, j);
					return token;
				}
			}

		}
		return null;
	}

	/**
	 * Intenta extraer laa palabra reservada main de la cadena cod a partir de la posici�n
	 * i, bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posici�n dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posici�n del siguiente lexema.
	 */
	public Token extraerPalabraMain(String cod, int i) {

		int j = i;
		if (cod.charAt(j)=='p') {
			j++;
			if (cod.charAt(j)=='r') {
				j++;
				if (cod.charAt(j)=='n') {
					j++;
					String lexema = cod.substring(i, j); 
					Token token = new Token(lexema, Token.METODOPRINCIPAL, j);
					return token;
				}
			}

		}
		return null;
	}

	/**
	 * Intenta extraer la palabra reservada bucle con cod a partir de la posici�n
	 * i, bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posici�n dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posici�n del siguiente lexema.
	 */
	public Token extraerPalabraBucle(String cod, int i) {

		int j = i;
		if (cod.charAt(j)=='c') {
			j++;
			if (cod.charAt(j)=='r') {
				j++;
				if (cod.charAt(j)=='c') {
					j++;
					String lexema = cod.substring(i, j); 
					Token token = new Token(lexema, Token.BUCLE, j);
					return token;
				}
			}

		}
		return null;
	}

	/**
	 * Intenta extraer la palabra reservada desici�n cod a partir de la posici�n
	 * i, bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posici�n dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posici�n del siguiente lexema.
	 */
	public Token extraerPalabraDesicion(String cod, int i) {

		int j = i;
		if (cod.charAt(j)=='d') {
			j++;
			if (cod.charAt(j)=='s') {
				j++;
				if (cod.charAt(j)=='c') {
					j++;
					String lexema = cod.substring(i, j); 
					Token token = new Token(lexema, Token.DESICION, j);
					return token;
				}
			}

		}
		return null;
	}

	/**
	 * Intenta extraer la paalabra reservada clase de la cadena cod a partir de la posici�n
	 * i, bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posici�n dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posici�n del siguiente lexema.
	 */
	public Token extraerPalabraClase(String cod, int i) {

		int j = i;
		if (cod.charAt(j)=='c') {
			j++;
			if (cod.charAt(j)=='l') {
				j++;
				if (cod.charAt(j)=='s') {
					j++;
					String lexema = cod.substring(i, j); 
					Token token = new Token(lexema, Token.CLASE, j);
					return token;
				}
			}

		}
		return null;
	}

	/**
	 * Intenta extraer el separador de sentencia cod a partir de la posici�n
	 * i, bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posici�n dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posici�n del siguiente lexema.
	 */
	public Token extraerSeparadorSentencia(String cod, int i) {
		if (cod.charAt(i) == '-') {
			int j = i + 1;

			String lex = cod.substring(i, j);
			Token token = new Token(lex, Token.SEPARADORSENTENCIA, j);
			return token;
		}

		return null;
	}

	/**
	 * Intenta extraer el salto de l�nea de la cadena cod a partir de la posici�n
	 * i, bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posici�n dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posici�n del siguiente lexema.
	 */
	public Token extraerSaltoLinea(String cod, int i) {

		int j = i;
		if (cod.charAt(j)=='@') {
			j++;
			if (cod.charAt(j)=='-') {
				j++;
				String lexema = cod.substring(i, j); 
				Token token = new Token(lexema, Token.SALTOLINEA, j);
				return token;

			}

		}
		return null;
	}


	/**
	 * Intenta extraer el arroba de la cadena cod a partir de la posici�n
	 * i, bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posici�n dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posici�n del siguiente lexema.
	 */
	public Token extraerArroba(String cod, int i) {

		int j = i;
		if (cod.charAt(j)=='@') {
			j++;
			if (cod.charAt(j)=='@') {
				j++;
				String lexema = cod.substring(i, j); 
				Token token = new Token(lexema, Token.ARROBA, j);
				return token;

			}

		}
		return null;
	}

	/**
	 * Intenta extraer ula tabulaci�n de la cadena cod a partir de la posici�n
	 * i, bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posici�n dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posici�n del siguiente lexema.
	 */
	public Token extraerTabulacion(String cod, int i) {

		int j = i;
		if (cod.charAt(j)=='@') {
			j++;
			if (cod.charAt(j)=='!') {
				j++;
				String lexema = cod.substring(i, j); 
				Token token = new Token(lexema, Token.TABULACION, j);
				return token;

			}

		}
		return null;
	}

	/**
	 * Intenta extraer el terminal de la cadena cod a partir de la posici�n
	 * i, bas�ndose en el Aut�mata
	 * 
	 * @param cod - c�digo al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posici�n a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posici�n dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posici�n del siguiente lexema.
	 */
	public Token extraerTerminal(String cod, int i) {
		if (cod.charAt(i) == '.') {
			int j = i + 1;

			String lex = cod.substring(i, j);
			Token token = new Token(lex, Token.TERMINAL, j);
			return token;
		}

		return null;
	}

	/**
	 * Determina si un car�cter es un d�gito
	 * 
	 * @param caracter - Car�cter que se va a analizar - caracter!=null,
	 * @return true o false seg�n el car�cter sea un d�gito o no
	 */
	public boolean esDigito(char caracter) {
		return caracter >= '0' && caracter <= '9';
	}

	/**
	 * Determina si un car�cter es una letra
	 * 
	 * @param caracter - Car�cter que se va a analizar - caracter!=null,
	 * @return true o false seg�n el car�cter sea una letra o no
	 */
	public boolean esLetra(char caracter) {
		return (caracter >= 'A' && caracter <= 'Z') || (caracter >= 'a' && caracter <= 'z');
	}


}

