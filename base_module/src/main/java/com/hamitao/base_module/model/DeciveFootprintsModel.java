package com.hamitao.base_module.model;

import java.util.List;

/**
 * Created by linjianwen on 2018/3/16.
 */

public class DeciveFootprintsModel {


    /**
     * responseDataObj : {"footprints":[{"GSMIntensity":0,"alarmcondition":"null","battery":80,"devicecondition":"null","deviceid":"1111111","direction":0,"geom":{"altitude":0,"latitude":22.5607,"longitude":113.8898},"gpslocatestatus":0,"gpssatellitetime":"2018-04-10 16:08:54.0","gpssatellitetimeUTC":"2018-04-10 08:08:54.0","gsmtime":"2018-04-10 16:08:54.0","gsmtimeUTC":"2018-04-10 08:08:54.0","gsmtowergeom":{"altitude":0,"latitude":0,"longitude":0},"gsmtowerid":"null","hitwordbyscale":"null","horizonalaccuracy":0,"mileage":0,"possourcetype":"null","precision":0,"satellitenum":0,"sensorcondition":"null","seqid":"358ed97f9a484d4ebeb0ccdfe2b8eb8720180410160854000939","speed":0,"statusword":"null","terminalid":"sz10002_kidsrobot_06adb14afb6649389f613027dab75bb3","triggeredby":"autoreport","verticalaccuracy":0,"wifigeom":{"altitude":0,"latitude":0,"longitude":0},"wifiid":"null","wifitime":"2018-04-10 16:08:54.0","wifitimeUTC":"2018-04-10 08:08:54.0"}]}
     * result : success
     */

    private ResponseDataObjBean responseDataObj;
    private String result;

    public ResponseDataObjBean getResponseDataObj() {
        return responseDataObj;
    }

