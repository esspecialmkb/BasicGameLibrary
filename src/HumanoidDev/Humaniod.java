/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HumanoidDev;

import UI.MeshElement;
import com.jme3.animation.Bone;
import com.jme3.animation.Skeleton;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 *
 * @author TigerSage
 */
public class Humaniod{
    // Mesh element to hold static mesh data
    MeshElement protoMesh;
    Mesh mesh;
    // Animated mesh data
    Bone[] bone;
    // List of bones
    Bone rootBone;
    Bone lHipBone;
    Bone lKneeBone;
    Bone lAnkleBone;
    Bone rHipBone;
    Bone rKneeBone;
    Bone rAnkleBone;
    
    Bone waistBone;
    Bone torsoBone;
    Bone chestBone;
    
    // Bone rotations
    Quaternion rootRotation;
    Quaternion lHipRotation;
    Quaternion lKneeRotation;
    Quaternion lAnkleRotation;
    Quaternion rHipRotation;
    Quaternion rKneeRotation;
    Quaternion rAnkleRotation;
    
    Quaternion waistRotation;
    Quaternion torsoRotation;
    Quaternion chestRotation;

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

    // Node for movement
    Node model;

    protected void initProtoHumanoidData(){
        protoMesh = new MeshElement();
        protoMesh.initBuffer(MeshElement.MeshBufferVertex,56);
        protoMesh.initBuffer(MeshElement.MeshBufferIndex,252);
        protoMesh.initBuffer(MeshElement.MeshBufferColor,56);

        initLLegVerts();
        initRLegVerts();
        initPelvisVerts();
        initBodyVerts();
        
        initLLegIndex();
        initRLegIndex();
        initPelvisIndex();
        initBodyIndex();

        initLLegColors();
        initRLegColors();
        initPelvisColors();
        initBodyColors();
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

        protoMesh.vertices[12] = new Vector3f(0      ,leg.y ,leg.z/2);
        protoMesh.vertices[13] = new Vector3f(leg.x  ,leg.y ,leg.z/2);
        protoMesh.vertices[14] = new Vector3f(leg.x  ,leg.y ,-leg.z/2);
        protoMesh.vertices[15] = new Vector3f(0      ,leg.y ,-leg.z/2);
    }

    protected void initRLegVerts(){
        // Create Vertices - R Shin Next (shift over)
        protoMesh.vertices[16] = new Vector3f(-leg.x,   0 ,leg.z/2);
        protoMesh.vertices[17] = new Vector3f(0,        0 ,leg.z/2);
        protoMesh.vertices[18] = new Vector3f(0,        0 ,-leg.z/2);
        protoMesh.vertices[19] = new Vector3f(-leg.x,   0 ,-leg.z/2);

        protoMesh.vertices[20] = new Vector3f(-leg.x,   leg.y/2 ,leg.z/2);
        protoMesh.vertices[21] = new Vector3f(0,        leg.y/2 ,leg.z/2);
        protoMesh.vertices[22] = new Vector3f(0,        leg.y/2 ,-leg.z/2);
        protoMesh.vertices[23] = new Vector3f(-leg.x,   leg.y/2 ,-leg.z/2);

        protoMesh.vertices[24] = new Vector3f(-leg.x,   leg.y/2 ,leg.z/2);
        protoMesh.vertices[25] = new Vector3f(0,        leg.y/2 ,leg.z/2);
        protoMesh.vertices[26] = new Vector3f(0,        leg.y/2 ,-leg.z/2);
        protoMesh.vertices[27] = new Vector3f(-leg.x,   leg.y/2 ,-leg.z/2);

        protoMesh.vertices[28] = new Vector3f(-leg.x,  leg.y ,leg.z/2);
        protoMesh.vertices[29] = new Vector3f(0,       leg.y ,leg.z/2);
        protoMesh.vertices[30] = new Vector3f(0,        leg.y ,-leg.z/2);
        protoMesh.vertices[31] = new Vector3f(-leg.x,   leg.y ,-leg.z/2);
    }
    
