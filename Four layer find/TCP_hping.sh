#!/bin/bash
if [[ "$#" -ne 1 ]]; then
  #statements
  echo "Usage - ./tcp_hping.sh [/24 network address]"
  echo "Example - ./tcp_hping.sh 10.1.131.69"
  echo "Example will perform a TCP ping sweep of the 10.1.131.0/24 network an output to an output.txt file"
  exit
fi
prefix=$(echo $1 | cut -d "." -f 1-3)
for addr in $(seq 1 254); do
  #statements
  hping3 $prefix.$addr -c 1 >> r.txt
done
grep ^len r.txt | cut -d " " -f 2 | cut -d "=" -f 2 >> outputTCP.txt
rm r.txt
