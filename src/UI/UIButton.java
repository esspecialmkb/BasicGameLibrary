/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import static UI.MeshElement.MeshBufferColor;
import static UI.MeshElement.MeshBufferIndex;
import static UI.MeshElement.MeshBufferVertex;
import com.jme3.math.Vector3f;

/**
 *
 * @author michael
 */
public class UIButton extends UIElement{
    // Clickable button
    MeshElement mesh;

    @Override
    protected void buildMesh(MeshElement nMesh){

        if(nMesh != null){
            mesh = nMesh;
            super.buildMesh(mesh);
            return;
        }

        // Build a rectangle with local coordinates

        // Create the MeshElement with the proper buffer size
        mesh = new MeshElement();
        mesh.initBuffer(MeshBufferVertex, 4);
        mesh.initBuffer(MeshBufferIndex, 4);
        mesh.initBuffer(MeshBufferColor, 4);

        // Create Vertices - Top/Left Origin
        mesh.vertices[0] = new Vector3f(0           ,0              ,this.z);
        mesh.vertices[1] = new Vector3f(this.width  ,0              ,this.z);
        mesh.vertices[2] = new Vector3f(this.width  ,this.height    ,this.z);
        mesh.vertices[3] = new Vector3f(0           ,this.height    ,this.z);

        // Create Triangle Indices - Counterclockwise rotation
        mesh.index[0] = 0;
        mesh.index[1] = 1;
        mesh.index[2] = 2;
        mesh.index[3] = 2;
        mesh.index[4] = 3;
        mesh.index[5] = 0;

        // Create Colors
        mesh.color[0] = 0;
        mesh.color[1] = 1;
        mesh.color[2] = 0;
        mesh.color[3] = 1;

        mesh.color[4] = 0;
        mesh.color[5] = 1;
        mesh.color[6] = 0;
        mesh.color[7] = 1;

        mesh.color[8] = 0;
        mesh.color[9] = 1;
        mesh.color[10] = 0;
        mesh.color[11] = 1;

        mesh.color[12] = 0;
        mesh.color[13] = 1;
        mesh.color[14] = 0;
        mesh.color[15] = 1;

        // Let the base class take care of assembling the mesh buffers
        super.buildMesh(mesh);
    }
}
