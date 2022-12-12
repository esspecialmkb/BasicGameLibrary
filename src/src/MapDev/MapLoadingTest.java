/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapDev;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import java.util.List;

/**
 *
 * @author TigerSage
 */
public class MapLoadingTest extends SimpleApplication implements ActionListener{
    // Members for player and map
    Node player,playerRoot;
    Node map0;
    Node map1;
    int mapSelect = 1;
    
    // MEmbers for Physics Engine
    BulletAppState bulletAppState;
    RigidBodyControl landscape;
    CharacterControl playerPhyControl;
    
    //  Player control members
    Vector3f walkDirection = new Vector3f();
    boolean left = false, right = false, up = false, down = false, lookLeft = false, lookRight = false, lookUp = false, lookDown = false,fire = false;
    
    // Player view/heading members
    float hLookAt = 0,vLookAt = 0;
    
    // Camera tracking members
    Vector3f camDir = new Vector3f();
    Vector3f camLeft = new Vector3f();
    Vector3f camUp = new Vector3f();
    
    // Members for shooting
    Node shootables;
    Geometry mark;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MapLoadingTest app = new MapLoadingTest();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        // Set up crosshairs
        //setDisplayStatView(false);
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // crosshairs
        ch.setLocalTranslation( // center
          settings.getWidth() / 2 - ch.getLineWidth()/2,
          settings.getHeight() / 2 + ch.getLineHeight()/2, 0);
        guiNode.attachChild(ch);
        
        // Set up mark
        Sphere sphere = new Sphere(30, 30, 0.2f);
        mark = new Geometry("BOOM!", sphere);
        Material mark_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mark_mat.setColor("Color", ColorRGBA.Red);
        mark.setMaterial(mark_mat);
        
        // Prepare node to use for rayscan testing
        shootables = new Node("Shootables");
        rootNode.attachChild(shootables);
        
        // Set up Physics
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.setDebugEnabled(true);
        