    protected void initPelvisVerts(){
        protoMesh.vertices[32] = new Vector3f(-leg.x,   leg.y,  leg.z/2);
        protoMesh.vertices[33] = new Vector3f(leg.x,    leg.y,  leg.z/2);
        protoMesh.vertices[34] = new Vector3f(leg.x,    leg.y,  -leg.z/2);
        protoMesh.vertices[35] = new Vector3f(-leg.x,   leg.y,  -leg.z/2);
        
        protoMesh.vertices[36] = new Vector3f(-leg.x,  leg.y + pelvis.y,  leg.z/2);
        protoMesh.vertices[37] = new Vector3f(leg.x,   leg.y + pelvis.y,  leg.z/2);
        protoMesh.vertices[38] = new Vector3f(leg.x,   leg.y + pelvis.y,  -leg.z/2);
        protoMesh.vertices[39] = new Vector3f(-leg.x,  leg.y + pelvis.y,  -leg.z/2);
        
    }
    
    protected void initBodyVerts(){
        protoMesh.vertices[40] = new Vector3f(-leg.x,  leg.y + pelvis.y,  leg.z/2);
        protoMesh.vertices[41] = new Vector3f(leg.x,   leg.y + pelvis.y,  leg.z/2);
        protoMesh.vertices[42] = new Vector3f(leg.x,   leg.y + pelvis.y,  -leg.z/2);
        protoMesh.vertices[43] = new Vector3f(-leg.x,  leg.y + pelvis.y,  -leg.z/2);
        
        protoMesh.vertices[44] = new Vector3f(-leg.x,  leg.y + pelvis.y + torso.y,  leg.z/2);
        protoMesh.vertices[45] = new Vector3f(leg.x,   leg.y + pelvis.y + torso.y,  leg.z/2);
        protoMesh.vertices[46] = new Vector3f(leg.x,   leg.y + pelvis.y + torso.y,  -leg.z/2);
        protoMesh.vertices[47] = new Vector3f(-leg.x,  leg.y + pelvis.y + torso.y,  -leg.z/2);
        
        protoMesh.vertices[48] = new Vector3f(-leg.x, leg.y + pelvis.y + torso.y,  leg.z/2);
        protoMesh.vertices[49] = new Vector3f(leg.x,  leg.y + pelvis.y + torso.y,  leg.z/2);
        protoMesh.vertices[50] = new Vector3f(leg.x,  leg.y + pelvis.y + torso.y,  -leg.z/2);
        protoMesh.vertices[51] = new Vector3f(-leg.x, leg.y + pelvis.y + torso.y,  -leg.z/2);
        
        protoMesh.vertices[52] = new Vector3f(-leg.x,  leg.y + pelvis.y + torso.y + chest.y,  leg.z/2);
        protoMesh.vertices[53] = new Vector3f(leg.x,   leg.y + pelvis.y + torso.y + chest.y,  leg.z/2);
        protoMesh.vertices[54] = new Vector3f(leg.x,   leg.y + pelvis.y + torso.y + chest.y,  -leg.z/2);
        protoMesh.vertices[55] = new Vector3f(-leg.x,  leg.y + pelvis.y + torso.y + chest.y,  -leg.z/2);
        
    }
    
    // initLArmVerts
    // initRArmVerts
    // initHeadVerts
    
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
    
