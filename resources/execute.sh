#!/usr/bin/env bash

java -jar ./lib/mybatis-generator-core-1.3.5.jar -configfile $1 -overwrite > "$1.log"