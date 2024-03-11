#ifndef SENSOR_H
#define SENSOR_H

#include "Arduino.h"

class Sensor {
  private:
    DHT dht;
    float humidity;
    float temperature;

    void readData();

  public:
    Sensor(int pin, int type);
    void update();
    float getHumidity();
    float getTemperature();
    void sendData(String url);
};

#endif
