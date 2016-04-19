/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cedartalkclient;

import edu.cedarville.jvolante.cedartalknetworking.Dispatcher;
import edu.cedarville.jvolante.cedartalknetworking.InvalidConnectionException;
import edu.cedarville.jvolante.cedartalknetworking.Message;
import java.nio.channels.Channels;
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
    
    public ClientDispatcher(SocketChannel sc, MainWindow p) throws InvalidConnectionException{
        super(sc);
        parent = p;
    }
    
    protected void processIncoming(Message message){
        parent.processMessage(message);
    }
    
    
    
    protected boolean validateConnection(){
        boolean test = in.isOpen();
        Scanner s = new Scanner(Channels.newInputStream(in));
        boolean test2 = in.isOpen();
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
