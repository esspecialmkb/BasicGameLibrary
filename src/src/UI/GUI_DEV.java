

package UI;

import static UI.MeshElement.MeshBufferColor;
import static UI.MeshElement.MeshBufferIndex;
import static UI.MeshElement.MeshBufferVertex;
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
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
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
    
    public class UIText extends UIElement{
        // This object just makes a visible text object
        MeshElement mesh;
        String message;
        
        @Override
        protected void buildMesh(MeshElement nMesh){
            if(nMesh != null){
                // If a mesh has been provided, use it
                mesh = nMesh;
                super.buildMesh(mesh);
                return;
            }
            
            // DEMO TEXT
            BitmapFont fnt = assetManager.loadFont("Interface/Fonts/Default.fnt");
            txt = new BitmapText(fnt, false);
            txt.setBox(new Rectangle(0, 0, settings.getWidth(), settings.getHeight()));
            txt.setSize(fnt.getPreferredSize() * 2f);
            txt.setText("Demo Text");
            txt.setLocalTranslation(0, txt.getHeight(), 0);
            guiNode.attachChild(txt);

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
    UIText uitext0;

    public static void main(String[] args){
        GUI_DEV app = new GUI_DEV();
        app.start(); // start the game
    }

    // Initialize the application here
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
        
        // Event Handler Setup
        setupInputMappings();
    }
    
    protected void setupInputMappings(){
        // Test multiple inputs per mapping
        inputManager.addMapping("My Action",
                new KeyTrigger(KeyInput.KEY_SPACE),
                new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
        
        inputManager.addMapping("Mouse L", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("Mouse M", new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE));
        inputManager.addMapping("Mouse R", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        
        inputManager.addMapping("Mouse Right", new MouseAxisTrigger(MouseInput.AXIS_X, true));
        inputManager.addMapping("Mouse Left", new MouseAxisTrigger(MouseInput.AXIS_X, false));
        
        inputManager.addMapping("Mouse Up", new MouseAxisTrigger(MouseInput.AXIS_Y, true));
        inputManager.addMapping("Mouse Down", new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        
        inputManager.addMapping("Mouse Wheel Up", new MouseAxisTrigger(MouseInput.AXIS_WHEEL,false));
        inputManager.addMapping("Mouse Wheel Down", new MouseAxisTrigger(MouseInput.AXIS_WHEEL,true));

        // Test multiple listeners per mapping
        //inputManager.addListener(actionListener, "My Action");
        //inputManager.addListener(analogListener, "My Action");
        
        // Using the eventListeners from the main class
        /*
        inputManager.addListener(actionListener, "Mouse L");
        inputManager.addListener(actionListener, "Mouse M");
        inputManager.addListener(actionListener, "Mouse R");
        inputManager.addListener(analogListener, "Mouse Right");
        inputManager.addListener(analogListener, "Mouse Left");
        inputManager.addListener(analogListener, "Mouse Up");
        inputManager.addListener(analogListener, "Mouse Down");
        */
        
        // Using the eventListeners from the uiRoot object
        inputManager.addListener(uiRoot.actionListener, "Mouse L");
        inputManager.addListener(uiRoot.actionListener, "Mouse M");
        inputManager.addListener(uiRoot.actionListener, "Mouse R");
        inputManager.addListener(uiRoot.analogListener, "Mouse Right");
        inputManager.addListener(uiRoot.analogListener, "Mouse Left");
        inputManager.addListener(uiRoot.analogListener, "Mouse Up");
        inputManager.addListener(uiRoot.analogListener, "Mouse Down");
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
    
    protected UIText createUIText(String name, String message){
        // Create a text blurb
        UIText text = new UIText();
        
        text.x = 20;
        text.y = 20;
        text.z = -5;
        
        text.width = 200;
        text.height = 20;
        
        return text;
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
        uiRoot.uiTick();
    }
    
    // Event Listeners
    private ActionListener actionListener = new ActionListener(){
        public void onAction(String name, boolean pressed, float tpf){
            System.out.println(name + " = " + pressed);
        }
    };
    public AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {
            //System.out.println(name + " = " + (value/tpf));
        }
    };
}