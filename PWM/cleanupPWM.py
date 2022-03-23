pwmPin = 33

def destroy():
    pwm.stop()
    GPIO.output(pwmPin, GPIO.LOW)
    GPIO.cleanup()

if  __name__ == '__main__':
    destroy()