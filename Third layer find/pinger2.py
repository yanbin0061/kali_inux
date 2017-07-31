#!/usr/bin/python
import logging
import subprocess
logging.getLogger("scapy.runtime").setLevel(logging.ERROR)
from scapy.all import *
if len(sys.argv)!=2:
    print "Usage - ./pinger.py [/24 network address]"
    print "Example - ./pinger.py 10.1.131.69"
    print "Example will perform an ICMP scan of the 10.1.131.0/24 range"
    sys.exit()
filename = str(sys.argv[1])
file = open(filename, "r")
for addr in file:
    answer=sr1(IP(dst=addr.strip())/ICMP(),timeout=0.1,verbose=0)
    if answer==None:
        pass
    else:
        print addr.strip()
