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
        cd dmesh-streaming && ./consumer.py "$@" && exit 0
        ;;

        --streaming)
        shift
        cd dmesh-streaming
        spark-submit --master 'local' --name 'dmesh-streaming' 'main.py' "$@" && exit 0
        ;;

        --batch)
        shift
        cd dmesh-batch
        spark-submit --master 'local' --name 'dmesh-batch' 'main.py' "$@" && exit 0
        ;;

        --mock-api)
        shift
        cd mock-api && ./main.py "$@" && exit 0
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
