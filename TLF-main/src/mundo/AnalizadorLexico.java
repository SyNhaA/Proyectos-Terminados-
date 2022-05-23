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

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.plaf.multi.MultiSliderUI;

import interfaz.InterfazAnalizadorLexico;

/**
 * Clase que modela un analizador léxico
 */

public class AnalizadorLexico {

	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	private InterfazAnalizadorLexico analizador;

	/**
	 * Extrae los tokens de un código fuente dado
	 * 
	 * @param cod - código al cual se le van a extraer los tokens - !=null
	 * @return vector con los tokens
	 */
	public ArrayList extraerTokens(String cod, InterfazAnalizadorLexico analizador) {
		Token token;
		ArrayList vectorTokens = new ArrayList();
		this.analizador = analizador;

		// El primer token se extrae a partir de posición cero
		int i = 0;

		// Ciclo para extraer todos los tokens
		while (i < cod.length()) {
			// Extrae el token de la posición i
			token = extraerSiguienteToken(cod, i);
			vectorTokens.add(token);
			i = token.darIndiceSiguiente();
		}
		return vectorTokens;
	}

	/**
	 * Extrae el token de la cadena cod a partir de la posición i, basándose en el
	 * Autómata
	 * 
	 * @param cod - código al cual se le va a extraer un token - codigo!=null
	 * @param i   - posición a partir de la cual se va a extraer el token - i>=0
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

		// Intenta extraer un operador de asignación
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

		// Intenta extraer palabra reservada desición
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

		// Intenta extraer el salto de línea
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
	 * Intenta extraer un entero de la cadena cod a partir de la posición i,
	 * basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer un entero -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer un entero
	 *            - 0<=indice<codigo.length()
	 * @return el token entero o NULL, si el token en la posición dada no es un
	 *         entero. El Token se compone de el lexema, el tipo y la posición del
	 *         siguiente lexema.
	 */

	// Este método usa el método substring(), que se explica a continuación:
	// x.substring( i, j ) retorna una nueva cadena que es una subcadena de la
	// cadena x.
	// La subcadena comienza en la posición i y
	// se extiende hasta el carácter en la posición j-1.
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
	 * Intenta extraer un entero de la cadena cod a partir de la posición i,
	 * basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer un entero -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer un entero
	 *            - 0<=indice<codigo.length()
	 * @return el token entero o NULL, si el token en la posición dada no es un
	 *         entero. El Token se compone de el lexema, el tipo y la posición del
	 *         siguiente lexema.
	 */

	// Este método usa el método substring(), que se explica a continuación:
	// x.substring( i, j ) retorna una nueva cadena que es una subcadena de la
	// cadena x.
	// La subcadena comienza en la posición i y
	// se extiende hasta el carácter en la posición j-1.
	// Ejemplo: "universidad".substring(3,6) retorna "ver",

	public Token extraerSimboloAbrir(String cod, int i) {

		int j;
		String lex;
		j = i + 1;
		if (j <= cod.length() && (cod.charAt(i) == '\'' || cod.charAt(i) == '\"' || cod.charAt(i) == '¿')) {

			lex = cod.substring(i, j);
			Token token = new Token(lex, Token.SIMBOLOABRIR, j);
			return token;
		}

		return null;
	}

	/**
	 * Intenta extraer un entero de la cadena cod a partir de la posición i,
	 * basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer un entero -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer un entero
	 *            - 0<=indice<codigo.length()
	 * @return el token entero o NULL, si el token en la posición dada no es un
	 *         entero. El Token se compone de el lexema, el tipo y la posición del
	 *         siguiente lexema.
	 */

	// Este método usa el método substring(), que se explica a continuación:
	// x.substring( i, j ) retorna una nueva cadena que es una subcadena de la
	// cadena x.
	// La subcadena comienza en la posición i y
	// se extiende hasta el carácter en la posición j-1.
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
	 * Intenta extraer un operador aditivo de la cadena cod a partir de la posición
	 * i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el operador aditivo -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            operador aditivo - 0<=i<codigo.length()
	 * @return el token operador aditivo o NULL, si el token en la posición dada no
	 *         es un operador aditivo.El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
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
	 * Intenta extraer un operador de asignación de la cadena cod a partir de la
	 * posición i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el operador de
	 *            asignación - codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            operador de asingación - 0<=i<codigo.length()
	 * @return el token operador asignación o NULL, si el token en la posición dada
	 *         no es un operador de asignación. El Token se compone de el lexema, el
	 *         tipo y la posición del siguiente lexema.
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
	 * Intenta extraer un operador de asignación de la cadena cod a partir de la
	 * posición i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el operador de
	 *            asignación - codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            operador de asingación - 0<=i<codigo.length()
	 * @return el token operador asignación o NULL, si el token en la posición dada
	 *         no es un operador de asignación. El Token se compone de el lexema, el
	 *         tipo y la posición del siguiente lexema.
	 */
	public Token extraerOperadorLogico(String cod, int i) {
		if (cod.charAt(i) == '!' || cod.charAt(i) == '¡') {
			int j = i + 1;

			String lex = cod.substring(i, j);
			Token token = new Token(lex, Token.OPERADORLOGICO, j);
			return token;
		}

		return null;
	}




