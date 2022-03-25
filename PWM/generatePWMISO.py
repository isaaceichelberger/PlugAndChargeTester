import RPi.GPIO as GPIO
import time
import sys
 
pwmPin = 33

def setup():
    global pwm
    global dc
    global START_TIME
    START_TIME = time.time()
    dc = float(sys.argv[1]) # 50 dc default
    GPIO.setmode(GPIO.BOARD)
    GPIO.setup(pwmPin, GPIO.OUT)
    GPIO.output(pwmPin, GPIO.LOW)
    pwm = GPIO.PWM(pwmPin, 1000) # Set Frequency to 1 KHz
    pwm.start(dc) # Set the starting Duty Cycle

  
def loop():
    while time.time() - START_TIME <= 3:
        pwm.ChangeDutyCycle(dc)
        time.sleep(0.01)
    print("[INFO] Starting ISO15118, changing Duty Cycle to 5%\n")
    while True:
        pwm.ChangeDutyCycle(5)
        time.sleep(0.01)

if  __name__ == '__main__':
    setup()
    loop()