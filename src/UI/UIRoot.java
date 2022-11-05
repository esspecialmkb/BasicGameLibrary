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
        
        // Record the last highlight element
        UIElement lastHover;
        UIElement lastClick;
        
        // Click state
        boolean isHover, isHoverNotify, isClick, isClickHold, isClickRelease;
        int lastMouseX, lastMouseY;
        
        // Iterate through child nodes and assign mats
        public void validate(){
            this.assignMaterial(mat);
            this.assignUIHub(ui);
        }
        
        // Iterate through child nodes
        /*
        Hub topic ids
        1 - mouse interaction
        */
        @Override
        public boolean collideWith(int x, int y){
            // Cache the result
            boolean result = false;
            
            // Check previous element
            if(isHover){
                
                // Check lastHover for children...
                if(lastHover.children != null){
                    if(lastHover.children.size() > 0){
                        for(UIElement hoverChild :lastHover.children){
                            result = hoverChild.collideWith(x, y);
                            
                            if(result){
                                //System.out.println(this.name + " Collide With " + hoverChild.name);
                                ui.post(1, hoverChild.id, hoverChild.name + "::MOUSEOVER");
                                return true;
                            }
                        }
                        //Continue on if no collision found
                    }
                }
                // Check lastHover for collision
                result = lastHover.collideWith(x, y);
                
                if(result){
                    if(isHoverNotify == false){
                        //System.out.println(this.name + " Hover With " + lastHover.name);
                        ui.post(1, lastHover.id, lastHover.name + "::HOVER");
                        isHoverNotify = true;
                    }
                    return true;
                }else{
                    isHover = false;
                    isHoverNotify = false;
                }
            }
            // Iterate through children
            for(UIElement child :children){
                
                result = child.collideWith(x, y);
                
                if(result){
                    // Set the last element and return true
                    lastHover = child;
                    isHover = true;
                    lastMouseX = x;
                    lastMouseY = y;
                    //System.out.println(this.name + " Collide With " + lastHover.name);
                    ui.post(1, lastHover.id, lastHover.name + "::MOUSEOVER");
                    return true;
                }
            }
            
            // Return false if no element found
            lastHover = null;
            isHover = false;
            return false;
        }
        public void uiTick(){
            this.ui.onUpdate();
        }
    }
