package com.lightningrobotics.robowar;

public class FullFeaturedRobot extends BaseRobot {
    TankDrive drive;
    LaserWeapon laser;
    Ultrasonic forwardUltrasonic;
    LIDAR forwardLIDAR;
    Gyroscope gyro;
    BumpSensor frontBump;
    LocationSensor location;

    public FullFeaturedRobot(RoboWar game) {
        super(game);

        joinBlueAlliance();
        setTexture(Assets.blueRobot, 0.5f, 1);

        drive = new TankDrive(6);
        addFeature(drive);

        frontBump = new BumpSensor(Direction.FRONT);
        addFeature(frontBump);

        forwardUltrasonic = new Ultrasonic(Direction.FRONT);
        addFeature(forwardUltrasonic);

        forwardLIDAR = new LIDAR(LIDAR.Direction.FRONT);
        addFeature(forwardLIDAR);

        laser = new LaserWeapon(LaserWeapon.Direction.FRONT);
        addFeature(laser);

        gyro = new Gyroscope();
        addFeature(gyro);

        location = new LocationSensor();
        addFeature(location);
    }

    public TankDrive getDrive() {
        return drive;
    }

    public LaserWeapon getLaser() {
        return laser;
    }

    public Ultrasonic getForwardUltrasonic() {
        return forwardUltrasonic;
    }

    public LIDAR getForwardLIDAR() {
        return forwardLIDAR;
    }

    public Gyroscope getGyro() {
        return gyro;
    }

    public BumpSensor getFrontBump() {
        return frontBump;
    }

    public LocationSensor getLocation() {
        return location;
    }

}
