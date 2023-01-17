package Utility.Controllers;

import mygame.*;
import HumanoidDev.Humaniod;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.SkeletonControl;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.effect.shapes.EmitterSphereShape;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

import com.jme3.scene.VertexBuffer;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.shape.Sphere;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.util.BufferUtils;
import com.jme3.util.SkyFactory;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class TestController_Dev extends SimpleApplication {
    
    private BulletAppState bulletAppState;
    // Character Model
    Humaniod human;
    private Quaternion rotation = new Quaternion();
    
    // Character physics
    CharacterControl humanControl;
    Node humanNode;
    
    //bullet
    Sphere bullet;
    SphereCollisionShape bulletCollisionShape;
    
    //Materials
    Material matRock;
    Material matBullet;
    
    //explosion
    ParticleEmitter effect;
    
    //terrain
    TerrainQuad terrain;
    RigidBodyControl terrainPhysicsNode;
    
    //animation
    AnimChannel animationChannel;
    AnimChannel shootingChannel;
    AnimControl animationControl;
    float airTime = 0;
    //camera
    boolean left = false, right = false, up = false, down = false;
    ChaseCamera chaseCam;

    public static void main(String[] args) {
        TestController_Dev app = new TestController_Dev();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        stateManager.attach(bulletAppState);
        
        // Setup Inputs
        inputManager.addRawInputListener( new DevController(inputManager) );
        
        // Setup Input Triggers
        inputManager.addMapping("wireframe", new KeyTrigger(KeyInput.KEY_T));
        inputManager.addListener(actionListener, "wireframe");
        inputManager.addMapping("CharLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("CharRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("CharUp", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("CharDown", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("CharSpace", new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addMapping("CharShoot", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(actionListener, "CharLeft");
        inputManager.addListener(actionListener, "CharRight");
        inputManager.addListener(actionListener, "CharUp");
        inputManager.addListener(actionListener, "CharDown");
        inputManager.addListener(actionListener, "CharSpace");
        inputManager.addListener(actionListener, "CharShoot");
        
        
        
        // 
        
        // Create our Humanoid Ragdoll
        human = new Humaniod();
        human.initPrototype();
        
        // Create geometry to add humanoid mesh to scene
        Geometry hGeom = new Geometry("Custom Mesh", human.mesh);
        human.model = new Node("Human");
        
        Material hmat = assetManager.loadMaterial("Materials/VertexColorMat.j3m");
        hGeom.setMaterial(hmat);
        human.model.attachChild(hGeom);
        // Create skeleton control
        SkeletonControl skeletonControl = new SkeletonControl(human.skeleton);
        human.model.addControl(skeletonControl);
        rootNode.attachChild(human.model);
        
        // Create physics character
        CapsuleCollisionShape capsule = new CapsuleCollisionShape(3f, 4f);
        humanControl = new CharacterControl(capsule, 0.01f);
        //model = (Node) assetManager.loadModel("Models/Oto/Oto.mesh.xml");
        //model.setLocalScale(0.5f);
        human.model.addControl(humanControl);
        humanControl.setPhysicsLocation(new Vector3f(-140, 40, -10));
        rootNode.attachChild(human.model);
        getPhysicsSpace().add(humanControl);
        
        // SETUP CAMERA
        flyCam.setMoveSpeed(25);
        flyCam.setEnabled(false);
        chaseCam = new ChaseCamera(cam, human.model, inputManager);
        
        // Setup animation controller
        animationControl = human.model.getControl(AnimControl.class);
        animationControl.addListener(animEventListener);
        animationChannel = animationControl.createChannel();
        shootingChannel = animationControl.createChannel();
        shootingChannel.addBone(animationControl.getSkeleton().getBone("uparm.right"));
        shootingChannel.addBone(animationControl.getSkeleton().getBone("arm.right"));
        shootingChannel.addBone(animationControl.getSkeleton().getBone("hand.right"));
        
        // Load test model
        Node testLevel = (Node) assetManager.loadModel("Models/GravisTelum/MapDevTest.j3o");
        //Node scene = (Node) testLevel.getChild(0);
        //List<Spatial> children = scene.getChildren();
        
        Material testLevelMat = assetManager.loadMaterial("Materials/DevMaterial_DarkGrey.j3m");
        Material testLevelMat2 = assetManager.loadMaterial("Materials/DevMaterial_LightGrey.j3m");
        testLevel.setMaterial(testLevelMat);
        rootNode.attachChild(testLevel);
    }

    float time = 0;
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
        //TODO: add update code for skeleton rotation test
        time += tpf;
        
        // Rotate around X axis
        Quaternion rotate = new Quaternion();
        rotate.fromAngleAxis(tpf, Vector3f.UNIT_X);

        // Combine rotation with previous
        rotation.multLocal(rotate);
        
        // Set new rotation into bone
        //rotation.fromAngleAxis(hipRot * FastMath.RAD_TO_DEG, Vector3f.UNIT_X);
        
        //human.hipBone.setUserTransforms(Vector3f.ZERO, rotation,Vector3f.UNIT_XYZ);
        human.lKneeBone.setUserTransforms(Vector3f.ZERO, rotation.inverse(),Vector3f.UNIT_XYZ);
        // After changing skeleton transforms, must update world data
        human.skeleton.updateWorldVectors();
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    private PhysicsSpace getPhysicsSpace() {
        return bulletAppState.getPhysicsSpace();
    }
        
    // Event Listeners
    private ActionListener actionListener = new ActionListener(){
        public void onAction(String name, boolean pressed, float tpf){
            System.out.println(name + " = " + pressed);
        }
    };
    private PhysicsCollisionListener physicsCollisionListener = new PhysicsCollisionListener(){
        @Override
        public void collision(PhysicsCollisionEvent event) {
            
        }
        
    };
    public AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {
            //System.out.println(name + " = " + (value/tpf));
        }
    };
    private AnimEventListener animEventListener = new AnimEventListener(){
        @Override
        public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
}
