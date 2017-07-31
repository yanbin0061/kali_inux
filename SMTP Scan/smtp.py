#!/usr/bin/python
import socket
import sys
if len(sys.argv)!=2:
    print "Usage: smtp.py <username>"
    sys.exit()
s=socket.socket(socket.AF_INET, socket.SOCK_STREAM)
connect=s.connect(('10.1.131.62',25))
banners=s.recv(1024)
print banners +"aaaaaa"
s.send("VRFY"+sys.argv[1]+'\r\n')
result=s.recv(1024)
print result
s.close
