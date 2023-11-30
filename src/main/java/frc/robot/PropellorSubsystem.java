// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PropellorSubsystem extends SubsystemBase {
  Spark propellorMotor;

  /** Creates a new PropellorSubsystem. */
  public PropellorSubsystem() {
    propellorMotor = new Spark(1);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  
  public void spinPropellor(double power){
    propellorMotor.set(power);
    SmartDashboard.putNumber("propellor.power", power);
  }
}
