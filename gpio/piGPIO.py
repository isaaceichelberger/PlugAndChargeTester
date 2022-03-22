import RPi.GPIO as GPIO
import time
 
proximityPilot = 37
 
def setup():
    GPIO.setmode(GPIO.BOARD)
    GPIO.setup(proximityPilot, GPIO.IN)
    """GPIO.add_event_detect(proximityPilot, GPIO.FALLING, callback=inputLow, bouncetime=200) # Wait for the input to go low, run the function when it does
    GPIO.add_event_detect(proximityPilot, GPIO.RISING, callback=inputHigh, bouncetime=200) # Wait for the input to go low, run the function when it does"""

  
def loop():
    while True:
        if (GPIO.input(proximityPilot) == True): # Literally everything is going to flag as high bc 1.5 is considered hi by pi
            print("Button Pressed")
        else:
            print("Vehicle Connected")
        time.sleep(5)

         
def destroy():
    GPIO.cleanup()

     
if  __name__ == '__main__':
    setup()
    try:
        loop()
    except KeyboardInterrupt:
        destroy()