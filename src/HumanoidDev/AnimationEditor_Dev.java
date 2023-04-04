/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HumanoidDev;

import HumanoidAnimation.HumanoidRunAnimationData;
import static Utility.StringCharConst.textRed;
import static Utility.StringCharConst.textReset;
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
import com.jme3.input.JoystickButton;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.RawInputListener;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.event.JoyAxisEvent;
import com.jme3.input.event.JoyButtonEvent;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.input.event.TouchEvent;
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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        FrameTimeNeg,
        
        // Toggle typing keyboard mode
        TypingKeyboardToggle
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
    
    // Input latch toggles
    boolean moveLeft, moveRight, moveUp, moveDown, rotateLeft, rotateRight, mouseSelect;
    boolean rotateBonePos, rotateBoneNeg, selectAxisX, selectAxisY, selectAxisZ;
    boolean rotateValueIncr, rotateValueDecr, nextPoseFrame, prevPoseFrame;
    boolean typingKeyboardToggle;
    
    // Cam position data
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
        
        //
        getBoneData();
        
        // Add a raw listener because it's eisier to get all joystick events
        // this way.
        inputManager.addRawInputListener( new KeyboardController() );
        
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
        boolean rotationTextUpdateFlag = false;
        
        // Check for mouse select
        if(mouseSelect && !typingKeyboardToggle){
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
                    rotationTextUpdateFlag = true;
                    boneText.setText("Current bone selection: " + selectionBones[currentBoneSelection].getName().toUpperCase()); 
                }
                if(lastBoneSelected != -1){
                    // Set the material of the last selected bone back to normal
                    selectionGeometries[lastBoneSelected].setMaterial(selectionMatDeselected);
                }
            }if(bSelect == -1){
                rotationTextUpdateFlag = true;
                boneText.setText("Current bone selection: NULL"); 
            }
            
            mouseSelect = false;
        }
        
        // Check bone rotation commands
        if(rotateBonePos && !typingKeyboardToggle){
            //parseRotateBoneSelection(axisIncrements[currentAxisIncrement]);
            
            rotateBoneSelection(axisIncrements[currentAxisIncrement]*FastMath.DEG_TO_RAD);
            rotationTextUpdateFlag = true;
            rotateBonePos = false;
        }if(rotateBoneNeg && !typingKeyboardToggle){
            //parseRotateBoneSelection(-1*axisIncrements[currentAxisIncrement]);
            
            rotateBoneSelection(-1*axisIncrements[currentAxisIncrement]*FastMath.DEG_TO_RAD);
            rotationTextUpdateFlag = true;
            rotateBoneNeg = false;
        }
        
        // Manipulate the increment value
        if(rotateValueIncr && !typingKeyboardToggle){
            currentAxisIncrement++;
            if(currentAxisIncrement > (axisIncrements.length-1)){
                currentAxisIncrement = axisIncrements.length-1;
            }
            valueText.setText("Current Increment Value: " + axisIncrements[currentAxisIncrement]);             // the text
            rotateValueIncr = false;
        }if(rotateValueDecr && !typingKeyboardToggle){
            currentAxisIncrement--;
            if(currentAxisIncrement < 0){
                currentAxisIncrement = 0;
            }
            valueText.setText("Current Increment Value: " + axisIncrements[currentAxisIncrement]);             // the text
            rotateValueDecr = false;
        }
        
        // Check axis-select commands
        if(selectAxisX && !typingKeyboardToggle){
            currentAxis = 2;
            axisText.setText("Current axis selection: X"); 
            rotationTextUpdateFlag = true;
            selectAxisX = false;
        }if(selectAxisY && !typingKeyboardToggle){
            currentAxis = 0;
            axisText.setText("Current axis selection: Y"); 
            rotationTextUpdateFlag = true;
            selectAxisY = false;
        }if(selectAxisZ && !typingKeyboardToggle){
            currentAxis = 1;
            axisText.setText("Current axis selection: Z"); 
            rotationTextUpdateFlag = true;
            selectAxisZ = false;
        }if(nextPoseFrame){
            // Make sure we have a next frame...
            int pFCount = poseFrames.size();
            currentPoseFrame++;
            if(currentPoseFrame > pFCount){
                currentPoseFrame = pFCount;
            }
            nextPoseFrame = false;
        }if(prevPoseFrame){
            // Make sure we have a prev frame...
            int pFCount = poseFrames.size();
            currentPoseFrame--;
            if(currentPoseFrame == 0){
                currentPoseFrame = 1;
            }
            prevPoseFrame = false;
        }
        if(rotationTextUpdateFlag){
            Quaternion boneSelectionRotation = getBoneSelectionRotation();
            float[] angles = {0,0,0};
            angles = boneSelectionRotation.toAngles(angles);
            angles[0] = ((float)Math.round(angles[0]*FastMath.RAD_TO_DEG * 100))/100;
            angles[1] = ((float)Math.round(angles[1]*FastMath.RAD_TO_DEG * 100))/100;
            angles[2] = ((float)Math.round(angles[2]*FastMath.RAD_TO_DEG * 100))/100;
            rotationText.setText("Current Rotation Value: X_" + angles[2] + ", Y_" + angles[0] + ", Z_" + angles[1]);
        }
        
        if(typingKeyboardToggle){
            // Get info from typing keyboard to build a string
        }
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
    
    // Grab the rotation data from all bones and store it in arrays
    private void getBoneData(){
        for(int b = 0; b > 17; b++){
            // Get the bone from the skeleton
            Bone bone = human.skeleton.getBone(b);
            // Grab rotation and local position
            Vector3f localPosition = bone.getLocalPosition();
            Quaternion localRotation = bone.getLocalRotation();
            
            float[] angles = {0,0,0};
            // Convert the quaternion into array (yaw,roll,pitch)
            angles = localRotation.toAngles(angles);
            parseBoneRotation(b,convertRadToDeg(angles));
        }
    }
    
    // Convert float array from radians to degrees
    private float[] convertRadToDeg(float[] angles){
        angles[0] = angles[0] * FastMath.RAD_TO_DEG;
        angles[1] = angles[1] * FastMath.RAD_TO_DEG;
        angles[2] = angles[2] * FastMath.RAD_TO_DEG;
        return angles;
    }
    
    // Convert float array from degrees to radians
    private float[] convertDegToRad(float[] angles){
        angles[0] = angles[0] * FastMath.DEG_TO_RAD;
        angles[1] = angles[1] * FastMath.DEG_TO_RAD;
        angles[2] = angles[2] * FastMath.DEG_TO_RAD;
        return angles;
    }
    
    private void parseBoneRotation(int bone, float[] value){
        switch(bone){
            case 0:
                rootRot= value;
                break;
            case 1:
                lHipRot= value;
                break;
            case 2:
                lKneeRot= value;
                break;
            case 3:
                lAnkleRot= value;
                break;
            case 4:
                rHipRot= value;
                break;
            case 5:
                rKneeRot= value;
                break;
            case 6:
                rAnkleRot= value;
                break;
            case 7:
                waistRot= value;
                break;
            case 8:
                torsoRot= value;
                break;
            case 9:
                chestRot= value;
                break;
            case 10:
                lShoulderRot= value;
                break;
            case 11:
                lElbowRot= value;
                break;
            case 12:
                lWristRot= value;
                break;
            case 13:
                rShoulderRot= value;
                break;
            case 14:
                rElbowRot= value;
                break;
            case 15:
                rWristRot= value;
                break;
            case 16:
                headRot= value;
                break;
        }
    }
    
    // Using arrays to update bone rotation
    float[] tempAngles = {0,0,0};
    
    float[] rootRot = {0,0,0};
    float[] lHipRot = {0,0,0};
    float[] rHipRot = {0,0,0};
    float[] lKneeRot = {0,0,0};
    float[] rKneeRot = {0,0,0};
    float[] lAnkleRot = {0,0,0};
    float[] rAnkleRot = {0,0,0};
    float[] waistRot = {0,0,0};
    float[] torsoRot = {0,0,0};
    float[] chestRot = {0,0,0};
    float[] lShoulderRot = {0,0,0};
    float[] rShoulderRot = {0,0,0};
    float[] lElbowRot = {0,0,0};
    float[] rElbowRot = {0,0,0};
    float[] lWristRot = {0,0,0};
    float[] rWristRot = {0,0,0};
    float[] headRot = {0,0,0};
    
    private Quaternion getBoneSelectionRotation(){
        // Get the bone from the skeleton
        Bone bone = human.skeleton.getBone(currentBoneSelection);
        // Grab rotation and local position
        
        return bone.getLocalRotation();
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
        
        inputManager.addMapping(InputMapping.TypingKeyboardToggle.name(), new KeyTrigger(KeyInput.KEY_TAB));
        
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
                                       InputMapping.FrameTimeNeg.name(),
                                       InputMapping.TypingKeyboardToggle.name()
        );
    }
    
    protected void removeInputMappings(){
        inputManager.deleteTrigger(InputMapping.RotateBonePos.name(), new KeyTrigger(KeyInput.KEY_PERIOD));
        inputManager.deleteTrigger(InputMapping.RotateBoneNeg.name(), new KeyTrigger(KeyInput.KEY_COMMA));
        inputManager.deleteTrigger(InputMapping.SelectAxisX.name(), new KeyTrigger(KeyInput.KEY_J));
        inputManager.deleteTrigger(InputMapping.SelectAxisY.name(), new KeyTrigger(KeyInput.KEY_K));
        inputManager.deleteTrigger(InputMapping.SelectAxisZ.name(), new KeyTrigger(KeyInput.KEY_L));
        inputManager.deleteTrigger(InputMapping.CreatePoseFrame.name(), new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.deleteTrigger(InputMapping.EditPoseFrame.name(), new KeyTrigger(KeyInput.KEY_UP));
        inputManager.deleteTrigger(InputMapping.DeletePoseFrame.name(), new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.deleteTrigger(InputMapping.RotateValueIncr.name(), new KeyTrigger(KeyInput.KEY_U));
        inputManager.deleteTrigger(InputMapping.RotateValueDecr.name(), new KeyTrigger(KeyInput.KEY_I));
        inputManager.deleteTrigger(InputMapping.NextPoseFrame.name(), new KeyTrigger(KeyInput.KEY_O));
        inputManager.deleteTrigger(InputMapping.PrevPoseFrame.name(), new KeyTrigger(KeyInput.KEY_P));
        inputManager.deleteTrigger(InputMapping.FrameTimePos.name(), new KeyTrigger(KeyInput.KEY_N));
        inputManager.deleteTrigger(InputMapping.FrameTimeNeg.name(), new KeyTrigger(KeyInput.KEY_M));
        inputManager.deleteTrigger(InputMapping.MouseSelect.name(), new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        //inputManager.deleteTrigger(InputMapping.TypingKeyboardToggle.name(), new KeyTrigger(KeyInput.KEY_PERIOD));
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
                
            case TypingKeyboardToggle:
                if(isPressed){
                    typingKeyboardToggle =! typingKeyboardToggle;
                }
                
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
    
    // Raw input listener for typing keyboard
    public class KeyboardController implements RawInputListener{

        @Override
        public void beginInput() {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void endInput() {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onJoyAxisEvent(JoyAxisEvent evt) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onJoyButtonEvent(JoyButtonEvent evt) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onMouseMotionEvent(MouseMotionEvent evt) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onMouseButtonEvent(MouseButtonEvent evt) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onKeyEvent(KeyInputEvent evt) {
            // Grab keyboard presses here
            //System.out.println(textRed+"Key Input Event"+textReset);

            int keyCode = evt.getKeyCode();
            boolean pressed = evt.isPressed();
            boolean released = evt.isReleased();
            boolean repeating = evt.isRepeating();
            
            System.out.println("KeyCode "+keyCode+" isPressed? "+ pressed + " isReleased? "+ released);
        }

        @Override
        public void onTouchEvent(TouchEvent evt) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    // Method to manually set a pose frame
    protected void setFramePose(){
        human.rootBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X)  ,Vector3f.UNIT_XYZ);
        human.lHipBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(45 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.lKneeBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.lAnkleBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.rHipBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(-45 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.rKneeBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.rAnkleBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.waistBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.torsoBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.chestBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.lShoulderBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(-30 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.lElbowBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.lWristBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.rShoulderBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(30 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.rElbowBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.rWristBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.headBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
    }
    
    // File input
    public void readDataFile(){
        File file= new File("C:\\Users\\TigerSage\\Documents\\JavaProjects\\BasicGamePlayGround\\assets\\Models\\Maplet\\ConversionMap\\conversionTestMap2.x");
        
        try {
            Scanner sc = new Scanner(file);
            // Keep track of how many lines read
            int lineCounter = 0;
            int splitCounter = 0;
            
            // This loop reads every line of the file
            while(sc.hasNextLine()){
                // Read the line
                String line = sc.nextLine();
                lineCounter++;
                
                // Split into empty character terminals
                String[] split = line.split(" ");
                splitCounter = split.length;
                
                // Print the line
                System.out.println(lineCounter +": "+ line);
                
                // Parse the line...
                
                // XOF tag - VALIDATES .X FILE FORMAT
                //if("xof".equals(split[0]))
                
                
                int numPoseFrames = 0;
                if("Validation".equals(split[0])){
                    
                    if("PoseFrame".equals(split[1])){
                        String itemCountData[] = split[2].split(":");
                        numPoseFrames = Integer.getInteger(itemCountData[1]);
                        poseFrames.clear();
                    }
                    
                }if("PoseFrame".equals(split[0])){
                    PoseFrame frame = new PoseFrame();
                    frame.readPoseFrame(sc);
                }if("Animation".equals(split[0])){
                    // NOT IMPLEMENTED YET
                }
                
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AnimationEditor_Dev.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    // Prime Prototype Reader
    protected void readFrameData(){
        boolean success = false;
        ArrayList<PoseFrame> tempFrames = new ArrayList<>();
        
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream("MyFile.txt"), "UTF-16");
            BufferedReader bufferedReader = new BufferedReader(reader);
 
            String line;
            String[] quatData;
 
            // Read the file contents
            while ((line = bufferedReader.readLine()) != null) {
                
                // Get the next line
                line = bufferedReader.readLine();
                // Split the line into tokens
                quatData = line.split(",");
                System.out.println(line);
                
                
                
                if(line.equals("FrameData")){
                    
                    // If the first line checks out, grab our rotation data
                    
                    PoseFrame frame = new PoseFrame();
                    frame.readPoseFrameData(bufferedReader);
                    tempFrames.add(frame);
                }else if(line.equals("KeyFrameData")){

                }
                
                
                
                System.out.println(line);
            }
            // If we've made it this far, add the frames
            poseFrames.clear();
            poseFrames.addAll(tempFrames);
            
            
            reader.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // File output
    public void writeDataFile(){
        try {
            FileWriter myWriter = new FileWriter("filename.txt");
            // Example
            //myWriter.write("Files in Java might be tricky, but it is fun enough!");
            
            // Write the pose frames first
            int size = poseFrames.size();
            myWriter.write("Validation PoseFrame itemCount:"+size);
            for(int pF = 0; pF < size; pF++){
                PoseFrame frame = poseFrames.get(pF);
                // Write PoseFrame Header
                myWriter.write("PoseFrame");
                frame.writePoseFrame(myWriter);
            }
            
            // After the pose frames, write the animations
            
            // Close the file
            myWriter.close();
            
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    // Prime Prototype Writer
    protected void writeFrameData(){
        try {
            FileOutputStream outputStream = new FileOutputStream("MyFile.txt");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-16");
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
             
            // Write file contents
            //bufferedWriter.write("Xin chào");
            //bufferedWriter.newLine();
            //bufferedWriter.write("Hẹn gặp lại!");
            //bufferedWriter.newLine();
            
            // Dump all of our pose frames
            for(int i = 0; i < poseFrames.size(); i++){
                poseFrames.get(i).writePoseFrameData(bufferedWriter);
            }            
            
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
