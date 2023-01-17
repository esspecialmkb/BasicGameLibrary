/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import static UI.MeshElement.MeshBufferColor;
import static UI.MeshElement.MeshBufferIndex;
import static UI.MeshElement.MeshBufferVertex;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author michael
 */
public class UIScrollBar extends UIElement{
    // Basic scrolling interface
    // REFACTOR THIS CLASS AS A NESTED UIElement!!!
    MeshElement mesh;

    // NESTED UIELEMENTS!!!
    UIElement scrollBar;
    UIElement scrollButton;
    UIElement incrButton;
    UIElement decrButton;

    int valMin, valMax, valIncr, value;

    @Override
    protected void buildMesh(MeshElement nMesh){
        if(nMesh != null){
            mesh = nMesh;
            super.buildMesh(mesh);
            return;
        }

        // Build a scrollbar using nested objects
        // Create this MeshElement as the background
        // Create the MeshElement with the proper buffer size
        mesh = new MeshElement();
        mesh.initBuffer(MeshBufferVertex, 4);
        mesh.initBuffer(MeshBufferIndex, 4);
        mesh.initBuffer(MeshBufferColor, 4);

        // Create Vertices - Top/Left Origin
        mesh.vertices[0] = new Vector3f(0           ,0              ,this.z);
        mesh.vertices[1] = new Vector3f(this.width  ,0              ,this.z);
        mesh.vertices[2] = new Vector3f(this.width  ,this.height    ,this.z);
        mesh.vertices[3] = new Vector3f(0           ,this.height    ,this.z);

        // Create Triangle Indices - Counterclockwise rotation
        mesh.index[0] = 0;
        mesh.index[1] = 1;
        mesh.index[2] = 2;
        mesh.index[3] = 2;
        mesh.index[4] = 3;
        mesh.index[5] = 0;

        // Create Colors
        mesh.color[0] = 0;
        mesh.color[1] = 1;
        mesh.color[2] = 1;
        mesh.color[3] = 1;

        mesh.color[4] = 0;
        mesh.color[5] = 1;
        mesh.color[6] = 1;
        mesh.color[7] = 1;

        mesh.color[8] = 0;
        mesh.color[9] = 1;
        mesh.color[10] = 1;
        mesh.color[11] = 1;

        mesh.color[12] = 0;
        mesh.color[13] = 1;
        mesh.color[14] = 1;
        mesh.color[15] = 1;

        // Create the scrollbar 'pieces'
        //buildScrollBar();
        buildScrollButton();

        // 'Assemble' the child elements to the container


        // Let the base class take care of assembling the mesh buffers
        super.buildMesh(mesh);

        // Explicitly add the children
        //this.uiNode.attachChild(scrollBar.uiNode);
        //this.uiNode.attachChild(scrollButton.uiNode);
        //this.addChild(scrollBar);
        addChild(scrollButton);
    }

