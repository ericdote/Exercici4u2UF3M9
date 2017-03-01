package exercici4u2;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SecureTimeServer {
    //Lo mismo que en el cliente
    private static final String PROPIETARI = "javax.net.ssl.keyStore";
    private static final String v_PROPIETARI = "C:\\Users\\Eric\\Desktop\\DAM\\M9\\UF3\\Exercici4u2\\src\\exercici4u2\\SSL\\servidorKey.jks";
    private static final String PROPIETARI2 = "javax.net.ssl.keyStorePassword";
    private static final String v_PROPIETARI2 = "123456";
    private static final String[] nomDies = {"Diumenge", "Dilluns",
        "Dimarts",
        "Dimecres", "Dijous",
        "Divendres",
        "Dissabte"};
    //Declaramos el ServerSocketFactory ya que es necesario para poder declarar el protocolo
    private static ServerSocketFactory ssF;

    public static void main(String[] argv) throws Exception {
        try {
            //Lo mismo que en el cliente
            System.setProperty(PROPIETARI, v_PROPIETARI);
            System.setProperty(PROPIETARI2, v_PROPIETARI2);
            ssF = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
            ServerSocket srvSocket = (SSLServerSocket)ssF.createServerSocket(8745);

            while (true) {
                Socket ss = (SSLSocket)srvSocket.accept();
                Scanner in = new Scanner(ss.getInputStream());
                int[] data = new int[3];
                boolean ok = true;
                for (int i = 0; i < data.length; i++) {
                    if (in.hasNextInt()) {
                        data[i] = in.nextInt();
                    } else {
                        ok = false;
                    }
                }
                PrintStream out = new PrintStream(ss.getOutputStream());
                if (ok) {
                    data[1] -= 1;
                    GregorianCalendar cal = new GregorianCalendar(data[2],
                            data[1], data[0]);
                    int dia = cal.get(Calendar.DAY_OF_WEEK) - 1;
                    System.out.println("Aquest dia era " + nomDies[dia] + ".");

                } else {
                    System.out.println("Format de les dades incorrecte.");
                }
                ss.close();
            }
        } catch (Exception ex) {
            System.out.println("Error en les comuncacions: " + ex);
        }
    }
}
