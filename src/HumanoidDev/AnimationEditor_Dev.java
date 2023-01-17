/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HumanoidDev;

import HumanoidAnimation.HumanoidRunAnimationData;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.Animation;
import com.jme3.animation.Bone;
import com.jme3.animation.Skeleton;
import com.jme3.animation.SkeletonControl;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.SkeletonDebugger;
import com.jme3.scene.shape.Sphere;
import java.util.ArrayList;

/**
 *
 * @author TigerSage
 */
public class AnimationEditor_Dev extends SimpleApplication implements ActionListener, AnalogListener, AnimEventListener{

    
    // Define the controls that we will implement
    public enum InputMapping{
        MoveLeft,
        MoveRight,
        MoveUp,
        MoveDown,
        RotateLeft,
        RotateRight,
        MouseSelect
        
        // We will need additional inputs...
        
        // Create PoseFrame
        // Edit PoseFrame
        // Delete PoseFrame
        
        // Create BoneTracks (from PoseFrame data)
    }
    
    // Define the frame data that will be used to construct bonetrack data
    public class PoseFrame{
        int id;
        int frameNumber;
        float time;
        
        //Root
        float[] rootRot;
        float[] rootTrans;
        float[] rootScale;
        
        // L Hip
        float[] lHipRot;
        float[] lHipTrans;
        float[] lHipScale;
        
        // L Knee
        float[] lKneeRot;
        float[] lKneeTrans;
        float[] lKneeScale;
        
        // L Ankle
        float[] lAnkleRot;
        float[] lAnkleTrans;
        float[] lAnkleScale;
        
        // R Hip
        float[] rHipRot;
        float[] rHipTrans;
        float[] rHipScale;
        
        // R Knee
        float[] rKneeRot;
        float[] rKneeTrans;
        float[] rKneeScale;
        
        // R Ankle
        float[] rAnkleRot;
        float[] rAnkleTrans;
        float[] rAnkleScale;
        
        // Waist
        float[] waistRot;
        float[] waistTrans;
        float[] waistScale;
        
        // Torso
        float[] torsoRot;
        float[] torsoTrans;
        float[] torsoScale;
        
        // Chest
        float[] chestRot;
        float[] chestTrans;
        float[] chestScale;
        
        // L Shoulder
        float[] lShoulderRot;
        float[] lShoulderTrans;
        float[] lShoulderScale;
        
        // L Elbow
        float[] lElbowRot;
        float[] lElbowTrans;
        float[] lElbowScale;
        
        // L Wrist
        float[] lWristRot;
        float[] lWristTrans;
        float[] lWristScale;
        
        // R Shoulder
        float[] rShoulderRot;
        float[] rShoulderTrans;
        float[] rShoulderScale;
        
        // R Elbow
        float[] rElbowRot;
        float[] rElbowTrans;
        float[] rElbowScale;
        
        // R Wrist
        float[] rWristRot;
        float[] rWristTrans;
        float[] rWristScale;
        
        // Head
        float[] headRot;
        float[] headTrans;
        float[] headScale;
    }
    
    // We need a mutable list of PoseFrames
    ArrayList<PoseFrame> poseFrames;
    
    boolean moveLeft, moveRight, moveUp, moveDown, rotateLeft, rotateRight, mouseSelect;
    
    private Vector3f camLocation = new Vector3f(0,20,0);
    private Vector3f lookAtDirection = new Vector3f(0,-0.8f,-0.2f);
    private float camDistance = 10;
    //private Camera cam = app.getCamera();
    
    // Reference to humanoid ragdoll
    Humaniod human;
    
    // Animation Controller
    AnimControl control;
    
    // Animation channel (groups bones together) used for play back
    AnimChannel animChannel;
    
    // An array of nodes and bones to represent the skeleton
    Node[] selectionBoneNodes;
    Bone[] selectionBones;
    
    // Data to keep track of the currently selected bone
    int currentBoneSelection = -1;
    int lastBoneSelected = -1;
    
    // An array of geometries to allow bone highlighting
    Geometry[] selectionGeometries;
    Sphere selectionMesh = new Sphere(12, 12, 0.75f, false, false);
    
    // Selection state materials
    Material selectionMatSelected;
    Material selectionMatDeselected;
    
    public static void main(String[] args) {
        AnimationEditor_Dev app = new AnimationEditor_Dev();
        app.start();
    }

