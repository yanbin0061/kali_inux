#!/usr/bin/python
import logging
logging.getLogger("scapy.runtime").setLevel(logging.ERROR)
from scapy.all import *
if len(sys.argv)!=2:
    print "Usage - ./ACK_Ping.py [/24 network address]"
    print "Example - ./ACK_Ping.py 10.1.131.0"
    print "Example will perform a TCP ACK ping scan of the 10.1.131.0/24 range"
    sys.exit()
address=str(sys.argv[1])
prefix=address.split(".")[0]+"."+address.split(".")[1]+"."+address.split(".")[2]+"."
for addr in range(0,254):
    response=sr1(IP(dst=prefix+str(addr))/TCP(dport=2222,flags="A"),timeout=0.1,verbose=0)
    try:
        if int(response[TCP].flags)==4:
            print prefix+str(addr)
    except:
        pass
