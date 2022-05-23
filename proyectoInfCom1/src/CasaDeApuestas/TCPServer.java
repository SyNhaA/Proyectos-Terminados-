package CasaDeApuestas;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class TCPServer {

	// ---------------------------------- SERVIDOR -----------------------------------------

	public static final int PORT = 9999;

	private ServerSocket listener;
	private Socket serverSideSocket;
	private Casa casa = new Casa();
	private boolean apuestaAbierta = true;
	private ArrayList<Apuesta>apuestas = new ArrayList<>();

	int contador = 1;
	int contApuestas = 0;
	HashMap<String, Cuenta> hashMapCuentas = new HashMap<String, Cuenta>();
	int cuentasEnHashmap = 0;
	Boolean run = true;
	Boolean run2 = true;

	public TCPServer() {
		System.out.println("Servidor corriendo en el puerto:" + " " + PORT);
	}

	protected void init() {

		try {

			listener = new ServerSocket(PORT);
			while (true) {
				serverSideSocket = listener.accept();
				protocol(serverSideSocket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// ---------------------------------- PROTOCOLO -----------------------------------------

	private static PrintWriter toNetwork;
	private static BufferedReader fromNetwork;

	private void protocol(Socket serverSideSocket) {

		createStreams(serverSideSocket);



		try {

			// Ciclo para mantener el servidor constantemente funcioniando

			do{
				// Variables utilizadas
				Cuenta datosUsuario = new Cuenta();
				String nombre;
				double saldoADepositar = 0;
				double saldoARetirar = 0;
				double saldoAnterior = 0;
				String message = fromNetwork.readLine();

				if(message.equals("SALIR"))
				{
					run = false;
				}
				else{

					if(message.contains("CARGAR")){
						Casa casa = new Casa(0, 0);
						toNetwork.println("Carga completa");

					}

					// --------------------- CREAR CUENTA ----------------------------------

					if(message.contains("CREAR_CUENTA")) {
						nombre = message.split(",")[1];
						boolean verificarDatos = verificarLetras(nombre);
						if(verificarDatos==true){
							crearCuenta(nombre,datosUsuario);
						}else{
							toNetwork.println("ERROR: Datos incosistentes");
						}
					}

					// --------------------- DEPOSITAR DINERO ------------------------------

					else if(message.contains("DEPOSITAR")) {
						if(message.length()<=10){
							toNetwork.println("ERROR: Datos insuficientes");
						}else if(message.contains(",")){
							nombre = message.split(",")[1];
							Boolean esNumerico = isNumeric(nombre);
							if(esNumerico==true){
								depositar(saldoAnterior,saldoADepositar,message,nombre);
							}else{
								toNetwork.println("ERROR: Datos incosistentes");
							}
						}
					}

					// --------------------- CANCELAR CUENTA AHORROS -----------------------

					else if(message.contains("CANCELAR_CUENTA")) {
						if(message.length()<=16){
							toNetwork.println("ERROR: Datos insuficientes");
						}else if(message.contains(",")){
							nombre = message.split(",")[1];
							Boolean esNumerico = isNumeric(nombre);
							if(esNumerico==true){
								cancelarCuenta(nombre);
							}else{
								toNetwork.println("ERROR: Datos incosistentes");
							}
						}
					}



					// --------------------- RETIRAR DINERO --------------------------------

					else if(message.contains("RETIRAR")) {
						if(message.length()<=10){
							toNetwork.println("ERROR: Datos insuficientes");
						}else if(message.contains(",")){
							nombre = message.split(",")[1];
							Boolean esNumerico = isNumeric(nombre);
							if(esNumerico==true){
								retirar(saldoARetirar,saldoAnterior,nombre,message);
							}else{
								toNetwork.println("ERROR: Datos incosistentes");
							}
						}
					}

					// --------------------- APOSTAR --------------------------------

					else if(message.contains("APOSTAR")) {
						if(message.length()<=14){
							toNetwork.println("ERROR: Datos insuficientes");
						}else if(message.contains(",")){
							Boolean esNumerico = isNumeric(message.split(",")[1]);
							int numCuenta = Integer.parseInt(message.split(",")[1]);
							String tipoApuesta = message.split(",")[2];
							String numeroApostar = message.split(",")[3];
							if(esNumerico==true){
								apostar(numCuenta, tipoApuesta, numeroApostar);
							}else{
								toNetwork.println("ERROR: Datos incosistentes");
							}
						}
					}

					// --------------------- CERRAR APLICACION --------------------------------

					else if(message.contains("CERRAR")) {
						if(apuestas.size() == 0) {
							boolean run2 = true;
							String message2 = "";
							do {
								toNetwork.println("Seguro que desesa cerrar las apuestas?");
								message2 = fromNetwork.readLine();
								if(message2.contains("SI")) {
									toNetwork.println("Casa de apuestas cerrada");
									apuestaAbierta = false;
									run2 = false;
								}else if(message2.contains("NO")){
									toNetwork.println("Sigue apostando");
									run2 = false;
								}
							}while(run2);
							//toNetwork.println("");
						}else {
							toNetwork.println("Casa de apuestas cerrada");
							apuestaAbierta = false;
						}
					}

						// --------------------- CERRAR APLICACION --------------------------------

						else if(message.contains("CERRAR")) {
							if(apuestas.size() == 0) {

							}
						}

						// --------------------- CONSULTAR SALDO -------------------------------------

						else if(message.contains("CONSULTAR")) {
							if(message.length()<=10){
								toNetwork.println("ERROR: Datos insuficientes");
							}else if(message.contains(",")){
								nombre = message.split(",")[1];
								Boolean esNumerico = isNumeric(nombre);
								if(esNumerico==true||nombre.contains("b")){
									consultar(nombre);
								}else{
									toNetwork.println("ERROR: Datos incosistentes");
								}
							}else{
								toNetwork.println("ERROR: Datos incosistentes");
							}
						}else{
							toNetwork.println("El comando digitado no existe");
						}

					}

					// ---------------------- SORTEO -----------------------------------
					if(message.contains("SORTEO")) {
						if(message.length() > 11 || message.length() < 11 ){
							toNetwork.println("ERROR: Datos insuficientes");

						} else if (message.contains(",")) {
							sorteo(message.split(",")[1]);
						} else {
							toNetwork.println("ERROR: Datos insuficientes");
						}
					}

					//------------------ REPORTE -----------------------------------
					if(message.contains("REPORTE")){
						if(message != "REPORTE"){
							toNetwork.println("ERROR: Solicitud incorrecta");

						} else{
							realizarReporte();
						}
					}

				}while(run);
			} catch (IOException e) {
				e.printStackTrace();
			}


		}


		//------------------------------- REALIZA EL REPORTE ---------------------------------

		private void realizarReporte() {
			String reporte = "";
			reporte = reporte + "Cantidad de apuesta realizadas: " + "\n\t\t" + String.valueOf(apuestas.size()-1) + "\n\n";

			for (int i = 0; i < apuestas.size(); i++) {
				Cuenta cuenta = existeCuenta(apuestas.get(i).getNumeroCuenta());

				reporte = reporte +
						"Nombre: " + cuenta.getNombreUsuario() + "\t" +
						"Tipo de apuesta: " + apuestas.get(i).getTipoApuesta() + "\t" +
						"Numero en juego del apostador: " + apuestas.get(i).getNumeroCualApostar() + "\n";
			}

			reporte = reporte +
					"Cantidad de dinero recaudado en el tipo de apuesta A:  " + valorRecaudadoSegunTipo("A") + "\n" +
					"Cantidad de dinero recaudado en el tipo de apuesta B:  " + valorRecaudadoSegunTipo("B") + "\n" +
					"Cantidad de dinero recaudado en el tipo de apuesta C:  " + valorRecaudadoSegunTipo("C") + "\n\n";

			reporte = reporte + "\t\t" + "FIN DEL REPORTE";

			toNetwork.println(reporte);
		}

		// ---------------------------------- M�TODO CREAR CUENTA ---------------------------------------

		public void crearCuenta(String nombre, Cuenta datosUsuario) {

			// Condici�n para crear la primer cuenta (No tiene restricci�n)
			if(cuentasEnHashmap == 0) {
				Cuenta cuenta = new Cuenta(contador,nombre,0,0);
				String numCadena = String.valueOf(contador);//-1
				hashMapCuentas.put(numCadena, cuenta);
				cuentasEnHashmap++;
				datosUsuario =	hashMapCuentas.get(numCadena);
				contador++;
				toNetwork.println("�Cuenta creada con �xito!"+" "+datosUsuario.toString());
				System.out.println("[Server] From client se ha solicitado la creaci�n de una cuenta"+" "+"|"+" "+datosUsuario.toString());
			}else
				// En caso de que ya hayan cuentas creadas, debemos verificar que no hayan nombres repetidos y el procedimiento ser� diferente
			{
				String nombreActual = "";
				String nombreRepetido = "";
				Set<String> cuentas = hashMapCuentas.keySet();
				// for-each para recorrer todas las cuentas
				for(String valor: cuentas) {
					Cuenta nombres = hashMapCuentas.get(valor);
					nombreActual = nombres.getNombreUsuario();
					// En caso de haber cuentas repetidas, no se podr� crear la cuenta
					if(nombreActual.equals(nombre)) {
						nombreRepetido = nombreActual;
					}
				}
				if(nombreRepetido.equals(nombre)) {
					toNetwork.println("ERROR: Nombre repetido, por favor, int�ntelo de nuevo e ingrese otro nombre");
				}else {
					// En caso de que no hayan nombres repetidos, la cuenta se crear� normalmente
					Cuenta cuenta = new Cuenta(contador,nombre,0,0);
					String numCadena = String.valueOf(contador);
					hashMapCuentas.put(numCadena, cuenta);
					cuentasEnHashmap++;
					datosUsuario =	hashMapCuentas.get(numCadena);
					contador++;
					toNetwork.println("�Cuenta creada con �xito!"+" "+datosUsuario.toString());
					System.out.println("[Server] From client se ha solicitado la creaci�n de una cuenta"+" "+"|"+" "+datosUsuario.toString());
				}
			}
		}

		// ---------------------------------- M�TODO CANCELAR CUENTA ---------------------------------------

		private void cancelarCuenta(String valor) {
			int numeroActual = 0;
			Boolean existeCuenta = false;
			int numeroEscritoPorElUsuario = Integer.parseInt(valor);
			Set<String> cuentas = hashMapCuentas.keySet();
			Cuenta cuentaEncontrada = new Cuenta();
			// for-each para recorrer todas las cuentas
			for(String valores: cuentas) {
				Cuenta nombres = hashMapCuentas.get(valores);
				numeroActual = nombres.getNumeroCuenta();
				if(numeroActual==numeroEscritoPorElUsuario) {
					existeCuenta = true;
					cuentaEncontrada = hashMapCuentas.get(valores);
				}
			}
			if(cuentasEnHashmap==0){
				toNetwork.println("ERROR: A�n no hay cuentas registradas en el sistema, por favor cree una");
			}else{
				if(existeCuenta==true){
					if(cuentaEncontrada.getSaldo()==0 && cuentaEncontrada.getNumeroApuestas()==0){
						hashMapCuentas.remove(valor);
						System.out.println("[Server] From client se ha solicitad la cancelaci�n de una cuenta con n�mero:"+" "+cuentaEncontrada.getNumeroCuenta()+" "+"a nombre de:"+" "+cuentaEncontrada.getNombreUsuario());
						toNetwork.println("�La cuenta con n�mero"+" "+valor+" "+"a nombre de:"+" "+cuentaEncontrada.getNombreUsuario()+" "+"ha sido cancelada con �xito!");
						cuentasEnHashmap--;
					}else{
						toNetwork.println("ERROR: La cuenta no ha podido eliminarse debido a que el saldo no es 0.00");
					}
				}else{
					toNetwork.println("ERROR: La cuenta ingresada no existe");
				}
			}
		}

		// ---------------------------------- M�TODO DEPOSITAR ---------------------------------------

		private void depositar(double saldoAnterior,double saldoADepositar, String message, String valor) {
			Boolean existeCuenta = false;
			double nuevoSaldo = 0;
			int numeroEscritoPorElUsuario = Integer.parseInt(valor);
			int cuentaActual = 0;
			Boolean esNumerico = isNumeric(message.split(",")[2]);

			if(esNumerico){
				saldoADepositar = Double.parseDouble(message.split(",")[2]);
				Set<String> cuentas = hashMapCuentas.keySet();
				Cuenta cuentaEncontrada = new Cuenta(); 	//cuenta sin atributos para usar en metodos
				// for-each para recorrer todas las cuentas
				for(String valores: cuentas) {
					Cuenta nombres = hashMapCuentas.get(valores);
					cuentaActual = nombres.getNumeroCuenta();
					if(cuentaActual==numeroEscritoPorElUsuario) {
						saldoAnterior = nombres.getSaldo();
						existeCuenta = true;
						cuentaEncontrada = hashMapCuentas.get(valores);
					}
				}
				if(cuentasEnHashmap==0){
					toNetwork.println("ERROR: A�n no hay cuentas registradas en el sistema, por favor cree una");
				}else{
					if(existeCuenta==true){
						nuevoSaldo = cuentaEncontrada.getSaldo()+saldoADepositar;
						cuentaEncontrada.setSaldo(nuevoSaldo);
						System.out.println("[Server] From client se ha solicitado un deposito de:"+" "+saldoADepositar+"$"+" "+"en la cuenta:"+" "+cuentaEncontrada.getNumeroCuenta()+" | "+"saldo anterior:"+" "+saldoAnterior+
								" "+"nuevo saldo:"+" "+nuevoSaldo+" "+"$");
						toNetwork.println("�Deposito exitoso, se han depositado"+" "+saldoADepositar+"$"+" "+"en la cuenta:"+" "+cuentaEncontrada.getNumeroCuenta()+" "+"saldo anterior:"+" "+saldoAnterior+" "+"nuevo saldo:"+" "+nuevoSaldo+" "+"$");
					}else{
						toNetwork.println("ERROR: La cuenta ingresada no existe");
					}
				}
			}else{
				toNetwork.println("ERROR: Datos incosistentes");
			}

		}

		// ---------------------------------- M�TODO RETIRAR ---------------------------------------

		private void retirar(double saldoARetirar, double saldoAnterior, String valor, String message) {
			Boolean existeCuenta = false;
			double nuevoSaldoRetiro = 0;
			int numeroEscritoPorElUsuario = Integer.parseInt(valor);
			int cuentaActual = 0;
			Boolean esNumerico = isNumeric(message.split(",")[2]);

			if(esNumerico==true){
				saldoARetirar = Double.parseDouble(message.split(",")[2]);
				double saldoAnteriorRetiro = 0;

				Set<String> cuentas = hashMapCuentas.keySet();
				Cuenta cuentaEncontrada = new Cuenta();  // Para usar el constructor sin apuestas
				// for-each para recorrer todas las cuentas
				for(String valores: cuentas) {
					Cuenta nombres = hashMapCuentas.get(valores);
					cuentaActual = nombres.getNumeroCuenta();
					if(cuentaActual==numeroEscritoPorElUsuario) {
						saldoAnterior = nombres.getSaldo();
						existeCuenta = true;
						cuentaEncontrada = hashMapCuentas.get(valores);
						saldoAnteriorRetiro = nombres.getSaldo();
					}
				}
				if(cuentasEnHashmap==0){
					toNetwork.println("ERROR: A�n no hay cuentas registradas en el sistema, por favor cree una");
				}else{
					if(existeCuenta==true){
						if(saldoARetirar>=0 && saldoARetirar<=cuentaEncontrada.getSaldo()){
							nuevoSaldoRetiro = cuentaEncontrada.getSaldo()-saldoARetirar;
							cuentaEncontrada.setSaldo(nuevoSaldoRetiro);
							System.out.println("[Server] From client se ha solicitado un retiro de:"+" "+saldoARetirar+"$"+" "+"de la cuenta:"+" "+cuentaEncontrada.getNumeroCuenta());
							toNetwork.println("�Retiro aprobado, usted ha retirado:"+" "+saldoARetirar+"$"+" "+"de la cuenta:"+" "+cuentaEncontrada.getNumeroCuenta()+" "+" | "+
									"saldo anterior:"+" "+saldoAnteriorRetiro+"$"+" "+"nuevo saldo:"+" "+cuentaEncontrada.getSaldo()+"$");
						}else{
							toNetwork.println("ERROR: Usted no dispone de esa cantidad para retirar, por favor, consulte su saldo");
						}
					}else{
						toNetwork.println("ERROR: La cuenta ingresada no existe");
					}
				}
			}else{
				toNetwork.println("ERROR: Datos incosistentes");
			}
		}


		// ---------------------------------- M�TODO CONSULTAR ---------------------------------------

		private void consultar(String valor) {
			int numeroEscritoPorElUsuario = 0;
			Boolean existeCuenta = false;
			int cuentaActual = 0;
			Set<String> cuentas = hashMapCuentas.keySet();
			Cuenta cuentaEncontrada = new Cuenta();  // Para usar el constructor sin apostar

			numeroEscritoPorElUsuario = Integer.parseInt(valor);
			for(String valores: cuentas) {
				Cuenta nombres = hashMapCuentas.get(valores);
				cuentaActual = nombres.getNumeroCuenta();
				if(cuentaActual==numeroEscritoPorElUsuario) {
					existeCuenta = true;
					cuentaEncontrada = hashMapCuentas.get(valores);
				}
			}
			if(cuentasEnHashmap==0){
				toNetwork.println("ERROR: A�n no hay cuentas registradas en el sistema, por favor cree una");
			}else{
				if(existeCuenta==true){
					toNetwork.println("El saldo de la cuenta:"+" "+cuentaEncontrada.getNumeroCuenta()+" "+"es:"+" "+cuentaEncontrada.getSaldo()+"$");
				}else{
					toNetwork.println("ERROR: La cuenta ingresada no existe");
				}
			}

		}


		// ---------------------------------- M�TODO DE APOSTAR ---------------------------------------

		private void apostar(int numCuenta, String tipoApuesta, String numeroApostar) {
			//validar que el usuario este registrado
			if(apuestaAbierta) {
				Cuenta cuenta = existeCuenta(numCuenta);
				if(cuenta != null) {
					if(tipoApuesta.equalsIgnoreCase("A")) {
						if(numeroApostar.length() == 4) {
							if(cuenta.getSaldo() >= 10000) {
								cuenta.setSaldo(cuenta.getSaldo() - 10000);
								Apuesta apuesta = new Apuesta(numCuenta, tipoApuesta, numeroApostar);
								casa.setSaldo(casa.getSaldo() + 10000);
								apuestas.add(apuesta);
								toNetwork.println("�Apuesta registrada con Exito, Buena Suerte!");
							}else {
								toNetwork.println("ERROR: el saldo minimo es de 10.000 y su saldo es: " + cuenta.getSaldo());
							}
						}else {
							toNetwork.println("ERROR: El tipo de apuesta A debe de recibir 4 cifras");
						}
						//el valor de cada apuesta es de 10.000
					}else if(tipoApuesta.equalsIgnoreCase("B")) {
						if(numeroApostar.length() == 3) {
							if(cuenta.getSaldo() >= 10000) {
								cuenta.setSaldo(cuenta.getSaldo() - 10000);
								Apuesta apuesta = new Apuesta(numCuenta, tipoApuesta, numeroApostar);
								casa.setSaldo(casa.getSaldo() + 10000);
								apuestas.add(apuesta);
								toNetwork.println("�Apuesta registrada con Exito, Buena Suerte!");
							}else {
								toNetwork.println("ERROR: el saldo minimo es de 10.000 y su saldo es: " + cuenta.getSaldo());
							}
						}else {
							toNetwork.println("ERROR: El tipo de apuesta A debe de recibir 4 cifras");
						}
						//el valor de cada apuesta es de 10.000
					}else if(tipoApuesta.equalsIgnoreCase("C")) {
						if(numeroApostar.length() == 2) {
							if(cuenta.getSaldo() >= 10000) {
								cuenta.setSaldo(cuenta.getSaldo() - 10000);
								Apuesta apuesta = new Apuesta(numCuenta, tipoApuesta, numeroApostar);
								casa.setSaldo(casa.getSaldo() + 10000);
								apuestas.add(apuesta);
								toNetwork.println("�Apuesta registrada con Exito, Buena Suerte!");
							}else {
								toNetwork.println("ERROR: el saldo minimo es de 10.000 y su saldo es: " + cuenta.getSaldo());
							}
						}else {
							toNetwork.println("ERROR: El tipo de apuesta A debe de recibir 4 cifras");
						}
						//el valor de cada apuesta es de 10.000
					}else {
						toNetwork.println("ERROR: El tipo de apuesta " + tipoApuesta +" No es correcto, las opciones son A,B,C");
					}
				}else {
					toNetwork.println("ERROR: La cuenta ingresada no existe");
				}
			}else {
				toNetwork.println("ERROR: La casa est� cerrada");
			}

		}

		//---------------------------- METODO QUE REALIZA EL SORTEO --------------------------------------- //

		private void sorteo(String string) {
			int contadorCuentasGanadoras = 0;
			double valorAPagar = 0;
			valorAPagar = valorAPagar + valorRecaudadoSegunTipo("A") * 0.8;
			valorAPagar = valorAPagar + valorRecaudadoSegunTipo("B") * 0.7;
			valorAPagar = valorAPagar + valorRecaudadoSegunTipo("C") * 0.6;

			for (int i = 0; i < apuestas.size(); i++) {
				if(apuestas.get(i).getNumeroCualApostar() == string){
					contadorCuentasGanadoras++;
				}
			}

			if(contadorCuentasGanadoras == 0){
				System.out.println("No hubo ganador");
				return;
			}

			valorAPagar =  valorAPagar / contadorCuentasGanadoras;

			for (int i = 0; i < apuestas.size(); i++) {
				if(apuestas.get(i).getNumeroCualApostar() == string){
					setearNuevoSaldo(apuestas.get(i).getNumeroCuenta(), valorAPagar);
				}
			}
		}

		//-------------------------- METODO QUE DEVUELVE EL VALOR RECAUDADO SEGUN EL TIPO --------------  //
		private double valorRecaudadoSegunTipo(String tipoApuesta) {

			double suma = 0;
			for (int i = 0; i < apuestas.size(); i++) {
				if(apuestas.get(i).getTipoApuesta() == tipoApuesta){
					suma = suma + 10000;
				}
			}
			return suma;
		}

		//------------------------ LE SETTEA EL NUEVO SALDO AL GANADOR O GANADORES ------------------ //
		private void setearNuevoSaldo(int numeroCuenta, double saldo){

			Set<String> cuentas = hashMapCuentas.keySet();
			for(String valor: cuentas) {
				Cuenta cuenta = hashMapCuentas.get(valor);
				if(cuenta.getNumeroCuenta() == numeroCuenta) {
					double nuevoSaldo = cuenta.getSaldo() + saldo;
					cuenta.setSaldo(nuevoSaldo);
				}
			}
		}

		// ---------------------------------- M�TODO VERIFICAR SI EXISTE CUENTA --------------------------
		public Cuenta existeCuenta(int numCuenta) {
			// for-each para recorrer todas las cuentas
			Set<String> cuentas = hashMapCuentas.keySet();
			for(String valor: cuentas) {
				Cuenta cuenta = hashMapCuentas.get(valor);
				if(cuenta.getNumeroCuenta() == numCuenta) {
					return cuenta;
				}
			}
			return null;
		}

		// ---------------------------------- M�TODO QUE CUENTA LA CANTIDAD DE CIFRAS DE UN NUMERO --------------------------
		public static int contarCifras(String numApostar) {
			int numero = Integer.parseInt(numApostar);
			int cont = 0;
			while(numero > 0) {
				cont++;
				numero /=10;
			}
			return cont;
		}

		public boolean verificarLetras(String valor) {
			for (int x = 0; x < valor.length(); x++) {
				char c = valor.charAt(x);
				// Si no est� entre a y z, ni entre A y Z, ni es un espacio
				if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ')) {
					return false;
				}
			}
			return true;
		}

		private static boolean isNumeric(String valor){
			try {
				Double.parseDouble(valor);
				return true;
			} catch (NumberFormatException nfe){
				return false;
			}
		}

		// ---------------------------------- M�TODO DEL MENU ---------------------------------------
		private static String menu() {
			return "";
		}

		// ---------------------------------- M�TODO PARA CREAR STREAMS ---------------------------------------

		private static void createStreams(Socket socket) {

			try {

				toNetwork = new PrintWriter(socket.getOutputStream(), true);
				fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		// ---------------------------------- M�TODO MAIN ---------------------------------------

		public static void main(String[] args) {
			TCPServer server = new TCPServer();
			server.init();
		}

	}
