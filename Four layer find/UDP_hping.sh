#!/bin/bash
if [ "$#" -ne 1 ]; then
  #statements
  echo "Usage - ./udp_hping.sh [/24 network address]"
  echo "Example - ./udp_hping.sh 10.1.131.69"
  echo "Example will perform a UDP ping sweep of the 10.1.131.69"
  exit
fi
prefix=$(echo $1 | cut -d "." -f 1-3)
for addr in $(seq 1 254); do
  #statements
  hping3 $prefix.$addr --udp -c 1 >> r.txt
done
grep Unreachable r.txt | cut -d " " -f 5 | cut -d "=" -f 2 >>output.txt
rm r.txt