    protected void initRLegIndex(){
        // Clock wise rotations for triangles
        // Bottom faces of shin
        protoMesh.index[72] = 17;
        protoMesh.index[73] = 16;
        protoMesh.index[74] = 19;
        
        protoMesh.index[75] = 19;
        protoMesh.index[76] = 18;
        protoMesh.index[77] = 17;
        
        // Front faces of the shin
        protoMesh.index[78] = 20;
        protoMesh.index[79] = 16;
        protoMesh.index[80] = 17;
        
        protoMesh.index[81] = 17;
        protoMesh.index[82] = 21;
        protoMesh.index[83] = 20;
        
        // Left faces of the shin
        protoMesh.index[84] = 20;
        protoMesh.index[85] = 23;
        protoMesh.index[86] = 19;
        
        protoMesh.index[87] = 19;
        protoMesh.index[88] = 16;
        protoMesh.index[89] = 20;
        
        // Back faces of the shin
        protoMesh.index[90] = 23;
        protoMesh.index[91] = 22;
        protoMesh.index[92] = 18;
        
        protoMesh.index[93] = 18;
        protoMesh.index[94] = 19;
        protoMesh.index[95] = 23;
        
        // Right faces of the shin
        protoMesh.index[96] = 22;
        protoMesh.index[97] = 21;
        protoMesh.index[98] = 17;
        
        protoMesh.index[99] = 17;
        protoMesh.index[100] = 18;
        protoMesh.index[101] = 22;
        
        // Top faces of the shin
        protoMesh.index[102] = 22;
        protoMesh.index[103] = 23;
        protoMesh.index[104] = 20;
        
        protoMesh.index[105] = 20;
        protoMesh.index[106] = 21;
        protoMesh.index[107] = 22;
        
        // Bottom faces of the thigh
        protoMesh.index[108] = 25;
        protoMesh.index[109] = 24;
        protoMesh.index[110] = 27;
        
        protoMesh.index[111] = 27;
        protoMesh.index[112] = 26;
        protoMesh.index[113] = 25;
        
        // Front thigh faces
        protoMesh.index[114] = 29;
        protoMesh.index[115] = 28;
        protoMesh.index[116] = 24;
        
        protoMesh.index[117] = 24;
        protoMesh.index[118] = 25;
        protoMesh.index[119] = 29;
        
        // Left thigh faces
        protoMesh.index[120] = 28;
        protoMesh.index[121] = 31;
        protoMesh.index[122] = 27;
        
        protoMesh.index[123] = 27;
        protoMesh.index[124] = 24;
        protoMesh.index[125] = 28;
        
        // Back thigh faces
        protoMesh.index[126] = 31;
        protoMesh.index[127] = 30;
        protoMesh.index[128] = 26;
        
        protoMesh.index[129] = 26;
        protoMesh.index[130] = 27;
        protoMesh.index[131] = 31;
        
        // Right thigh faces
        protoMesh.index[132] = 30;
        protoMesh.index[133] = 29;
        protoMesh.index[134] = 25;
        
        protoMesh.index[135] = 25;
        protoMesh.index[136] = 26;
        protoMesh.index[137] = 30;
        
        // Top thigh faces
        protoMesh.index[138] = 30;
        protoMesh.index[139] = 31;
        protoMesh.index[140] = 28;
        
        protoMesh.index[141] = 28;
        protoMesh.index[142] = 29;
        protoMesh.index[143] = 30;
        
    }
    
    protected void initPelvisIndex(){
        // Bottom pelvic faces
        protoMesh.index[144] = 33;
        protoMesh.index[145] = 32;
        protoMesh.index[146] = 35;
        
        protoMesh.index[147] = 35;
        protoMesh.index[148] = 34;
        protoMesh.index[149] = 33;
        
        // Front pelvic faces
        protoMesh.index[150] = 37;
        protoMesh.index[151] = 36;
        protoMesh.index[152] = 32;
        
        protoMesh.index[153] = 32;
        protoMesh.index[154] = 33;
        protoMesh.index[155] = 37;
        
        // Left pelvic faces
        protoMesh.index[156] = 36;
        protoMesh.index[157] = 39;
        protoMesh.index[158] = 35;
        
        protoMesh.index[159] = 35;
        protoMesh.index[160] = 32;
        protoMesh.index[161] = 36;
        
        // Back pelvic faces
        protoMesh.index[162] = 39;
        protoMesh.index[163] = 38;
        protoMesh.index[164] = 34;
        
        protoMesh.index[165] = 34;
        protoMesh.index[166] = 35;
        protoMesh.index[167] = 39;
        
        // Right pelvic faces
        protoMesh.index[168] = 38;
        protoMesh.index[169] = 37;
        protoMesh.index[170] = 33;
        
        protoMesh.index[171] = 33;
        protoMesh.index[172] = 34;
        protoMesh.index[173] = 38;
        
        // Top pelvic faces
        protoMesh.index[174] = 38;
        protoMesh.index[175] = 39;
        protoMesh.index[176] = 36;
        
        protoMesh.index[177] = 36;
        protoMesh.index[178] = 37;
        protoMesh.index[179] = 38;
        
    }

