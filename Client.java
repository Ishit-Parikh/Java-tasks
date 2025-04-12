package chatting_application;

import java.io.*;
import java.util.*;
import java.net.*;

public class Client {

    private Socket client_Class_Passed_Socket;
    private BufferedReader bufferedReader_in_client_class;
    private BufferedWriter bufferedWriter_in_client_class;
    private String client_Username_class;

    public Client(Socket socket_of_client_class_In_Constructor, String client_Username_class_In_Constructor) {

        try {
            this.client_Class_Passed_Socket = socket_of_client_class_In_Constructor;
            this.bufferedWriter_in_client_class = new BufferedWriter(
                    new OutputStreamWriter(socket_of_client_class_In_Constructor.getOutputStream()));
            this.bufferedReader_in_client_class = new BufferedReader(
                    new InputStreamReader(socket_of_client_class_In_Constructor.getInputStream()));
            this.client_Username_class = client_Username_class_In_Constructor;
        } catch (IOException e) {
            close_Every_thing(client_Class_Passed_Socket, bufferedReader_in_client_class,
                    bufferedWriter_in_client_class);
        }

    }

    public void send_message() {
        try {
            bufferedWriter_in_client_class.write(client_Username_class);
            bufferedWriter_in_client_class.newLine();
            bufferedWriter_in_client_class.flush();

            Scanner scanner = new Scanner(System.in);
            while (client_Class_Passed_Socket.isConnected()) {
                String message_to_send = scanner.nextLine();
                bufferedWriter_in_client_class.write(client_Username_class + ": " + message_to_send);
                bufferedWriter_in_client_class.newLine();
                bufferedWriter_in_client_class.flush();
            }

        } catch (IOException e) {
            close_Every_thing(client_Class_Passed_Socket, bufferedReader_in_client_class,
                    bufferedWriter_in_client_class);
        }
    }

    public void listen_for_message() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String message_from_lobby;
                while (client_Class_Passed_Socket.isConnected()) {
                    try {
                        message_from_lobby = bufferedReader_in_client_class.readLine();
                        System.out.println(message_from_lobby);
                    } catch (IOException e) {
                        close_Every_thing(client_Class_Passed_Socket, bufferedReader_in_client_class,
                                bufferedWriter_in_client_class);
                    }
                }
            }
        }).start();
    }

    public void close_Every_thing(Socket client_Class_Passed_Socket, BufferedReader bufferedReader_in_client_class,
            BufferedWriter bufferedWriter_in_client_class) {
        try {
            if (bufferedReader_in_client_class != null) {
                bufferedReader_in_client_class.close();
            }

            if (bufferedWriter_in_client_class != null) {
                bufferedWriter_in_client_class.close();
            }
            if (client_Class_Passed_Socket != null) {
                client_Class_Passed_Socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username for lobby: ");
        String username = scanner.nextLine();

        Socket client_Socket = new Socket("localhost", 3354);
        Client client_object = new Client(client_Socket, username);
        client_object.listen_for_message();
        client_object.send_message();

    }

}
