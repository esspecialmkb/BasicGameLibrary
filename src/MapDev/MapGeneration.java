/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapDev;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

/**
 *
 * TODO finish TileChunk.updateMesh()
 * @author michael
 */
public class MapGeneration extends SimpleApplication{

    public class TileChunk{
        Tile[] tile;
        
        // Mesh data
        Vector3f[] verts;
        int[] tris;
        float[] colors;
        
        Mesh mesh;
        
        int x;
        int y;
        
        int chunkSize = 8;
        int tileSize = 10;
        
        public TileChunk(){
            // Create an 8x8 tile chunk
            tile = new Tile[chunkSize * chunkSize];
            initTiles();
            initMesh();
            updateMesh();
        }
        
        protected void initTiles(){
            for(int tx = 0; tx > (tile.length); tx++){
                tile[tx] = new Tile();
            }
        }
        
        protected void initMesh(){
            // New mesh data arrays
            verts = new Vector3f[ (tile.length) * 4];
            tris = new int[ (tile.length) * 6];
            colors = new float[ (tile.length) * 4];
        }
        
        public Tile getTile(int x, int y){
            // X first ordering
            return tile[x + (y * chunkSize)];
        }
        
        public Mesh getMesh(){ return mesh;}
        
        public void setTileColor(int x, int y,float r, float g, float b){
            Tile t = getTile(x,y);
        }
        
        public void updateMesh(){            
            // Iterate through the tile array to get all mesh data for this chunk
            
            // Vertex
            int vertCount = 0;
            int vX = 0;
            int vY = 0;
            for( int vL = 0; vL < tile.length;vL++){
                
                vX++;
                
                if(vX > chunkSize){
                    vY++;
                    vX = 0;
                }
                
                vertCount = vL*4;
                
                verts[vertCount]     = new Vector3f((vX*tileSize)              ,(vY*tileSize),0);
                verts[vertCount + 1] = new Vector3f((vX*tileSize)              ,(vY*tileSize) + tileSize,0);
                verts[vertCount + 2] = new Vector3f((vX*tileSize) + tileSize   ,(vY*tileSize) + tileSize,0);
                verts[vertCount + 3] = new Vector3f((vX*tileSize) + tileSize   ,(vY*tileSize),0);
                
                vertCount += 4;
            }
            
            /*
            
            int x = 0;
            int y = 0;
            
            for( ix ; ...){
                x++;
                if(x > chunkSize){
                    y++;
                    x = 0;
                }
                
                verts...
            
            }
            
            */
            
            // Triangles
            int triCount = 0;
            vertCount = 0;
            for( int tL = 0; tL < tile.length;tL++){
                triCount = tL*6;
                vertCount = tL*4;
                tris[triCount] = vertCount + 2;
                tris[triCount + 1] = vertCount + 1;
                tris[triCount + 2] = vertCount + 0;
                tris[triCount + 3] = vertCount + 0;
                tris[triCount + 4] = vertCount + 3;
                tris[triCount + 5] = vertCount + 2;
            }
            
            // Colors
            for( int cL = 0; cL < tile.length; cL++){
                colors[(cL * 4)+0] = 1;
                colors[(cL * 4)+1] = 1;
                colors[(cL * 4)+2] = 1;
                colors[(cL * 4)+3] = 1;
            }
            
            // Prototype for single tile
            //verts = new Vector3f[4];
            //tris = new int[6];
            //colors = new float[4 * 4];
            
            mesh = new Mesh();
            mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(verts));
            mesh.setBuffer(VertexBuffer.Type.Color, 4, BufferUtils.createFloatBuffer(colors));
            //mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
            mesh.setBuffer(VertexBuffer.Type.Index, 1, BufferUtils.createIntBuffer(tris));
            mesh.updateBound();
            
            // ---------------------------------------------
            /*
            colors[0] = 1;
            colors[1] = 1;
            colors[2] = 1;
            colors[3] = 1;
            
            colors[4] = 1;
            colors[5] = 1;
            colors[6] = 1;
            colors[7] = 1;
            
            colors[8] = 1;
            colors[9] = 1;
            colors[10] = 1;
            colors[11] = 1;
            
            colors[12] = 1;
            colors[13] = 1;
            colors[14] = 1;
            colors[15] = 1;*/
        }
    }
    
    public class Tile{
        float[] tileColors;
        
        public Tile(){
            tileColors = new float[16];
            
            tileColors[0] = 1;  //  r
            tileColors[1] = 1;  //  g
            tileColors[2] = 1;  //  b
            tileColors[3] = 1;  //  a
            
            tileColors[4] = 1;
            tileColors[5] = 1;
            tileColors[6] = 1;
            tileColors[7] = 1;
            
            tileColors[8] = 1;
            tileColors[9] = 1;
            tileColors[10] = 1;
            tileColors[11] = 1;
            
            tileColors[12] = 1;
            tileColors[13] = 1;
            tileColors[14] = 1;
            tileColors[15] = 1;
        }
        
        public void setColor(float r, float g, float b){
            
            tileColors[0] = r;
            tileColors[1] = g;
            tileColors[2] = b;
            
            tileColors[4] = r;
            tileColors[5] = g;
            tileColors[6] = b;
            
            tileColors[8] = r;
            tileColors[9] = g;
            tileColors[10] = b;
            
            tileColors[12] = r;
            tileColors[13] = g;
            tileColors[14] = b;
        }
        
        public void setPointColor(int index, float r, float g, float b){
            tileColors[(index * 4)] = r;
            tileColors[(index * 4) + 1] = g;
            tileColors[(index * 4) + 2] = b;
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MapGeneration app = new MapGeneration();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        TileChunk chunk = new TileChunk();
        Mesh chunkMesh = chunk.getMesh();
        
        Geometry geo = new Geometry("ChunkMesh",chunkMesh);
        Node chunkNode = new Node();
        chunkNode.attachChild(geo); 
    
        Material mat = assetManager.loadMaterial("Materials/unlitTileMat.j3m");
        geo.setMaterial(mat);
        rootNode.attachChild(chunkNode);
        
        flyCam.setMoveSpeed(30f);
    }
    
    @Override
    public void simpleUpdate(float tpf){
        
    }
    
}
