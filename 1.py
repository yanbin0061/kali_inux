#!/usr/bin/python
import socket
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
try:
    print "\nSending evil buffer...."
    s.connect(("10.1.131.75",110))
    data = s.recv(1024)
    print data

    s.send("USER bin" + "\r\n")
    data = s.recv(1024)
    print data

    s.send("PASS test" + "\r\n")
    data = s.recv(1024)
    print data

    s.close()
    print "\nDone!"
except:
    print "Could not connect to POP3!"
