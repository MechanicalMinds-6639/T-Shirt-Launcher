package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
// import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;


public class DriveSubsystem extends SubsystemBase {

//MOST CODE TAKEN FROM 2023 CHARGED UP SEASON CODE

/** Creates a new DriveSubsystem. */

  private final WPI_VictorSPX left1;
  private final WPI_VictorSPX left2;
  private final WPI_VictorSPX left3;
  private final WPI_VictorSPX right1;
  private final WPI_VictorSPX right2;
  private final WPI_VictorSPX right3;

  private final double correction = 0.0;

  private final DifferentialDrive mDrive;

  //private final CANcoder fleft;
  //private final CANcoder fright;

  public DriveSubsystem() {
    left1 = new WPI_VictorSPX(Constants.DriveMotors.LEFT1);
    left2 = new WPI_VictorSPX(Constants.DriveMotors.LEFT2);
    left3 = new WPI_VictorSPX(Constants.DriveMotors.LEFT3);
    right1 = new WPI_VictorSPX(Constants.DriveMotors.RIGHT1);
    right2 = new WPI_VictorSPX(Constants.DriveMotors.RIGHT2);
    right3 = new WPI_VictorSPX(Constants.DriveMotors.RIGHT3);

    left1.configFactoryDefault();
    left2.configFactoryDefault();
    left3.configFactoryDefault();
    right1.configFactoryDefault();
    right2.configFactoryDefault();
    right3.configFactoryDefault();

    left2.setInverted(true);

    right1.setInverted(true);
    right3.setInverted(true);

    MotorControllerGroup left = new MotorControllerGroup(left1, left2, left3);
    MotorControllerGroup right = new MotorControllerGroup(right1, right2, right3);

    mDrive = new DifferentialDrive(left, right);

  }

  public void drive(double forward, double clockwise){
    // Correction factor calculation
    double x = forward * correction;
    clockwise = clockwise + x;
    if (clockwise > 1.0) {
      clockwise = 1.0;
    } else if (clockwise < -1.0) {
      clockwise = -1.0;
    }
    mDrive.arcadeDrive(forward, clockwise); 
  }
 
  public Command driveCommand(CommandXboxController driveController) {
    return run(() -> {
      //If turning is inverted, make driveController.getLeftX() neagative -> -driveController.getLeftX()
      mDrive.arcadeDrive(driveController.getLeftY(), driveController.getLeftX());
    });
  }

  public void periodic() {
    // This method will be called once per scheduler run
  }


}
