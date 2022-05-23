package co.redSocial.exceptions;


public class UsuarioExistenteException extends Exception {

	private static final long serialVersionUID = 1L;

	public UsuarioExistenteException(String nombre) {
       super("El usuario " + nombre + " ya se encuentra registrado");
   }

}