    protected void initBodyIndex(){
        // TORSO SECTION
        // Bottom torso faces
        protoMesh.index[180] = 41;
        protoMesh.index[181] = 40;
        protoMesh.index[182] = 43;
        
        protoMesh.index[183] = 43;
        protoMesh.index[184] = 42;
        protoMesh.index[185] = 41;
        
        // Front torso faces
        protoMesh.index[186] = 45;
        protoMesh.index[187] = 44;
        protoMesh.index[188] = 40;
        
        protoMesh.index[189] = 40;
        protoMesh.index[190] = 41;
        protoMesh.index[191] = 45;
        
        // Left torso faces
        protoMesh.index[192] = 44;
        protoMesh.index[193] = 47;
        protoMesh.index[194] = 43;
        
        protoMesh.index[195] = 43;
        protoMesh.index[196] = 40;
        protoMesh.index[197] = 44;
        
        // Back torso faces
        protoMesh.index[198] = 47;
        protoMesh.index[199] = 46;
        protoMesh.index[200] = 42;
        
        protoMesh.index[201] = 42;
        protoMesh.index[202] = 43;
        protoMesh.index[203] = 47;
        
        // Right torso faces
        protoMesh.index[204] = 46;
        protoMesh.index[205] = 45;
        protoMesh.index[206] = 41;
        
        protoMesh.index[207] = 41;
        protoMesh.index[208] = 42;
        protoMesh.index[209] = 46;
        
        // Top torso faces
        protoMesh.index[210] = 46;
        protoMesh.index[211] = 47;
        protoMesh.index[212] = 44;
        
        protoMesh.index[213] = 44;
        protoMesh.index[214] = 45;
        protoMesh.index[215] = 46;
        
        // CHEST SECTION
        // Bottom body faces
        protoMesh.index[216] = 49;
        protoMesh.index[217] = 48;
        protoMesh.index[218] = 51;
        
        protoMesh.index[219] = 51;
        protoMesh.index[220] = 50;
        protoMesh.index[221] = 49;
        
        // Front body faces
        protoMesh.index[222] = 53;
        protoMesh.index[223] = 52;
        protoMesh.index[224] = 48;
        
        protoMesh.index[225] = 48;
        protoMesh.index[226] = 49;
        protoMesh.index[227] = 53;
        
        // Left body faces
        protoMesh.index[228] = 52;
        protoMesh.index[229] = 55;
        protoMesh.index[230] = 51;
        
        protoMesh.index[231] = 51;
        protoMesh.index[232] = 48;
        protoMesh.index[233] = 52;
        
        // Back body faces
        protoMesh.index[234] = 55;
        protoMesh.index[235] = 54;
        protoMesh.index[236] = 50;
        
        protoMesh.index[237] = 50;
        protoMesh.index[238] = 51;
        protoMesh.index[239] = 55;
        
        // Right body faces
        protoMesh.index[240] = 54;
        protoMesh.index[241] = 53;
        protoMesh.index[242] = 49;
        
        protoMesh.index[243] = 49;
        protoMesh.index[244] = 50;
        protoMesh.index[245] = 54;
        
        // Top body faces
        protoMesh.index[246] = 54;
        protoMesh.index[247] = 55;
        protoMesh.index[248] = 52;
        
        protoMesh.index[249] = 52;
        protoMesh.index[250] = 53;
        protoMesh.index[251] = 54;
    }
    
    // initLArmIndex
    // initRArmIndex
    // initHeadIndex
    
    protected void initLLegColors(){
        // Create Colors (rgba)
        // First vert ring (foot)
        // (Vertex, r,g,b,a)
        assignVertexColor(0,0,1,0,1);
        assignVertexColor(1,0,1,0,1);
        assignVertexColor(2,0,1,0,1);
        assignVertexColor(3,0,1,0,1);

        // Second vert ring
        assignVertexColor(4,0,1,0,1);
        assignVertexColor(5,0,1,0,1);
        assignVertexColor(6,0,1,0,1);
        assignVertexColor(7,0,1,0,1);
        
        // Third vert ring (copy of second but connects to fourth ring)
        assignVertexColor(8,1,0,0,1);
        assignVertexColor(9,1,0,0,1);
        assignVertexColor(10,1,0,0,1);
        assignVertexColor(11,1,0,0,1);

        // Fourth vert ring
        assignVertexColor(12,1,0,0,1);
        assignVertexColor(13,1,0,0,1);
        assignVertexColor(14,1,0,0,1);
        assignVertexColor(15,1,0,0,1);
    }

