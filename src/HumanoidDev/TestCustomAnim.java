/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HumanoidDev;

/*
 * Copyright (c) 2009-2021 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.Animation;
import com.jme3.animation.BoneTrack;
import com.jme3.animation.SkeletonControl;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.SkeletonDebugger;

public class TestCustomAnim extends SimpleApplication implements AnimEventListener{
    
    Humaniod human;

    public static void main(String[] args) {
        TestCustomAnim app = new TestCustomAnim();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        human = new Humaniod();
        human.initPrototype();
        
        // Create geometry to add mesh to scene
        Geometry geom = new Geometry("Custom Mesh", human.mesh);
        human.model = new Node("Human");
        
        Material mat = assetManager.loadMaterial("Materials/VertexColorMat.j3m");
        //mat.getAdditionalRenderState().setWireframe(true);
        geom.setMaterial(mat);
        human.model.attachChild(geom);
        // Create skeleton control
        SkeletonControl skeletonControl = new SkeletonControl(human.skeleton);
        human.model.addControl(skeletonControl);
        rootNode.attachChild(human.model);
        
        flyCam.setMoveSpeed(25);
        
        SkeletonDebugger skeletonDebug = new SkeletonDebugger("skeleton", skeletonControl.getSkeleton());
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.White);
        mat2.getAdditionalRenderState().setDepthTest(false);
        skeletonDebug.setMaterial(mat2);
        skeletonDebug.getWires().setLineWidth(5);
        human.model.attachChild(skeletonDebug);
    }

    @Override
    public void simpleUpdate(float tpf){
        
    }
    
    protected void initAnimation(){
        // Create Animation Controller for the player
        AnimControl playerControl; // you need one Control per model
        //Node player = (Node) assetManager.loadModel("Models/Oto/Oto.mesh.xml"); // load a model
        playerControl = human.model.getControl(AnimControl.class); // get control over this model
        playerControl.addListener(this); // add listener
        
        // Animation controls effect which bones use animations
        AnimChannel channel_walk = playerControl.createChannel();
        
        // Create a basic animation
        Animation anim0 = new Animation();
        
        // Create bone tracks to store transformations i.e. 'KeyFrames'
        /*
        byte rootId = 0;
        byte lHipId = 1;
        byte lKneeId = 2;
        byte lAnkleId = 3;
        byte rHipId = 4;
        byte rKneeId = 5;
        byte rAnkleId = 6;
        byte waistId = 7;
        byte torsoId = 8;
        byte chestId = 9;
        byte lShoulderId = 10;
        byte lElbowId = 11;
        byte lWristId = 12;
        byte rShoulderId = 13;
        byte rElbowId = 14;
        byte rWristId = 15;
        byte headId = 16;
        */
        /* 
        BoneTrack(int targetBoneIndex)
        BoneTrack(int targetBoneIndex, float[] times, Vector3f[] translations, Quaternion[] rotations)
        BoneTrack(int targetBoneIndex, float[] times, Vector3f[] translations, Quaternion[] rotations, Vector3f[] scales) */
        
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
        
        // Need a bit of space for value tracking
        
        // Add data to tracks???
        // setKeyframes(float[] times, Vector3f[] translations, Quaternion[] rotations, Vector3f[] scales)
        lHipBoneTrack.setKeyframes(/*TIMES*/new float[]{0,1,2,3} , /*TRANSLATIONS*/new Vector3f[]{}, /*ROTATIONS*/new Quaternion[]{});
        rHipBoneTrack.setKeyframes(/*TIMES*/new float[]{} , /*TRANSLATIONS*/new Vector3f[]{}, /*ROTATIONS*/new Quaternion[]{});
        lShoulderBoneTrack.setKeyframes(/*TIMES*/new float[]{} , /*TRANSLATIONS*/new Vector3f[]{}, /*ROTATIONS*/new Quaternion[]{});
        rShoulderBoneTrack.setKeyframes(/*TIMES*/new float[]{} , /*TRANSLATIONS*/new Vector3f[]{}, /*ROTATIONS*/new Quaternion[]{});
        
        // Add tracks to animation 
        anim0.setTracks(new BoneTrack[]{
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
        
        // Add the animation to the AnimControl
        playerControl.addAnim(anim0);
    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
        
    }

}