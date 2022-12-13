/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapDev;


import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.control.BillboardControl;
import com.jme3.texture.Texture;
import com.jme3.util.BufferUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
// This import allows usage of the StringCharConst class
import static Utility.StringCharConst.*;
/**
 *
 * @author TigerSage
 */
public class MapConversionTest  extends SimpleApplication {
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MapConversionTest app = new MapConversionTest();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Package_Tag mesh = readXFile();
        
        Mesh jme_Mesh = new Mesh();
        
        // Set mesh buffers
        jme_Mesh.setBuffer(VertexBuffer.Type.Position,3, BufferUtils.createFloatBuffer(mesh.mesh.vertIndex));
        jme_Mesh.setBuffer(VertexBuffer.Type.TexCoord,2, BufferUtils.createFloatBuffer(mesh.tCoords.textureCoords));
        jme_Mesh.setBuffer(VertexBuffer.Type.Index,1, BufferUtils.createIntBuffer(mesh.mesh.faceIndex));
        
        jme_Mesh.updateBound();
        
        Geometry geom = new Geometry("Package_Tag mesh", jme_Mesh);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture matTex = assetManager.loadTexture("Textures/DevTexture_LightGrey.png"); 
        matTex.setWrap(Texture.WrapMode.Repeat);
        mat.setTexture("ColorMap", matTex);
        //mat.getAdditionalRenderState().setWireframe(true);
        //mat.setColor("Color", ColorRGBA.Blue);
        //mat.setTextureParam(textRed, VarType.Int, matTex);
        geom.setMaterial(mat);

