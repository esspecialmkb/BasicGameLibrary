/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;
import java.util.ArrayList;

/**
 *
 * @author michael
 */
public class UIElement{
    // Base class for methods common to all elements
    int x,y,z,width,height;
    int transformX,transformY,transformZ;
    int id;

    // Name of the object in the gui tree
    String name;
    String text;

    ArrayList<UIElement> children;
    ArrayList<Node> childNodes;
    UIElement parent;

    // Reference to UIHub
    UIHub ui;

    // Mesh for displaying objects?
    Mesh uiMesh;
    
    // Geometry and Node for scene-graph transform
    Geometry uiGeometry;
    Node uiNode;
    
    // Prototype method to create mesh to display
    protected void buildMesh(MeshElement meshElement){
        // Would be nice to dump the excess MeshElement data when we're done with it...
        if(meshElement == null){
            uiNode = new Node();
            System.out.println("(null .super call) BuildMesh: " + name);
            return;
        }
        
        System.out.println("(.super call) BuildMesh: " + name);
        // Assemble uiMeshBuffers;
        // 4 verts
        int length = meshElement.vertices.length;
        // 6 indices
        int length1 = meshElement.index.length;
        // 16 colors
        int length2 = meshElement.color.length;
        
        uiMesh = new Mesh();
        uiMesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(meshElement.vertices));
        uiMesh.setBuffer(Type.Index, 1, BufferUtils.createIntBuffer(meshElement.index));
        uiMesh.setBuffer(Type.Color, 4, BufferUtils.createFloatBuffer(meshElement.color));
        uiMesh.updateBound();        
        
        // Geometry needs material!!!
        uiGeometry = new Geometry(this.name + "_Geometry",uiMesh);
        if(uiNode == null){
            uiNode = new Node();
        }
        
        uiNode.attachChild(uiGeometry);
    }

    // We will need to test if the object was clicked
    protected boolean collideWith(int x, int y){
        // Use the position and size of the element to determine collision
        if(x>this.x && x< (this.x + this.width)){
            if(y>this.y && y< (this.y + this.height)){
                //System.out.println(this.name + " Collide With");
                // Return true if we have detected a collision
                return true;
            }
        }
        // Return false if no collision
        return false;
    }
    
    protected String collideWithResponse(int x, int y){
        if(x>this.x && x< (this.x + this.width)){
            if(y>this.y && y< (this.y + this.height)){
                return this.name + " Collide With";
            }
        }
        return null;
    }
    
    // SET POSITION
    public void setPosition(int px, int py, int pz){
        // Z is height in the 2d viewport
        x = px; y = py; z = pz;
    }
    public void setPosition(int px, int py){
        setPosition(px,py,this.z);
    }
    
    // SET SIZE
    public void setSize(int nWidth, int nHeight){
        setWidth(nWidth);setHeight(nHeight);
    }
    public void setWidth(int nWidth){
        width = nWidth;
    }
    public void setHeight(int nHeight){
        height = nHeight;
    }
    
    // GET PARAMS
    public int getX(){return x;}
    public int getY(){return y;}
    public int getZ(){return z;}
    
    public int getWidth(){return width;}
    public int getHeight(){return height;}

    // SCENEGRAPH MANAGEMENT
    public void addChild(UIElement child){
        children.add(child);
        child.transformX = this.x + child.x;
        System.out.println(child.name + ": child.transformX = "+ child.x);
        child.transformY = this.y + child.y;
        System.out.println(child.name + ": child.transformY = "+ child.y);
        child.transformZ = this.z + child.z;
        
        Node n = new Node();
        n.setLocalTranslation(child.x, child.y, child.z);
        n.attachChild(child.uiNode);
        uiNode.attachChild(n);
        childNodes.add(n);
        //child.uiGeometry.setLocalTranslation(transformX, transformY, transformZ);
    }
    public void assignMaterial(Material mat){
        // Recursively call all children first
        if(children != null){
            for(UIElement c : children){
                c.assignMaterial(mat);
            }
        }
        // Assign mat
        if(uiGeometry != null){
           uiGeometry.setMaterial(mat);
        }
        
    }
    public void assignUIHub(UIHub newHub){
        // Recursively call all children first
        if(children != null){
            for(UIElement c : children){
                assignUIHub(newHub);
            }
        }
        
        // Assign uiHub
        this.ui.post(0, this.id, "New UIHub assign: " + newHub);
        int pingCount = this.ui.ping(0);
        if(pingCount > 0){
            // Grab messages
        }
        this.ui = newHub;
        this.ui.post(0, this.id, name);
    }
    public UIElement getParent(){
        return null;
    }

    // UPDATE
    public void onUpdate(){
        // Recursively call all children...
        if(children != null){
            for(UIElement c : children){
                
            }
        }
    }
}