    @Override public void simpleInitApp(){
        // Set the camera's angle and location
        //cam.lookAtDirection(camLocation, camLocation);
        //camLocation.set(cam.getDirection().mult(-camDistance));
        
        // Create human
        human = new Humaniod();
        human.initPrototype();
        
        // Create geometry to add human mesh to scene
        Geometry geom = new Geometry("Custom Mesh", human.mesh);
        human.model = new Node("Human");
        
        // Create material for the human
        Material mat = assetManager.loadMaterial("Materials/VertexColorMat.j3m");
        //mat.getAdditionalRenderState().setWireframe(true);
        geom.setMaterial(mat);
        human.model.attachChild(geom);
        
        selectionMatSelected = assetManager.loadMaterial("Materials/DevMaterial_LightGrey.j3m");
        selectionMatDeselected = assetManager.loadMaterial("Materials/DevMaterial_DarkGrey.j3m");
        
        // Create skeleton control
        SkeletonControl skeletonControl = new SkeletonControl(human.skeleton);
        skeletonControl.setEnabled(true);
        
        // Attach skeleton control 
        human.model.addControl(skeletonControl);
        rootNode.attachChild(human.model);
        
        // Setup flycam
        flyCam.setMoveSpeed(25);
        flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);
        //flyCam.setEnabled(false);
        
