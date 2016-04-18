/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cedartalkclient;

import edu.cedarville.jvolante.cedartalknetworking.Dispatcher;
import edu.cedarville.jvolante.cedartalknetworking.InvalidConnectionException;
import edu.cedarville.jvolante.cedartalknetworking.Message;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

/**
 *
 * @author kfilbrun
 */
public class ClientDispatcher extends Dispatcher{
    
    MainWindow parent;
    
    public ClientDispatcher(SocketChannel sc, MainWindow p) throws InvalidConnectionException{
        super(sc);
        parent = p;
    }
    
    protected void processIncoming(Message message){
        parent.processMessage(message);
    }
    
    protected boolean validateConnection(ReadableByteChannel in, WritableByteChannel out){
        //TODO: Implement
        return false;
    };
}
