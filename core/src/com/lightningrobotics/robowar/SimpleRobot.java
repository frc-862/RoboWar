package com.lightningrobotics.robowar;

public class SimpleRobot extends BaseRobot {
    private TankDrive drive;
    private Ultrasonic frontSensor;
    private int counter = 0;

    public SimpleRobot(RoboWar game) {
        super(game);

        drive = new TankDrive(2);
        addFeature(drive);

        frontSensor = new Ultrasonic(Direction.FRONT);
        addFeature(frontSensor);

        featureComplete();
    }

    @Override
    public void update() {
        if (++counter % 60 * 3 == 0) {
            drive.setLeftAcc(RoboWar.rand.nextFloat() * 10f - 5f);
            drive.setRightAcc(RoboWar.rand.nextFloat() * 10f - 5f);
        }

        super.update();
    }
}