        // Debug skeleton setup
        SkeletonDebugger skeletonDebug = new SkeletonDebugger("skeleton", skeletonControl.getSkeleton());
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.White);
        mat2.getAdditionalRenderState().setDepthTest(false);
        skeletonDebug.setMaterial(mat2);
        skeletonDebug.getWires().setLineWidth(5);
        human.model.attachChild(skeletonDebug);
        
        // Debugging animation system
        human.model.addControl(new AnimControl(human.skeleton));
        control = human.model.getControl(AnimControl.class);
        Skeleton skeleton = control.getSkeleton();
        int boneCount = skeleton.getBoneCount();
        
        // TODO: Create ragdoll bone handles
        
        // Test animation creation cycle
        // TODO: Create the means to build animation data from pose data
        HumanoidRunAnimationData anim = new HumanoidRunAnimationData();
        anim.prepareBoneTracks();
        control.addAnim(anim.animation);
        control.addListener(this);
        animChannel = control.createChannel();
        //animChannel.setAnim("Test");
        
        
        Animation anim1 = control.getAnim("Test");
        System.out.println("Animation name" +anim1.getName());
        
        // Input mappings
        addInputMappings();
        createSelectionSkeleton();
        
    }
    
    @Override
    public void simpleUpdate(float tpf){
        // Check for mouse select
        if(mouseSelect){
            // Collision test for bone-joints
            int bSelect = updateSelectionSkeleton();
            
            // Update currentBoneSelected flag
            if(bSelect != currentBoneSelection){
                lastBoneSelected = currentBoneSelection;
                currentBoneSelection = bSelect;
                
                if(currentBoneSelection != -1){
                    // Change the material of the selected bone
                    System.out.println("NEW BONE :" + selectionBones[currentBoneSelection].getName());
                    selectionGeometries[currentBoneSelection].setMaterial(selectionMatSelected);
                }
                if(lastBoneSelected != -1){
                    // Set the material of the last selected bone back to normal
                    selectionGeometries[lastBoneSelected].setMaterial(selectionMatDeselected);
                }
            }
            
            mouseSelect = false;
        }
    }
    
    private void createSelectionSkeleton(){
        SkeletonControl control = human.model.getControl(SkeletonControl.class);
        int boneCount = human.skeleton.getBoneCount();
        
        // Create the bone arrays
        selectionBoneNodes = new Node[boneCount];
        selectionBones = new Bone[boneCount];
        selectionGeometries = new Geometry[boneCount];
        
        System.out.println("Num bones: "+ human.skeleton.getBoneCount());
        for(int b = 0; b < boneCount; b++){
            selectionBones[b] = human.skeleton.getBone(b);
            selectionBoneNodes[b] = control.getAttachmentsNode(selectionBones[b].getName());
            selectionGeometries[b] = new Geometry(selectionBones[b].getName(), selectionMesh); // wrap shape into geometry
            selectionGeometries[b].setMaterial(selectionMatDeselected);
            selectionBoneNodes[b].attachChild(selectionGeometries[b]);
            
            String name = selectionBones[b].getName();
            System.out.println("Bone "+b+": \" "+name+" \"");
            Vector3f bindPosition = selectionBones[b].getBindPosition();
            Quaternion bindRotation = selectionBones[b].getBindRotation();
            Vector3f localPosition = selectionBones[b].getLocalPosition();
            Quaternion localRotation = selectionBones[b].getLocalRotation();
            /*
            ArrayList<Bone> children = selectionBones[b].getChildren();
            System.out.println("Children: ");
            for(Bone child :children){
                System.out.println("    "+child.getName());
            }
            */
        }
    }
    
    private int updateSelectionSkeleton(){
        int boneSelectionResult = -1;
        // Reset results list.
        CollisionResults results = new CollisionResults();
        
        // Convert screen click to 3d position
        Vector2f click2d = inputManager.getCursorPosition();
        Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
        Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
        // Aim the ray from the clicked spot forwards.
        Ray mouseRay = new Ray(click3d, dir);
        
        // Aim the ray from camera location in camera direction
        // (assuming crosshairs in center of screen).
        Ray camRay = new Ray(cam.getLocation(), cam.getDirection());
        
        
        for(int b = 0; b < human.skeleton.getBoneCount(); b++){
            //selectionBones[b] = human.skeleton.getBone(b);
            //selectionGeometries[b] = new Geometry("A shape", selectionMesh); // wrap shape into geometry
            
            // Collect intersections between ray and all boneSelectionNodes in results list.
            selectionGeometries[b].collideWith(mouseRay, results);
            
            // Print the results so we see what is going on
            for (int i = 0; i < results.size(); i++) {
                // For each "hit", we know distance, impact point, geometry.
                float dist = results.getCollision(i).getDistance();
                Vector3f pt = results.getCollision(i).getContactPoint();
                String target = results.getCollision(i).getGeometry().getName();
                System.out.println("Selection #" + i + ": " + target + " at " + pt + ", " + dist + " WU away.");
            }
            // 5. Use the results -- we change the material and set the control index the selected geometry.
            if (results.size() > 0) {
                // The closest result is the target that the player picked:
                Geometry target = results.getClosestCollision().getGeometry();
                
                System.out.println("Object hit");
                
                boneSelectionResult = b;
                return b;
            }
            
            selectionGeometries[b].setMaterial(selectionMatDeselected);
            
            String name = selectionBones[b].getName();
            //System.out.println("Bone "+b+": \" "+name+" \"");
            Vector3f bindPosition = selectionBones[b].getBindPosition();
            Quaternion bindRotation = selectionBones[b].getBindRotation();
            Vector3f localPosition = selectionBones[b].getLocalPosition();
            Quaternion localRotation = selectionBones[b].getLocalRotation();
            /*
            ArrayList<Bone> children = selectionBones[b].getChildren();
            System.out.println("Children: ");
            for(Bone child :children){
                System.out.println("    "+child.getName());
            }
            */
        }
        
        return -1;
    }
    // Setup inputs
    // TODO: Need mappings for rotateUp and rotateDown
    // TODO: Need triggers for rotation
    private void addInputMappings(){
        /*inputManager.addMapping(InputMapping.MoveDown.name(), new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping(InputMapping.MoveUp.name(), new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping(InputMapping.MoveLeft.name(), new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping(InputMapping.MoveRight.name(), new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping(InputMapping.RotateLeft.name(), new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping(InputMapping.RotateRight.name(), new KeyTrigger(KeyInput.KEY_DOWN)); */
        
        inputManager.addMapping(InputMapping.MouseSelect.name(), new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        
        inputManager.addListener(this, InputMapping.MouseSelect.name());
    }
    
    // Input mappings send events here
    @Override
    public void onAction(String name, boolean isPressed, float tpf){
        InputMapping input = InputMapping.valueOf(name);
        switch(input){
            case MoveDown:
                break;
            case MoveUp:
                break;
            case MoveLeft:
                break;
            case MoveRight:
                break;
            case RotateLeft:
                break;
            case RotateRight:
                break;
            case MouseSelect:
                // Perform ray-cast testing for bone-joint intersection
                mouseSelect = isPressed;
                break;
        }
    }
    
    // Analog input mappings
    @Override
    public void onAnalog(String name, float value, float tpf) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
