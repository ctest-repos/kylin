#!/usr/bin/env bash

#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ${DIR} || exit
echo "build image in dir "${DIR}

# TODO: download from the official website
echo "package kylin in local for building image"
rm -rf ${DIR}/package/*
if [[ ! -d ${DIR}/package/ ]]; then
    mkdir -p ${DIR}/package/
fi
wget https://dist.apache.org/repos/dist/dev/kylin/apache-kylin-5.0.0-beta-rc1/apache-kylin-5.0.0-beta-bin.tar.gz -P ${DIR}/package/
echo "start to build Hadoop docker image"
docker build -f Dockerfile_hadoop -t hadoop3.2.1-all-in-one-for-kylin5 .
docker build -f Dockerfile_kylin -t apachekylin/apache-kylin-standalone:5.0.0 .
