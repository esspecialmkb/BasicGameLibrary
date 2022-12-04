package HumanoidDev;

import UI.MeshElement;
import com.jme3.animation.Bone;
import com.jme3.animation.Skeleton;
import mygame.*;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.shape.Box;
import com.jme3.util.BufferUtils;
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
    
    public class Humaniod{
        // Mesh element to hold static mesh data
        MeshElement protoMesh;
        Mesh mesh;
        // Animated mesh data
        Bone[] bone;
        Bone rootBone;
        Bone hipBone;
        Bone kneeBone;
        Bone ankleBone;
        int[] boneIndex;
        float[] boneWeight;
        Skeleton skeleton;
        
        // Humanoid dimension data
        Vector3f leg;
        Vector3f thigh;
        Vector3f pelvis;
        Vector3f torso;
        Vector3f chest;
        Vector3f uArm;
        Vector3f fArm;
        Vector3f neck;
        Vector3f face;
        
        protected void initProtoHumanoidData(){
            protoMesh = new MeshElement();
            protoMesh.initBuffer(MeshElement.MeshBufferVertex,16);
            protoMesh.initBuffer(MeshElement.MeshBufferIndex,72);
            protoMesh.initBuffer(MeshElement.MeshBufferColor,16);
            
            initLLegVerts();
            
            initLLegIndex();
            
            initLLegColors();
        }
        
        protected void initLLegVerts(){
            // Create Vertices - L Shin first
            protoMesh.vertices[0] = new Vector3f(0      ,0 ,leg.z/2);
            protoMesh.vertices[1] = new Vector3f(leg.x ,0 ,leg.z/2);
            protoMesh.vertices[2] = new Vector3f(leg.x  ,0 ,-leg.z/2);
            protoMesh.vertices[3] = new Vector3f(0      ,0 ,-leg.z/2);
            
            protoMesh.vertices[4] = new Vector3f(0      ,leg.y/2 ,leg.z/2);
            protoMesh.vertices[5] = new Vector3f(leg.x ,leg.y/2 ,leg.z/2);
            protoMesh.vertices[6] = new Vector3f(leg.x  ,leg.y/2 ,-leg.z/2);
            protoMesh.vertices[7] = new Vector3f(0      ,leg.y/2 ,-leg.z/2);
            
            protoMesh.vertices[8] = new Vector3f(0      ,leg.y/2 ,leg.z/2);
            protoMesh.vertices[9] = new Vector3f(leg.x ,leg.y/2 ,leg.z/2);
            protoMesh.vertices[10] = new Vector3f(leg.x  ,leg.y/2 ,-leg.z/2);
            protoMesh.vertices[11] = new Vector3f(0      ,leg.y/2 ,-leg.z/2);
            
            protoMesh.vertices[12]  = new Vector3f(0      ,leg.y ,leg.z/2);
            protoMesh.vertices[13]  = new Vector3f(leg.x ,leg.y ,leg.z/2);
            protoMesh.vertices[14] = new Vector3f(leg.x  ,leg.y ,-leg.z/2);
            protoMesh.vertices[15] = new Vector3f(0      ,leg.y ,-leg.z/2);
        }
        
        protected void initLLegIndex(){
            // Create Triangle Indices - Clockwise rotation
            // Bottom (foot) faces
            protoMesh.index[0] = 0;
            protoMesh.index[1] = 3;
            protoMesh.index[2] = 2;
            
            protoMesh.index[3] = 2;
            protoMesh.index[4] = 1;
            protoMesh.index[5] = 0;
            
            // Front shin faces
            protoMesh.index[6] = 0;
            protoMesh.index[7] = 1;
            protoMesh.index[8] = 5;
            
            protoMesh.index[9] = 5;
            protoMesh.index[10] = 4;
            protoMesh.index[11] = 0;
            
            // Left shin faces
            protoMesh.index[12] = 3;
            protoMesh.index[13] = 0;
            protoMesh.index[14] = 4;
            
            protoMesh.index[15] = 4;
            protoMesh.index[16] = 7;
            protoMesh.index[17] = 3;
            
            // Back shin faces
            protoMesh.index[18] = 2;
            protoMesh.index[19] = 3;
            protoMesh.index[20] = 7;
            
            protoMesh.index[21] = 7;
            protoMesh.index[22] = 6;
            protoMesh.index[23] = 2;
            
            // Right shin faces
            protoMesh.index[24] = 1;
            protoMesh.index[25] = 2;
            protoMesh.index[26] = 6;
            
            protoMesh.index[27] = 6;
            protoMesh.index[28] = 5;
            protoMesh.index[29] = 1;
            
            // Top shin faces
            protoMesh.index[30] = 7;
            protoMesh.index[31] = 4;
            protoMesh.index[32] = 5;
            
            protoMesh.index[33] = 5;
            protoMesh.index[34] = 6;
            protoMesh.index[35] = 7;
            
            // Bottom Thigh faces
            protoMesh.index[36] = 8;
            protoMesh.index[37] = 11;
            protoMesh.index[38] = 10;
            
            protoMesh.index[39] = 10;
            protoMesh.index[40] = 9;
            protoMesh.index[41] = 8;
            
            // Front thigh faces
            protoMesh.index[42] = 13;
            protoMesh.index[43] = 12;
            protoMesh.index[44] = 8;
            
            protoMesh.index[45] = 8;
            protoMesh.index[46] = 9;
            protoMesh.index[47] = 13;
            
            // Left thigh faces
            protoMesh.index[48] = 12;
            protoMesh.index[49] = 15;
            protoMesh.index[50] = 11;
            
            protoMesh.index[51] = 11;
            protoMesh.index[52] = 8;
            protoMesh.index[53] = 12;
            
            // Back thigh faces
            protoMesh.index[54] = 15;
            protoMesh.index[55] = 14;
            protoMesh.index[56] = 10;
            
            protoMesh.index[57] = 10;
            protoMesh.index[58] = 11;
            protoMesh.index[59] = 15;
            
            // Right thigh faces
            protoMesh.index[60] = 14;
            protoMesh.index[61] = 13;
            protoMesh.index[62] = 9;
            
            protoMesh.index[63] = 9;
            protoMesh.index[64] = 10;
            protoMesh.index[65] = 14;
            
            // Top thigh faces
            protoMesh.index[66] = 14;
            protoMesh.index[67] = 15;
            protoMesh.index[68] = 12;
            
            protoMesh.index[69] = 12;
            protoMesh.index[70] = 13;
            protoMesh.index[71] = 14;
            
        }
        
        protected void initLLegColors(){
            // Create Colors (rgba)
            // First vert ring (foot)
            protoMesh.color[0] = 0;
            protoMesh.color[1] = 0;
            protoMesh.color[2] = 1;
            protoMesh.color[3] = 1;
            
            protoMesh.color[4] = 0;
            protoMesh.color[5] = 0;
            protoMesh.color[6] = 1;
            protoMesh.color[7] = 1;
            
            protoMesh.color[8] = 0;
            protoMesh.color[9] = 0;
            protoMesh.color[10] = 1;
            protoMesh.color[11] = 1;
            
            protoMesh.color[12] = 0;
            protoMesh.color[13] = 0;
            protoMesh.color[14] = 1;
            protoMesh.color[15] = 1;
            
            // Second vert ring
            protoMesh.color[16] = 0;
            protoMesh.color[17] = 1;
            protoMesh.color[18] = 0;
            protoMesh.color[19] = 1;
            
            protoMesh.color[20] = 0;
            protoMesh.color[21] = 1;
            protoMesh.color[22] = 0;
            protoMesh.color[23] = 1;
            
            protoMesh.color[24] = 0;
            protoMesh.color[25] = 1;
            protoMesh.color[26] = 0;
            protoMesh.color[27] = 1;
            
            protoMesh.color[28] = 0;
            protoMesh.color[29] = 1;
            protoMesh.color[30] = 0;
            protoMesh.color[31] = 1;
            
            // Third vert ring (copy of second but connects to fourth ring)
            protoMesh.color[32] = 0;
            protoMesh.color[33] = 1;
            protoMesh.color[34] = 0;
            protoMesh.color[35] = 1;
            
            protoMesh.color[36] = 0;
            protoMesh.color[37] = 1;
            protoMesh.color[38] = 0;
            protoMesh.color[39] = 1;
            
            protoMesh.color[40] = 0;
            protoMesh.color[41] = 1;
            protoMesh.color[42] = 0;
            protoMesh.color[43] = 1;
            
            protoMesh.color[44] = 0;
            protoMesh.color[45] = 1;
            protoMesh.color[46] = 0;
            protoMesh.color[47] = 1;
            
            // Fourth vert ring
            protoMesh.color[48] = 1;
            protoMesh.color[49] = 0;
            protoMesh.color[50] = 0;
            protoMesh.color[51] = 1;
            
            protoMesh.color[52] = 1;
            protoMesh.color[53] = 0;
            protoMesh.color[54] = 0;
            protoMesh.color[55] = 1;
            
            protoMesh.color[56] = 1;
            protoMesh.color[57] = 0;
            protoMesh.color[58] = 0;
            protoMesh.color[59] = 1;
            
            protoMesh.color[60] = 1;
            protoMesh.color[61] = 0;
            protoMesh.color[62] = 0;
            protoMesh.color[63] = 1;
        }
        
        protected void initMeshData(){
            // MeshElement uses static arrays, meaning we need to know
            //exactly how mant verts, uvs, etc... we will need
            protoMesh = new MeshElement();
            
            protoMesh.initBuffer(MeshElement.MeshBufferVertex,12);
            protoMesh.initBuffer(MeshElement.MeshBufferIndex,72);
            protoMesh.initBuffer(MeshElement.MeshBufferColor,12);
            
            // Create Vertices - L Shin first
            protoMesh.vertices[0] = new Vector3f(0      ,0 ,leg.z/2);
            protoMesh.vertices[1] = new Vector3f(leg.x ,0 ,leg.z/2);
            protoMesh.vertices[2] = new Vector3f(leg.x  ,0 ,-leg.z/2);
            protoMesh.vertices[3] = new Vector3f(0      ,0 ,-leg.z/2);
            
            protoMesh.vertices[4] = new Vector3f(0      ,leg.y/2 ,leg.z/2);
            protoMesh.vertices[5] = new Vector3f(leg.x ,leg.y/2 ,leg.z/2);
            protoMesh.vertices[6] = new Vector3f(leg.x  ,leg.y/2 ,-leg.z/2);
            protoMesh.vertices[7] = new Vector3f(0      ,leg.y/2 ,-leg.z/2);
            
            protoMesh.vertices[8]  = new Vector3f(0      ,leg.y ,leg.z/2);
            protoMesh.vertices[9]  = new Vector3f(leg.x ,leg.y ,leg.z/2);
            protoMesh.vertices[10] = new Vector3f(leg.x  ,leg.y ,-leg.z/2);
            protoMesh.vertices[11] = new Vector3f(0      ,leg.y ,-leg.z/2);
            
            // Create Triangle Indices - Clockwise rotation
            // Bottom (foot) faces
            protoMesh.index[0] = 0;
            protoMesh.index[1] = 3;
            protoMesh.index[2] = 2;
            
            protoMesh.index[3] = 2;
            protoMesh.index[4] = 1;
            protoMesh.index[5] = 0;
            
            // Front shin faces
            protoMesh.index[6] = 0;
            protoMesh.index[7] = 1;
            protoMesh.index[8] = 5;
            
            protoMesh.index[9] = 5;
            protoMesh.index[10] = 4;
            protoMesh.index[11] = 0;
            
            // Left shin faces
            protoMesh.index[12] = 3;
            protoMesh.index[13] = 0;
            protoMesh.index[14] = 4;
            
            protoMesh.index[15] = 4;
            protoMesh.index[16] = 7;
            protoMesh.index[17] = 3;
            
            // Back shin faces
            protoMesh.index[18] = 2;
            protoMesh.index[19] = 3;
            protoMesh.index[20] = 7;
            
            protoMesh.index[21] = 7;
            protoMesh.index[22] = 6;
            protoMesh.index[23] = 2;
            
            // Right shin faces
            protoMesh.index[24] = 1;
            protoMesh.index[25] = 2;
            protoMesh.index[26] = 6;
            
            protoMesh.index[27] = 6;
            protoMesh.index[28] = 5;
            protoMesh.index[29] = 1;
            
            // Top shin faces
            protoMesh.index[30] = 7;
            protoMesh.index[31] = 4;
            protoMesh.index[32] = 5;
            
            protoMesh.index[33] = 5;
            protoMesh.index[34] = 6;
            protoMesh.index[35] = 7;
            
            // Bottom thigh faces
            protoMesh.index[36] = 4;
            protoMesh.index[37] = 7;
            protoMesh.index[38] = 6;
            
            protoMesh.index[39] = 6;
            protoMesh.index[40] = 5;
            protoMesh.index[41] = 4;
            
            // Front thigh faces
            protoMesh.index[42] = 9;
            protoMesh.index[43] = 8;
            protoMesh.index[44] = 4;
            
            protoMesh.index[45] = 4;
            protoMesh.index[46] = 5;
            protoMesh.index[47] = 9;
            
            // Left thigh faces
            protoMesh.index[48] = 8;
            protoMesh.index[49] = 11;
            protoMesh.index[50] = 7;
            
            protoMesh.index[51] = 7;
            protoMesh.index[52] = 4;
            protoMesh.index[53] = 8;
            
            // Back thigh faces
            protoMesh.index[54] = 11;
            protoMesh.index[55] = 10;
            protoMesh.index[56] = 6;
            
            protoMesh.index[57] = 6;
            protoMesh.index[58] = 7;
            protoMesh.index[59] = 11;
            
            // Right thigh faces
            protoMesh.index[60] = 10;
            protoMesh.index[61] = 9;
            protoMesh.index[62] = 5;
            
            protoMesh.index[63] = 5;
            protoMesh.index[64] = 6;
            protoMesh.index[65] = 10;
            
            // Top thigh faces
            protoMesh.index[66] = 10;
            protoMesh.index[67] = 11;
            protoMesh.index[68] = 8;
            
            protoMesh.index[69] = 8;
            protoMesh.index[70] = 9;
            protoMesh.index[71] = 10;
            /*
            protoMesh.index[0] = 0;
            protoMesh.index[0] = 0;
            protoMesh.index[0] = 0;
            */
            // Create Colors (rgba)
            // First vert ring (foot)
            protoMesh.color[0] = 0;
            protoMesh.color[1] = 0;
            protoMesh.color[2] = 1;
            protoMesh.color[3] = 1;
            
            protoMesh.color[4] = 0;
            protoMesh.color[5] = 0;
            protoMesh.color[6] = 1;
            protoMesh.color[7] = 1;
            
            protoMesh.color[8] = 0;
            protoMesh.color[9] = 0;
            protoMesh.color[10] = 1;
            protoMesh.color[11] = 1;
            
            protoMesh.color[12] = 0;
            protoMesh.color[13] = 0;
            protoMesh.color[14] = 1;
            protoMesh.color[15] = 1;
            
            // Second vert ring
            protoMesh.color[16] = 0;
            protoMesh.color[17] = 1;
            protoMesh.color[18] = 0;
            protoMesh.color[19] = 1;
            
            protoMesh.color[20] = 0;
            protoMesh.color[21] = 1;
            protoMesh.color[22] = 0;
            protoMesh.color[23] = 1;
            
            protoMesh.color[24] = 0;
            protoMesh.color[25] = 1;
            protoMesh.color[26] = 0;
            protoMesh.color[27] = 1;
            
            protoMesh.color[28] = 0;
            protoMesh.color[29] = 1;
            protoMesh.color[30] = 0;
            protoMesh.color[31] = 1;
            
            // Third vert ring
            protoMesh.color[32] = 1;
            protoMesh.color[33] = 0;
            protoMesh.color[34] = 0;
            protoMesh.color[35] = 1;
            
            protoMesh.color[36] = 1;
            protoMesh.color[37] = 0;
            protoMesh.color[38] = 0;
            protoMesh.color[39] = 1;
            
            protoMesh.color[40] = 1;
            protoMesh.color[41] = 0;
            protoMesh.color[42] = 0;
            protoMesh.color[43] = 1;
            
            protoMesh.color[44] = 1;
            protoMesh.color[45] = 0;
            protoMesh.color[46] = 0;
            protoMesh.color[47] = 1;
        }
        
        protected void initAnimData(){
            // ANIMATED MESH SETUP
            // Create bone buffers (Weights and Indices)...
            // WE SEEM TO NEED THESE EMPTY BUFFERS
            VertexBuffer weightsHW = new VertexBuffer(Type.HWBoneWeight);
            VertexBuffer indicesHW = new VertexBuffer(Type.HWBoneIndex);
            indicesHW.setUsage(VertexBuffer.Usage.CpuOnly);
            weightsHW.setUsage(VertexBuffer.Usage.CpuOnly);
            
            // Set the buffers to our mesh
            mesh.setBuffer(weightsHW);
            mesh.setBuffer(indicesHW);

            // Setup bone weight buffer
            FloatBuffer weights = FloatBuffer.allocate( mesh.getVertexCount() * 4 );
            VertexBuffer weightsBuf = new VertexBuffer(Type.BoneWeight);
            weightsBuf.setupData(VertexBuffer.Usage.CpuOnly, 4, VertexBuffer.Format.Float, weights);
            mesh.setBuffer(weightsBuf);

            // Setup bone index buffer
            ByteBuffer indices = ByteBuffer.allocate( mesh.getVertexCount() * 4 );
            VertexBuffer indicesBuf = new VertexBuffer(Type.BoneIndex);
            indicesBuf.setupData(VertexBuffer.Usage.CpuOnly, 4, VertexBuffer.Format.UnsignedByte, indices);
            mesh.setBuffer(indicesBuf);

            // Create skeleton with a few bones...
            rootBone = new Bone("root");
            rootBone.setBindTransforms(Vector3f.ZERO, Quaternion.IDENTITY, Vector3f.UNIT_XYZ);
            rootBone.setUserControl(true);
            
            hipBone = new Bone("root");
            hipBone.setBindTransforms(Vector3f.ZERO, Quaternion.IDENTITY, Vector3f.UNIT_XYZ);
            hipBone.setUserControl(true);
            
            kneeBone = new Bone("knee");
            kneeBone.setBindTransforms(Vector3f.ZERO, Quaternion.IDENTITY, Vector3f.UNIT_XYZ);
            kneeBone.setUserControl(true);
            
            ankleBone = new Bone("ankle");
            ankleBone.setBindTransforms(Vector3f.ZERO, Quaternion.IDENTITY, Vector3f.UNIT_XYZ);
            ankleBone.setUserControl(true);
            
            // Set the hierarchy before transforms
            rootBone.addChild(hipBone);
            hipBone.addChild(kneeBone);
            kneeBone.addChild(ankleBone);
            
            // Set user transforms seperate to keep them consistent
            rootBone.setUserTransformsInModelSpace(Vector3f.ZERO, Quaternion.IDENTITY);
            hipBone.setUserTransformsInModelSpace(Vector3f.ZERO, Quaternion.IDENTITY);
            kneeBone.setUserTransformsInModelSpace(Vector3f.ZERO, Quaternion.IDENTITY);
            ankleBone.setUserTransformsInModelSpace(Vector3f.ZERO, Quaternion.IDENTITY);
            
            // Create bind pose buffers
            mesh.generateBindPose(true);
            
            skeleton = new Skeleton(new Bone[]{ rootBone,hipBone,kneeBone,ankleBone });

            // Assign all verticies to bone 0 with weight 1
            // Assign hip verticies to bone index 1, four weights per vertex
            
            //v=0
            indices.array()[0]= 1;
            indices.array()[1]= 1;
            indices.array()[2]= 1;
            indices.array()[3]= 1;
            
            // (Buffer, vertID, value)
            assignVertexIndex(indices,0,(byte)1);
            assignVertexWeight(weights,0,1);
            
            //v=1
            /*for (int i = 0; i < mesh.getVertexCount() * 4; i += 4){
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
            }*/

            // Maximum number of weights per bone is 1
            mesh.setMaxNumWeights(1);
        }
        
        protected void assignVertexIndex(ByteBuffer index,int vertex, byte boneIndex){
            int vertId = vertex * 4;
            index.array()[vertId + 0] = boneIndex;
            index.array()[vertId + 1] = boneIndex;
            index.array()[vertId + 2] = boneIndex;
            index.array()[vertId + 3] = boneIndex;
            
        }
        
        protected void assignVertexWeight(FloatBuffer wIndex,int vertex, float weight){
            int vertId = vertex * 4;
            wIndex.array()[vertId+0] = weight;
            wIndex.array()[vertId+1] = 0;
            wIndex.array()[vertId+2] = 0;
            wIndex.array()[vertId+3] = 0;
            
        }
        
        public void initPrototype(){
            
            // Init dimension data
            leg    = new Vector3f(1,3,1);
            pelvis = new Vector3f(2,1,1);
            torso  = new Vector3f(2,3,1);
            chest  = new Vector3f(2,3,1);
            uArm   = new Vector3f(1,3,1);
            fArm   = new Vector3f(1,3,1);
            neck   = new Vector3f(1,1,1);
            face   = new Vector3f(1,3,1);
            
            // Get the mesh data ready
            //initMeshData();
            initProtoHumanoidData();
            mesh = new Mesh();
            mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(protoMesh.vertices));
            mesh.setBuffer(Type.Index, 1, BufferUtils.createIntBuffer(protoMesh.index));
            mesh.setBuffer(Type.Color, 4, BufferUtils.createFloatBuffer(protoMesh.color));
            mesh.updateBound();
        }
        
        
    }
    
    public static void main(String[] args) {
        TestCustomAnimMesh app = new TestCustomAnimMesh();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Humaniod human = new Humaniod();
        human.initPrototype();
        
        // Create geometry to add mesh to scene
        Geometry geom = new Geometry("Custom Mesh", human.mesh);
        
        Material mat = assetManager.loadMaterial("Materials/VertexColorMat.j3m");
        geom.setMaterial(mat);
        rootNode.attachChild(geom);
        
        flyCam.setMoveSpeed(25);
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

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code for skeleton rotation test
        rotation.fromAngleAxis(tpf, Vector3f.UNIT_X);
        
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
