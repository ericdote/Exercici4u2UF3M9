package exercici4u2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JOptionPane;


public class SecureTimeClient {
    //Tras crear los certificados y las keys SSL declaramos lo siguiente
    //Declaramos el almacen de claves en las que confiamos
    private static final String PROPIETARI="javax.net.ssl.trustStore";
    //Damos la ruta de del almacen de claves
    private static final String v_PROPIETARI="C:\\Users\\Eric\\Desktop\\DAM\\M9\\UF3\\Exercici4u2\\src\\exercici4u2\\SSL\\ClienteTrustStore.jks";    
    //Declaramos el almacen de contraseñas en las que confiamos
    private static final String PROPIETARI2="javax.net.ssl.trustStorePassword";
    //Damos la contraseña del certificado
    private static final String v_PROPIETARI2="123456";
    static String HOST = "localhost";
    static int PORT = 8745;
    DataOutputStream outToServer;
    BufferedReader bf;
    //Declaramos SocketFactory y Socket, necesitamos ambos ya que si no, no podemos realizar el portocolo SSL
    SocketFactory ssF;
    Socket ss;

    public SecureTimeClient(String host, int port) throws IOException {
        //Establecemos los almacenes de trustore
        System.setProperty(PROPIETARI, v_PROPIETARI);
        System.setProperty(PROPIETARI2, v_PROPIETARI2);
        //Creamos los socolos para conectarnos con servidor mediante SSL
        ssF = (SSLSocketFactory)SSLSocketFactory.getDefault();
        ss = (SSLSocket)ssF.createSocket(HOST, PORT);
        outToServer = new DataOutputStream(ss.getOutputStream());
        bf = new BufferedReader(new InputStreamReader(ss.getInputStream()));
        String dia = JOptionPane.showInputDialog(null, "Diu el numero del dia: ", "Entrando", 3);
        String mes = JOptionPane.showInputDialog(null, "Diu el mes: ", "Entrando", 3);
        String any = JOptionPane.showInputDialog(null, "Diu l'any: ", "Entrando", 3);
        enviarAServer(Integer.parseInt(dia), Integer.parseInt(mes), Integer.parseInt(any));

    }

    public void enviarAServer(int dia, int mes, int any) throws IOException {
        outToServer.writeBytes(dia + " " + mes + " " + any);
        bf.close();
        outToServer.close();
        ss.close();
    }

    public static void main(String[] args) throws IOException {
        new SecureTimeClient(HOST, PORT);
    }
}
