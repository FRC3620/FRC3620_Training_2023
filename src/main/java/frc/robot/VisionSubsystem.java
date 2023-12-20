// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

import edu.wpi.first.networktables.DoubleArrayEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable.TableEventListener;
import edu.wpi.first.networktables.NetworkTableEvent.Kind;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSubsystem extends SubsystemBase {
  DoubleArrayEntry areaEntry;
  DoubleArrayEntry xEntry;
  boolean dirty;

  Contour biggest;

  Contour contourZero = new Contour();

  /** Creates a new VisionSubsystem. */
  public VisionSubsystem() {
    NetworkTable contoursTable = NetworkTableInstance.getDefault().getTable("GRIP/myContoursReport");
    contoursTable.addListener(EnumSet.of(Kind.kValueAll), new Listener());
    areaEntry = contoursTable.getDoubleArrayTopic("area").getEntry(new double[0]);
    xEntry = contoursTable.getDoubleArrayTopic("centerX").getEntry(new double[0]);
  }

  public Contour getBiggest() {
    return biggest;
  }

  @Override
  public void periodic() {
    if (dirty) {
      do_all();

      SmartDashboard.putNumber("biggest.area", biggest.area);
      SmartDashboard.putNumber("biggest.x", biggest.x);
      dirty = false;
    }
  }

  void do_all() {
    double[] areas = areaEntry.get();
    double[] xs = xEntry.get();
    int size = areas.length;
    if (size == 0) {
      biggest = contourZero;
      return;
    }
    if (xs.length != size) return;
    List<Contour> contours = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      Contour c = new Contour();
      c.setArea(areas[i]);
      c.setX(xs[i]);
      contours.add(c);
    }

    contours.sort(Comparator.comparing(Contour::getArea).reversed());
    biggest = contours.get(0);
  }

  class Listener implements TableEventListener {
    @Override
    public void accept(NetworkTable table, String key, NetworkTableEvent event) {
        dirty = true;
    }
  }

  public class Contour {
    double x, area;

    public double getX() {
      return x;
    }

    public void setX(double x) {
      this.x = x;
    }

    public double getArea() {
      return area;
    }

    public void setArea(double area) {
      this.area = area;
    }
  }
}