	/**
	 * Intenta extraer un operador Relacional de la cadena cod a partir de la posición
	 * i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posición dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
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
	 * posición i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer un identificador -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer un
	 *            identificador - 0<=indice<codigo.length()
	 * @return el token identificaror o NULL, si el token en la posición dada no es
	 *         un identificador. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
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
	 * Intenta extraer un operador Relacional de la cadena cod a partir de la posición
	 * i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posición dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
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
	 * posición i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer un identificador -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer un
	 *            identificador - 0<=indice<codigo.length()
	 * @return el token identificaror o NULL, si el token en la posición dada no es
	 *         un identificador. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
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
	 * posición i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer un identificador -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer un
	 *            identificador - 0<=indice<codigo.length()
	 * @return el token identificaror o NULL, si el token en la posición dada no es
	 *         un identificador. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
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
	 * Extraer un lexema no reconocido de la cadena cod a partir de la posición i.
	 * Antes de utilizar este método, debe haberse intentado todos los otros métodos
	 * para los otros tipos de token
	 * 
	 * @param cod - código al cual se le va a extraer el token no reconocido -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a extraer el token no
	 *            reconocido - 0<=indice<codigo.length()
	 * @return el token no reconocido. El Token se compone de lexema, el tipo y la
	 *         posición del siguiente lexema.
	 * 
	 */
	public Token extraerNoReconocido(String cod, int i) {
		String lexema = cod.substring(i, i + 1);
		int j = i + 1;
		Token token = new Token(lexema, Token.NORECONOCIDO, j);
		return token;
	}

	/**
	 * Intenta extraer la palabra reservada de los enteros de la cadena cod a partir de la posición
	 * i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posición dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
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
	 * Intenta extraer la palabra reservada de os rales de la cadena cod a partir de la posición
	 * i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posición dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
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
	 * Intenta extraer una palabra caracter de la cadena cod a partir de la posición
	 * i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posición dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
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
	 * Intenta extraer una cadena de caraceres de la cadena cod a partir de la posición
	 * i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posición dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
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
	 * Intenta extraer laa palabra reservada main de la cadena cod a partir de la posición
	 * i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posición dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
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
	 * Intenta extraer la palabra reservada bucle con cod a partir de la posición
	 * i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posición dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
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
	 * Intenta extraer la palabra reservada desición cod a partir de la posición
	 * i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posición dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
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
	 * Intenta extraer la paalabra reservada clase de la cadena cod a partir de la posición
	 * i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posición dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
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
	 * Intenta extraer el separador de sentencia cod a partir de la posición
	 * i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posición dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
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
	 * Intenta extraer el salto de línea de la cadena cod a partir de la posición
	 * i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posición dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
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
	 * Intenta extraer el arroba de la cadena cod a partir de la posición
	 * i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posición dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
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
	 * Intenta extraer ula tabulación de la cadena cod a partir de la posición
	 * i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posición dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
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
	 * Intenta extraer el terminal de la cadena cod a partir de la posición
	 * i, basándose en el Autómata
	 * 
	 * @param cod - código al cual se le va a intentar extraer el operador Logico -
	 *            codigo!=null
	 * @param i   - posición a partir de la cual se va a intentar extraer el
	 *            operador Logico - 0<=i<codigo.length()
	 * @return el token operador Logico o NULL, si el token en la posición dada no
	 *         es un operador Logico. El Token se compone de el lexema, el tipo y la
	 *         posición del siguiente lexema.
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
	 * Determina si un carácter es un dígito
	 * 
	 * @param caracter - Carácter que se va a analizar - caracter!=null,
	 * @return true o false según el carácter sea un dígito o no
	 */
	public boolean esDigito(char caracter) {
		return caracter >= '0' && caracter <= '9';
	}

	/**
	 * Determina si un carácter es una letra
	 * 
	 * @param caracter - Carácter que se va a analizar - caracter!=null,
	 * @return true o false según el carácter sea una letra o no
	 */
	public boolean esLetra(char caracter) {
		return (caracter >= 'A' && caracter <= 'Z') || (caracter >= 'a' && caracter <= 'z');
	}


}

