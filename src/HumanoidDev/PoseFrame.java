/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HumanoidDev;

import com.jme3.animation.Bone;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 *
 * @author TigerSage
 */
// Define the frame data that will be used to construct bonetrack data
public class PoseFrame{
    public int id;
    public int frameNumber;
    public float time;
    /*
    Quaternion Rotation;
    Vector3f Position;
    */
    //Root
    float[] rootRot;
    float[] rootTrans;
    float[] rootScale;
    Quaternion rootRotation;
    Vector3f rootPosition;

    // L Hip
    float[] lHipRot;
    float[] lHipTrans;
    float[] lHipScale;
    Quaternion lHipRotation;
    Vector3f lHipPosition;
    
    // L Knee
    float[] lKneeRot;
    float[] lKneeTrans;
    float[] lKneeScale;
    Quaternion lKneeRotation;
    Vector3f lKneePosition;
    
    // L Ankle
    float[] lAnkleRot;
    float[] lAnkleTrans;
    float[] lAnkleScale;
    Quaternion lAnkleRotation;
    Vector3f lAnklePosition;

    // R Hip
    float[] rHipRot;
    float[] rHipTrans;
    float[] rHipScale;
    Quaternion rHipRotation;
    Vector3f rHipPosition;

    // R Knee
    float[] rKneeRot;
    float[] rKneeTrans;
    float[] rKneeScale;
    Quaternion rKneeRotation;
    Vector3f rKneePosition;

    // R Ankle
    float[] rAnkleRot;
    float[] rAnkleTrans;
    float[] rAnkleScale;
    Quaternion rAnkleRotation;
    Vector3f rAnklePosition;

    // Waist
    float[] waistRot;
    float[] waistTrans;
    float[] waistScale;
    Quaternion waistRotation;
    Vector3f waistPosition;
    
    // Torso
    float[] torsoRot;
    float[] torsoTrans;
    float[] torsoScale;
    Quaternion torsoRotation;
    Vector3f torsoPosition;

    // Chest
    float[] chestRot;
    float[] chestTrans;
    float[] chestScale;
    Quaternion chestRotation;
    Vector3f chestPosition;
    
    // L Shoulder
    float[] lShoulderRot;
    float[] lShoulderTrans;
    float[] lShoulderScale;
    Quaternion lShoulderRotation;
    Vector3f lShoulderPosition;
    
    // L Elbow
    float[] lElbowRot;
    float[] lElbowTrans;
    float[] lElbowScale;
    Quaternion lElbowRotation;
    Vector3f lElbowPosition;
    
    // L Wrist
    float[] lWristRot;
    float[] lWristTrans;
    float[] lWristScale;
    Quaternion lWristRotation;
    Vector3f lWristPosition;
    
    // R Shoulder
    float[] rShoulderRot;
    float[] rShoulderTrans;
    float[] rShoulderScale;
    Quaternion rShoulderRotation;
    Vector3f rShoulderPosition;
    
    // R Elbow
    float[] rElbowRot;
    float[] rElbowTrans;
    float[] rElbowScale;
    Quaternion rElbowRotation;
    Vector3f rElbowPosition;
    
    // R Wrist
    float[] rWristRot;
    float[] rWristTrans;
    float[] rWristScale;
    Quaternion rWristRotation;
    Vector3f rWristPosition;
    
    // Head
    float[] headRot;
    float[] headTrans;
    float[] headScale;
    Quaternion headRotation;
    Vector3f headPosition;
    
    public float getFrameTime(){return time;}
    public void setFrameTime(float newTime){time = newTime;}
    