        rootNode.attachChild(geom); 
        flyCam.setMoveSpeed(40);
        viewPort.setBackgroundColor(ColorRGBA.DarkGray);
        textBlurb(); 
    }
    
    // Read a .x file and return a package object with it's assets
    protected Package_Tag readXFile(){
        // The file we are reading
        //File file= new File("C:\\Users\\TigerSage\\Documents\\JavaProjects\\BasicGamePlayGround\\assets\\Models\\Maplet\\ConversionMap\\conversionTestMap_1_1.x");
        //File file= new File("C:\\Users\\TigerSage\\Documents\\JavaProjects\\BasicGamePlayGround\\assets\\Models\\Maplet\\ConversionMap\\conversionTestMap.x");
        File file= new File("C:\\Users\\TigerSage\\Documents\\JavaProjects\\BasicGamePlayGround\\assets\\Models\\Maplet\\ConversionMap\\conversionTestMap2.x");
        
        // file= new File("C:\\Users\\TigerSage\\Documents\\JavaProjects\\BasicGamePlayGround\\assets\\Models\\Maplet\\TestMap\\test.x");
        
        Package_Tag pack = new Package_Tag();
        pack.materials = new Material_Tag[2];
        pack.mesh = new Mesh_Tag();
        pack.tCoords = new MeshTextureCoords_Tag();
        
        pack.fileName = "C:\\Users\\TigerSage\\Documents\\JavaProjects\\BasicGamePlayGround\\assets\\Models\\Maplet\\ConversionMap\\conversionTestMap.x";
        
        //Material_Tag[] materials = new Material_Tag[2];
        //Mesh_Tag mesh = new Mesh_Tag();
        //MeshTextureCoords_Tag tCoords = new MeshTextureCoords_Tag();
        
        try {
            // This scanner reads the file for us
            Scanner sc = new Scanner(file);
            
            // Keep track of how many lines read
            int lineCounter = 0;
            int splitCounter = 0;
            
            while(sc.hasNextLine()){
                
                // Read the line
                String line = sc.nextLine();
                lineCounter++;
                
                // Split into empty character terminals
                String[] split = line.split(" ");
                splitCounter = split.length;
                
                // Print the line
                //System.out.println(lineCounter +": "+ line);
                
                // Parse the line
                
                // XOF tag - VALIDATES .X FILE FORMAT
                if("xof".equals(split[0])){
                    System.out.println(textGreen + "Found XOF tag..." + textReset);
                    
                    // split[1] = "0302txt" / version number MMmm / format - txt
                    // split[2] = "0064"
                    pack.version = split[1];
                    System.out.println(textBlue + textBackWhite + "pack.version" + textReset + " = " +split[1]);
                    
                }
                
                // COMMENT tag
                if("#".equals(split[0])){
                    System.out.println(textGreen + "Found Comment tag..." + textReset);
                }
                
                // MATERIAL tag
                if("Material".equals(split[0])){
                    System.out.println(textGreen + "Found Material tag..." + textReset);
                    
                    // Read the line for color
                    line = sc.nextLine();
                    lineCounter++;
                    System.out.println(textBlue + textBackWhite + "material.color" + textReset + " = " + line);
                    
                    //      Get the material color data
                    split = line.split(";");
                    
                    
                    // Read the line for power
                    line = sc.nextLine();
                    lineCounter++;
                    System.out.println(textBlue + textBackWhite + "material.power" + textReset + " = " + line);
                    
                    
                    // Read the line for specular color
                    line = sc.nextLine();
                    lineCounter++;
                    System.out.println(textBlue + textBackWhite + "material.specular" + textReset + " = " + line);
                    
                    // Read the line for emissive color
                    line = sc.nextLine();
                    lineCounter++;
                    System.out.println(textBlue + textBackWhite + "material.emissive" + textReset + " = " + line);
                }
                
                // MESH tag
                if("Mesh{".equals(split[0])){
                    System.out.println(textGreen + "Found Mesh tag..." + textReset);
                    
                    // The next line contains the number of vertices
                    line = sc.nextLine();
                    lineCounter++;
                    System.out.println(textBlue + textBackWhite + "mesh.numVerts" + textReset + " = " + line);
                    split = line.split(";");
                    
                    pack.mesh.numVerts = Integer.valueOf(split[0]);
                    pack.mesh.vertIndex = new float[pack.mesh.numVerts * 3];
                    
                    // Loop through all of the vertices as we read them
                    for(int nVerts = 0 ; nVerts<pack.mesh.numVerts ; nVerts++){
                        line = sc.nextLine();
                        lineCounter++;
                        //System.out.println(textBlue + textBackWhite + "mesh.vertex." + nVerts + textReset + " = " + line);
                        split = line.split(";");
                        
                        pack.mesh.vertIndex[(nVerts * 3) + 0] = Float.valueOf(split[0]);
                        pack.mesh.vertIndex[(nVerts * 3) + 1] = Float.valueOf(split[1]);
                        pack.mesh.vertIndex[(nVerts * 3) + 2] = Float.valueOf(split[2]);
                        
                        System.out.println(textBlue + textBackWhite + "mesh.vertex." + nVerts + textReset + " = " + split[0] + ", "+ split[1] + ", "+ split[2]);
                    }
                    
                    // After the verts, there is a list of triangles, preceded by 
                    //the number of indices
                    line = sc.nextLine();
                    lineCounter++;
                    
                    if(line.isEmpty()){
                        line = sc.nextLine();
                        lineCounter++;
                        
                        line = sc.nextLine();
                        lineCounter++;
                    }
                    
                    split = line.split(";");
                    pack.mesh.numFaces = Integer.valueOf(split[0]);
                    System.out.println(textBlue + textBackWhite + "mesh.numFaces" + textReset + " = " + split[0]);
                    
                    // Cache for the face data
                    ArrayList<String> faceDataCache = new ArrayList<String>();
                    
                    // Loop through all of the faces as we read them
                    for( int nFaces = 0 ; nFaces < pack.mesh.numFaces; nFaces++){
                        line = sc.nextLine();
                        lineCounter++;
                        
                        split = line.split(";");
                        // split[0] = number of verts in face
                        int faceNum = Integer.valueOf(split[0]);                        
                        // FEED THE LINE INTO A FACE PARSER
                        switch(faceNum){
                            case 3:
                                System.out.println(textBlue + textBackWhite + "mesh.triangle" + textReset + " = " + split[1]);
                                
                                // Triangles can be added to the cache already
                                faceDataCache.add(split[1]);
                                break;
                            case 4:
                                System.out.println(textBlue + textBackWhite + "mesh.rectangle" + textReset + " = " + split[1]);
                                // Quads need to be split into two triangles
                                String[] faceRSplit = split[1].split(",");
                                faceDataCache.add(faceRSplit[0]+","+faceRSplit[1]+","+faceRSplit[2]);
                                faceDataCache.add(faceRSplit[2]+","+faceRSplit[3]+","+faceRSplit[0]);
                                break;
                            default:
                                System.out.println(textRed + textBackWhite + "Using parseTriangleStrip" + textReset);
                                System.out.println(textBlue + textBackWhite + "mesh.face" + textReset + " = " + split[0] + ":" + split[1]);
                                parseTriangleStrip(faceNum,split[1],faceDataCache);
                        }
                        // split[1] = face array
                    }
                    
                    // Construct face data from faceDataCache
                    pack.mesh.numFaces = faceDataCache.size();
                    pack.mesh.faceIndex = new int[pack.mesh.numFaces * 3];
                    
                    for( int nFaceData = 0; nFaceData < pack.mesh.numFaces; nFaceData++){
                        String[] faceSplit = faceDataCache.get(nFaceData).split(",");
                        pack.mesh.faceIndex[(nFaceData * 3)+0] = Integer.valueOf(faceSplit[0]);
                        pack.mesh.faceIndex[(nFaceData * 3)+1] = Integer.valueOf(faceSplit[1]);
                        pack.mesh.faceIndex[(nFaceData * 3)+2] = Integer.valueOf(faceSplit[2]);
                    }
                    // An embeded MeshMaterialList tag is next
                    
                    // Mesh normal tag, followed by number of vertices
                    // List of indices, matches the index from vertex list
                    
                    
                }
                
                // MESH MATERIAL LIST tag
                if("MeshMaterialList{".equals(split[0])){
                    System.out.println(textGreen + "Found Material List tag..." + textReset);
                }
                
                // MESH NORMALS tag
                if("MeshNormals{".equals(split[0])){
                    System.out.println(textGreen + "Found Normals tag..." + textReset);
                }
                
                // TEXTURE COORDS tag
                if("MeshTextureCoords{".equals(split[0])){
                    System.out.println(textGreen + "Found MeshTextureCoords tag..." + textReset);
                    // Get number of coords
                    line = sc.nextLine();
                    lineCounter++;
                    
                    split = line.split(";");
                    pack.tCoords.numCoords = Integer.valueOf(split[0]);
                    pack.tCoords.textureCoords = new float[pack.tCoords.numCoords*2];
                    System.out.println(textBlue + textBackWhite + "tCoords.numCoords" + textReset + " = " + split[0]);
                    
                    // Loop through the texture coords
                    for( int nCoords = 0; nCoords < pack.tCoords.numCoords; nCoords++){
                        line = sc.nextLine();
                        lineCounter++;
                        System.out.println(textBlue + textBackWhite + "tCoords.textureCoords" + textReset + " = " + line);
                        
                        split = line.split(";");
                        pack.tCoords.textureCoords[(nCoords*2)+0] = Float.valueOf(split[0]);
                        pack.tCoords.textureCoords[(nCoords*2)+1] = Float.valueOf(split[1]);
                    }
                }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MapConversionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pack;
    }
    
    // Build a triangle-only sub-mesh from a triangle strip style index (For jME meshing)
    protected ArrayList<String> parseTriangleStrip(int numFaces,String data,ArrayList<String> dataCache){
        String[] faceData = data.split(",");
        
        if(faceData.length < 3){
            return null;
        }
        
        int numRealFaces = faceData.length - 2;
        
        // Initial triangle
        //System.out.println(textGreen + "Adding triangle..." + faceData[0]+","+faceData[1]+","+faceData[2] + textReset);
        //dataCache.add(faceData[0]+","+faceData[1]+","+faceData[2]); // n , n + 1, n + 2
        
        for(int rFace = 0; rFace < numRealFaces;rFace++){
            int loop = rFace % 2;
            if(loop == 0){
                System.out.println(textGreen + "Adding triangle "+ rFace + "... " + faceData[rFace+2]+","+faceData[rFace+1]+","+faceData[rFace+0] + textReset);
                dataCache.add(faceData[rFace+2]+","+faceData[rFace+1]+","+faceData[rFace+0]); // n , n + 1, n + 2
            }if(loop == 1){
                System.out.println(textGreen + "Adding triangle "+ rFace + "... " + faceData[rFace+1]+","+faceData[rFace+2]+","+faceData[rFace+0] + textReset);
                dataCache.add(faceData[rFace+1]+","+faceData[rFace+2]+","+faceData[rFace+0]); // n , n + 2, n - 1
            }
            
            
        }
        return dataCache;
    }
    
    protected void textBlurb(){
        BitmapText hudText = new BitmapText(guiFont, false);
        hudText.setSize(0.25f);      // font size
        hudText.setColor(ColorRGBA.Blue);                             // font color
        hudText.setText("You can write any string here");             // the text
        hudText.setLocalTranslation(-2, 0, 0); // position
        
        Node bbNode = new Node();
        bbNode.attachChild(hudText);
        rootNode.attachChild(bbNode);
        
        BillboardControl bControl = new BillboardControl();
        bControl.setAlignment(BillboardControl.Alignment.Camera);
        bbNode.addControl(bControl);
        //bControl.
    }
    
    
    @Override
    public void simpleUpdate(float tpf){
        
    }
    
    // We will need data structures for the x file format
    //https://docs.microsoft.com/en-us/windows/win32/direct3d9/dx9-graphics-reference-x-file-format-templates
    
    public class XFILE_TAG{
        // Base class for all tag 'templates'
    }
    
    public class Package_Tag extends XFILE_TAG{
        String version;
        String fileName;
        
        Package_Tag pack;
        Material_Tag[] materials;
        Mesh_Tag mesh;
        MeshTextureCoords_Tag tCoords;
    }
    
    public class Material_Tag extends XFILE_TAG{
        float[] faceColor;
        float power;
        float[] specColor;
        float[] emisColor;
        TextureFilename_Tag texture;
    }
    
    public class MeshMaterialList_Tag extends XFILE_TAG{
        int numMaterials;
        int numFaces;
        int[] faceMatIndices;
        Material_Tag[] matIndex;
    }
    
    public class TextureFilename_Tag extends XFILE_TAG{
        String fileName;
    }
    
    public class MeshTextureCoords_Tag extends XFILE_TAG{
        int numCoords;
        float[] textureCoords;
    }
    
    public class MeshNormals_Tag extends XFILE_TAG{
        int numNormals;
        float[] normals;
        int numFaces;
        int[] faceArray;
    }
    
    public class Mesh_Tag extends XFILE_TAG{
        int numVerts;
        float[] vertIndex;
        int numFaces;
        int[] faceIndex;
        
        XFILE_TAG[] childTags;
    }
    
    // Use this class to treat all vertices as separate objects - WILL ALLOW VISUAL DEBUGGING!!!
    public class MeshVertex_Tag extends XFILE_TAG{
        String dataTag;
    }
    
    public class MeshVertex_Buffer extends XFILE_TAG{
        MeshVertex_Tag[] meshVertss;
        int numVerts;
        float[] vertIndex;
    }
    
    // Use this class to treat all faces as separate objects - WILL ALLOW VISUAL DEBUGGING!!!
    public class MeshFace_Tag extends XFILE_TAG{
        String dataTag;
    }
    
    public class MeshFace_Buffer extends XFILE_TAG{
        MeshFace_Tag[] meshFaces;
        int numFaces;
        int[] faceIndex;
    }
}
