/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HumanoidAnimation;

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
    public Animation animation;
    
    // Using the targetBoneIndex constructor
    public BoneTrack rootBoneTrack = new BoneTrack(0);
    public BoneTrack lHipBoneTrack = new BoneTrack(1);
    public BoneTrack lKneeBoneTrack = new BoneTrack(2);
    public BoneTrack lAnkleBoneTrack = new BoneTrack(3);
    public BoneTrack rHipBoneTrack = new BoneTrack(4);
    public BoneTrack rKneeBoneTrack = new BoneTrack(5);
    public BoneTrack rAnkleBoneTrack = new BoneTrack(6);
    public BoneTrack waistBoneTrack = new BoneTrack(7);
    public BoneTrack torsoBoneTrack = new BoneTrack(8);
    public BoneTrack chestBoneTrack = new BoneTrack(9);
    public BoneTrack lShoulderBoneTrack = new BoneTrack(10);
    public BoneTrack lElbowBoneTrack = new BoneTrack(11);
    public BoneTrack lWristBoneTrack = new BoneTrack(12);
    public BoneTrack rShoulderBoneTrack = new BoneTrack(13);
    public BoneTrack rElbowBoneTrack = new BoneTrack(14);
    public BoneTrack rWristBoneTrack = new BoneTrack(15);
    public BoneTrack headBoneTrack = new BoneTrack(16);
    
    public void buildTracks(){
        
    }
    
    public void prepareBoneTracks(){
        animation = new Animation("Test",2f);
        
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
