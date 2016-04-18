/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testapplication;

/**
 *
 * @author kfilbrun
 */
public class Caller {
    
    public Caller(TestApplication parent) {
        p = parent;
        register();
        parent.printer("The printer works!");
    }
    
    private TestApplication p;
    
    private void register() {
        p.callBackTest();
    }
} 