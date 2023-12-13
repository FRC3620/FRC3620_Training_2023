// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Add your docs here. */
public class RunPropellorForeverCommand {
    double savedPower;

    public RunPropellorForeverCommand(double power) {
        savedPower = power;
//        addRequirements(RobotContainer.propellorSubsystem);
        
    }

    public void execute() {
        RobotContainer.propellorSubsystem.spinProp(savedPower);
    }

  public void end (boolean interrupted){
      RobotContainer.propellorSubsystem.spinProp(0);
  }
}
