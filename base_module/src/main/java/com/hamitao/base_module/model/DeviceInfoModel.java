package com.hamitao.base_module.model;

/**
 * Created by linjianwen on 2018/4/13.
 */

public class DeviceInfoModel {


    /**
     * GSMIntensity : 2
     * battery : 42
     * deviceState : true
     * deviceid : 1111111
     * direction : 0
     * geom : {"altitude":0,"latitude":22.5607,"longitude":113.8898}
     * gpslocatestatus : 0
     * horizonalaccuracy : 0
     * isOpenEyeProtect : true
     * isPhoneManager : true
     * isPlaying : false
     * mileage : 0
     * precision : 0
     * satellitenum : 0
     * shutdownTime :
     * speed : 0
     * verticalaccuracy : 0
     * wifiid : "HAMITAO_5G"
     */

    private int GSMIntensity;
    private int battery;
    private boolean deviceState;
    private String deviceid;
    private int direction;
    private GeomBean geom;
    private int gpslocatestatus;
    private int horizonalaccuracy;
    private boolean isOpenEyeProtect;
    private boolean isPhoneManager;
    private boolean isPlaying;
    private int mileage;
    private int precision;
    private int satellitenum;
    private String shutdownTime;
    private int speed;
    private int verticalaccuracy;
    private String wifiid;
    private int timingCloseTime;

    public int getTimingCloseTime() {
        return timingCloseTime;
    }

    public void setTimingCloseTime(int timingCloseTime) {
        this.timingCloseTime = timingCloseTime;
    }

    public int getGSMIntensity() {
        return GSMIntensity;
    }

    public void setGSMIntensity(int GSMIntensity) {
        this.GSMIntensity = GSMIntensity;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public boolean isDeviceState() {
        return deviceState;
    }

    public void setDeviceState(boolean deviceState) {
        this.deviceState = deviceState;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public GeomBean getGeom() {
        return geom;
    }

    public void setGeom(GeomBean geom) {
        this.geom = geom;
    }

    public int getGpslocatestatus() {
        return gpslocatestatus;
    }

    public void setGpslocatestatus(int gpslocatestatus) {
        this.gpslocatestatus = gpslocatestatus;
    }

    public int getHorizonalaccuracy() {
        return horizonalaccuracy;
    }

    public void setHorizonalaccuracy(int horizonalaccuracy) {
        this.horizonalaccuracy = horizonalaccuracy;
    }

    public boolean isIsOpenEyeProtect() {
        return isOpenEyeProtect;
    }

    public void setIsOpenEyeProtect(boolean isOpenEyeProtect) {
        this.isOpenEyeProtect = isOpenEyeProtect;
    }

    public boolean isIsPhoneManager() {
        return isPhoneManager;
    }

    public void setIsPhoneManager(boolean isPhoneManager) {
        this.isPhoneManager = isPhoneManager;
    }

    public boolean isIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getSatellitenum() {
        return satellitenum;
    }

    public void setSatellitenum(int satellitenum) {
        this.satellitenum = satellitenum;
    }

    public String getShutdownTime() {
        return shutdownTime;
    }

    public void setShutdownTime(String shutdownTime) {
        this.shutdownTime = shutdownTime;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getVerticalaccuracy() {
        return verticalaccuracy;
    }

    public void setVerticalaccuracy(int verticalaccuracy) {
        this.verticalaccuracy = verticalaccuracy;
    }

    public String getWifiid() {
        return wifiid;
    }

    public void setWifiid(String wifiid) {
        this.wifiid = wifiid;
    }

    public static class GeomBean {
        /**
         * altitude : 0
         * latitude : 22.5607
         * longitude : 113.8898
         */

        private int altitude;
        private double latitude;
        private double longitude;

        public int getAltitude() {
            return altitude;
        }

        public void setAltitude(int altitude) {
            this.altitude = altitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
}
