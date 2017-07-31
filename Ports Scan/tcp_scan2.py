#!/usr/bin/python
import logging
logging.getLogger("scapy.runtime").setLevel(logging.ERROR)
from scapy.all import *
SYN=IP(dst="10.1.131.62")/TCP(dport=4444,flags="S")
print "-- SENT --"
SYN.display()
print "\n\n -- RECEIVED --"
response = sr1(SYN,timeout=1,verbose=0)
response.display()
if int(response[TCP].flags)==18:
    print "\n\n -- SENT --"
    A = IP(dst="10.1.131.62")/TCP(dport=25,flags="A",ack=(response[TCP].seq+1))
    A.display()
    print "\n\n -- RECEIVED --"
    response2 = sr1(A,timeout=1,verbose=0)
    response2.display()
else:
    print "SYN-ACK not retuned"
