

package UI;

import static UI.UIRoot.UI_BUTTON_HEIGHT;
import static UI.UIRoot.UI_BUTTON_WIDTH;
import static UI.UIRoot.UI_PANEL_HEIGHT;
import static UI.UIRoot.UI_PANEL_WIDTH;
import static UI.UIRoot.UI_SCROLLBAR_HEIGHT;
import static UI.UIRoot.UI_SCROLLBAR_WIDTH;
import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;

/** Sample 1 - how to get started with the most simple JME 3 application.
 * Display a blue 3D cube and view from all sides by
 * moving the mouse and pressing the WASD keys. */
public class GUI_DEV extends SimpleApplication {
    
    //public class UIText
    
    private BitmapText txt;
    
    
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
        
        // DEMO TEXT
        BitmapFont fnt = assetManager.loadFont("Interface/Fonts/Default.fnt");
        txt = new BitmapText(fnt, false);
        txt.setBox(new Rectangle(0, 0, settings.getWidth(), settings.getHeight()));
        txt.setSize(fnt.getPreferredSize() * 2f);
        txt.setText("Demo Text");
        txt.setLocalTranslation(0, txt.getHeight(), 0);
        guiNode.attachChild(txt);
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
        uiRoot.collideWith((int)cursorPosition.x, (int)cursorPosition.y);
    }
}