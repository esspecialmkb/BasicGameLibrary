/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HumanoidAnimation;

import HumanoidDev.Humaniod;
import com.jme3.animation.Animation;
import com.jme3.animation.BoneTrack;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * HumanoidBoneTrackData is designed to convert data directly into jME animations.
 * 
 * @author TigerSage
 */
public class HumanoidBoneTrackData {
    public Animation animation;
    
    public ArrayList<Float> keyFrameTimes = new ArrayList<>();
    public ArrayList<Humaniod.RotationFrame> rotationFrames = new ArrayList<>();
    
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
    
    public void addKeyFrame(){
        
    }
    
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
    
    public void readBoneTracks(){
        
        try{
            // Create a FileStream to read the file
            File file = new File("file.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            while((line = bufferedReader.readLine()) != null){
                System.out.println(line);
            }
            
            // Close the stream when we are done
            bufferedReader.close();
            
        } catch (FileNotFoundException ex) {
            // Thrown when we cannot find the file
            Logger.getLogger(HumanoidBoneTrackData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            // Thrown while reading the file
            Logger.getLogger(HumanoidBoneTrackData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeBoneTracks(){
        // Sample text for file writing
        String[] list = {"one", "two", "three", "four"};
        
        try{
            // Create a FileStream to write to
            File file = new File("file.txt");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            // Iterate through data to write
            for(String s : list){
                bufferedWriter.write(s +"\n");
            }
            
            // Close the stream when we are done
            bufferedWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(HumanoidBoneTrackData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
