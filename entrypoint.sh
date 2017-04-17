#!/bin/bash

set -e

# set hosts
# cat >> /etc/hosts <<- EOM
# 10.0.0.1 gw01.yhj gw01
# 10.0.0.6 center01.yhj center01
# EOM

if [ -z $MEM_MIN ]; then
	export MEM_MIN=1024M
fi
if [ -z $MEM_MAX ]; then
	export MEM_MAX=1024M
fi

if [ "$1" = "java" ]; then
	java \
		-Djava.security.egd=file:/dev/./urandom \
		-Denv=$ENV \
		-Xms$MEM_MIN -Xmx$MEM_MAX \
		-jar /*.jar
fi
exec "$@"
