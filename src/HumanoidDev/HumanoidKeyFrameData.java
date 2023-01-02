/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HumanoidDev;

import com.jme3.math.Quaternion;

/**
 *  HumanoidKeyFrameData is designed to hold data about the current frame
 * @author TigerSage
 */
// This class serves as a KeyFrame 'container'
public class HumanoidKeyFrameData{
    float time;
    int frame;
    boolean dataFlag[] = new boolean[17];

    Quaternion rRot;

    Quaternion lHRot;
    Quaternion lKRot;
    Quaternion lARot;

    Quaternion rHRot;
    Quaternion rKRot;
    Quaternion rARot;

    Quaternion wRot;
    Quaternion tRot;
    Quaternion cRot;

    Quaternion lSRot;
    Quaternion lERot;
    Quaternion lWRot;

    Quaternion rSRot;
    Quaternion rERot;
    Quaternion rWRot;

    Quaternion hRot;
}
