/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team4153;


import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotMain extends IterativeRobot {
    public static final int JOYSTICK_PORT = 1;
    public static final int JAG_MOTOR = 4;
    
    private CANJaguar rightFront;
    private Joystick joystick;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        joystick = new Joystick(JOYSTICK_PORT);
        try {
            rightFront = new CANJaguar(JAG_MOTOR, CANJaguar.ControlMode.kSpeed);
            configSpeedControl(rightFront);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
            //System.exit(-1);
        }
    }
    private void configSpeedControl(CANJaguar jag) throws CANTimeoutException {
        final int CPR = 360;
        final double ENCODER_FINAL_POS = 0;
        final double VOLTAGE_RAMP = 40;
//        jag.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
//        jag.setSpeedReference(CANJaguar.SpeedReference.kNone);
//        jag.enableControl();
//        jag.configMaxOutputVoltage(10);//ToDo: 
        // PIDs may be required.  Values here:
        //  http://www.chiefdelphi.com/forums/showthread.php?t=91384
        // and here:
        // http://www.chiefdelphi.com/forums/showthread.php?t=89721
        // neither seem correct.
//        jag.setPID(0.4, .005, 0);
        jag.setPID(0.3, 0.005, 0);
        jag.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
        jag.configEncoderCodesPerRev(CPR);
//        jag.setVoltageRampRate(VOLTAGE_RAMP);
        jag.enableControl();

//        System.out.println("Control Mode = " + jag.getControlMode());
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        try {
//            rightFront.setX(joystick.getRawAxis(1)*100);
            if(joystick.getRawButton(1)){
                rightFront.setX(60);
            }else if(joystick.getRawButton(2)){
                rightFront.setX(40);
            }
            System.err.println("Encoder: " + rightFront.getSpeed());
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
