package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) throws InterruptedException {
        while (true) {
            try (Socket client = new Socket("localhost", 1488)) {
                Scanner scanner = new Scanner(System.in);
                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                Messages messages = new Messages();

                System.out.println("Connected successfully");

                Scaner scaner = new Scaner();
                while(true) {
                    Enter enter = scaner.scan(scanner.nextLine());

                    messages.strings.add(enter);

                    if ((enter == Enter.ADD)|(enter == Enter.ADD_MIN)){
                        StudyGroup group = Collection.addElement();
                        messages.strings.add(group);
                    }
                    if (enter == Enter.REMOVE_ID){
                        System.out.println("Enter ID");
                        messages.strings.add(scanner.nextLine());
                    }
                    if (enter == Enter.UPDATE){
                        System.out.println("Enter ID");
                        messages.strings.add(scanner.nextLine());
                        StudyGroup group = Collection.addElement();
                        messages.strings.add(group);
                    }
                    if (enter == Enter.EXIT){
                        out.close();
                        in.close();
                        client.close();
                        System.out.println("Exiting...");
                        break;
                    }

                    out.writeObject(messages);
                    out.flush();

                    String string = (String) in.readObject();

                    System.out.println(string);
                    messages.strings.clear();
                    out.reset();
                }

                System.out.println("Goodbye");
                System.exit(1);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Reconnecting...");
                Thread.sleep(10000);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