    protected void initRLegColors(){
        // Create Colors (rgba)
        // First vert ring (foot)
        // (Vertex, r,g,b,a)
        assignVertexColor(16,0,0,1,1);
        assignVertexColor(17,0,0,1,1);
        assignVertexColor(18,0,0,1,1);
        assignVertexColor(19,0,0,1,1);

        // Second vert ring
        assignVertexColor(20,0,0,1,1);
        assignVertexColor(21,0,0,1,1);
        assignVertexColor(22,0,0,1,1);
        assignVertexColor(23,0,0,1,1);
        
        // Third vert ring (copy of second but connects to fourth ring)
        assignVertexColor(24,0,1,0,1);
        assignVertexColor(25,0,1,0,1);
        assignVertexColor(26,0,1,0,1);
        assignVertexColor(27,0,1,0,1);

        // Fourth vert ring
        assignVertexColor(28,0,1,0,1);
        assignVertexColor(29,0,1,0,1);
        assignVertexColor(30,0,1,0,1);
        assignVertexColor(31,0,1,0,1);
        
    }
    
    protected void initPelvisColors(){
        // Create Colors for Pelvis(rgba)
        // First vert ring (foot)
        // (Vertex, r,g,b,a)
        assignVertexColor(32,0,0,1,1);
        assignVertexColor(33,0,0,1,1);
        assignVertexColor(34,0,0,1,1);
        assignVertexColor(35,0,0,1,1);

        // Second vert ring
        assignVertexColor(36,0,0,1,1);
        assignVertexColor(37,0,0,1,1);
        assignVertexColor(38,0,0,1,1);
        assignVertexColor(39,0,0,1,1);
    }
    
    protected void initBodyColors(){
        // Create Colors (rgba)
        // First vert ring (foot)
        // (Vertex, r,g,b,a)
        assignVertexColor(40,0,1,0,1);
        assignVertexColor(41,0,1,0,1);
        assignVertexColor(42,0,1,0,1);
        assignVertexColor(43,0,1,0,1);

        // Second vert ring
        assignVertexColor(44,0,1,0,1);
        assignVertexColor(45,0,1,0,1);
        assignVertexColor(46,0,1,0,1);
        assignVertexColor(47,0,1,0,1);
        
        // Third vert ring (copy of second but connects to fourth ring)
        assignVertexColor(48,1,0,0,1);
        assignVertexColor(49,1,0,0,1);
        assignVertexColor(50,1,0,0,1);
        assignVertexColor(51,1,0,0,1);

        // Fourth vert ring
        assignVertexColor(52,1,0,0,1);
        assignVertexColor(53,1,0,0,1);
        assignVertexColor(54,1,0,0,1);
        assignVertexColor(55,1,0,0,1);
    }
    // initLArmColors
    // initRArmColors
    // initHeadColors
    
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
        VertexBuffer weightsHW = new VertexBuffer(VertexBuffer.Type.HWBoneWeight);
        VertexBuffer indicesHW = new VertexBuffer(VertexBuffer.Type.HWBoneIndex);
        indicesHW.setUsage(VertexBuffer.Usage.CpuOnly);
        weightsHW.setUsage(VertexBuffer.Usage.CpuOnly);

        // Set the buffers to our mesh
        mesh.setBuffer(weightsHW);
        mesh.setBuffer(indicesHW);

        // Setup bone weight buffer
        FloatBuffer weights = FloatBuffer.allocate( mesh.getVertexCount() * 4 );
        VertexBuffer weightsBuf = new VertexBuffer(VertexBuffer.Type.BoneWeight);
        weightsBuf.setupData(VertexBuffer.Usage.CpuOnly, 4, VertexBuffer.Format.Float, weights);
        mesh.setBuffer(weightsBuf);

