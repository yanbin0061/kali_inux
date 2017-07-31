#!/bin/bash
if [[ "$#" -ne 1 ]]; then
  echo "Usage - ./pinger.sh [/24 network address]"
  echo "Example - ./pinger.sh 10.1.131.69"
  echo "Example will perform an ICMP ping sweep of the 10.1.131.0/24 network"
  exit
fi
prefix=$(echo $1 | cut -d '.' -f 1-3)
for addr in $(seq 1 254); do
  #statements
  ping $prefix.$addr -c 1 | grep "bytes from" | cut -d ' ' -f 4 | cut -d ':' -f 1 &
done
#print "Done!"
