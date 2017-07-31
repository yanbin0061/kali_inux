#!/usr/bin/python
from scapy.all import *
import logging
logging.getLogger("scapy.runtime").setLevel(logging.ERROR)
import sys
if len(sys.argv)!=2:
    print "Usage - ./ttl.os.py [IP address]"
    print "Example - ./ttl.os.py 10.1.131.62"
    print "Example will perform ttl analaysis to attempt to determine whether the system is windows or linux.unix"
    sys.exit()
ip = sys.argv[1]
ans = sr1(IP(dst=str(ip))/ICMP(),timeout=1,verbose=0)
if ans ==None:
    print "No response was returned"
elif int(ans[IP].ttl)<=64:
    print "Host is Linux/Unix"
else:
    print "Host is Windows"
