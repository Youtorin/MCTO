package com.yangdonglin.mcto.utils;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;

/**
 * 工具类
 */
public class MathUtils {

    public static void main(String[] args) {
        System.out.println("经纬度距离计算结果：" + getDistance(109.371319, 22.155406, 108.009758, 21.679011) + "米");
    }


    public static double getDistance(double longitudeFrom, double latitudeFrom, double longitudeTo, double latitudeTo) {
        GlobalCoordinates source = new GlobalCoordinates(latitudeFrom, longitudeFrom);
        GlobalCoordinates target = new GlobalCoordinates(latitudeTo, longitudeTo);

        return new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.Sphere, source, target).getEllipsoidalDistance();
    }
}
