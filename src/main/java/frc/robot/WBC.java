// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.VisionSubsystem.Contour;

/** Add your docs here. */
public class WBC extends CommandBase {
    double savedPower;

    public WBC(double power) {
        savedPower = power;
//        addRequirements(RobotContainer.propellorSubsystem);
        
    }
    public void execute() {
        Contour contour = RobotContainer.visionSubsystem.getBiggest();
        if (contour.area > 1000) {
          if (contour.getX() > 160) {
            RobotContainer.propellorSubsystem.spinProp(0.2);
          } else {
            RobotContainer.propellorSubsystem.spinProp(-0.2);
          }
        } else {
          RobotContainer.propellorSubsystem.spinProp(0);
        }
      }

  public void end (boolean interrupted){
      RobotContainer.propellorSubsystem.spinProp(0);
  }

  public boolean isFinished(){
    return false;
  }
}

