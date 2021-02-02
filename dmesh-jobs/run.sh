#!/bin/bash -xe
# @script       run.sh
# @author       Anthony Vilarim Caliani
# @contact      github.com/avcaliani
#
# @description
# Utilities script for executing DMesh jobs.
#
# @params
# 01 - Job Name
#
# @usage
# ./run.sh [ --consumer | --streaming | --batch ]
BASE_DIR="$(dirname $0)"

cd $BASE_DIR
for arg in "$@"
do
    case $arg in

        --consumer)
        shift
        ./dmesh-consumer.py "$@" && exit 0
        ;;

        --streaming)
        shift
        spark-submit --master 'local' --name 'dmesh-streaming' 'dmesh-streaming.py' "$@" && exit 0
        ;;

        --batch)
        shift
        spark-submit --master 'local' --name 'dmesh-batch' 'dmesh-batch.py' "$@" && exit 0
        ;;

        *)
        if [[ "$1" != "" ]]; then
            echo "Invalid argument '$1'"
        fi
        shift
        ;;
    esac
done
exit 0
