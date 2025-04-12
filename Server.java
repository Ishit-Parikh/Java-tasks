package chatting_application;

import java.io.*;
import java.net.*;

public class Server {

    private final ServerSocket server_Socket;

    public Server(ServerSocket client_Passed_Server_Socket) {

        this.server_Socket = client_Passed_Server_Socket;

    }

    public void start_server() {
        try {
            while (!server_Socket.isClosed()) {
                Socket client_socket = server_Socket.accept();
                System.out.println("A new client has joined");
                Class_ClientHandler clients = new Class_ClientHandler(client_socket);

                Thread thread_that_handles_clients = new Thread(clients);
                thread_that_handles_clients.start();

            }
        } catch (IOException e) {

        }
    }

    public void close_server_Socket() {
        try {
            if (server_Socket != null) {
                server_Socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket server_Socket = new ServerSocket(3354);
        Server server_class_object = new Server(server_Socket);
        server_class_object.start_server();
    }

}
