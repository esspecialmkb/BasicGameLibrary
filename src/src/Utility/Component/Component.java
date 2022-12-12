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
 * The Component class is the cornerstone of the Virual File System.
 * 
 * Every object that can be described in the editor should extend this class.
 * 
 * Only the methods and functions common to ALL objects in the editor should exist here
 * @author Michael A. Bradford <SankofaDigitalMedia.com>
 */
public interface Component {
    public void operation();
    public void operation(String command);
    public void add(Component component);
    public void remove(Component component);
    public ArrayList<Component> getChildren();
    
    // New to I.D.E.
    public void fromString(String in, int flags);
    
    //File IO
    public void write(DataOutputStream out, int flags);
    public void read(DataInputStream in, int flags);
}
