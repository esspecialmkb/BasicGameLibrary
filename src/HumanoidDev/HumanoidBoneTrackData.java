/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HumanoidDev;

import com.jme3.animation.Animation;
import com.jme3.animation.BoneTrack;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import java.io.BufferedReader;

/**
 * HumanoidBoneTrackData is designed to convert data directly into jME animations.
 * 
 * @author TigerSage
 */
public class HumanoidBoneTrackData {
    Animation animation;
    
    // Using the targetBoneIndex constructor
    BoneTrack rootBoneTrack = new BoneTrack(0);
    BoneTrack lHipBoneTrack = new BoneTrack(1);
    BoneTrack lKneeBoneTrack = new BoneTrack(2);
    BoneTrack lAnkleBoneTrack = new BoneTrack(3);
    BoneTrack rHipBoneTrack = new BoneTrack(4);
    BoneTrack rKneeBoneTrack = new BoneTrack(5);
    BoneTrack rAnkleBoneTrack = new BoneTrack(6);
    BoneTrack waistBoneTrack = new BoneTrack(7);
    BoneTrack torsoBoneTrack = new BoneTrack(8);
    BoneTrack chestBoneTrack = new BoneTrack(9);
    BoneTrack lShoulderBoneTrack = new BoneTrack(10);
    BoneTrack lElbowBoneTrack = new BoneTrack(11);
    BoneTrack lWristBoneTrack = new BoneTrack(12);
    BoneTrack rShoulderBoneTrack = new BoneTrack(13);
    BoneTrack rElbowBoneTrack = new BoneTrack(14);
    BoneTrack rWristBoneTrack = new BoneTrack(15);
    BoneTrack headBoneTrack = new BoneTrack(16);
    
    public void buildTracks(){
        
    }
    
    public void prepareBoneTracks(){
        animation = new Animation("Test",8f);
        
        // Add data to tracks???
        // setKeyframes(float[] times, Vector3f[] translations = new Vector3f[]{}, Quaternion[] rotations = new Quaternion[]{}, Vector3f[] scales = new Vector3f[]{})
        
        buildTracks();
        
        // Add tracks to animation 
        animation.setTracks(new BoneTrack[]{
            rootBoneTrack,
            lHipBoneTrack,
            lKneeBoneTrack,
            lAnkleBoneTrack,
            rHipBoneTrack,
            rKneeBoneTrack,
            rAnkleBoneTrack,
            waistBoneTrack,
            torsoBoneTrack,
            chestBoneTrack,
            lShoulderBoneTrack,
            lElbowBoneTrack,
            lWristBoneTrack,
            rShoulderBoneTrack,
            rElbowBoneTrack,
            rWristBoneTrack,
            headBoneTrack
        });
        
        // Add the animation to an AnimControl
        //animControl.addAnim(animation);
    }
}
