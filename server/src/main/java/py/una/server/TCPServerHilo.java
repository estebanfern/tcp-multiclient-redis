package py.una.server;

import py.una.model.User;

import java.net.*;
import java.io.*;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public class TCPServerHilo extends Thread {

    private Socket socket = null;
    private User user = null;
    TCPMultiServer servidor;
    private final Logger logger = Logger.getLogger(this.getClass().getName() + "-" + this.getId());

    public TCPServerHilo(Socket socket, TCPMultiServer servidor ) {
        super("TCPServerHilo");
        this.socket = socket;
        this.servidor = servidor;
    }

    public void run() {

        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    socket.getInputStream()));
            out.println("Bienvenido!");
            String inputLine, outputLine;

            while ((inputLine = in.readLine()) != null) {
                logger.info("Mensaje recibido: " + inputLine);
                if (inputLine.equals("/logout")) {
                    if (Objects.nonNull(this.user)) {
                        servidor.deleteUser(this.user.getUid());
                        outputLine = String.format("Usuario [%s] deslogueado", user.getUsername());
                        this.user  = null;
                    } else {
                        outputLine = "No tienes una sesión activa!";
                    }
                } else if (inputLine.equals("/exit")) {
                    break;
                } else if (inputLine.startsWith("/login")) {
                    inputLine = inputLine.trim();
                    String[] parts = inputLine.split("\\s+");
                    if (parts.length > 1) {
                        String nombreUsuario = parts[1];
                        this.user = new User(
                                UUID.randomUUID().toString(),
                                nombreUsuario
                        );
                        servidor.addUser(this.user);
                        outputLine = String.format("Usuario [%s] logueado", nombreUsuario);
                    } else {
                        outputLine = "Error: Debe proporcionar un nombre de usuario después de /login";
                    }
                } else if (inputLine.equals("/users")) {
                	outputLine = String.format("Lista de usuarios: %s", servidor.getUsers()) ;
                } else {
                    outputLine = String.format("Debe proporcionar una opción válida. Recibido: [%s]", inputLine);
                }

                out.println(outputLine);
            }

            out.close();
            in.close();
            socket.close();
            if (Objects.nonNull(this.user)) {
                servidor.deleteUser(this.user.getUid());
                this.user  = null;
            }
            logger.info("Finalizando Hilo");

        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }
}
