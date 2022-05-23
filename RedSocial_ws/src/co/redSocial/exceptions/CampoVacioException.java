package co.redSocial.exceptions;


public class CampoVacioException extends Exception {

	private static final long serialVersionUID = 1L;

	public CampoVacioException(String campo) {
       super("Debe llenar el campo para poder " + campo);
   }

}
