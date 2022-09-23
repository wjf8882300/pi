# -*- coding: utf-8 -*-
from urllib import parse
import paho.mqtt.client as mqtt
import rpi.car

HOST = "ws://192.168.32.48/mqtt"
PORT = 8083
client_id = "pi_car"
username = "pi_car"
password = "RBzicGlr9fXhyx2b"


def on_connect(client, userdata, flags, rc):
    print("Connected with result code "+str(rc))
    client.subscribe("pi/cmd")


def on_message(client, userdata, msg):
    print("主题:" + msg.topic + " 消息:" + str(msg.payload.decode('utf-8')))
    if msg.topic == "pi/cmd":
        cmd = str(msg.payload.decode('utf-8'))
        if cmd == "front":
            rpi.car.front(3)
        elif cmd == "back":
            rpi.car.back(3)
        elif cmd == "left":
            rpi.car.left(3)
        elif cmd == "right":
            rpi.car.right(3)
        elif cmd == "stop":
            rpi.car.stop()


def on_subscribe(client, userdata, mid, granted_qos):
    print("On Subscribed: qos = %d" % granted_qos)


def on_disconnect(client, userdata, rc):
    if rc != 0:
        print("Unexpected disconnection %s" % rc)

if __name__ == "__main__":
    urlparts = parse.urlparse(HOST)
    client = mqtt.Client(client_id=client_id, transport='websockets')
    client.username_pw_set(username, password)
    client.on_connect = on_connect
    client.on_message = on_message
    client.on_subscribe = on_subscribe
    client.on_disconnect = on_disconnect
    client.ws_set_options(path=urlparts.path)
    client.connect(urlparts.netloc, PORT, 60)
    rpi.car.start()
    client.loop_forever()
    rpi.car.exit()
