#!/bin/bash
if [ "$#" -ne 1 ];then       #-ne 1 参数不等于为1
  echo "Usage - ./arping.sh [interface]"
  echo "Excample - ./arping.sh eth0"
  echo "Example will perform an ARP scan of the local subnet to which eth0 is assigned"
  exit
fi
#if [ "$#" -ne 1 ];then       #-ne 1 参数不等于为1
#  echo "Usage - ./arping.sh [interface]"
#  echo "Excample - ./arping.sh eth0"
#  echo "Example will perform an ARP scan of the local subnet to which eth0 is assigned"
#  exit
#fi

interface=$1
prefix=$(ifconfig eth0 | grep "netmask" | awk '{print $2}' | cut -d '.' -f 1-3)
#prefix=$(ifconfig $interface | grep 'netmask' | cut -d'.' -f1-3 | cut -d't' -f2 |cut -d' ' -f2 | cut -d'.' -f1-3)
for addr in $(seq 1 254);do
        arping -c 1 $prefix.$addr | grep 'reply from' | cut -d ' ' -f4 >>addr.txt
done
  echo "Done!"

