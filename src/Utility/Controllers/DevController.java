/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility.Controllers;

import com.jme3.input.InputManager;
import com.jme3.input.Joystick;
import com.jme3.input.JoystickAxis;
import com.jme3.input.JoystickButton;
import com.jme3.input.RawInputListener;
import com.jme3.input.event.JoyAxisEvent;
import com.jme3.input.event.JoyButtonEvent;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.input.event.TouchEvent;
import java.util.List;

// This import allows usage of the StringCharConst class
import static Utility.StringCharConst.*;

/**
 * Devlopment prototype of RawInputListener implementation.
 * 
 * @author michael
 */
public class DevController implements RawInputListener{
    Joystick[] joysticks;
    
    public DevController(InputManager inputManager){
        joysticks = inputManager.getJoysticks();
        
        for ( Joystick joystick : joysticks )
        {
            for ( JoystickButton button : joystick.getButtons() )
            {				
                if ( joystick.getJoyId() == 0 )
                    if ( button.getButtonId() == 0 )
                        button.assignButton( "TRIGGER_A" );
            }
        }
    }
    
    @Override
    public void beginInput() {
        //System.out.println("BEGIN INPUT");
    }

    @Override
    public void endInput() {
        //System.out.println("END INPUT");
    }

    @Override
    public void onJoyAxisEvent(JoyAxisEvent evt) {
        System.out.println("Joystick Axis Event");

        int joyIndex = evt.getJoyIndex();
        int axisIndex = evt.getAxisIndex();
        JoystickAxis axis = evt.getAxis();
        int axisId = axis.getAxisId();
        String logicalId = axis.getLogicalId();
        float value = evt.getValue();

        /*System.out.println("Joystick Index: "+ joyIndex);
        System.out.println("Axis Index: "+ axisIndex);
        System.out.println("Axis Logical Id: "+ logicalId);
        System.out.println("Event Value: "+ value); */

        System.out.println("Id: ( "+joyIndex +", "+ axisIndex +","+ logicalId + ") Value: "+value );

        /*
        Logical Axis Ids
        x
        y
        z
        rz
        */

        System.out.println();
    }

    @Override
    public void onJoyButtonEvent(JoyButtonEvent evt) {
        System.out.println(textRed+"Joystick Button Event"+textReset);

        JoystickButton button = evt.getButton();
        int buttonId = button.getButtonId();
        String logicalId = button.getLogicalId();
        int buttonIndex = evt.getButtonIndex();
        int joyIndex = evt.getJoyIndex();
        boolean pressed = evt.isPressed();

        /*System.out.println("Joystick Index: " + joyIndex);
        System.out.println("Button Index: " + buttonIndex);
        System.out.println("Button Id: " + buttonId);
        System.out.println("Is Pressed: " + pressed);*/

        System.out.println("Id: ( "+joyIndex +", "+ buttonIndex +","+ buttonId + ") Value: "+pressed );
    }

    @Override
    public void onMouseMotionEvent(MouseMotionEvent evt) {
        System.out.println(textRed+"Mouse Motion Event"+textReset);

        int dx = evt.getDX();
        int dy = evt.getDY();
        int deltaWheel = evt.getDeltaWheel();

        int x = evt.getX();
        int y = evt.getY();
        int wheel = evt.getWheel();


    }

    @Override
    public void onMouseButtonEvent(MouseButtonEvent evt) {
        System.out.println(textRed+"Mouse Button Event"+textReset);

        int buttonIndex = evt.getButtonIndex();
        int x = evt.getX();
        int y = evt.getY();
        boolean pressed = evt.isPressed();
        boolean released = evt.isReleased();
    }

    @Override
    public void onKeyEvent(KeyInputEvent evt) {
        System.out.println(textRed+"Key Input Event"+textReset);

        int keyCode = evt.getKeyCode();
        boolean pressed = evt.isPressed();
        boolean released = evt.isReleased();
        boolean repeating = evt.isRepeating();
    }

    @Override
    public void onTouchEvent(TouchEvent evt) {
        System.out.println(textRed+"Touch Event"+textReset);

        // TO BE IMPLEMENTED
    }
        
}
    
