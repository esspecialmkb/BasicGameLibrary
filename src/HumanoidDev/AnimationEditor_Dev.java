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
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
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
        MouseSelect,
        
        // We will need additional inputs...
        RotateBonePos,
        RotateBoneNeg,
        SelectAxisX,
        SelectAxisY,
        SelectAxisZ,
        
        // Create PoseFrame
        CreatePoseFrame,
        // Edit PoseFrame
        EditPoseFrame,
        // Delete PoseFrame
        DeletePoseFrame,
        
        // Create BoneTracks (from PoseFrame data)
        UpdateBoneTracks,
        
        // Manipulate the axis incrementation value
        RotateValueIncr,
        RotateValueDecr,
        
        // Navigate the pose frames
        NextPoseFrame,
        PrevPoseFrame,
        
        // Manipulate the frame's time value
        FrameTimePos,
        FrameTimeNeg
    }
    
    // We need a mutable list of PoseFrames
    // TODO: ADD A REFERENCE POSEFRAME FOR THE CURRENT FRAME
    ArrayList<PoseFrame> poseFrames;
    int currentPoseFrame = 0;
    
    // HUD
    BitmapText boneText;
    BitmapText axisText;
    BitmapText valueText;
    BitmapText rotationText;
    
    boolean moveLeft, moveRight, moveUp, moveDown, rotateLeft, rotateRight, mouseSelect;
    boolean rotateBonePos, rotateBoneNeg, selectAxisX, selectAxisY, selectAxisZ;
    boolean rotateValueIncr, rotateValueDecr, nextPoseFrame, prevPoseFrame;
    private Vector3f camLocation = new Vector3f(0,20,0);
    private Vector3f lookAtDirection = new Vector3f(0,-0.8f,-0.2f);
    private float camDistance = 10;
    //private Camera cam = app.getCamera();
    
    // Current axis
    int currentAxis = 1;
    
    // Axis increments
    float[] axisIncrements = {0.1f,0.5f,1,5,10,15};
    int currentAxisIncrement = 2;
    
    // Reference to humanoid ragdoll
    // TODO: USE THIS OBJECT FOR BONE REFERENCING
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
    

    @Override 
    public void simpleInitApp(){
        // Set the camera's angle and location
        //cam.lookAtDirection(camLocation, camLocation);
        //camLocation.set(cam.getDirection().mult(-camDistance));
        
        // Create human
        createHuman();
        
        // Create skeleton control
        createAnimationSkeleton();
        
        // Setup flycam
        setupCamera();
        
        // Setup animation system
        human.model.addControl(new AnimControl(human.skeleton));
        control = human.model.getControl(AnimControl.class);
        
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
        
        // Create the pose frame array
        createPoseFrameTimeline();
        
        // Create a primitive HUD
        // First line for the axis selection
        boneText = new BitmapText(guiFont, false);
        boneText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        boneText.setColor(ColorRGBA.White);                             // font color
        boneText.setText("Current bone selection:");             // the text
        boneText.setLocalTranslation(0, settings.getHeight()/* - hudText.getLineHeight()*/, 0); // position
        guiNode.attachChild(boneText);
        
        axisText = new BitmapText(guiFont, false);
        axisText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        axisText.setColor(ColorRGBA.White);                             // font color
        axisText.setText("Current axis selection:");             // the text
        axisText.setLocalTranslation(0, settings.getHeight()- axisText.getLineHeight(), 0); // position
        guiNode.attachChild(axisText);
        
        valueText = new BitmapText(guiFont, false);
        valueText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        valueText.setColor(ColorRGBA.White);                             // font color
        valueText.setText("Current Increment Value: " + axisIncrements[currentAxisIncrement]);             // the text
        valueText.setLocalTranslation(0, settings.getHeight()- (valueText.getLineHeight()*2), 0); // position
        guiNode.attachChild(valueText);
        
        rotationText = new BitmapText(guiFont, false);
        rotationText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        rotationText.setColor(ColorRGBA.White);                             // font color
        rotationText.setText("Current Rotation Value: " + axisIncrements[currentAxisIncrement]);             // the text
        rotationText.setLocalTranslation(0, settings.getHeight()- (rotationText.getLineHeight()*3), 0); // position
        guiNode.attachChild(rotationText);
    }
    
    @Override
    public void simpleUpdate(float tpf){
        
        // Check for mouse select
        if(mouseSelect){
            // Collision test for bone-joints
            int bSelect = collideTestSelectionSkeleton();
            
            // Update currentBoneSelected flag
            if(bSelect != currentBoneSelection){
                lastBoneSelected = currentBoneSelection;
                currentBoneSelection = bSelect;
                
                if(currentBoneSelection != -1){
                    // Change the material of the selected bone
                    System.out.println("NEW BONE :" + selectionBones[currentBoneSelection].getName());
                    selectionGeometries[currentBoneSelection].setMaterial(selectionMatSelected);
                    boneText.setText("Current bone selection: " + selectionBones[currentBoneSelection].getName().toUpperCase()); 
                }
                if(lastBoneSelected != -1){
                    // Set the material of the last selected bone back to normal
                    selectionGeometries[lastBoneSelected].setMaterial(selectionMatDeselected);
                }
            }if(bSelect == -1){
                boneText.setText("Current bone selection: NULL"); 
            }
            
            mouseSelect = false;
        }
        
        // Check bone rotation commands
        if(rotateBonePos){
            rotateBoneSelection(axisIncrements[currentAxisIncrement]*FastMath.DEG_TO_RAD);
            rotateBonePos = false;
        }if(rotateBoneNeg){
            rotateBoneSelection(-1*axisIncrements[currentAxisIncrement]*FastMath.DEG_TO_RAD);
            rotateBoneNeg = false;
        }
        
        // Manipulate the increment value
        if(rotateValueIncr){
            currentAxisIncrement++;
            if(currentAxisIncrement > (axisIncrements.length-1)){
                currentAxisIncrement = axisIncrements.length-1;
            }
            valueText.setText("Current Increment Value: " + axisIncrements[currentAxisIncrement]);             // the text
            rotateValueIncr = false;
        }if(rotateValueDecr){
            currentAxisIncrement--;
            if(currentAxisIncrement < 0){
                currentAxisIncrement = 0;
            }
            valueText.setText("Current Increment Value: " + axisIncrements[currentAxisIncrement]);             // the text
            rotateValueDecr = false;
        }
        
        // Check axis-select commands
        if(selectAxisX){
            currentAxis = 2;
            axisText.setText("Current axis selection: X"); 
            selectAxisX = false;
        }if(selectAxisY){
            currentAxis = 0;
            axisText.setText("Current axis selection: Y"); 
            selectAxisY = false;
        }if(selectAxisZ){
            currentAxis = 1;
            axisText.setText("Current axis selection: Z"); 
            selectAxisZ = false;
        }
        
        rotationText.setText("Current Rotation Value: " + axisIncrements[currentAxisIncrement]);
    }
    
    private void rotateBoneSelection(float value){
        // Get the bone from the skeleton
        Bone bone = human.skeleton.getBone(currentBoneSelection);
        // Grab rotation and local position
        Vector3f localPosition = bone.getLocalPosition();
        Quaternion localRotation = bone.getLocalRotation();
        
        float[] angles = {0,0,0};
        // Convert the quaternion into array (yaw,roll,pitch)
        angles = localRotation.toAngles(angles);
        
        angles[currentAxis] += value;
        
        localRotation.fromAngles(angles);
        
        bone.setLocalRotation(localRotation);
    }
    
    private Quaternion getBoneSelectionRotation(){
        // Get the bone from the skeleton
        Bone bone = human.skeleton.getBone(currentBoneSelection);
        // Grab rotation and local position
        
        return bone.getLocalRotation();
    }
    
    private Vector3f getBoneSelectionPosition(){
        // Get the bone from the skeleton
        Bone bone = human.skeleton.getBone(currentBoneSelection);
        // Grab rotation and local position
        
        
        return bone.getLocalPosition();
    }
    
    private void createSelectionSkeleton(){
        SkeletonControl control = human.model.getControl(SkeletonControl.class);
        int boneCount = human.skeleton.getBoneCount();
        
        selectionMatSelected = assetManager.loadMaterial("Materials/DevMaterial_LightGrey.j3m");
        selectionMatDeselected = assetManager.loadMaterial("Materials/DevMaterial_DarkGrey.j3m");
        
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
    
    // Collision test for selection skeleton
    private int collideTestSelectionSkeleton(){
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
        
        // Test collision with all bones
        for(int b = 0; b < human.skeleton.getBoneCount(); b++){
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
    
    // Create a UI element for the timeline - in dev
    private void createPoseFrameTimeline(){
        poseFrames = new ArrayList();
        currentPoseFrame = 0;
    }
    
    private void createNewPoseFrame(){
        PoseFrame newFrame = new PoseFrame();
        
        newFrame.getBoneData(human);
        poseFrames.add(newFrame);
        
        currentPoseFrame++;
    }
    
    public void createHuman(){
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
    }
    
    public void createAnimationSkeleton(){
        SkeletonControl skeletonControl = new SkeletonControl(human.skeleton);
        skeletonControl.setEnabled(true);
        
        // Attach skeleton control 
        human.model.addControl(skeletonControl);
        rootNode.attachChild(human.model);
        
        // Debug skeleton setup
        SkeletonDebugger skeletonDebug = new SkeletonDebugger("skeleton", skeletonControl.getSkeleton());
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.White);
        mat2.getAdditionalRenderState().setDepthTest(false);
        skeletonDebug.setMaterial(mat2);
        skeletonDebug.getWires().setLineWidth(5);
        human.model.attachChild(skeletonDebug);
    }
    
    public void setupCamera(){
        flyCam.setMoveSpeed(25);
        flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);
        //flyCam.setEnabled(false);
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
        
        inputManager.addMapping(InputMapping.RotateBonePos.name(), new KeyTrigger(KeyInput.KEY_PERIOD));
        inputManager.addMapping(InputMapping.RotateBoneNeg.name(), new KeyTrigger(KeyInput.KEY_COMMA));
        inputManager.addMapping(InputMapping.SelectAxisX.name(), new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping(InputMapping.SelectAxisY.name(), new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping(InputMapping.SelectAxisZ.name(), new KeyTrigger(KeyInput.KEY_L));
        
        inputManager.addMapping(InputMapping.CreatePoseFrame.name(), new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping(InputMapping.EditPoseFrame.name(), new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping(InputMapping.DeletePoseFrame.name(), new KeyTrigger(KeyInput.KEY_LEFT));
        
        inputManager.addMapping(InputMapping.RotateValueIncr.name(), new KeyTrigger(KeyInput.KEY_U));
        inputManager.addMapping(InputMapping.RotateValueDecr.name(), new KeyTrigger(KeyInput.KEY_I));
        
        inputManager.addMapping(InputMapping.NextPoseFrame.name(), new KeyTrigger(KeyInput.KEY_O));
        inputManager.addMapping(InputMapping.PrevPoseFrame.name(), new KeyTrigger(KeyInput.KEY_P));
        
        inputManager.addMapping(InputMapping.FrameTimePos.name(), new KeyTrigger(KeyInput.KEY_N));
        inputManager.addMapping(InputMapping.FrameTimeNeg.name(), new KeyTrigger(KeyInput.KEY_M));
        
        
        inputManager.addMapping(InputMapping.MouseSelect.name(), new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        
        inputManager.addListener(this, InputMapping.RotateBonePos.name(),
                                       InputMapping.RotateBoneNeg.name(),
                                       InputMapping.SelectAxisX.name(),
                                       InputMapping.SelectAxisY.name(),
                                       InputMapping.SelectAxisZ.name(),
                                       InputMapping.CreatePoseFrame.name(),
                                       InputMapping.EditPoseFrame.name(),
                                       InputMapping.DeletePoseFrame.name(),
                                       InputMapping.RotateValueIncr.name(),
                                       InputMapping.RotateValueDecr.name(),
                                       InputMapping.NextPoseFrame.name(),
                                       InputMapping.PrevPoseFrame.name(),
                                       InputMapping.MouseSelect.name(),
                                       InputMapping.FrameTimePos.name(),
                                       InputMapping.FrameTimeNeg.name()
        );
    }
    
    // Input mappings send events here
    @Override
    public void onAction(String name, boolean isPressed, float tpf){
        InputMapping input = InputMapping.valueOf(name);
        switch(input){
            // Bone Rotation
            case RotateBonePos:
                rotateBonePos = isPressed;
                break;
            case RotateBoneNeg:
                rotateBoneNeg = isPressed; 
                break;
                
            // Axis Selection
            case SelectAxisX:
                selectAxisX = isPressed; 
                
                break;
            case SelectAxisY:
                selectAxisY = isPressed; 
                
                break;
            case SelectAxisZ:
                selectAxisZ = isPressed;;
                break;
                
            // Pose Frame Lifecycle
            case CreatePoseFrame:
                if(isPressed == false){
                    createNewPoseFrame();
                }
                break;
            case EditPoseFrame:
                break;
            case DeletePoseFrame:
                break;
                
            // Rotation value manipulation
            case RotateValueIncr:
                rotateValueIncr = isPressed;
                break;
            case RotateValueDecr:
                rotateValueDecr = isPressed;
                break;
                
            // Poseframe Navigation
            case NextPoseFrame:
                nextPoseFrame = isPressed;
                break;
            case PrevPoseFrame:
                prevPoseFrame = isPressed;
                break;
                
            // Frametime editing
            case FrameTimePos:
                break;
            case FrameTimeNeg:
                break;
                
            // Bone selection
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
