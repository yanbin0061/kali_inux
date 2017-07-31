#!/usr/bin/python
import logging
import subprocess
logging.getLogger("scapy.runtime").setLevel(logging.ERROR)
from scapy.all import *
if len(sys.argv)!=2:
    print "Usage - ./arp_disc.py [interface]"
    print "Example - ./arp_disc.py eth0"
    print "Example will perform an ARP scan of the local subnet to which eth0 is assigned"
    sys.exit()
filename = str(sys.argv[1])
file = open(filename,"r")

for addr in file:
    answer = sr1(ARP(pdst=addr.strip()),timeout=0.1,verbose=0)
    if answer==None:
        pass
    else:
        print addr.strip()
print "Done!"
