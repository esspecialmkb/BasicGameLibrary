/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.material.Material;

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
        // It "might" be good to send mouse position updates?
        // The ui hub uses "::" as a terminator string, x and y will be the parameters of the message
        //  ui.post(1, this.id, "MOUSE_POS::"+x+"::"+y);
        // Cache the result
        boolean result = false;

        // Check previous element for hover changes within it's children
        if(isHover){

            // Check lastHover for children...
            if(lastHover.children != null){
                if(lastHover.children.size() > 0){
                    for(UIElement hoverChild :lastHover.children){
                        result = hoverChild.collideWith(x, y);

                        if(result){
                            //LET THE UIHUB KNOW THAT WE ARE HOVERING OVER A NEW OBJECT!!!
                            ui.post(2, hoverChild.id, hoverChild.name + "::MOUSEOVER");
                            lastMouseX = x;
                            lastMouseY = y;
                            return true;
                        }
                    }
                    //Continue on if no collision found
                }
            }
            // Check lastHover for collision
            result = lastHover.collideWith(x, y);

            if(result){
                // This check is only here so we dont spam the ui hub
                // We will let the ui hub track hover status
                if(isHoverNotify == false){
                    //System.out.println(this.name + " Hover With " + lastHover.name);
                    ui.post(2, lastHover.id, lastHover.name + "::HOVER");
                    isHoverNotify = true;
                }else{

                }
                lastMouseX = x;
                lastMouseY = y;
                return true;
            }else{
                // If we reach here, we are no longer hovering over an object within the lastHover object
                isHover = false;
                isHoverNotify = false;
                ui.post(2, lastHover.id, lastHover.name + "::NO_HOVER");
            }
        }//END IF(ISHOVER)


        // Use this check for general hover collision check
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
                ui.post(2, lastHover.id, lastHover.name + "::MOUSEOVER");
                return true;
            }
        }

        // Return false if no element found
        lastHover = null;
        isHover = false;
        lastMouseX = x;
        lastMouseY = y;
        return false;
    }
    public void uiTick(){
        this.ui.onUpdate();
    }

    // Event Listeners
    protected ActionListener actionListener = new ActionListener(){
        public void onAction(String name, boolean pressed, float tpf){
            //System.out.println(name + " = " + pressed);
            ui.post(3,-1,name + " " +pressed);
        }
    };
    protected AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {
            //System.out.println(name + " = " + (value/tpf));
        }
    };
}
