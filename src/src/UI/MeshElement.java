/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

/**
 *  This class encapsulates raw (static) mesh data.
 * @author michael
 */
public class MeshElement{
    final static public int MeshBufferVertex = 0;
    final static public int MeshBufferNormal = 1;
    final static public int MeshBufferTexture = 2;
    final static public int MeshBufferColor = 3;
    final static public int MeshBufferIndex = 4;
    
    public Vector3f[] vertices;
    public Vector3f[] normals;
    public Vector2f[] textureCoords;
    public float[] color;
    public int[] index;

    public void initBuffer(int bufferType, int bufferSize){
        switch(bufferType){
            case MeshBufferVertex:
                vertices = new Vector3f[bufferSize * 3];
                break;
            case MeshBufferNormal:
                normals = new Vector3f[bufferSize * 3];
                break;
            case MeshBufferTexture:
                textureCoords = new Vector2f[bufferSize * 2];
                break;
            case MeshBufferColor:
                color = new float[bufferSize * 4];
                break;
            case MeshBufferIndex:
                index = new int[bufferSize * 3];
                break;
        }
    }

    public void readData(){

    }

    public void writeData(){

    }
}
