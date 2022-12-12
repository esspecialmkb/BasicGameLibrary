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
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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
        
        setFramePose();
        
        writeFrameData();
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
        human.rootBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0, Vector3f.UNIT_X)  ,Vector3f.UNIT_XYZ);
        human.lHipBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(30 * FastMath.DEG_TO_RAD, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.lKneeBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.lAnkleBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.rHipBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.rKneeBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.rAnkleBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.waistBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.torsoBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.chestBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.lShoulderBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.lElbowBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.lWristBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.rShoulderBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.rElbowBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
        human.rWristBone.setUserTransforms(Vector3f.ZERO, new Quaternion().fromAngleAxis(0, Vector3f.UNIT_X),Vector3f.UNIT_XYZ);
    }
    
    // UNTESTED
    protected void readFrameData(){
        try {
            FileReader reader = new FileReader("MyFile.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
 
            String line;
 
            // Read the file contents
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
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
            bufferedWriter.write("Xin chào");
            bufferedWriter.newLine();
            bufferedWriter.write("Hẹn gặp lại!");
             
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