    public void setResponseDataObj(ResponseDataObjBean responseDataObj) {
        this.responseDataObj = responseDataObj;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static class ResponseDataObjBean {
        private List<FootprintsBean> footprints;

        public List<FootprintsBean> getFootprints() {
            return footprints;
        }

        public void setFootprints(List<FootprintsBean> footprints) {
            this.footprints = footprints;
        }

        public static class FootprintsBean {
            /**
             * GSMIntensity : 0
             * alarmcondition : null
             * battery : 80
             * devicecondition : null
             * deviceid : 1111111
             * direction : 0
             * geom : {"altitude":0,"latitude":22.5607,"longitude":113.8898}
             * gpslocatestatus : 0
             * gpssatellitetime : 2018-04-10 16:08:54.0
             * gpssatellitetimeUTC : 2018-04-10 08:08:54.0
             * gsmtime : 2018-04-10 16:08:54.0
             * gsmtimeUTC : 2018-04-10 08:08:54.0
             * gsmtowergeom : {"altitude":0,"latitude":0,"longitude":0}
             * gsmtowerid : null
             * hitwordbyscale : null
             * horizonalaccuracy : 0
             * mileage : 0
             * possourcetype : null
             * precision : 0
             * satellitenum : 0
             * sensorcondition : null
             * seqid : 358ed97f9a484d4ebeb0ccdfe2b8eb8720180410160854000939
             * speed : 0
             * statusword : null
             * terminalid : sz10002_kidsrobot_06adb14afb6649389f613027dab75bb3
             * triggeredby : autoreport
             * verticalaccuracy : 0
             * wifigeom : {"altitude":0,"latitude":0,"longitude":0}
             * wifiid : null
             * wifitime : 2018-04-10 16:08:54.0
             * wifitimeUTC : 2018-04-10 08:08:54.0
             */

            private int GSMIntensity;
            private String alarmcondition;
            private int battery;
            private String devicecondition;
            private String deviceid;
            private int direction;
            private GeomBean geom;
            private int gpslocatestatus;
            private String gpssatellitetime;
            private String gpssatellitetimeUTC;
            private String gsmtime;
            private String gsmtimeUTC;
            private GsmtowergeomBean gsmtowergeom;
            private String gsmtowerid;
            private String hitwordbyscale;
            private int horizonalaccuracy;
            private int mileage;
            private String possourcetype;
            private int precision;
            private int satellitenum;
            private String sensorcondition;
            private String seqid;
            private int speed;
            private String statusword;
            private String terminalid;
            private String triggeredby;
            private int verticalaccuracy;
            private WifigeomBean wifigeom;
            private String wifiid;
            private String wifitime;
            private String wifitimeUTC;

            public int getGSMIntensity() {
                return GSMIntensity;
            }

            public void setGSMIntensity(int GSMIntensity) {
                this.GSMIntensity = GSMIntensity;
            }

            public String getAlarmcondition() {
                return alarmcondition;
            }

            public void setAlarmcondition(String alarmcondition) {
                this.alarmcondition = alarmcondition;
            }

            public int getBattery() {
                return battery;
            }

            public void setBattery(int battery) {
                this.battery = battery;
            }

            public String getDevicecondition() {
                return devicecondition;
            }

            public void setDevicecondition(String devicecondition) {
                this.devicecondition = devicecondition;
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

            public String getGpssatellitetime() {
                return gpssatellitetime;
            }

            public void setGpssatellitetime(String gpssatellitetime) {
                this.gpssatellitetime = gpssatellitetime;
            }

            public String getGpssatellitetimeUTC() {
                return gpssatellitetimeUTC;
            }

            public void setGpssatellitetimeUTC(String gpssatellitetimeUTC) {
                this.gpssatellitetimeUTC = gpssatellitetimeUTC;
            }

            public String getGsmtime() {
                return gsmtime;
            }

            public void setGsmtime(String gsmtime) {
                this.gsmtime = gsmtime;
            }

            public String getGsmtimeUTC() {
                return gsmtimeUTC;
            }

            public void setGsmtimeUTC(String gsmtimeUTC) {
                this.gsmtimeUTC = gsmtimeUTC;
            }

            public GsmtowergeomBean getGsmtowergeom() {
                return gsmtowergeom;
            }

            public void setGsmtowergeom(GsmtowergeomBean gsmtowergeom) {
                this.gsmtowergeom = gsmtowergeom;
            }

            public String getGsmtowerid() {
                return gsmtowerid;
            }

            public void setGsmtowerid(String gsmtowerid) {
                this.gsmtowerid = gsmtowerid;
            }

            public String getHitwordbyscale() {
                return hitwordbyscale;
            }

            public void setHitwordbyscale(String hitwordbyscale) {
                this.hitwordbyscale = hitwordbyscale;
            }

            public int getHorizonalaccuracy() {
                return horizonalaccuracy;
            }

            public void setHorizonalaccuracy(int horizonalaccuracy) {
                this.horizonalaccuracy = horizonalaccuracy;
            }

            public int getMileage() {
                return mileage;
            }

            public void setMileage(int mileage) {
                this.mileage = mileage;
            }

            public String getPossourcetype() {
                return possourcetype;
            }

            public void setPossourcetype(String possourcetype) {
                this.possourcetype = possourcetype;
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

            public String getSensorcondition() {
                return sensorcondition;
            }

            public void setSensorcondition(String sensorcondition) {
                this.sensorcondition = sensorcondition;
            }

            public String getSeqid() {
                return seqid;
            }

            public void setSeqid(String seqid) {
                this.seqid = seqid;
            }

            public int getSpeed() {
                return speed;
            }

            public void setSpeed(int speed) {
                this.speed = speed;
            }

            public String getStatusword() {
                return statusword;
            }

            public void setStatusword(String statusword) {
                this.statusword = statusword;
            }

            public String getTerminalid() {
                return terminalid;
            }

            public void setTerminalid(String terminalid) {
                this.terminalid = terminalid;
            }

            public String getTriggeredby() {
                return triggeredby;
            }

            public void setTriggeredby(String triggeredby) {
                this.triggeredby = triggeredby;
            }

            public int getVerticalaccuracy() {
                return verticalaccuracy;
            }

            public void setVerticalaccuracy(int verticalaccuracy) {
                this.verticalaccuracy = verticalaccuracy;
            }

            public WifigeomBean getWifigeom() {
                return wifigeom;
            }

            public void setWifigeom(WifigeomBean wifigeom) {
                this.wifigeom = wifigeom;
            }

            public String getWifiid() {
                return wifiid;
            }

            public void setWifiid(String wifiid) {
                this.wifiid = wifiid;
            }

            public String getWifitime() {
                return wifitime;
            }

            public void setWifitime(String wifitime) {
                this.wifitime = wifitime;
            }

            public String getWifitimeUTC() {
                return wifitimeUTC;
            }

            public void setWifitimeUTC(String wifitimeUTC) {
                this.wifitimeUTC = wifitimeUTC;
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

            public static class GsmtowergeomBean {
                /**
                 * altitude : 0
                 * latitude : 0
                 * longitude : 0
                 */

                private int altitude;
                private int latitude;
                private int longitude;

                public int getAltitude() {
                    return altitude;
                }

                public void setAltitude(int altitude) {
                    this.altitude = altitude;
                }

                public int getLatitude() {
                    return latitude;
                }

                public void setLatitude(int latitude) {
                    this.latitude = latitude;
                }

                public int getLongitude() {
                    return longitude;
                }

                public void setLongitude(int longitude) {
                    this.longitude = longitude;
                }
            }

            public static class WifigeomBean {
                /**
                 * altitude : 0
                 * latitude : 0
                 * longitude : 0
                 */

                private int altitude;
                private int latitude;
                private int longitude;

                public int getAltitude() {
                    return altitude;
                }

                public void setAltitude(int altitude) {
                    this.altitude = altitude;
                }

                public int getLatitude() {
                    return latitude;
                }

                public void setLatitude(int latitude) {
                    this.latitude = latitude;
                }

                public int getLongitude() {
                    return longitude;
                }

                public void setLongitude(int longitude) {
                    this.longitude = longitude;
                }
            }
        }
    }
}