    // This is the horizontal bar
    protected void buildScrollBar(){
        scrollBar = new UIElement();
        scrollBar.x = 0;
        scrollBar.y = 0;
        scrollBar.z = 0;
        scrollBar.width = this.width;
        scrollBar.height = 20;
        scrollBar.name = this.name + "_scrollBar";
        scrollBar.uiNode = new Node();
        MeshElement sBMesh = new MeshElement();
        sBMesh.initBuffer(MeshBufferVertex, 4);
        sBMesh.initBuffer(MeshBufferIndex, 4);
        sBMesh.initBuffer(MeshBufferColor, 4);


        // Create Vertices - Top/Left Origin
        // Use margins
        sBMesh.vertices[0] = new Vector3f(0           ,0              ,-0.25f);
        sBMesh.vertices[1] = new Vector3f(this.width  ,0              ,-0.25f);
        sBMesh.vertices[2] = new Vector3f(this.width  ,this.height  ,-0.25f);
        sBMesh.vertices[3] = new Vector3f(0           ,this.height  ,-0.25f);

        // Create Triangle Indices - Counterclockwise rotation
        sBMesh.index[0] = 0;
        sBMesh.index[1] = 1;
        sBMesh.index[2] = 2;
        sBMesh.index[3] = 2;
        sBMesh.index[4] = 3;
        sBMesh.index[5] = 0;

        // Create Colors
        sBMesh.color[0] = 0;
        sBMesh.color[1] = 1;
        sBMesh.color[2] = 1;
        sBMesh.color[3] = 1;

        sBMesh.color[4] = 0;
        sBMesh.color[5] = 1;
        sBMesh.color[6] = 1;
        sBMesh.color[7] = 1;

        sBMesh.color[8] = 0;
        sBMesh.color[9] = 1;
        sBMesh.color[10] = 1;
        sBMesh.color[11] = 1;

        sBMesh.color[12] = 0;
        sBMesh.color[13] = 1;
        sBMesh.color[14] = 1;
        sBMesh.color[15] = 1;

        // The base-class method builds the mesh - USE IT
        scrollBar.buildMesh(sBMesh);
    }

    // This is the button
    // This object's x position will change depending on value
    protected void buildScrollButton(){
        scrollButton = new UIPanel();
        scrollButton.x = 0;
        scrollButton.y = 0;
        scrollButton.z = 10;
        scrollButton.width = 6;
        scrollButton.height = 20;
        scrollButton.name = this.name + "_scrollButton";

        //scrollButton.uiNode = new Node();
        MeshElement sBTMesh = new MeshElement();
        sBTMesh.initBuffer(MeshBufferVertex, 4);
        sBTMesh.initBuffer(MeshBufferIndex, 4);
        sBTMesh.initBuffer(MeshBufferColor, 4);


        // Create Vertices - Top/Left Origin
        // Use margins
        sBTMesh.vertices[0] = new Vector3f(-3   ,-10              ,this.z+5f);
        sBTMesh.vertices[1] = new Vector3f(3    ,-10              ,this.z+5f);
        sBTMesh.vertices[2] = new Vector3f(3    ,this.height + 10    ,this.z+5f);
        sBTMesh.vertices[3] = new Vector3f(-3   ,this.height +10   ,this.z+5f);

        // Create Triangle Indices - Counterclockwise rotation
        sBTMesh.index[0] = 0;
        sBTMesh.index[1] = 1;
        sBTMesh.index[2] = 2;
        sBTMesh.index[3] = 2;
        sBTMesh.index[4] = 3;
        sBTMesh.index[5] = 0;

        // Create Colors
        sBTMesh.color[0] = 1;
        sBTMesh.color[1] = 1;
        sBTMesh.color[2] = 1;
        sBTMesh.color[3] = 1;

        sBTMesh.color[4] = 1;
        sBTMesh.color[5] = 1;
        sBTMesh.color[6] = 1;
        sBTMesh.color[7] = 1;

        sBTMesh.color[8] = 1;
        sBTMesh.color[9] = 1;
        sBTMesh.color[10] = 1;
        sBTMesh.color[11] = 1;

        sBTMesh.color[12] = 1;
        sBTMesh.color[13] = 1;
        sBTMesh.color[14] = 1;
        sBTMesh.color[15] = 1;

        // The base-class method builds the mesh - USE IT
        scrollButton.buildMesh(sBTMesh);
        //this.addChild(scrollBar);
    }
    
    // UPDATE
    @Override
    public void onUpdate(){
        // Recursively call all children...
        if(children != null){
            for(UIElement c : children){
                c.onUpdate();
            }
        }
        // Check UIHub for messages
        ui.ping(0);
        
        // We need to figure out where the scroll button needs to be
        // 
        float range = valMax - valMin;
        float div = (value*width)/valMax;
        scrollButton.uiNode.setLocalTranslation(div,0,5);
        
    }
}
