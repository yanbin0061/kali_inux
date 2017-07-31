#!/usr/bin/python
import logging
logging.getLogger("scapy.runtime").setLevel(logging.ERROR)
from scapy.all import *
import sys
if len(sys.argv)!=4:
    print "Usage - ./syn.scan.py [Target-IP] [First Port] [Last Port]"
    print "Example - ./syn.scan.py 10.1.131.62 1 100"
    print "Example will TCP SYN scan ports 1 through 10 on 10.1.131.62"
    sys.exit()
ip = sys.argv[1]
start = int(sys.argv[2])
end = int(sys.argv[3])

for port in range(start,end):
    answer = sr1(IP(dst=ip)/TCP(dport=port),timeout=1,verbose=0)
    if answer == None:
        pass
    else:
        if int(answer[TCP].flags)==18:
            print port
        else:
            pass
