package HumanoidDev;

import com.jme3.animation.Bone;
import com.jme3.animation.Skeleton;
import com.jme3.animation.SkeletonControl;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.debug.SkeletonDebugger;
import com.jme3.util.BufferUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class TestCustomAnimMesh extends SimpleApplication {

    // Data members for animated mesh
    private Bone bone;
    private Skeleton skeleton;
    private Quaternion rotation = new Quaternion();
    
    Humaniod human;
    
    public static void main(String[] args) {
        TestCustomAnimMesh app = new TestCustomAnimMesh();
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
        
        //setFramePose();
        
        //writeFrameData();
        readFrameData();
    }
    
    protected void buildTestScene(){
        // Create our own mesh
        Mesh m = new Mesh();
        
        // Vertex position data
        Vector3f [] vts = new Vector3f[4];
        vts[0] = new Vector3f(0,0,0);
        vts[1] = new Vector3f(3,0,0);
        vts[2] = new Vector3f(0,3,0);
        vts[3] = new Vector3f(3,3,0);

        // Texture coordinate data
        Vector2f [] tCd = new Vector2f[4];
        tCd[0] = new Vector2f(0,0);
        tCd[0] = new Vector2f(0,0);
        tCd[0] = new Vector2f(0,0);
        tCd[0] = new Vector2f(0,0);
        
        // Indices
        short [] iDx = {
            2,0,1,
            1,3,2
        };
        
        // Set mesh buffers
        m.setBuffer(Type.Position,3, BufferUtils.createFloatBuffer(vts));
        m.setBuffer(Type.TexCoord,2, BufferUtils.createFloatBuffer(tCd));
        m.setBuffer(Type.Index,1, BufferUtils.createShortBuffer(iDx));
        m.updateBound();
        
        // ANIMATED MESH SETUP
        // Create bone buffers (Weights and Indices)...
        // WE SEEM TO NEED THESE EMPTY BUFFERS
        VertexBuffer weightsHW = new VertexBuffer(Type.HWBoneWeight);
        VertexBuffer indicesHW = new VertexBuffer(Type.HWBoneIndex);
        indicesHW.setUsage(VertexBuffer.Usage.CpuOnly);
        weightsHW.setUsage(VertexBuffer.Usage.CpuOnly);
        
        // Set the buffers to our mesh
        m.setBuffer(weightsHW);
        m.setBuffer(indicesHW);
        
        // Setup bone weight buffer
        FloatBuffer weights = FloatBuffer.allocate( m.getVertexCount() * 4 );
        VertexBuffer weightsBuf = new VertexBuffer(Type.BoneWeight);
        weightsBuf.setupData(VertexBuffer.Usage.CpuOnly, 4, VertexBuffer.Format.Float, weights);
        m.setBuffer(weightsBuf);

        // Setup bone index buffer
        ByteBuffer indices = ByteBuffer.allocate( m.getVertexCount() * 4 );
        VertexBuffer indicesBuf = new VertexBuffer(Type.BoneIndex);
        indicesBuf.setupData(VertexBuffer.Usage.CpuOnly, 4, VertexBuffer.Format.UnsignedByte, indices);
        m.setBuffer(indicesBuf);

        // Create bind pose buffers
        m.generateBindPose(true);

        // Create skeleton with a single bone...
        bone = new Bone("root");
        bone.setBindTransforms(Vector3f.ZERO, Quaternion.IDENTITY, Vector3f.UNIT_XYZ);
        bone.setUserControl(true);
        skeleton = new Skeleton(new Bone[]{ bone });

        // Assign all verticies to bone 0 with weight 1
        for (int i = 0; i < m.getVertexCount() * 4; i += 4){
            // assign vertex to bone index 0
            indices.array()[i+0] = 0;
            indices.array()[i+1] = 0;
            indices.array()[i+2] = 0;
            indices.array()[i+3] = 0;

            // set weight to 1 only for first entry
            weights.array()[i+0] = 1;
            weights.array()[i+1] = 0;
            weights.array()[i+2] = 0;
            weights.array()[i+3] = 0;
        }

        // Maximum number of weights per bone is 1
        m.setMaxNumWeights(1);
        
        // Create geometry to add mesh to scene
        Geometry geom = new Geometry("Custom Mesh", m);
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        

        geom.setMaterial(mat);

        rootNode.attachChild(geom);
    }

    float hipMin = 0;
    float hipMax = 90;
    float hipRot = 0;
    boolean dir = true;
    float time = 0;
    float key0 = 0;
    float key1 = 1;
    
    protected void setFramePose(){
        human.rootBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X)  ,Vector3f.UNIT_XYZ);
        human.lHipBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(45 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.lKneeBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.lAnkleBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.rHipBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(-45 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.rKneeBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.rAnkleBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.waistBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.torsoBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.chestBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.lShoulderBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(-30 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.lElbowBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.lWristBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.rShoulderBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(30 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.rElbowBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.rWristBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.headBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
    }
    
    protected void readFrameData(){
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream("MyFile.txt"), "UTF-16");
            BufferedReader bufferedReader = new BufferedReader(reader);
 
            String line;
 
            // Read the file contents
            //while ((line = bufferedReader.readLine()) != null) {
            //    System.out.println(line);
            //}
            
            line = bufferedReader.readLine();
            System.out.println(line);
            if(line.equals("FrameData")){
                String[] quatData;
                // If the first line checks out, grab our rotation data
                line = bufferedReader.readLine();
                quatData = line.split(",");
                System.out.println(line);
                System.out.println(quatData.length);
                System.out.println(quatData[1]);
                human.rootBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])), Vector3f.UNIT_XYZ);
                
                line = bufferedReader.readLine();
                quatData = line.split(",");
                System.out.println(line);
                System.out.println(quatData.length);
                System.out.println(quatData[1]);
                human.lHipBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngles(Float.parseFloat(quatData[1].trim()), Float.parseFloat(quatData[2].trim()), Float.parseFloat(quatData[3].trim())), Vector3f.UNIT_XYZ);
                
                line = bufferedReader.readLine();
                quatData = line.split(",");
                System.out.println(line);
                human.lKneeBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])), Vector3f.UNIT_XYZ);
                
                line = bufferedReader.readLine();
                quatData = line.split(",");
                System.out.println(line);
                human.lAnkleBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])), Vector3f.UNIT_XYZ);
                
                line = bufferedReader.readLine();
                quatData = line.split(",");
                System.out.println(line);
                human.rHipBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])), Vector3f.UNIT_XYZ);
                
                line = bufferedReader.readLine();
                quatData = line.split(",");
                System.out.println(line);
                human.rKneeBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])), Vector3f.UNIT_XYZ);
                
                line = bufferedReader.readLine();
                quatData = line.split(",");
                System.out.println(line);
                human.rAnkleBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])), Vector3f.UNIT_XYZ);
                
                line = bufferedReader.readLine();
                quatData = line.split(",");
                System.out.println(line);
                human.waistBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])), Vector3f.UNIT_XYZ);
                
                line = bufferedReader.readLine();
                quatData = line.split(",");
                System.out.println(line);
                human.torsoBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])), Vector3f.UNIT_XYZ);
                
                line = bufferedReader.readLine();
                quatData = line.split(",");
                System.out.println(line);
                human.chestBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])), Vector3f.UNIT_XYZ);
                
                line = bufferedReader.readLine();
                quatData = line.split(",");
                System.out.println(line);
                human.lShoulderBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])), Vector3f.UNIT_XYZ);
                
                line = bufferedReader.readLine();
                quatData = line.split(",");
                System.out.println(line);
                human.lElbowBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])), Vector3f.UNIT_XYZ);
                
                line = bufferedReader.readLine();
                quatData = line.split(",");
                System.out.println(line);
                human.lWristBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])), Vector3f.UNIT_XYZ);
                
                line = bufferedReader.readLine();
                quatData = line.split(",");
                System.out.println(line);
                human.rShoulderBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])), Vector3f.UNIT_XYZ);
                
                line = bufferedReader.readLine();
                quatData = line.split(",");
                System.out.println(line);
                human.rElbowBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])), Vector3f.UNIT_XYZ);
                
                line = bufferedReader.readLine();
                quatData = line.split(",");
                System.out.println(line);
                human.rWristBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])), Vector3f.UNIT_XYZ);
                
                line = bufferedReader.readLine();
                quatData = line.split(",");
                System.out.println(line);
                human.headBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngles(Float.parseFloat(quatData[1]), Float.parseFloat(quatData[2]), Float.parseFloat(quatData[3])), Vector3f.UNIT_XYZ);
                
            }else if(line.equals("KeyFrameData")){
                
            }else{
                reader.close();
                return;
            }
            
            reader.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    protected void writeFrameData(){
        try {
            FileOutputStream outputStream = new FileOutputStream("MyFile.txt");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-16");
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
             
            // Write file contents
            //bufferedWriter.write("Xin chào");
            //bufferedWriter.newLine();
            //bufferedWriter.write("Hẹn gặp lại!");
            //bufferedWriter.newLine();
            bufferedWriter.write("FrameData");
            bufferedWriter.newLine();
            Quaternion rRot = human.rootBone.getLocalRotation();
            
            Quaternion lHRot = human.lHipBone.getLocalRotation();
            Quaternion lKRot = human.lKneeBone.getLocalRotation();
            Quaternion lARot = human.lAnkleBone.getLocalRotation();
            
            Quaternion rHRot = human.rHipBone.getLocalRotation();
            Quaternion rKRot = human.rKneeBone.getLocalRotation();
            Quaternion rARot = human.rAnkleBone.getLocalRotation();
            
            Quaternion wRot = human.waistBone.getLocalRotation();
            Quaternion tRot = human.torsoBone.getLocalRotation();
            Quaternion cRot = human.chestBone.getLocalRotation();
            
            Quaternion lSRot = human.lShoulderBone.getLocalRotation();
            Quaternion lERot = human.lElbowBone.getLocalRotation();
            Quaternion lWRot = human.lWristBone.getLocalRotation();
            
            Quaternion rSRot = human.rShoulderBone.getLocalRotation();
            Quaternion rERot = human.rElbowBone.getLocalRotation();
            Quaternion rWRot = human.rWristBone.getLocalRotation();
            
            Quaternion hRot = human.headBone.getLocalRotation();
            
            float[] angles = new float[3];
            
            rRot.toAngles(angles);
            bufferedWriter.write(0 +"," +Float.toString(angles[0]*FastMath.RAD_TO_DEG)+ "," +Float.toString(angles[1]*FastMath.RAD_TO_DEG)+","+Float.toString(angles[2]*FastMath.RAD_TO_DEG));
            bufferedWriter.newLine();
            lHRot.toAngles(angles);
            bufferedWriter.write(1 +"," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            bufferedWriter.newLine();
            lKRot.toAngles(angles);
            bufferedWriter.write(2 +"," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            bufferedWriter.newLine();
            lARot.toAngles(angles);
            bufferedWriter.write(3 +"," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            bufferedWriter.newLine();
            rHRot.toAngles(angles);
            bufferedWriter.write(4 +"," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            bufferedWriter.newLine();
            rKRot.toAngles(angles);
            bufferedWriter.write(5 +"," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            bufferedWriter.newLine();
            rARot.toAngles(angles);
            bufferedWriter.write(6 +"," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            bufferedWriter.newLine();
            wRot.toAngles(angles);
            bufferedWriter.write(7 +"," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            bufferedWriter.newLine();
            tRot.toAngles(angles);
            bufferedWriter.write(8 +"," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            bufferedWriter.newLine();
            cRot.toAngles(angles);
            bufferedWriter.write(9 +"," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            bufferedWriter.newLine();
            lSRot.toAngles(angles);
            bufferedWriter.write(10 +"," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            bufferedWriter.newLine();
            lERot.toAngles(angles);
            bufferedWriter.write(11 +"," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            bufferedWriter.newLine();
            lWRot.toAngles(angles);
            bufferedWriter.write(12 +"," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            bufferedWriter.newLine();
            rSRot.toAngles(angles);
            bufferedWriter.write(13 +"," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            bufferedWriter.newLine();
            rERot.toAngles(angles);
            bufferedWriter.write(14 +"," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            bufferedWriter.newLine();
            rWRot.toAngles(angles);
            bufferedWriter.write(15 +"," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            bufferedWriter.newLine();
            hRot.toAngles(angles);
            bufferedWriter.write(16 +"," +(angles[0]*FastMath.RAD_TO_DEG)+ "," +(angles[1]*FastMath.RAD_TO_DEG)+","+(angles[2]*FastMath.RAD_TO_DEG));
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code for skeleton rotation test
        time += tpf;
        
        // Rotate around X axis
        Quaternion rotate = new Quaternion();
        rotate.fromAngleAxis(tpf, Vector3f.UNIT_X);

        // Combine rotation with previous
        rotation.multLocal(rotate);
        
        // Set new rotation into bone
        //rotation.fromAngleAxis(hipRot * FastMath.RAD_TO_DEG, Vector3f.UNIT_X);
        
        //human.hipBone.setUserTransforms(Vector3f.ZERO, rotation,Vector3f.UNIT_XYZ);
        //human.lShoulderBone.setUserTransforms(Vector3f.ZERO, rotation.inverse(),Vector3f.UNIT_XYZ);
        // After changing skeleton transforms, must update world data
        human.skeleton.updateWorldVectors();
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