        /** A white ambient light source. */ 
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient); 
        
        // Try to load the model
        map0 = (Node) assetManager.loadModel("Models/mapTesting.j3o");
        
        // Get the map mesh from the file
        map1 = (Node) map0.getChild(1);
        map0 = (Node) map0.getChild(0);
        
        // Get the player mesh starting point
        Vector3f playerStartingPosition = new Vector3f();
        
        // We need to decide which map we're loading
        switch(mapSelect){
            case 0:
                player = (Node) map0.getChild(0);
                playerStartingPosition = player.getLocalTranslation();
                // Remove the player object from the scene's tree before creating collision shape
                player.removeFromParent();
                
                // Set up collision detection for the scene with a CollisionShape
                CollisionShape mapShape = CollisionShapeFactory.createMeshShape(map0);
                landscape = new RigidBodyControl(mapShape,0);
                map0.addControl(landscape);
                
                shootables.attachChild(map0); // Add the map to shootables node
                break;
            case 1:
                player = (Node) map1.getChild("Player");
                playerStartingPosition = player.getLocalTranslation();
                // Remove the player object from the scene's tree before creating collision shape
                player.removeFromParent();
                
                
                
                // Set up collision detection for the scene with a CollisionShape
                CollisionShape mapShape1 = CollisionShapeFactory.createMeshShape(map1);
                landscape = new RigidBodyControl(mapShape1,0);
                map1.addControl(landscape);
                
                // The map feels small, scale it up
                landscape.getCollisionShape().setScale(new Vector3f(2,2,2));
                //map1.scale(2);
                playerStartingPosition.multLocal(2);
                
                shootables.attachChild(map1); // Add the map to shootables node
                break;
        }
        
        
        
        
        
        
        
        // Using a playerRoot node to fix floating CharacterController
        playerRoot = new Node("playerRoot Node");
        playerRoot.attachChild(player);
        
        
        
        // Set up collision for the player
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 2f, 1);
        playerPhyControl = new CharacterControl(capsuleShape,0.01f);
        playerPhyControl.setJumpSpeed(20);
        playerPhyControl.setFallSpeed(30);
        playerRoot.addControl(playerPhyControl);
        player.setLocalTranslation(0,-2,0);
        
        // Add the player to the root node 
        
        rootNode.attachChild(playerRoot);
        
        // Add the player and map the to physics space
        bulletAppState.getPhysicsSpace().add(landscape);
        bulletAppState.getPhysicsSpace().add(playerPhyControl);
        
        // Set up the gravity for the player
        playerPhyControl.setGravity(30);
        playerPhyControl.setPhysicsLocation(playerStartingPosition.add(0,5,0));
        
        
        // Reuse the flyby camera for rotation
        viewPort.setBackgroundColor(new ColorRGBA(0.7f,0.8f,1f,1f));
        //flyCam.setEnabled(false);
        flyCam.setMoveSpeed(50f);
        
        //setUpKeys();
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("LookLeft", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("LookRight", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("LookUp", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("LookDown", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping("Fire", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
        inputManager.addListener(this, "LookLeft");
        inputManager.addListener(this, "LookRight");
        inputManager.addListener(this, "LookUp");
        inputManager.addListener(this, "LookDown");
        inputManager.addListener(this, "Fire");
        
        //setUpLights();
    }
    
    // Custom action triggers
    public void onAction(String binding, boolean isPressed, float tpf){
        if(binding.equals("Left")){
            left = isPressed;
        }if(binding.equals("Right")){
            right = isPressed;
        }if(binding.equals("Up")){
            up = isPressed;
        }if(binding.equals("Down")){
            down = isPressed;
        }if(binding.equals("LookLeft")){
            lookLeft = isPressed;
        }if(binding.equals("LookRight")){
            lookRight = isPressed;
        }if(binding.equals("LookUp")){
            lookUp = isPressed;
        }if(binding.equals("LookDown")){
            lookDown = isPressed;
        }if(binding.equals("Fire") && !isPressed){
            // 1. Reset results list.
            CollisionResults results = new CollisionResults();
            // 2. Aim the ray from cam loc to cam direction.
            Ray ray = new Ray(cam.getLocation(), cam.getDirection());
            // 3. Collect intersections between Ray and Shootables in results list.
            // DO NOT check collision with the root node, or else ALL collisions will hit the
            // skybox! Always make a separate node for objects you want to collide with.
            shootables.collideWith(ray, results);
            // 4. Print the results
            System.out.println("----- Collisions? " + results.size() + "-----");
            for (int i = 0; i < results.size(); i++) {
              // For each hit, we know distance, impact point, name of geometry.
              float dist = results.getCollision(i).getDistance();
              Vector3f pt = results.getCollision(i).getContactPoint();
              String hit = results.getCollision(i).getGeometry().getName();
              System.out.println("* Collision #" + i);
              System.out.println("  You shot " + hit + " at " + pt + ", " + dist + " wu away.");
            }
            // 5. Use the results (we mark the hit object)
            if (results.size() > 0) {
              // The closest collision point is what was truly hit:
              CollisionResult closest = results.getClosestCollision();
              // Let's interact - we mark the hit with a red dot.
              mark.setLocalTranslation(closest.getContactPoint());
              rootNode.attachChild(mark);
            } else {
              // No hits? Then remove the red mark.
              rootNode.detachChild(mark);
            }
      
        }
    }
    private Quaternion rotation = new Quaternion();
    
    
    @Override
    public void simpleUpdate(float tpf){
        // We need to figure out the players walking direction based off the inputs
        
        // In first person, we can use the cam for View-Axis vectors
        camDir.set(cam.getDirection()).multLocal(0.6f);
        camLeft.set(cam.getLeft()).multLocal(0.4f);
        
        // Prepare the walk direction
        walkDirection.set(0, 0, 0);
        if (left) {
            walkDirection.addLocal(camLeft);
        }
        if (right) {
            walkDirection.addLocal(camLeft.negate());
        }
        if (up) {
            walkDirection.addLocal(camDir);
        }
        if (down) {
            walkDirection.addLocal(camDir.negate());
        }if(fire) {
            vLookAt = 0;
        }
        
        // Move the player
        playerPhyControl.setWalkDirection(walkDirection);
        
        // In first person mode, the cam follows the players P.O.V.
        Vector3f physicsLocation = playerPhyControl.getPhysicsLocation();
        cam.setLocation(physicsLocation.add(0, 3.5f, 0));
        
        
    }
    
}
