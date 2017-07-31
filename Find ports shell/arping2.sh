#!/bin/bash
if [ "$#" -ne 1 ];then
#if [ '$#' -ne 1 ]; then
  echo "Usage - ./arping.sh [iterface]"
  echo "Example - ./arping.sh addr"
  echo "Example will perform an ARP scan of the local subnet to witch eth0 is assigned"
   exit
fi
file=$1
for addr in $(cat $file); do
# arping -c 1 $prefix.$addr | grep 'reply from' | cut -d ' ' -f4 
  arping -c 1 $addr | grep 'reply from' | cut -d ' ' -f4 
done
   echo "Done!"
