#include <SimpleTimer.h>
#include "sensor.h"
#include "led.h"

const char* ssid = "YourWiFiSSID";
const char* password = "YourWiFiPassword";

Sensor sensor(D4, DHT11);
Led led(D7);

SimpleTimer timer;

void setup() {
  Serial.begin(115200);
  delay(1000);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }

  Serial.println("Connected to WiFi");

  // 每分钟发送一次数据给后端
  timer.setInterval(60000, [](){
    sensor.update();
  });
}

void loop() {
  timer.run();

  // 处理控制 LED 的请求
  WiFiClient client;
  HTTPClient http;

  if (WiFi.status() != WL_CONNECTED) {
    Serial.println("WiFi not connected");
    return;
  }

  if (http.begin(client, "http://your.ruoyi-vue-backend.com/led/control")) {
    int httpResponseCode = http.GET();
    if (httpResponseCode == HTTP_CODE_OK) {
      String response = http.getString();
      Serial.println("LED control request received: " + response);

      if (response == "on") {
        led.turnOn();
      } else if (response == "off") {
        led.turnOff();
      }
    } else {
      Serial.print("LED control request failed. Error code: ");
      Serial.println(httpResponseCode);
    }
  } else {
    Serial.println("Error connecting to LED control URL");
  }

  http.end();
}
