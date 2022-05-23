package CasaDeApuestas;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPClient {

	// ---------------------------------- SERVIDOR  -----------------------------------------

	public static final int PORT = 9999;
	public static final String SERVER = "localhost";
	private Socket clientSideSocket;
	static Boolean run = true;
	public static Boolean bandera = true;
	
	public TCPClient() {
		System.out.println("El cliente se ha conectado ...");
	}

	public void init() {

		try {

			clientSideSocket = new Socket(SERVER, PORT);
			protocol(clientSideSocket);
			clientSideSocket.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ---------------------------------- PROTOCOLO -----------------------------------------

	public static final Scanner SCANNER = new Scanner(System.in);
	private static PrintWriter toNetwork;
	private static BufferedReader fromNetwork;

	public static void protocol(Socket socket) {

		try {
		
			while(run) {
				toNetwork = new PrintWriter(socket.getOutputStream(), true);
				fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				if(bandera) {
					System.out.println();
					System.out.println("Ingrese una opci�n: " + " ");
					System.out.println();
					System.out.println("- Para abrir una cuenta de ahorros digite:CREAR_CUENTA,nombre apellido");
					System.out.println("- Para depositar dinero en una cuenta digite: DEPOSITAR,cuenta,valor");
					System.out.println("- Para Cancelar una cuenta de ahorros digite: CANCELAR_CUENTA,cuenta");
					System.out.println("- Para retirar dinero de una cuenta digite: RETIRAR,cuenta,valor");
					System.out.println("- Para apostar dinero digite: APOSTAR,cuenta,tipo de apuesta,numero a apostar");
					System.out.println("- Para consultar su saldo digite: CONSULTAR,cuenta");
					System.out.println("- Para salir de la aplicaci�n digite: SALIR");
					System.out.println("- Para cerrar la aplicacion de apuestas digite: CERRAR" + "\n");
				}else {
					System.out.println("[From Server:] " + fromNetwork.readLine());
					bandera = true;
				}
				String fromUser = SCANNER.nextLine();
				toNetwork.println(fromUser);
				if(fromUser.equals("SALIR"))
				{
					run = false;
					System.out.println("Ha salido de la aplicacion");
				}else if(fromUser.contains("CARGAR")){
					
						String valor;
						
						valor = fromUser.split(",")[1];
						
						String ruta = "src//archivos//"+valor+".txt";
						
						FileReader fr=new FileReader(ruta);
						BufferedReader bfr=new BufferedReader(fr);
						String linea="";
						while((linea = bfr.readLine())!=null){
							try {
								toNetwork.println(linea);
								Thread.sleep(1000);
								fromNetwork.readLine();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}													
						}
						System.out.println();
						System.out.println("[Client] From server:" + " " +"�Carga completa con �xito!");
						bfr.close();
						fr.close();				
					
				}else if(fromUser.contains("CERRAR")) {
					bandera = false;
				}else{
					String fromServer = fromNetwork.readLine();
					System.out.println();
					System.out.println("[Client] From server:" + " " + fromServer);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static Boolean getBandera() {
		return bandera;
	}

	public static void setBandera(Boolean bandera) {
		TCPClient.bandera = bandera;
	}

	// ---------------------------------- M�TODO MAIN ---------------------------------------

	public static void main(String[] args) {
		TCPClient client = new TCPClient();
		client.init();
	}

}
