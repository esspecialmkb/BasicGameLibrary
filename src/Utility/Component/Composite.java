/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility.Component;

import Utility.StringCharConst;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;

/**
 * The Composite class is a Component with children.
 * 
 * This class is directly used for generic objects that don't have concrete objects
 * 
 * @author Michael A. Bradford <SankofaDigitalMedia.com>
 */
public class Composite implements Component{
    protected String name;
    protected String data;
    
    protected ArrayList<Component> children;
    
    // This byte-flag controls how the toString method builds it's output string from this data
    protected byte stringOutputMode = 0;
    
    public Composite(String name){
        this.name = name;
        this.children = new ArrayList<>();
    }

    @Override
    public void operation() {
        if(name != null){
            System.out.println("Composite [ " + name + " ]-> operation() called.");
        }else{
            System.out.println("Composite -> operation() called.");
        }
        
    }
    
    @Override
    public void operation(String command) {
        // THIS IF BRACKET IS DUPLICATED CODE!!!!
        if(name != null){
            System.out.println("Composite [ " + name + " ]-> operation() called.");
        }else{
            System.out.println("Composite -> operation() called.");
        }
        
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
        System.out.println("Composite -> add() called.");
        this.children.add(component);
    }

    @Override
    public void remove(Component component) {
        System.out.println("Composite -> remove() called.");
        this.children.remove(component);
    }

    @Override
    public ArrayList<Component> getChildren() {
        System.out.println("Composite -> getChildren() called.");
        
        return this.children;
    }
    
    @Override
    public String toString(){
        int tabLevel = 0;
        String output = "";
        output += "[ " + this.name + ":" + this.data;
        
        //Build a string containing the data from this Composite
        if(this.children.isEmpty() == false){
            output += StringCharConst.NEWLINE;
            
            for(Component c : this.children){
                String componentOutput = c.toString();
                output += componentOutput + StringCharConst.NEWLINE;
            }
        }
        output += "]";
        
        return output;
    }

    @Override
    public void write(DataOutputStream out, int flags) {
        // Write the contents of this object
    }

    @Override
    public void read(DataInputStream in, int flags) {
        
    }
    
    @Override
    public void fromString(String in, int flags) {
        // Build this object from a sting
    }
}
