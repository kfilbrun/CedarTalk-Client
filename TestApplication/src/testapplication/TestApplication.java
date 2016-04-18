/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testapplication;

interface CallBack {
    void callBackTest();
}

/**
 *
 * @author kfilbrun
 */
public class TestApplication implements CallBack{

    /**
     * @param args the command line arguments
     */
    
    public TestApplication(){
        Caller c = new Caller(this);
    }
    
    public static void main(String[] args) {
        TestApplication t = new TestApplication();
    }
    
    @Override
    public void callBackTest(){
        System.out.println("It worked!");
    }
    
    public void printer(String msg){
        System.out.println(msg);
    }
    
}
