# -*- coding:UTF-8 -*-
import redis
import sys
import getopt
import hashlib

############################################
# example:
# python3 redis_mqtt.py  -u 'pi_car' -p '123456' -c 'abcd'
############################################

host = '192.168.32.48'
port = '6379'
password = 'kCOs142GAhrVR3cw'

if __name__ == "__main__":
    argv = sys.argv[1:]
    if len(argv) < 1:
        print('redis_mqtt.py -u <Username> -p <Password> -c <ClientId>')
        sys.exit()

    try:
        opts, args = getopt.getopt(argv, "hu:p:c:", ["uUsername=", "pPassword=", "cClientId="])
    except getopt.GetoptError:
        print('redis_mqtt.py -u <Username> -p <Password> -c <ClientId>')
        sys.exit(2)

    for opt, arg in opts:
        if opt == '-h':
            print('redis_mqtt.py -u <Username> -p <Password> -c <ClientId>')
            sys.exit()
        elif opt in ("-u", "--uUsername"):
            mqtt_username = arg
        elif opt in ("-p", "--pPassword"):
            mqtt_password = arg
        elif opt in ("-c", "--cClientId"):
            mqtt_client = arg
    r = redis.Redis(host=host, port=port, db=0, password=password, decode_responses=True)
    r.hset("mqtt_user:%s:%s" % (mqtt_username, mqtt_client), "password", hashlib.sha256(mqtt_password.encode("utf-8")).hexdigest())
