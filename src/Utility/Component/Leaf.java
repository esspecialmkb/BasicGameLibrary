/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Michael A. Bradford <SankofaDigitalMedia.com>
 */
public class Leaf implements Component{
    protected String name;
    protected String data;
    
    protected Composite proxyComponent;
    
    // DUPLICATED FROM COMPOSITE.JAVA
    // This byte-flag controls how the toString method builds it's output string from this data
    protected byte stringOutputMode = 0;

    public Leaf(String name){
        this.name = name; 
    }
    
    @Override
    public void operation() {
        // THIS IF BRACKET IS DUPLICATED CODE!!!!
        if(name != null){
            System.out.println("Leaf [ " + name + " ]-> operation() called.");
        }else{
            System.out.println("Leaf -> operation() called.");
        }
    }
    
    @Override
    public void operation(String command) {
        // THIS IF BRACKET IS DUPLICATED CODE!!!!
        if(name != null){
            System.out.println("Leaf [ " + name + " ]-> operation( " + command + " ) called.");
        }else{
            System.out.println("Leaf -> operation( " + command + " ) called.");
        }
        
        // DUPLICATED FROM COMPOSITE.JAVA
        // @TODO: COMMAND DESIGN PATTERN OPPORTUNITY!!!
        switch(command){
            // Stinging the case conditions (without break statements) should allow multiple cases to execute the same code
            case "dump":
                this.stringOutputMode = 0;
            case "toString":
            case "print":
                System.out.println(this);
                break;
        }
    }

    @Override
    public void add(Component component) {
        throw new UnsupportedOperationException("Not supported in Leaf objects."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Component component) {
        throw new UnsupportedOperationException("Not supported in Leaf objects."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Component> getChildren() {
        throw new UnsupportedOperationException("Not supported in Leaf objects."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString(){
        int tabLevel = 0;
        String output = "";
        output += "[ " + this.name + ":" + this.data;
        //There are no children involved with leaf objects
        output += "]";
        
        return output;
    }
    
    @Override
    public void write(DataOutputStream out, int flags) {
        
    }

    @Override
    public void read(DataInputStream in, int flags) {
        
    }
    
    @Override
    public void fromString(String in, int flags) {
        // Build this object from a sting
    }
    
}
