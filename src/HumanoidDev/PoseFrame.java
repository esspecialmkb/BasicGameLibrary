/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HumanoidDev;

import com.jme3.animation.Bone;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public float[] convertAnglesToDeg(float[] angles){
        angles[0] = ((float)Math.round(angles[0]*FastMath.RAD_TO_DEG * 100))/100;
        angles[1] = ((float)Math.round(angles[1]*FastMath.RAD_TO_DEG * 100))/100;
        angles[2] = ((float)Math.round(angles[2]*FastMath.RAD_TO_DEG * 100))/100;
        System.out.println("Parsing Rotation Value: X_" + angles[2] + ", Y_" + angles[0] + ", Z_" + angles[1]);
        
        return new float[]{angles[2],angles[0],angles[1]};
    }
    
    public void setBoneData(Humaniod human){
        
    }
    
    public void getBoneData(Humaniod human){
        
        // Root 
        Bone rootBone = human.skeleton.getBone(0);
        rootRotation = rootBone.getLocalRotation();
        rootPosition = rootBone.getLocalPosition();
        rootRot = convertAnglesToDeg(rootRotation.toAngles(rootRot));
        
        
        // L Hip
        Bone lHipBone = human.skeleton.getBone(0);
        lHipRotation = rootBone.getLocalRotation();
        lHipPosition = rootBone.getLocalPosition();
        lHipRot = convertAnglesToDeg(lHipRotation.toAngles(lHipRot));
        
        // L Knee
        Bone lKneeBone = human.skeleton.getBone(0);
        lKneeRotation = rootBone.getLocalRotation();
        lKneePosition = rootBone.getLocalPosition();
        lKneeRot = convertAnglesToDeg(lKneeRotation.toAngles(lKneeRot));

        // L Ankle
        Bone lAnkleBone = human.skeleton.getBone(0);
        lAnkleRotation = rootBone.getLocalRotation();
        lAnklePosition = rootBone.getLocalPosition();
        lAnkleRot = convertAnglesToDeg(lAnkleRotation.toAngles(lAnkleRot));
        
        // RHip
        Bone rHipBone = human.skeleton.getBone(0);
        rHipRotation = rootBone.getLocalRotation();
        rHipPosition = rootBone.getLocalPosition();
        rHipRot = convertAnglesToDeg(rHipRotation.toAngles(rHipRot));
        
        // L Knee
        Bone rKneeBone = human.skeleton.getBone(0);
        rKneeRotation = rootBone.getLocalRotation();
        rKneePosition = rootBone.getLocalPosition();
        rKneeRot = convertAnglesToDeg(rKneeRotation.toAngles(rKneeRot));
        
        // L Ankle
        Bone rAnkleBone = human.skeleton.getBone(0);
        rAnkleRotation = rootBone.getLocalRotation();
        rAnklePosition = rootBone.getLocalPosition();
        rAnkleRot = convertAnglesToDeg(rAnkleRotation.toAngles(rAnkleRot));
        
        // Waist 
        Bone waistBone = human.skeleton.getBone(0);
        waistRotation = rootBone.getLocalRotation();
        waistPosition = rootBone.getLocalPosition();
        waistRot = convertAnglesToDeg(waistRotation.toAngles(waistRot));
        
        // Torso 
        Bone torsoBone = human.skeleton.getBone(0);
        torsoRotation = rootBone.getLocalRotation();
        torsoPosition = rootBone.getLocalPosition();
        torsoRot = convertAnglesToDeg(torsoRotation.toAngles(torsoRot));
        
        // Chest 
        Bone chestBone = human.skeleton.getBone(0);
        chestRotation = rootBone.getLocalRotation();
        chestPosition = rootBone.getLocalPosition();
        chestRot = convertAnglesToDeg(chestRotation.toAngles(chestRot));
        
        // lShoulder 
        Bone lShoulderBone = human.skeleton.getBone(0);
        lShoulderRotation = rootBone.getLocalRotation();
        lShoulderPosition = rootBone.getLocalPosition();
        lShoulderRot = convertAnglesToDeg(lShoulderRotation.toAngles(lShoulderRot));
        
        // lElbow 
        Bone lElbowBone = human.skeleton.getBone(0);
        lElbowRotation = rootBone.getLocalRotation();
        lElbowPosition = rootBone.getLocalPosition();
        lElbowRot = convertAnglesToDeg(lElbowRotation.toAngles(lElbowRot));
        
        // lWrist 
        Bone lWristBone = human.skeleton.getBone(0);
        lWristRotation = rootBone.getLocalRotation();
        lWristPosition = rootBone.getLocalPosition();
        lWristRot = convertAnglesToDeg(lWristRotation.toAngles(lWristRot));
        
        // rShoulder 
        Bone rShoulderBone = human.skeleton.getBone(0);
        rShoulderRotation = rootBone.getLocalRotation();
        rShoulderPosition = rootBone.getLocalPosition();
        rShoulderRot = convertAnglesToDeg(rShoulderRotation.toAngles(rShoulderRot));
        
        // rElbow 
        Bone rElbowBone = human.skeleton.getBone(0);
        rElbowRotation = rootBone.getLocalRotation();
        rElbowPosition = rootBone.getLocalPosition();
        rElbowRot = convertAnglesToDeg(rElbowRotation.toAngles(rElbowRot));
        
        // rWrist 
        Bone rWristBone = human.skeleton.getBone(0);
        rWristRotation = rootBone.getLocalRotation();
        rWristPosition = rootBone.getLocalPosition();
        rWristRot = convertAnglesToDeg(rWristRotation.toAngles(rWristRot));
        
        // head 
        Bone headBone = human.skeleton.getBone(0);
        headRotation = rootBone.getLocalRotation();
        headPosition = rootBone.getLocalPosition();
        headRot = convertAnglesToDeg(headRotation.toAngles(headRot));
        
    }
    
    public void readPoseFrame(Scanner sc){
        for( int l = 0; l < 17; l++){
            String line = sc.nextLine();
            String data[] = line.split(" ");
            float x = Float.parseFloat(data[2]);
            float y = Float.parseFloat(data[3]);
            float z = Float.parseFloat(data[4]);
            readPoseFrameRotation(new float[]{x,y,z},l);
        }
        
    }
    
    // Prime Prototype Reader
    public void readPoseFrameData(BufferedReader reader){
        try {
            String line;
            String[] quatData;
            
            line = reader.readLine();
            quatData = line.split(",");
            rootRotation = new Quaternion().fromAngles(Float.parseFloat(quatData[1])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[2])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[3])*FastMath.DEG_TO_RAD);
            
            line = reader.readLine();
            quatData = line.split(",");
            lHipRotation = new Quaternion().fromAngles(Float.parseFloat(quatData[1])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[2])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[3])*FastMath.DEG_TO_RAD);
            
            line = reader.readLine();
            quatData = line.split(",");
            lKneeRotation = new Quaternion().fromAngles(Float.parseFloat(quatData[1])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[2])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[3])*FastMath.DEG_TO_RAD);
            
            line = reader.readLine();
            quatData = line.split(",");
            lAnkleRotation = new Quaternion().fromAngles(Float.parseFloat(quatData[1])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[2])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[3])*FastMath.DEG_TO_RAD);
            
            line = reader.readLine();
            quatData = line.split(",");
            rHipRotation = new Quaternion().fromAngles(Float.parseFloat(quatData[1])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[2])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[3])*FastMath.DEG_TO_RAD);
            
            line = reader.readLine();
            quatData = line.split(",");
            rKneeRotation = new Quaternion().fromAngles(Float.parseFloat(quatData[1])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[2])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[3])*FastMath.DEG_TO_RAD);
            
            line = reader.readLine();
            quatData = line.split(",");
            rAnkleRotation = new Quaternion().fromAngles(Float.parseFloat(quatData[1])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[2])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[3])*FastMath.DEG_TO_RAD);
            
            line = reader.readLine();
            quatData = line.split(",");
            waistRotation = new Quaternion().fromAngles(Float.parseFloat(quatData[1])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[2])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[3])*FastMath.DEG_TO_RAD);
            
            line = reader.readLine();
            quatData = line.split(",");
            torsoRotation = new Quaternion().fromAngles(Float.parseFloat(quatData[1])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[2])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[3])*FastMath.DEG_TO_RAD);
            
            line = reader.readLine();
            quatData = line.split(",");
            chestRotation = new Quaternion().fromAngles(Float.parseFloat(quatData[1])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[2])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[3])*FastMath.DEG_TO_RAD);
            
            line = reader.readLine();
            quatData = line.split(",");
            lShoulderRotation = new Quaternion().fromAngles(Float.parseFloat(quatData[1])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[2])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[3])*FastMath.DEG_TO_RAD);
            
            line = reader.readLine();
            quatData = line.split(",");
            lElbowRotation = new Quaternion().fromAngles(Float.parseFloat(quatData[1])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[2])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[3])*FastMath.DEG_TO_RAD);
            
            line = reader.readLine();
            quatData = line.split(",");
            lWristRotation = new Quaternion().fromAngles(Float.parseFloat(quatData[1])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[2])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[3])*FastMath.DEG_TO_RAD);
            
            line = reader.readLine();
            quatData = line.split(",");
            rShoulderRotation = new Quaternion().fromAngles(Float.parseFloat(quatData[1])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[2])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[3])*FastMath.DEG_TO_RAD);
            
            line = reader.readLine();
            quatData = line.split(",");
            rElbowRotation = new Quaternion().fromAngles(Float.parseFloat(quatData[1])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[2])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[3])*FastMath.DEG_TO_RAD);
            
            line = reader.readLine();
            quatData = line.split(",");
            rWristRotation = new Quaternion().fromAngles(Float.parseFloat(quatData[1])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[2])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[3])*FastMath.DEG_TO_RAD);
            
            line = reader.readLine();
            quatData = line.split(",");
            headRotation = new Quaternion().fromAngles(Float.parseFloat(quatData[1])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[2])*FastMath.DEG_TO_RAD, Float.parseFloat(quatData[3])*FastMath.DEG_TO_RAD);
            
        } catch (IOException ex) {
            Logger.getLogger(PoseFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void readPoseFrameRotation(float[] rot, int index){
        switch(index){
            case 0:
                rootRot = rot;
                break;
            case 1:
                lHipRot = rot;
                break;
            case 2:
                lKneeRot = rot;
                break;
            case 3:
                lAnkleRot = rot;
                break;
            case 4:
                rHipRot = rot;
                break;
            case 5:
                rKneeRot = rot;
                break;
            case 6:
                rAnkleRot = rot;
                break;
            case 7:
                waistRot = rot;
                break;
            case 8:
                torsoRot = rot;
                break;
            case 9:
                chestRot = rot;
                break;
            case 10:
                lShoulderRot = rot;
                break;
            case 11:
                lElbowRot = rot;
                break;
            case 12:
                lWristRot = rot;
                break;
            case 13:
                rShoulderRot = rot;
                break;
            case 14:
                rElbowRot = rot;
                break;
            case 15:
                rWristRot = rot;
                break;
            case 16:
                headRot = rot;
                break;
            default:
                
        }
    }
    
    public void writePoseFrame(FileWriter myWriter){
        try {
            //myWriter.write("Writing a pose frame");
            myWriter.write("Rotation Root "+rootRot[2]+" "+rootRot[0]+" "+rootRot[1]);
            myWriter.write("Rotation LHip "+lHipRot[0]+" "+lHipRot[1]+" "+lHipRot[2]);
            myWriter.write("Rotation LKnee "+lKneeRot[0]+" "+lKneeRot[1]+" "+lKneeRot[2]);
            myWriter.write("Rotation LAnkle "+lAnkleRot[0]+" "+lAnkleRot[1]+" "+lAnkleRot[2]);
            myWriter.write("Rotation RHip "+rHipRot[0]+" "+rHipRot[1]+" "+rHipRot[2]);
            myWriter.write("Rotation RKnee "+rKneeRot[0]+" "+rKneeRot[1]+" "+rKneeRot[2]);
            myWriter.write("Rotation RAnkle "+rAnkleRot[0]+" "+rAnkleRot[1]+" "+rAnkleRot[2]);
            myWriter.write("Rotation Waist "+waistRot[0]+" "+waistRot[1]+" "+waistRot[2]);
            myWriter.write("Rotation Torso "+torsoRot[0]+" "+torsoRot[1]+" "+torsoRot[2]);
            myWriter.write("Rotation Chest "+chestRot[0]+" "+chestRot[1]+" "+chestRot[2]);
            myWriter.write("Rotation LShoulder "+lShoulderRot[0]+" "+lShoulderRot[1]+" "+lShoulderRot[2]);
            myWriter.write("Rotation LElbow "+lElbowRot[0]+" "+lElbowRot[1]+" "+lElbowRot[2]);
            myWriter.write("Rotation LWrist "+lWristRot[0]+" "+lWristRot[1]+" "+lWristRot[2]);
            myWriter.write("Rotation RShoulder "+rShoulderRot[0]+" "+rShoulderRot[1]+" "+rShoulderRot[2]);
            myWriter.write("Rotation RElbow "+rElbowRot[0]+" "+rElbowRot[1]+" "+rElbowRot[2]);
            myWriter.write("Rotation RWrist "+rWristRot[0]+" "+rWristRot[1]+" "+rWristRot[2]);
            myWriter.write("Rotation Head "+headRot[0]+" "+headRot[1]+" "+headRot[2]);
        } catch (IOException ex) {
            Logger.getLogger(PoseFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Prime Prototype Writer
    public void writePoseFrameData(BufferedWriter writer){
        try {
            writer.write("PoseFrameData");
            writer.newLine();
            
            float[] angles = new float[3];
            
            rootRotation.toAngles(angles);
            writer.write("Rotation Root," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            
            lHipRotation.toAngles(angles);
            writer.write("Rotation LHip," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            
            lKneeRotation.toAngles(angles);
            writer.write("Rotation LKnee," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            
            lAnkleRotation.toAngles(angles);
            writer.write("Rotation LAnkle," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            
            rHipRotation.toAngles(angles);
            writer.write("Rotation RHip," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            
            rKneeRotation.toAngles(angles);
            writer.write("Rotation RKnee," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            
            rAnkleRotation.toAngles(angles);
            writer.write("Rotation RAnkle," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            
            waistRotation.toAngles(angles);
            writer.write("Rotation Waist," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            
            torsoRotation.toAngles(angles);
            writer.write("Rotation Torso," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            
            chestRotation.toAngles(angles);
            writer.write("Rotation Chest," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            
            lShoulderRotation.toAngles(angles);
            writer.write("Rotation LShoulder," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            
            lElbowRotation.toAngles(angles);
            writer.write("Rotation LElbow," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            
            lWristRotation.toAngles(angles);
            writer.write("Rotation LWrist," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            
            rShoulderRotation.toAngles(angles);
            writer.write("Rotation RShoulder," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            
            rElbowRotation.toAngles(angles);
            writer.write("Rotation RElbow," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            
            rWristRotation.toAngles(angles);
            writer.write("Rotation RWrist," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            
            headRotation.toAngles(angles);
            writer.write("Rotation Head," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
        } catch (IOException ex) {
            Logger.getLogger(PoseFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
