package mygame;

import HumanoidDev.Humaniod;
import com.jme3.animation.SkeletonControl;
import com.jme3.app.SimpleApplication;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

import com.jme3.scene.VertexBuffer;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    Humaniod human;
    private Quaternion rotation = new Quaternion();

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Box b = new Box(1, .1f, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Gray);
        geom.setMaterial(mat);
        geom.move(0, -0.1f, 0);

        rootNode.attachChild(geom);
        
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
        
        flyCam.setMoveSpeed(25);
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
