/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cedartalkclient;

import edu.cedarville.jvolante.cedartalknetworking.Dispatcher;
import edu.cedarville.jvolante.cedartalknetworking.InvalidConnectionException;
import edu.cedarville.jvolante.cedartalknetworking.Message;
import java.io.IOException;
import java.net.Socket;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 *
 * @author kfilbrun
 */
public class ClientDispatcher extends Dispatcher{
    
    MainWindow parent;
    ReadableByteChannel input = null;
    
    public ClientDispatcher(Socket sc, MainWindow p) throws InvalidConnectionException, IOException{
        super(sc);
        parent = p;
    }
    
    protected void processIncoming(Message message){
        parent.processMessage(message);
    }
    
    protected boolean validateConnection(){
        Scanner s = new Scanner(in);
        Message m = new Message(s.nextLine());
        
        if(m.getID() == 6){
            onGoodConnection();
            return true;
        }
        else{
            return false;
        }
    };
}