        // Setup bone index buffer
        ByteBuffer indices = ByteBuffer.allocate( mesh.getVertexCount() * 4 );
        VertexBuffer indicesBuf = new VertexBuffer(VertexBuffer.Type.BoneIndex);
        indicesBuf.setupData(VertexBuffer.Usage.CpuOnly, 4, VertexBuffer.Format.UnsignedByte, indices);
        mesh.setBuffer(indicesBuf);

        mesh.generateBindPose(true);

        // Create skeleton with a few bones...
        rootBone = new Bone("root");
        rootRotation = new Quaternion().fromAngles(0,0,0);
        rootBone.setBindTransforms(Vector3f.ZERO, Quaternion.IDENTITY, Vector3f.UNIT_XYZ);
        rootBone.setUserControl(true);

        lHipBone = new Bone("l hip");
        lHipRotation = new Quaternion().fromAngleAxis(0, Vector3f.UNIT_X);
        lHipBone.setBindTransforms(new Vector3f(leg.x/2,-pelvis.y/2,0), Quaternion.IDENTITY, Vector3f.UNIT_XYZ);
        lHipBone.setUserControl(true);

        lKneeBone = new Bone("l knee");
        lKneeRotation = new Quaternion().fromAngleAxis(0, Vector3f.UNIT_X);
        lKneeBone.setBindTransforms(new Vector3f(0,leg.y/-2,0), Quaternion.IDENTITY, Vector3f.UNIT_XYZ);
        lKneeBone.setUserControl(true);

        lAnkleBone = new Bone("l ankle");
        lAnkleRotation = new Quaternion().fromAngleAxis(0, Vector3f.UNIT_X);
        lAnkleBone.setBindTransforms(new Vector3f(0,leg.y/-2,0), Quaternion.IDENTITY, Vector3f.UNIT_XYZ);
        lAnkleBone.setUserControl(true);
        
        // RIGHT LEG
        
        rHipBone = new Bone("r hip");
        rHipRotation = new Quaternion().fromAngleAxis(0, Vector3f.UNIT_X);
        rHipBone.setBindTransforms(new Vector3f(-leg.x/2,-pelvis.y/2,0), Quaternion.IDENTITY, Vector3f.UNIT_XYZ);
        rHipBone.setUserControl(true);

        rKneeBone = new Bone("r knee");
        rKneeRotation = new Quaternion().fromAngleAxis(0, Vector3f.UNIT_X);
        rKneeBone.setBindTransforms(new Vector3f(0,leg.y/-2,0), Quaternion.IDENTITY, Vector3f.UNIT_XYZ);
        rKneeBone.setUserControl(true);

        rAnkleBone = new Bone("r ankle");
        rAnkleRotation = new Quaternion().fromAngleAxis(0, Vector3f.UNIT_X);
        rAnkleBone.setBindTransforms(new Vector3f(0,leg.y/-2,0), Quaternion.IDENTITY, Vector3f.UNIT_XYZ);
        rAnkleBone.setUserControl(true);
        
        waistBone = new Bone("r waist");
        waistRotation = new Quaternion().fromAngleAxis(0, Vector3f.UNIT_X);
        waistBone.setBindTransforms(new Vector3f(0,leg.y + pelvis.y/2,0), Quaternion.IDENTITY, Vector3f.UNIT_XYZ);
        waistBone.setUserControl(true);

        // Set the hierarchy before transforms
        rootBone.addChild(waistBone);
        
        waistBone.addChild(lHipBone);
        waistBone.addChild(rHipBone);
        
        lHipBone.addChild(lKneeBone);
        lKneeBone.addChild(lAnkleBone);
        
        rHipBone.addChild(rKneeBone);
        rKneeBone.addChild(rAnkleBone);

        skeleton = new Skeleton(new Bone[]{ rootBone,lHipBone,lKneeBone,lAnkleBone,rHipBone,rKneeBone,rAnkleBone,waistBone });

        // Assign all verticies to bone 0 with weight 1

        byte rootId = 0;
        byte lHipId = 1;
        byte lKneeId = 2;
        byte lAnkleId = 3;
        byte rHipId = 4;
        byte rKneeId = 5;
        byte rAnkleId = 6;
        byte waistId = 7;

        // Assign l shin verticies to bone index 2
        // 0 - 7
        // (Buffer, vertID, value)
        
