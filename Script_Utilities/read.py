import json, requests
import serial
import sys

# set default connection
port = '/dev/ttyACM0'
baudrate = 9600
ip = "172.24.1.1:8000"

# permit to change configuration of the connection
for arg in sys.argv:
    if arg[:3] == "ip=":
    	ip = arg[3:]
    elif arg[:5] == "port=":
        port = arg[5:]
    elif arg[:9] == "baudrate=":
        port = arg[9:]

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
    readData = ser.readline()
    print "read " + readData
    try :
    	url = "http://"+ip+"/api/message.json"
    	data = {'message' : readData}
    	headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
    	r = requests.post(url, data=json.dumps(data), headers=headers)
    except requests.exceptions.ConnectionError :
    	print "Error : Impossible to join the connection program cut"
    	break
