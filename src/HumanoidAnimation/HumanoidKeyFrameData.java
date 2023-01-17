/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HumanoidAnimation;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;

/**
 *  HumanoidKeyFrameData is designed to hold data about the current frame
 * @author TigerSage
 */
// This class serves as a KeyFrame 'container'
public class HumanoidKeyFrameData{
    public float time;
    public int frame,fps;
    public boolean dataFlag[] = new boolean[17];

    public Quaternion rRot;
    public float rootXRot,rootYRot,rootZRot;
    
    public Quaternion lHRot;
    public Quaternion lKRot;
    public Quaternion lARot;
    public float lHipXRot,lHipYRot,lHipZRot;
    public float lKneeXRot,lKneeYRot,lKneeZRot;
    public float lAnkleXRot,lAnkleYRot,lAnkleZRot;
    
    public Quaternion rHRot;
    public Quaternion rKRot;
    public Quaternion rARot;
    public float rHipXRot,rHipYRot,rHipZRot;
    public float rKneeXRot,rKneeYRot,rKneeZRot;
    public float rAnkleXRot,rAnkleYRot,rAnkleZRot;

    public Quaternion wRot;
    public Quaternion tRot;
    public Quaternion cRot;

    public Quaternion lSRot;
    public Quaternion lERot;
    public Quaternion lWRot;

    public Quaternion rSRot;
    public Quaternion rERot;
    public Quaternion rWRot;

    public Quaternion hRot;
    
    public HumanoidKeyFrameData(){
        this(0);
    }
    
    public HumanoidKeyFrameData(int frame){
        this.frame = frame;
        fps = 24;
        time = (float)frame/fps;
        
        rRot = new Quaternion();

        lHRot = new Quaternion();
        lKRot = new Quaternion();
        lARot = new Quaternion();

        rHRot = new Quaternion();
        rKRot = new Quaternion();
        rARot = new Quaternion();

        wRot = new Quaternion();
        tRot = new Quaternion();
        cRot = new Quaternion();

        lSRot = new Quaternion();
        lERot = new Quaternion();
        lWRot = new Quaternion();

        rSRot = new Quaternion();
        rERot = new Quaternion();
        rWRot = new Quaternion();

        hRot = new Quaternion();
    }
    
    public void setRotation(byte boneIndex, float x, float y, float z){
        dataFlag[boneIndex] = true;
        switch(boneIndex){
            case 0:
                rRot.fromAngles(x * FastMath.DEG_TO_RAD, y * FastMath.DEG_TO_RAD, z * FastMath.DEG_TO_RAD);
                break;
            case 1:
                lHRot.fromAngles(x * FastMath.DEG_TO_RAD, y * FastMath.DEG_TO_RAD, z * FastMath.DEG_TO_RAD);
                break;
            case 2:
                lKRot.fromAngles(x * FastMath.DEG_TO_RAD, y * FastMath.DEG_TO_RAD, z * FastMath.DEG_TO_RAD);
                break;
            case 3:
                lARot.fromAngles(x * FastMath.DEG_TO_RAD, y * FastMath.DEG_TO_RAD, z * FastMath.DEG_TO_RAD);
                break;
            case 4:
                rHRot.fromAngles(x * FastMath.DEG_TO_RAD, y * FastMath.DEG_TO_RAD, z * FastMath.DEG_TO_RAD);
                break;
            case 5:
                rKRot.fromAngles(x * FastMath.DEG_TO_RAD, y * FastMath.DEG_TO_RAD, z * FastMath.DEG_TO_RAD);
                break;
            case 6:
                rARot.fromAngles(x * FastMath.DEG_TO_RAD, y * FastMath.DEG_TO_RAD, z * FastMath.DEG_TO_RAD);
                break;
            case 7:
                wRot.fromAngles(x * FastMath.DEG_TO_RAD, y * FastMath.DEG_TO_RAD, z * FastMath.DEG_TO_RAD);
                break;
            case 8:
                tRot.fromAngles(x * FastMath.DEG_TO_RAD, y * FastMath.DEG_TO_RAD, z * FastMath.DEG_TO_RAD);
                break;
            case 9:
                cRot.fromAngles(x * FastMath.DEG_TO_RAD, y * FastMath.DEG_TO_RAD, z * FastMath.DEG_TO_RAD);
                break;
            case 10:
                lSRot.fromAngles(x * FastMath.DEG_TO_RAD, y * FastMath.DEG_TO_RAD, z * FastMath.DEG_TO_RAD);
                break;
            case 11:
                lERot.fromAngles(x * FastMath.DEG_TO_RAD, y * FastMath.DEG_TO_RAD, z * FastMath.DEG_TO_RAD);
                break;
            case 12:
                lWRot.fromAngles(x * FastMath.DEG_TO_RAD, y * FastMath.DEG_TO_RAD, z * FastMath.DEG_TO_RAD);
                break;
            case 13:
                rSRot.fromAngles(x * FastMath.DEG_TO_RAD, y * FastMath.DEG_TO_RAD, z * FastMath.DEG_TO_RAD);
                break;
            case 14:
                rERot.fromAngles(x * FastMath.DEG_TO_RAD, y * FastMath.DEG_TO_RAD, z * FastMath.DEG_TO_RAD);
                break;
            case 15:
                rWRot.fromAngles(x * FastMath.DEG_TO_RAD, y * FastMath.DEG_TO_RAD, z * FastMath.DEG_TO_RAD);
                break;
            case 16:
                hRot.fromAngles(x * FastMath.DEG_TO_RAD, y * FastMath.DEG_TO_RAD, z * FastMath.DEG_TO_RAD);
                break;
        }
        
    }
    
    
}
