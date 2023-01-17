/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HumanoidAnimation;

import com.jme3.math.Quaternion;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author TigerSage
 */
// This class serves as a KeyFrame 'container'
public class HumanoidAnimationData{
    
    int frame;
    boolean dataFlag[] = new boolean[17];
    ArrayList<Float> time;
    
    ArrayList<Quaternion> rRot;

    ArrayList<Quaternion> lHRot;
    ArrayList<Quaternion> lKRot;
    ArrayList<Quaternion> lARot;

    ArrayList<Quaternion> rHRot;
    ArrayList<Quaternion> rKRot;
    ArrayList<Quaternion> rARot;

    ArrayList<Quaternion> wRot;
    ArrayList<Quaternion> tRot;
    ArrayList<Quaternion> cRot;

    ArrayList<Quaternion> lSRot;
    ArrayList<Quaternion> lERot;
    ArrayList<Quaternion> lWRot;

    ArrayList<Quaternion> rSRot;
    ArrayList<Quaternion> rERot;
    ArrayList<Quaternion> rWRot;

    ArrayList<Quaternion> hRot;
    
    float[] timeArray;
    
    Quaternion[] rRotArray;

    Quaternion[] lHRotArray;
    Quaternion[] lKRotArray;
    Quaternion[] lARotArray;

    Quaternion[] rHRotArray;
    Quaternion[] rKRotArray;
    Quaternion[] rARotArray;

    Quaternion[] wRotArray;
    Quaternion[] tRotArray;
    Quaternion[] cRotArray;

    Quaternion[] lSRotArray;
    Quaternion[] lERotArray;
    Quaternion[] lWRotArray;

    Quaternion[] rSRotArray;
    Quaternion[] rERotArray;
    Quaternion[] rWRotArray;

    Quaternion[] hRotArray;
    
    public HumanoidAnimationData(){
        time = new ArrayList<>();
        
        rRot = new ArrayList<>();
        
        lHRot = new ArrayList<>();
        lKRot = new ArrayList<>();
        lARot = new ArrayList<>();
        
        rHRot = new ArrayList<>();
        rKRot = new ArrayList<>();
        rARot = new ArrayList<>();
        
        wRot = new ArrayList<>();
        tRot = new ArrayList<>();
        cRot = new ArrayList<>();
        
        lSRot = new ArrayList<>();
        lERot = new ArrayList<>();
        lWRot = new ArrayList<>();
        
        rSRot = new ArrayList<>();
        rERot = new ArrayList<>();
        rWRot = new ArrayList<>();
        
        hRot = new ArrayList<>();
        
        
    }
    