    public void getBoneData(Humaniod human){
        // Root 
        Bone rootBone = human.skeleton.getBone(0);
        rootRotation = rootBone.getLocalRotation();
        rootPosition = rootBone.getLocalPosition();
        rootRot = rootRotation.toAngles(rootRot);
        
        // L Hip
        Bone lHipBone = human.skeleton.getBone(0);
        lHipRotation = rootBone.getLocalRotation();
        lHipPosition = rootBone.getLocalPosition();
        lHipRot = lHipRotation.toAngles(lHipRot);
        
        // L Knee
        Bone lKneeBone = human.skeleton.getBone(0);
        lKneeRotation = rootBone.getLocalRotation();
        lKneePosition = rootBone.getLocalPosition();
        lKneeRot = lKneeRotation.toAngles(lKneeRot);

        // L Ankle
        Bone lAnkleBone = human.skeleton.getBone(0);
        lAnkleRotation = rootBone.getLocalRotation();
        lAnklePosition = rootBone.getLocalPosition();
        lAnkleRot = lAnkleRotation.toAngles(lAnkleRot);
        
        // RHip
        Bone rHipBone = human.skeleton.getBone(0);
        rHipRotation = rootBone.getLocalRotation();
        rHipPosition = rootBone.getLocalPosition();
        rHipRot = rHipRotation.toAngles(rHipRot);
        
        // L Knee
        Bone rKneeBone = human.skeleton.getBone(0);
        rKneeRotation = rootBone.getLocalRotation();
        rKneePosition = rootBone.getLocalPosition();
        rKneeRot = rKneeRotation.toAngles(rKneeRot);
        
        // L Ankle
        Bone rAnkleBone = human.skeleton.getBone(0);
        rAnkleRotation = rootBone.getLocalRotation();
        rAnklePosition = rootBone.getLocalPosition();
        rAnkleRot = rAnkleRotation.toAngles(rAnkleRot);
        
        // Waist 
        Bone waistBone = human.skeleton.getBone(0);
        waistRotation = rootBone.getLocalRotation();
        waistPosition = rootBone.getLocalPosition();
        waistRot = waistRotation.toAngles(waistRot);
        
        // Torso 
        Bone torsoBone = human.skeleton.getBone(0);
        torsoRotation = rootBone.getLocalRotation();
        torsoPosition = rootBone.getLocalPosition();
        torsoRot = torsoRotation.toAngles(torsoRot);
        
        // Chest 
        Bone chestBone = human.skeleton.getBone(0);
        chestRotation = rootBone.getLocalRotation();
        chestPosition = rootBone.getLocalPosition();
        chestRot = chestRotation.toAngles(chestRot);
        
        // lShoulder 
        Bone lShoulderBone = human.skeleton.getBone(0);
        lShoulderRotation = rootBone.getLocalRotation();
        lShoulderPosition = rootBone.getLocalPosition();
        lShoulderRot = lShoulderRotation.toAngles(lShoulderRot);
        
        // lElbow 
        Bone lElbowBone = human.skeleton.getBone(0);
        lElbowRotation = rootBone.getLocalRotation();
        lElbowPosition = rootBone.getLocalPosition();
        lElbowRot = lElbowRotation.toAngles(lElbowRot);
        
        // lWrist 
        Bone lWristBone = human.skeleton.getBone(0);
        lWristRotation = rootBone.getLocalRotation();
        lWristPosition = rootBone.getLocalPosition();
        lWristRot = lWristRotation.toAngles(lWristRot);
        
        // rShoulder 
        Bone rShoulderBone = human.skeleton.getBone(0);
        rShoulderRotation = rootBone.getLocalRotation();
        rShoulderPosition = rootBone.getLocalPosition();
        rShoulderRot = rShoulderRotation.toAngles(rShoulderRot);
        
        // rElbow 
        Bone rElbowBone = human.skeleton.getBone(0);
        rElbowRotation = rootBone.getLocalRotation();
        rElbowPosition = rootBone.getLocalPosition();
        rElbowRot = rElbowRotation.toAngles(rElbowRot);
        
        // rWrist 
        Bone rWristBone = human.skeleton.getBone(0);
        rWristRotation = rootBone.getLocalRotation();
        rWristPosition = rootBone.getLocalPosition();
        rWristRot = rWristRotation.toAngles(rWristRot);
        
        // head 
        Bone headBone = human.skeleton.getBone(0);
        headRotation = rootBone.getLocalRotation();
        headPosition = rootBone.getLocalPosition();
        headRot = headRotation.toAngles(headRot);
        
    }
}
