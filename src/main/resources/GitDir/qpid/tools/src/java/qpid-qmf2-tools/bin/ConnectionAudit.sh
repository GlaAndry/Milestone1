#!/usr/bin/env bash
#
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
# 
#   http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
#

# Test if we're running on Cygwin.
cygwin=false;
case "`uname`" in
  CYGWIN*) cygwin=true;;
esac

WHEREAMI=`dirname $0`
if [ -z "$QMF2_HOME" ]; then
    export QMF2_HOME=`cd $WHEREAMI/../ && pwd`
fi

CLASSPATH=$QMF2_HOME/lib/*:$CLASSPATH

# If we're on Cygwin we need to convert to Windows path.
if $cygwin; then
  CLASSPATH=$(cygpath -wp $CLASSPATH)
fi

# Get the log level from the AMQJ_LOGGING_LEVEL environment variable.
if [ -n "$AMQJ_LOGGING_LEVEL" ]; then
    PROPERTIES=-Damqj.logging.level=$AMQJ_LOGGING_LEVEL
fi

java -cp "$CLASSPATH" $PROPERTIES org.apache.qpid.qmf2.tools.ConnectionAudit "$@"
