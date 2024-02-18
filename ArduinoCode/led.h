#ifndef LED_H
#define LED_H

class Led {
  private:
    int pin;
    bool state;

  public:
    Led(int pin);
    void turnOn();
    void turnOff();
    void setState(bool newState);
    bool getState();
};

#endif
