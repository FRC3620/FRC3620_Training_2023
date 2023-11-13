package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Set;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.usfirst.frc3620.logger.EventLogging;
import org.usfirst.frc3620.logger.LogCommand;
import org.usfirst.frc3620.logger.EventLogging.Level;
import org.usfirst.frc3620.misc.CANDeviceFinder;

import edu.wpi.first.wpilibj2.command.Command;

import org.usfirst.frc3620.misc.DPad;
import org.usfirst.frc3620.misc.RobotParametersContainer;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  public final static Logger logger = EventLogging.getLogger(RobotContainer.class, Level.INFO);
  
  // need this
  public static CANDeviceFinder canDeviceFinder;
  public static RobotParameters robotParameters;

  // hardware here...

  // subsystems here

  // joysticks here....
  public static Joystick driverJoystick;
  public static Joystick operatorJoystick;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    canDeviceFinder = new CANDeviceFinder();

    robotParameters = RobotParametersContainer.getRobotParameters(RobotParameters.class);
    logger.info ("got parameters for chassis '{}'", robotParameters.getName());

    makeSubsystems();

    // CAN bus ok?
    Set<CANDeviceFinder.NamedCANDevice> missingDevices = canDeviceFinder.getMissingDeviceSet();
    if (missingDevices.size() > 0) {
      SmartDashboard.putBoolean("can.ok", false);
      SmartDashboard.putString("can.missing", missingDevices.toString());
    } else {
      SmartDashboard.putBoolean("can.ok", true);
      SmartDashboard.putString("can.missing", "");
    }

    // Configure the button bindings
    configureButtonBindings();

    setupSmartDashboardCommands();

    setupAutonomousCommands();
  }

  private void makeSubsystems() {
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

    DPad operatorDPad = new DPad(operatorJoystick, 0);
  }

  private void setupSmartDashboardCommands() {
    // DriveSubsystem
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

  /**
   * Determine if we should make software objects, even if the device does 
   * not appear on the CAN bus.
   *
   * We should if it's connected to an FMS.
   *
   * We should if it is missing a grounding jumper on DigitalInput 0.
   *
   * We should if the robot_parameters.json says so for this MAC address.
   * 
   * @return true if we should make all software objects for CAN devices
   */
  public static boolean shouldMakeAllCANDevices() {
    if (DriverStation.isFMSAttached()) {
      return true;
    }

    if (!Robot.isReal()) {
      return false;
    }

    if (robotParameters.shouldMakeAllCANDevices()) {
      return true;
    }

    return false;
  }
}