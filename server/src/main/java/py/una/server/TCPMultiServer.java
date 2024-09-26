package py.una.server;

import py.una.model.User;
import py.una.dao.UserDao;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.logging.Logger;

public class TCPMultiServer {

	//variables compartidas
	boolean listening = true;
	List<TCPServerHilo> hilosClientes; //almacenar los hilos (no se utiliza en el ejemplo, se deja para que el alumno lo utilice)
    UserDao userDao;
	List<User> usuarios;

    public void ejecutar() throws IOException {
        ServerSocket serverSocket = null;
        this.userDao = new UserDao();
        Logger logger = Logger.getLogger(TCPMultiServer.class.getName());

        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            logger.warning("No se puede abrir el puerto: 4444.");
            System.exit(1);
        }
        logger.info("Puerto abierto: 4444.");

        while (listening) {
        	
        	TCPServerHilo hilo = new TCPServerHilo(serverSocket.accept(), this);
            hilosClientes.add(hilo);
            hilo.start();
        }

        serverSocket.close();
        this.userDao.close();
    }

    public void addUser(User usuario) {
        userDao.addUser(usuario);
    }

    public String getUsers() {
        return userDao.getUsers();
    }

    public void deleteUser(String id) {
        userDao.deleteUser(id);
    }
    
    public static void main(String[] args) throws IOException {
    	
    	TCPMultiServer tms = new TCPMultiServer();
    	
    	tms.hilosClientes = new ArrayList<>();
    	tms.usuarios = new ArrayList<>();
    	
    	tms.ejecutar();
    	
    }

}
