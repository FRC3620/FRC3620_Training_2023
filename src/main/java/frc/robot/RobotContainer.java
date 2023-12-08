package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.LogCommand;
import org.usfirst.frc3620.logger.EventLogging.Level;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  public final static Logger logger = EventLogging.getLogger(RobotContainer.class, Level.INFO);
  
  // hardware here...

  // subsystems here
  public static PropellorSubsystem propellorSubsystem;


  // joysticks here....
  public static Joystick driverJoystick;
  public static Joystick operatorJoystick;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    makeSubsystems();

    // Configure the button bindings
    configureButtonBindings();

    setupSmartDashboardCommands();

    setupAutonomousCommands();
  }

  private void makeSubsystems() {
    propellorSubsystem= new PropellorSubsystem();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    driverJoystick = new Joystick(0);
    operatorJoystick = new Joystick(1);
    Trigger button1 = new JoystickButton(driverJoystick, 1);
    Trigger button2 = new JoystickButton(driverJoystick,2 );
    button1.whileTrue(new RunPropellorComand(-0.2));
    button2.whileTrue(new RunPropellorComand( -0.3 ));
    // DPad operatorDPad = new DPad(operatorJoystick, 0);
  }
  public static double readSpinJoystick(){
    return driverJoystick.getRawAxis (0); 
  }

  private void setupSmartDashboardCommands() {
    // DriveSubsystem
    SmartDashboard.putData (new RunPropellorComand (0.4));
    SmartDashboard.putData (new ForwardAndBackCommand());
    SmartDashboard.putData (new ForwardAndBackCommand());
    SmartDashboard.putData (new RunPropellorFromJoystickComand());
  }

  SendableChooser<CommandFactory> chooser = new SendableChooser<>();
  public void setupAutonomousCommands() {
    SmartDashboard.putData("Auto mode", chooser);
    chooser.setDefaultOption("Do nothing", () -> new LogCommand("no autonomous specified, did nothing"));
  }

  interface CommandFactory extends Supplier<Command> { }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    CommandFactory factory = chooser.getSelected();
    Command command = factory.get();
    logger.info ("Command Factory gave us a {}", command);
    return command;
  }
}
