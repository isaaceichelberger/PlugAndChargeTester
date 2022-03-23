import RPi.GPIO as GPIO
import time
import sys
 
pwmPin = 33
 
def setup():
    global pwm
    global dc
    dc = float(sys.argv[1]) # 50 dc default
    GPIO.setmode(GPIO.BOARD)
    GPIO.setup(pwmPin, GPIO.OUT)
    GPIO.output(pwmPin, GPIO.LOW)
    pwm = GPIO.PWM(pwmPin, 1000) # Set Frequency to 1 KHz
    pwm.start(dc) # Set the starting Duty Cycle
  
def loop():
    print("[INFO] Duty Cycle: " + str(dc) + "\n")
    count = 0
    while True:
        pwm.ChangeDutyCycle(dc)
        time.sleep(0.01)
        count += 1

def end():
    print("[INFO] PWM Wave Running\n")
     
if  __name__ == '__main__':
    setup()
    loop()
    end()