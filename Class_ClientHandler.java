package chatting_application;

import java.io.*;
import java.net.*;
import java.util.*;
public class Class_ClientHandler implements Runnable {

    public static ArrayList<Class_ClientHandler> connected_Clients = new ArrayList<>();
    private Socket server_Class_Passed_Socket;
    private BufferedReader bufferedReader_in_client_handler_class;
    private BufferedWriter bufferedWriter_in_client_handler_class;
    private String client_Username;

    public Class_ClientHandler(Socket server_Class_Passed_Socket_In_Constructor) {
        try {

            this.server_Class_Passed_Socket = server_Class_Passed_Socket_In_Constructor;
            this.bufferedWriter_in_client_handler_class = new BufferedWriter(
                    new OutputStreamWriter(server_Class_Passed_Socket_In_Constructor.getOutputStream()));
            this.bufferedReader_in_client_handler_class = new BufferedReader(
                    new InputStreamReader(server_Class_Passed_Socket_In_Constructor.getInputStream()));
            this.client_Username = bufferedReader_in_client_handler_class.readLine();
            connected_Clients.add(this);
            broadcast_Message("Server says: " + client_Username + " has joined the lobby");

        } catch (IOException e) {
            close_Every_Thing(server_Class_Passed_Socket, bufferedWriter_in_client_handler_class,
                    bufferedReader_in_client_handler_class);
        }
    }

    @Override
    public void run() {

        String recived_message_from_client;

        while (server_Class_Passed_Socket.isConnected()) {

            try {
                recived_message_from_client = bufferedReader_in_client_handler_class.readLine();
                broadcast_Message(recived_message_from_client);
            } catch (IOException e) {
                close_Every_Thing(server_Class_Passed_Socket, bufferedWriter_in_client_handler_class,
                        bufferedReader_in_client_handler_class);
                break;
            }

        }

    }

    public void broadcast_Message(String message_to_send) {
        for (Class_ClientHandler represent_client_in_for_loop : connected_Clients) {
            try {
                if (!represent_client_in_for_loop.client_Username.equals(client_Username)) {
                    represent_client_in_for_loop.bufferedWriter_in_client_handler_class.write(message_to_send);
                    represent_client_in_for_loop.bufferedWriter_in_client_handler_class.newLine();
                    represent_client_in_for_loop.bufferedWriter_in_client_handler_class.flush();
                }
            } catch (IOException e) {
                close_Every_Thing(server_Class_Passed_Socket, bufferedWriter_in_client_handler_class,
                        bufferedReader_in_client_handler_class);
            }
        }
    }

    public void remove_Client_Handler() {
        connected_Clients.remove(this);
        broadcast_Message("Server says: " + client_Username + " has left the lobby.");
    }

    public void close_Every_Thing(Socket server_Class_Passed_Socket, BufferedWriter buffered_Writer,
            BufferedReader buffered_Reader) {
        remove_Client_Handler();
        try {
            if (buffered_Reader != null) {
                buffered_Reader.close();
            }

            if (buffered_Writer != null) {
                buffered_Writer.close();
            }
            if (server_Class_Passed_Socket != null) {
                server_Class_Passed_Socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
