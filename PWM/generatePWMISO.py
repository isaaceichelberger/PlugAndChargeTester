import RPi.GPIO as GPIO
import time
import sys
 
pwmPin = 33
MAX_COUNT = 50
 
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
    count = 0
    while True:
        pwm.ChangeDutyCycle(dc)
        time.sleep(0.01)
        count += 1
        if (count == MAX_COUNT):
            print("[INFO] Starting ISO15118, changing Duty Cycle to 5%")
            dc = 5

         
def destroy():
    pwm.stop()
    GPIO.output(pwmPin, GPIO.LOW)
    GPIO.cleanup()
     
if  __name__ == '__main__':
    setup()
    try:
        loop()
    except KeyboardInterrupt:
        destroy()