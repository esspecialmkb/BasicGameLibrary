/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility.Component;

/**
 *  The Component Design Pattern faciliates structuring objects into 'Components'.
 * 
 * Any object could be represented by this component design
 * 
 * This file tests the functionality of this DataComponent system
 * @author Michael A. Bradford <SankofaDigitalMedia.com>
 */
public class ComponentClientTest {
    
    static Component meshDataRoot;
    static Component skeletonDataRoot;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // Lets create a .obj file
        meshDataRoot = new Composite(".obj Root");
        
        meshDataRoot.add(new Leaf("v 0.0 0.0 0.0"));
        meshDataRoot.add(new Leaf("v 0.0 0.0 1.0"));
        meshDataRoot.add(new Leaf("v 0.0 1.0 0.0"));
        meshDataRoot.add(new Leaf("v 0.0 1.0 1.0"));
        meshDataRoot.add(new Leaf("v 1.0 0.0 0.0"));
        meshDataRoot.add(new Leaf("v 1.0 0.0 1.0"));
        meshDataRoot.add(new Leaf("v 1.0 1.0 0.0"));
        meshDataRoot.add(new Leaf("v 1.0 1.0 1.0"));
        
        meshDataRoot.add(new Leaf("vn  0.0  0.0  1.0"));
        meshDataRoot.add(new Leaf("vn  0.0  0.0 -1.0"));
        meshDataRoot.add(new Leaf("vn  0.0  1.0  0.0"));
        meshDataRoot.add(new Leaf("vn  0.0 -1.0  0.0"));
        meshDataRoot.add(new Leaf("vn  1.0  0.0  0.0"));
        meshDataRoot.add(new Leaf("vn -1.0  0.0  0.0"));
        
        meshDataRoot.add(new Leaf("f 1//2 7//2 5//2"));
        meshDataRoot.add(new Leaf("f 1//2 3//2 7//2"));
        meshDataRoot.add(new Leaf("f 1//6 4//6 3//6"));
        meshDataRoot.add(new Leaf("f 1//6 2//6 4//6"));
        meshDataRoot.add(new Leaf("f 3//3 8//3 7//3"));
        meshDataRoot.add(new Leaf("f 3//3 4//3 8//3"));
        meshDataRoot.add(new Leaf("f 5//5 7//5 8//5"));
        meshDataRoot.add(new Leaf("f 5//5 8//5 6//5"));
        meshDataRoot.add(new Leaf("f 1//4 5//4 6//4"));
        meshDataRoot.add(new Leaf("f 1//4 6//4 2//4"));
        meshDataRoot.add(new Leaf("f 2//1 6//1 8//1"));
        meshDataRoot.add(new Leaf("f 2//1 8//1 4//1"));
        
        //Call the empty operation
        meshDataRoot.operation("toString");
    }
    
}
