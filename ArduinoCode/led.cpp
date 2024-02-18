#include "led.h"
#include <Arduino.h>

Led::Led(int pin) : pin(pin), state(false) {
  pinMode(pin, OUTPUT);
  digitalWrite(pin, LOW);
}

void Led::turnOn() {
  digitalWrite(pin, HIGH);
  state = true;
}

void Led::turnOff() {
  digitalWrite(pin, LOW);
  state = false;
}

void Led::setState(bool newState) {
  state = newState;
  digitalWrite(pin, state ? HIGH : LOW);
}

bool Led::getState() {
  return state;
}