    protected void readFrameData(BufferedReader bufferedReader){
        
        String line;
        
        try{
            String[] quatData;
            // If the first line checks out, grab our rotation data

            // Grab root rotation data
            line = bufferedReader.readLine();
            quatData = line.split(",");
            rRot.add( new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])) );

            // Grab lhip
            line = bufferedReader.readLine();
            quatData = line.split(",");
            lHRot.add( new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])) );

            // Grab lknee
            line = bufferedReader.readLine();
            quatData = line.split(",");
            lKRot.add( new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])) );

            // Grab lankle
            line = bufferedReader.readLine();
            quatData = line.split(",");
            lARot.add( new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])) );

            // Grab rhip
            line = bufferedReader.readLine();
            quatData = line.split(",");
            rHRot.add( new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])) );

            // Grab rknee
            line = bufferedReader.readLine();
            quatData = line.split(",");
            rKRot.add( new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])) );

            // Grab rankle
            line = bufferedReader.readLine();
            quatData = line.split(",");
            rARot.add( new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])) );

            // Grab waist
            line = bufferedReader.readLine();
            quatData = line.split(",");
            wRot.add( new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])) );

            // Grab torso
            line = bufferedReader.readLine();
            quatData = line.split(",");
            tRot.add( new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])) );

            // Grab chest
            line = bufferedReader.readLine();
            quatData = line.split(",");
            cRot.add( new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])) );

            // Grab shoulder
            line = bufferedReader.readLine();
            quatData = line.split(",");
            lSRot.add( new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])) );

            // Grab elbow
            line = bufferedReader.readLine();
            quatData = line.split(",");
            lERot.add( new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])) );

            // Grab wrist
            line = bufferedReader.readLine();
            quatData = line.split(",");
            lWRot.add( new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])) );

            // Grab shoulder
            line = bufferedReader.readLine();
            quatData = line.split(",");
            rSRot.add( new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])) );

            // Grab elbow
            line = bufferedReader.readLine();
            quatData = line.split(",");
            rERot.add( new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])) );

            // Grab wrist
            line = bufferedReader.readLine();
            quatData = line.split(",");
            rWRot.add( new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])) );

            // Grab head
            line = bufferedReader.readLine();
            quatData = line.split(",");
            hRot.add( new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])) );
        }catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public void readAnimationData(String filename){
        // Read data from the file
        try{
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename), "UTF-16");
            BufferedReader bufferedReader = new BufferedReader(reader);
 
            String line;
            
            line = bufferedReader.readLine();
            System.out.println(line);
            
            if(line.equals("FrameData")){
                readFrameData(bufferedReader);
            }else if(line.equals("AnimationData")){
                // Read frame count and fps
                line = bufferedReader.readLine();
                String[] frameCountData = line.split(" ");
                
                int frameCount = Integer.parseInt(frameCountData[1]);
                float animFPS = Float.parseFloat(frameCountData[2]);
                for(int frameRead = 0; frameRead > frameCount; frameRead++){
                    line = bufferedReader.readLine();
                    time.add(Float.parseFloat(line)/animFPS);
                    
                    readFrameData(bufferedReader);
                }
                
                
            }
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public float[] getTimeTrack(){
        if(timeArray == null){
            timeArray = new float[5];
            for(int i = 0; i < 5; i++){
                timeArray[i] = time.get(i);
            }
        }
        
        return timeArray;
    }
    public Quaternion[] getBoneTrackRotation(int boneIndex){
        switch(boneIndex){
            case 0:
                if(rRotArray == null){
                    rRotArray = new Quaternion[5];
                    for(int i = 0; i < 5; i++){
                        rRotArray[i] = rRot.get(i);
                    }
                }

                return rRotArray;
            case 1:
                if(lHRotArray == null){
                    lHRotArray = new Quaternion[5];
                    for(int i = 0; i < 5; i++){
                        lHRotArray[i] = lHRot.get(i);
                    }
                }

                return lHRotArray;
            case 2:
                if(lKRotArray == null){
                    lKRotArray = new Quaternion[5];
                    for(int i = 0; i < 5; i++){
                        lKRotArray[i] = lKRot.get(i);
                    }
                }

                return lKRotArray;
            case 3:
                if(lARotArray == null){
                    lARotArray = new Quaternion[5];
                    for(int i = 0; i < 5; i++){
                        lARotArray[i] = lARot.get(i);
                    }
                }

                return lARotArray;
            case 4:
                if(rHRotArray == null){
                    rHRotArray = new Quaternion[5];
                    for(int i = 0; i < 5; i++){
                        rHRotArray[i] = rHRot.get(i);
                    }
                }

                return rHRotArray;
            case 5:
                if(rKRotArray == null){
                    rKRotArray = new Quaternion[5];
                    for(int i = 0; i < 5; i++){
                        rKRotArray[i] = rKRot.get(i);
                    }
                }

                return rKRotArray;
            case 6:
                if(rARotArray == null){
                    rARotArray = new Quaternion[5];
                    for(int i = 0; i < 5; i++){
                        rARotArray[i] = rARot.get(i);
                    }
                }

                return rARotArray;
            case 7:
                if(wRotArray == null){
                    wRotArray = new Quaternion[5];
                    for(int i = 0; i < 5; i++){
                        wRotArray[i] = wRot.get(i);
                    }
                }

                return wRotArray;
            case 8:
                if(tRotArray == null){
                    tRotArray = new Quaternion[5];
                    for(int i = 0; i < 5; i++){
                        tRotArray[i] = tRot.get(i);
                    }
                }

                return tRotArray;
            case 9:
                if(cRotArray == null){
                    cRotArray = new Quaternion[5];
                    for(int i = 0; i < 5; i++){
                        cRotArray[i] = cRot.get(i);
                    }
                }

                return cRotArray;
            case 10:
                if(lSRotArray == null){
                    lSRotArray = new Quaternion[5];
                    for(int i = 0; i < 5; i++){
                        lSRotArray[i] = lSRot.get(i);
                    }
                }

                return lSRotArray;
            case 11:
                if(lERotArray == null){
                    lERotArray = new Quaternion[5];
                    for(int i = 0; i < 5; i++){
                        lERotArray[i] = lERot.get(i);
                    }
                }

                return lERotArray;
            case 12:
                if(lWRotArray == null){
                    lWRotArray = new Quaternion[5];
                    for(int i = 0; i < 5; i++){
                        lWRotArray[i] = lWRot.get(i);
                    }
                }

                return lWRotArray;
            case 13:
                if(rSRotArray == null){
                    rSRotArray = new Quaternion[5];
                    for(int i = 0; i < 5; i++){
                        rSRotArray[i] = rSRot.get(i);
                    }
                }

                return rSRotArray;
            case 14:
                if(rERotArray == null){
                    rERotArray = new Quaternion[5];
                    for(int i = 0; i < 5; i++){
                        rERotArray[i] = rERot.get(i);
                    }
                }

                return rERotArray;
            case 15:
                if(rWRotArray == null){
                    rWRotArray = new Quaternion[5];
                    for(int i = 0; i < 5; i++){
                        rWRotArray[i] = rWRot.get(i);
                    }
                }

                return rWRotArray;
            case 16:
                if(hRotArray == null){
                    hRotArray = new Quaternion[5];
                    for(int i = 0; i < 5; i++){
                        hRotArray[i] = hRot.get(i);
                    }
                }

                return hRotArray;
        }
        
        return null;
    }
}
