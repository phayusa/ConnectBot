# ConnectBot

ConnectBot is Web API permit to communicate between two devices by network way. In our case, we use it to control a robot.
The API must have a client which play the role of controller (or sender) and an other script which receive his commands.
We give two example of clients in Client folder.
Receive scripts are also include in the project in Script_Utilities folder.

## Requirements

* `pip install django`
* `pip install djangorestframework`
* `pip install django-cors-headers`
* `pip install django-sslserver`
* `pip install pygame` or linux : `apt-get install python-pygame`
* `pip install djangorestframework-jwt`

## Run
### Server (API/b2b_server)
`python manage.py runserver`
### Web client (Client/b2b_client)
`python manage.py runserver`
### Android client (Client/Android Client)
* build the apk 
* launch it on your smartphone
### Scripts (ConnectBot/Script_Utilities)
* `python script.py` : receive command of the client and send it on the serial connection
* `python read.py` : receive data from the robot captors and send it by message to the client

 
## Documentation
   The documentation is available [here](https://github.com/phayusa/ConnectBot/blob/master/API/Documentation/Documentation.pdf).