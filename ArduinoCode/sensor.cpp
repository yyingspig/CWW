#include "sensor.h"
#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266HTTPClient.h>

Sensor::Sensor(int pin, int type) : dht(pin, type) {}

void Sensor::readData() {
  humidity = dht.readHumidity();
  temperature = dht.readTemperature();
}

void Sensor::update() {
  readData();
  sendData("http://your.ruoyi-vue-backend.com/sensor/data");
}

float Sensor::getHumidity() {
  return humidity;
}

float Sensor::getTemperature() {
  return temperature;
}

void Sensor::sendData(String url) {
  HTTPClient http;
  http.begin(url);
  http.addHeader("Content-Type", "application/json");

  String jsonPayload = "{\"humidity\":" + String(humidity) + ",\"temperature\":" + String(temperature) + "}";

  int httpResponseCode = http.POST(jsonPayload);
  if (httpResponseCode > 0) {
    Serial.print("HTTP Response code: ");
    Serial.println(httpResponseCode);
  } else {
    Serial.println("Error sending sensor data");
  }

  http.end();
}
