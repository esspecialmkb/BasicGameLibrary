/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import com.jme3.material.Material;
import com.jme3.math.Vector2f;

/**
 *
 * @author michael
 */
public class UIRoot extends UIElement{
        // Basic virtual container
        // Executes mesh building?
        Material mat;
        static public int UI_BUTTON_WIDTH = 100;
        static public int UI_BUTTON_HEIGHT = 20;
        static public int UI_PANEL_WIDTH = 100;
        static public int UI_PANEL_HEIGHT = 100;
        static public int UI_SCROLLBAR_WIDTH = 100;
        static public int UI_SCROLLBAR_HEIGHT = 20;
        
        // Iterate through child nodes and assign mats
        public void validate(){
            this.assignMaterial(mat);
        }
        
        // Iterate through child nodes
        @Override
        public void collideWith(int x, int y){
            for(UIElement child :children){
                child.collideWith(x, y);
            }
        }
        
    }
