import json, requests
import serial
import sys
import time

# set default connection
port = '/dev/ttyACM0'
baudrate = 9600
ip = "172.24.1.1:8000"

# permit to change configuration of the connection
for arg in sys.argv:
    if arg[:5] == "port=":
        port = arg[5:]
    elif arg[:9] == "baudrate=":
        port = arg[9:]
    elif arg[:3] == "ip=":
    	ip = arg[3:]

# inform the user on what it is connected
print "Server ip : "+ip
print "Serial port : "+str(port)+" baudrate : "+str(baudrate)


try :
    # configure the serial connections (the parameters differs on the device you are connecting to)
    ser = serial.Serial()
    ser.baudrate = baudrate
    ser.port = port
    ser.open()
except serial.serialutil.SerialException :
    print "You try to acess to undefiened port"
    sys.exit(1)

ser.isOpen()

# getting the command on continue
while (1):
	try :
		url = "http://"+ip+"/Api/command.json"
		resp = requests.get(url=url)
		data = json.loads(resp.text)
	except requests.exceptions.ConnectionError :
		print "Error : You try to catch an impossible connection"
		break

	# if receive no request we continue
	if not data:
	    continue
	    time.sleep(0.5)

	# treat all the command
	for cmd in data:
		charact = cmd["characters"]
		if charact == "STOP":
			charact = " "
		print 'receive : ' + charact * cmd["value"]
		ser.write(charact)