        for(int lsi = 0; lsi < 8; lsi++){
            assignVertexIndex(indices,lsi,lKneeId);
            assignVertexWeight(weights,lsi,1);
        }
        

        // Assign l hip verticies to bone index 1, four weights per vertex
        // 8 - 15
        // (Buffer, vertID, value)

        for(int lhi = 8; lhi < 16; lhi++){
            assignVertexIndex(indices,lhi,lHipId);
            assignVertexWeight(weights,lhi,1);
        }

        // Assign r shin vertices to bone index 5
        // 16 - 23
        // (Buffer, vertID, value)
        
        for(int rsi = 16; rsi < 24; rsi++){
            assignVertexIndex(indices,rsi,rKneeId);
            assignVertexWeight(weights,rsi,1);
        }
        
        // Assign r hip vertices to bone index 5
        // 24 - 31
        // (Buffer, vertID, value)
        
        for(int rhi = 24; rhi < 32; rhi++){
            assignVertexIndex(indices,rhi,rHipId);
            assignVertexWeight(weights,rhi,1);
        }
        
        // Assign waist vertices to bone index 5
        // 32 - 39
        // (Buffer, vertID, value)
        
        for(int wi = 32; wi < 40; wi++){
            assignVertexIndex(indices,wi,waistId);
            assignVertexWeight(weights,wi,1);
        }
        
        // Maximum number of weights per bone is 1
        mesh.setMaxNumWeights(1);
    }
    
    protected void assignVertexColor(int vertex, float r, float g, float b, float a){
        int vertId = vertex * 4;
        protoMesh.color[vertId + 0] = r;
        protoMesh.color[vertId + 1] = g;
        protoMesh.color[vertId + 2] = b;
        protoMesh.color[vertId + 3] = a;
    }

    protected void assignVertexIndex(ByteBuffer index,int vertex, byte boneIndex){
        int vertId = vertex * 4;
        index.array()[vertId + 0] = boneIndex;
        index.array()[vertId + 1] = boneIndex;
        index.array()[vertId + 2] = boneIndex;
        index.array()[vertId + 3] = boneIndex;
    }

    protected void assignVertexIndices(ByteBuffer index,int vertex, byte boneIndex0, byte boneIndex1, byte boneIndex2, byte boneIndex3){
        int vertId = vertex * 4;
        index.array()[vertId + 0] = boneIndex0;
        index.array()[vertId + 1] = boneIndex1;
        index.array()[vertId + 2] = boneIndex2;
        index.array()[vertId + 3] = boneIndex3;
    }

    protected void assignVertexWeight(FloatBuffer wIndex,int vertex, float weight){
        int vertId = vertex * 4;
        wIndex.array()[vertId+0] = weight;
        wIndex.array()[vertId+1] = 0;
        wIndex.array()[vertId+2] = 0;
        wIndex.array()[vertId+3] = 0;
    }

    protected void assignVertexWeights(FloatBuffer wIndex,int vertex, float weight0, float weight1, float weight2, float weight3){
        int vertId = vertex * 4;
        wIndex.array()[vertId+0] = weight0;
        wIndex.array()[vertId+1] = weight1;
        wIndex.array()[vertId+2] = weight2;
        wIndex.array()[vertId+3] = weight3;
    }

    public void initPrototype(){

        // Init dimension data
        leg    = new Vector3f(1,3,1);
        pelvis = new Vector3f(2,1,1);
        torso  = new Vector3f(2,1.5f,1);
        chest  = new Vector3f(2,1.5f,1);
        uArm   = new Vector3f(1,3,1);
        fArm   = new Vector3f(1,3,1);
        neck   = new Vector3f(1,1,1);
        face   = new Vector3f(1,3,1);

        // Get the mesh data ready
        //initMeshData();
        initProtoHumanoidData();
        mesh = new Mesh();
        mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(protoMesh.vertices));
        mesh.setBuffer(VertexBuffer.Type.Index, 1, BufferUtils.createIntBuffer(protoMesh.index));
        mesh.setBuffer(VertexBuffer.Type.Color, 4, BufferUtils.createFloatBuffer(protoMesh.color));
        mesh.updateBound();

        initAnimData();




    }


}
