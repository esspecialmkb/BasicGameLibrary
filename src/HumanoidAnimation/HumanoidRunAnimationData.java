/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HumanoidAnimation;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 * This is a test example of creating an animation in-code.
 * 
 * TODO - implement file save/load
 * 
 * @author TigerSage
 */
public class HumanoidRunAnimationData extends HumanoidBoneTrackData{
    @Override
    public void buildTracks(){
        rootBoneTrack.setKeyframes( /*TIMES*/new float[]{0,2} , 
                                        /*TRANSLATIONS*/new Vector3f[]{ new Vector3f(),
                                                                        new Vector3f()}, 
                                        /*ROTATIONS*/new Quaternion[]{  new Quaternion().fromAngles(0,0,0),
                                                                        new Quaternion().fromAngles(0,0,0)});

        lHipBoneTrack.setKeyframes( /*TIMES*/new float[]{0,.50f,1,1.5f,2} , 
                                    /*TRANSLATIONS*/new Vector3f[]{ new Vector3f(),
                                                                    new Vector3f(),
                                                                    new Vector3f(),
                                                                    new Vector3f(),
                                                                    new Vector3f()}, 
                                    /*ROTATIONS*/new Quaternion[]{  new Quaternion().fromAngles(-70,0,0),
                                                                    new Quaternion().fromAngles(0,0,0),
                                                                    new Quaternion().fromAngles(70,0,0),
                                                                    new Quaternion().fromAngles(0,0,0),
                                                                    new Quaternion().fromAngles(-70,0,0)});

        lKneeBoneTrack.setKeyframes( /*TIMES*/new float[]{0,2} , 
                                    /*TRANSLATIONS*/new Vector3f[]{ new Vector3f(),
                                                                    new Vector3f()}, 
                                    /*ROTATIONS*/new Quaternion[]{  new Quaternion().fromAngles(0,0,0),
                                                                    new Quaternion().fromAngles(0,0,0)});

        lAnkleBoneTrack.setKeyframes( /*TIMES*/new float[]{0,2} , 
                                    /*TRANSLATIONS*/new Vector3f[]{ new Vector3f(),
                                                                    new Vector3f()}, 
                                    /*ROTATIONS*/new Quaternion[]{  new Quaternion().fromAngles(0,0,0),
                                                                    new Quaternion().fromAngles(0,0,0)});

        rHipBoneTrack.setKeyframes( /*TIMES*/new float[]{0,.50f,1,1.5f,2} , 
                                    /*TRANSLATIONS*/new Vector3f[]{ new Vector3f(),
                                                                    new Vector3f(),
                                                                    new Vector3f(),
                                                                    new Vector3f(),
                                                                    new Vector3f()}, 
                                    /*ROTATIONS*/new Quaternion[]{  new Quaternion().fromAngles(70,0,0),
                                                                    new Quaternion().fromAngles(0,0,0),
                                                                    new Quaternion().fromAngles(-70,0,0),
                                                                    new Quaternion().fromAngles(0,0,0),
                                                                    new Quaternion().fromAngles(70,0,0)});

        rKneeBoneTrack.setKeyframes( /*TIMES*/new float[]{0,2} , 
                                    /*TRANSLATIONS*/new Vector3f[]{ new Vector3f(),
                                                                    new Vector3f()}, 
                                    /*ROTATIONS*/new Quaternion[]{  new Quaternion().fromAngles(0,0,0),
                                                                    new Quaternion().fromAngles(0,0,0)});

        rAnkleBoneTrack.setKeyframes( /*TIMES*/new float[]{0,2} , 
                                    /*TRANSLATIONS*/new Vector3f[]{ new Vector3f(),
                                                                    new Vector3f()}, 
                                    /*ROTATIONS*/new Quaternion[]{  new Quaternion().fromAngles(0,0,0),
                                                                    new Quaternion().fromAngles(0,0,0)});

        waistBoneTrack.setKeyframes( /*TIMES*/new float[]{0,2} , 
                                    /*TRANSLATIONS*/new Vector3f[]{ new Vector3f(),
                                                                    new Vector3f()}, 
                                    /*ROTATIONS*/new Quaternion[]{  new Quaternion().fromAngles(0,0,0),
                                                                    new Quaternion().fromAngles(0,0,0)});

        torsoBoneTrack.setKeyframes( /*TIMES*/new float[]{0,2} , 
                                    /*TRANSLATIONS*/new Vector3f[]{ new Vector3f(),
                                                                    new Vector3f()}, 
                                    /*ROTATIONS*/new Quaternion[]{  new Quaternion().fromAngles(0,0,0),
                                                                    new Quaternion().fromAngles(0,0,0)});

        chestBoneTrack.setKeyframes( /*TIMES*/new float[]{0,2} , 
                                    /*TRANSLATIONS*/new Vector3f[]{ new Vector3f(),
                                                                    new Vector3f()}, 
                                    /*ROTATIONS*/new Quaternion[]{  new Quaternion().fromAngles(0,0,0),
                                                                    new Quaternion().fromAngles(0,0,0)});

        lShoulderBoneTrack.setKeyframes( /*TIMES*/new float[]{0,2} , 
                                    /*TRANSLATIONS*/new Vector3f[]{ new Vector3f(),
                                                                    new Vector3f()}, 
                                    /*ROTATIONS*/new Quaternion[]{  new Quaternion().fromAngles(0,0,0),
                                                                    new Quaternion().fromAngles(0,0,0)});

        lElbowBoneTrack.setKeyframes( /*TIMES*/new float[]{0,2} , 
                                    /*TRANSLATIONS*/new Vector3f[]{ new Vector3f(),
                                                                    new Vector3f()}, 
                                    /*ROTATIONS*/new Quaternion[]{  new Quaternion().fromAngles(0,0,0),
                                                                    new Quaternion().fromAngles(0,0,0)});

        lWristBoneTrack.setKeyframes( /*TIMES*/new float[]{0,2} , 
                                    /*TRANSLATIONS*/new Vector3f[]{ new Vector3f(),
                                                                    new Vector3f()}, 
                                    /*ROTATIONS*/new Quaternion[]{  new Quaternion().fromAngles(0,0,0),
                                                                    new Quaternion().fromAngles(0,0,0)});

        rShoulderBoneTrack.setKeyframes( /*TIMES*/new float[]{0,2} , 
                                    /*TRANSLATIONS*/new Vector3f[]{ new Vector3f(),
                                                                    new Vector3f()}, 
                                    /*ROTATIONS*/new Quaternion[]{  new Quaternion().fromAngles(0,0,0),
                                                                    new Quaternion().fromAngles(0,0,0)});

        rElbowBoneTrack.setKeyframes( /*TIMES*/new float[]{0,2} , 
                                    /*TRANSLATIONS*/new Vector3f[]{ new Vector3f(),
                                                                    new Vector3f()}, 
                                    /*ROTATIONS*/new Quaternion[]{  new Quaternion().fromAngles(0,0,0),
                                                                    new Quaternion().fromAngles(0,0,0)});

        rWristBoneTrack.setKeyframes( /*TIMES*/new float[]{0,2} , 
                                    /*TRANSLATIONS*/new Vector3f[]{ new Vector3f(),
                                                                    new Vector3f()}, 
                                    /*ROTATIONS*/new Quaternion[]{  new Quaternion().fromAngles(0,0,0),
                                                                    new Quaternion().fromAngles(0,0,0)});

        headBoneTrack.setKeyframes( /*TIMES*/new float[]{0,2} , 
                                    /*TRANSLATIONS*/new Vector3f[]{ new Vector3f(),
                                                                    new Vector3f()}, 
                                    /*ROTATIONS*/new Quaternion[]{  new Quaternion().fromAngles(0,0,0),
                                                                    new Quaternion().fromAngles(0,0,0)});
    }
}
