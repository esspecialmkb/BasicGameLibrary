

package UI;

import static UI.MeshElement.MeshBufferColor;
import static UI.MeshElement.MeshBufferIndex;
import static UI.MeshElement.MeshBufferNormal;
import static UI.MeshElement.MeshBufferVertex;
import static UI.UIRoot.UI_BUTTON_HEIGHT;
import static UI.UIRoot.UI_BUTTON_WIDTH;
import static UI.UIRoot.UI_PANEL_HEIGHT;
import static UI.UIRoot.UI_PANEL_WIDTH;
import static UI.UIRoot.UI_SCROLLBAR_HEIGHT;
import static UI.UIRoot.UI_SCROLLBAR_WIDTH;
import mygame.*;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;

/** Sample 1 - how to get started with the most simple JME 3 application.
 * Display a blue 3D cube and view from all sides by
 * moving the mouse and pressing the WASD keys. */
public class GUI_DEV extends SimpleApplication {
    
    //public class UIText
    
    public class UIButton extends UIElement{
        // Clickable button
        MeshElement mesh;
        
        @Override
        protected void buildMesh(MeshElement nMesh){
            
            if(nMesh != null){
                mesh = nMesh;
                super.buildMesh(mesh);
                return;
            }
            
            // Build a rectangle with local coordinates
            
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
            mesh.color[2] = 0;
            mesh.color[3] = 1;
            
            mesh.color[4] = 0;
            mesh.color[5] = 1;
            mesh.color[6] = 0;
            mesh.color[7] = 1;
            
            mesh.color[8] = 0;
            mesh.color[9] = 1;
            mesh.color[10] = 0;
            mesh.color[11] = 1;
            
            mesh.color[12] = 0;
            mesh.color[13] = 1;
            mesh.color[14] = 0;
            mesh.color[15] = 1;
            
            // Let the base class take care of assembling the mesh buffers
            super.buildMesh(mesh);
        }
    }
    
    UIHub uiHub;
    UIRoot uiRoot;
    UIButton uiButton0;
    UIPanel uiPanel0;
    UIScrollBar uiScroll0;

    public static void main(String[] args){
        GUI_DEV app = new GUI_DEV();
        app.start(); // start the game
    }

    @Override
    public void simpleInitApp() {
        createRoot();
        uiButton0 = createButton("uibutton0","Test");
        uiPanel0 = createPanel("uipanel0");
        uiScroll0 = createScrollBar("uiscroll0");
        
        uiRoot.addChild(uiButton0);
        uiRoot.addChild(uiPanel0);
        uiRoot.addChild(uiScroll0);
        
        uiRoot.validate();
        
        flyCam.setEnabled(false);
        inputManager.setCursorVisible(true);
    }
    
    protected void createRoot(){
        uiRoot = new UIRoot();
        
        uiRoot.name = "uiroot";
        
        uiRoot.children = new ArrayList<>();
        uiRoot.childNodes = new ArrayList<>();
        uiRoot.mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //uiRoot.mat.getAdditionalRenderState().setWireframe(true);
        uiRoot.mat.setBoolean("VertexColor", true);
        //uiRoot.mat.setColor("Color", ColorRGBA.Green);
        // Init the UIHub - NOT IMPLEMENTED YET!
        uiRoot.ui = new UIHub();
        
        // Init Node within this RootNode
        uiRoot.buildMesh(null);
        
        // Attach this RootNode to jME's RootNode
        guiNode.attachChild(uiRoot.uiNode);
        guiNode.setQueueBucket(RenderQueue.Bucket.Gui);
    }
    
    protected UIPanel createPanel(String name){
        // Create the panel object
        UIPanel panel = new UIPanel();
        
        // Setup parameters
        panel.x = 400;
        panel.y = 100;
        panel.z = -5;
        
        panel.width = UI_PANEL_WIDTH;
        panel.height = UI_PANEL_HEIGHT;
        
        panel.name = name;
        
        // Build the panel
        panel.buildMesh(null);
        return panel;
    }
    
    protected UIButton createButton(String name, String text){
        // Create a button
        UIButton button = new UIButton();
        
        // Setup parameters
        button.x = 100;
        button.y = 100;
        button.z = 5;
        
        button.width = UI_BUTTON_WIDTH;
        button.height = UI_BUTTON_HEIGHT;
        
        button.name = name;
        button.text = text;
        
        // Build the button
        button.buildMesh(null);
        
        return button;
    }
    
    protected UIScrollBar createScrollBar(String name){
        // Create a scrollbar
        UIScrollBar scroll = new UIScrollBar();
        
        scroll.children = new ArrayList<>();
        scroll.childNodes = new ArrayList<>();
        
        scroll.x = 200;
        scroll.y = 300;
        scroll.z = -5;
        
        scroll.width = UI_SCROLLBAR_WIDTH;
        scroll.height = UI_SCROLLBAR_HEIGHT;
        
        //valMin, valMax, valIncr, value;
        
        scroll.valMin = 0;
        scroll.valMax = 100;
        scroll.valIncr = 10;
        scroll.value = 50;
        
        scroll.name = name;
        
        scroll.buildMesh(null);
        
        return scroll;
    }
    
    public void createScene(){
        Box b = new Box(1, 1, 1); // create cube shape
        
        Geometry geom = new Geometry("Box", b);  // create cube geometry from the shape
        
        Material mat = new Material(assetManager,
          "Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
        
        mat.setColor("Color", ColorRGBA.Blue);   // set color of material to blue
        
        geom.setMaterial(mat);                   // set the cube's material
        
        rootNode.attachChild(geom);              // make the cube appear in the scene
    }
    
    Vector2f cursorPosition;
    
    @Override
    public void simpleUpdate(float tpf) {
        cursorPosition = inputManager.getCursorPosition();
        uiRoot.collideWidth((int)cursorPosition.x, (int)cursorPosition.y);
    }
}