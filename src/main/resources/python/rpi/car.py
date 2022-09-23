# -*- coding:UTF-8 -*-
import RPi.GPIO as GPIO
import time

# MOTOR D 左后
A1 = 24
A2 = 26

# MOTOR C 右后
B1 = 22
B2 = 18

# MOTOR B 左前
C1 = 12
C2 = 16

# MOTOR A 右前
D1 = 8
D2 = 10

# 设置GPIO模式
GPIO.setmode(GPIO.BOARD)

# 初始化接口
def start():
    GPIO.setup(A1, GPIO.OUT)
    GPIO.setup(A2, GPIO.OUT)
    GPIO.setup(B1, GPIO.OUT)
    GPIO.setup(B2, GPIO.OUT)
    GPIO.setup(C1, GPIO.OUT)
    GPIO.setup(C2, GPIO.OUT)
    GPIO.setup(D1, GPIO.OUT)
    GPIO.setup(D2, GPIO.OUT)


# 前进的代码
def front(sleep_time):
    GPIO.output(A1, GPIO.HIGH)
    GPIO.output(A2, GPIO.LOW)
    GPIO.output(B1, GPIO.HIGH)
    GPIO.output(B2, GPIO.LOW)
    GPIO.output(C1, GPIO.HIGH)
    GPIO.output(C2, GPIO.LOW)
    GPIO.output(D1, GPIO.HIGH)
    GPIO.output(D2, GPIO.LOW)
    time.sleep(sleep_time)

# 后退
def back(sleep_time):
    GPIO.output(A1, GPIO.LOW)
    GPIO.output(A2, GPIO.HIGH)
    GPIO.output(B1, GPIO.LOW)
    GPIO.output(B2, GPIO.HIGH)
    GPIO.output(C1, GPIO.LOW)
    GPIO.output(C2, GPIO.HIGH)
    GPIO.output(D1, GPIO.LOW)
    GPIO.output(D2, GPIO.HIGH)
    time.sleep(sleep_time)

# 左转
def left(sleep_time):
    GPIO.output(B1, False)
    GPIO.output(B2, False)
    GPIO.output(A1, GPIO.HIGH)
    GPIO.output(A2, GPIO.LOW)
    GPIO.output(C1, GPIO.HIGH)
    GPIO.output(C2, GPIO.LOW)
    GPIO.output(D1, GPIO.HIGH)
    GPIO.output(D2, GPIO.LOW)
    time.sleep(sleep_time)

# 右转
def right(sleep_time):
    GPIO.output(A1, False)
    GPIO.output(A2, False)
    GPIO.output(B1, GPIO.HIGH)
    GPIO.output(B2, GPIO.LOW)
    GPIO.output(C1, GPIO.HIGH)
    GPIO.output(C2, GPIO.LOW)
    GPIO.output(D1, GPIO.HIGH)
    GPIO.output(D2, GPIO.LOW)
    time.sleep(sleep_time)

# 停止
def stop():
    GPIO.output(A1, False)
    GPIO.output(A2, False)
    GPIO.output(B1, False)
    GPIO.output(B2, False)
    GPIO.output(C1, False)
    GPIO.output(C2, False)
    GPIO.output(D1, False)
    GPIO.output(D2, False)

def exit():
    GPIO.cleanup()


