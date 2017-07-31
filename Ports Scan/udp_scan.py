#!/usr/bin/python
import logging
logging.getLogger("scapy.runtime").setLevel(logging.ERROR)
from scapy.all import *
import time
import sys
if len(sys.argv)!=4:
    print "Usage - ./udp_scan.py [Target-IP] [First Port] [Last Port]"
    print "Example - ./udp_scan.py 10.1.131.69 1 100"
    print "Example will UDP port scan ports 1 through 100 on 10.1.131.69"
    sys.exit()
ip = sys.argv[1]
start = int(sys.argv[2])
end = int(sys.argv[3])
for port in range(start,end):
    a=sr1(IP(dst=ip)/UDP(dport=port),timeout=5,verbose=0)
    time.sleep(1)
    if a == None:
        print port
    else:
        pass
