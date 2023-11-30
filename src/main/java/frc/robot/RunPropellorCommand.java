// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class RunPropellorCommand extends CommandBase {
  double savedPower;

  /** Creates a new RunPropellorCommand. */
  public RunPropellorCommand(double power) {
    savedPower = power;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.propellorSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.propellorSubsystem.spinPropellor(savedPower);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.propellorSubsystem.spinPropellor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}