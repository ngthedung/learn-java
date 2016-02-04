#!/bin/bash

cygwin=false
case "`uname`" in
  CYGWIN*) cygwin=true;;
esac

bin=`dirname "$0"`
bin=`cd "$bin"; pwd`

JAVACMD="$JAVA_HOME/bin/java"
APP_HOME=`cd $bin/..; pwd; cd $bin`

LIB="$APP_HOME/lib" ;
CLASSPATH="$JAVA_HOME/lib/tools.jar"
CLASSPATH="${CLASSPATH}:$APP_HOME/lib/*"

if $cygwin; then
  JAVA_HOME=`cygpath --absolute --windows "$JAVA_HOME"`
  CLASSPATH=`cygpath --path --windows "$CLASSPATH"`
fi

###########################Start Profiling Config##########################################
JPDA_TRANSPORT=dt_socket
JPDA_ADDRESS=8000

REMOTE_DEBUG="-Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n"

#for linux
LD_LIBRARY_PATH="/opt/Yourkit/bin/linux-x86-64/"
#for MAC
#DYLD_LIBRARY_PATH="/Users/tuannguyen/java/YourKit/bin/mac/"
#for Window

PATH="$PATH:$LD_LIBRARY_PATH"
export LD_LIBRARY_PATH DYLD_LIBRARY_PATH

#YOURKIT_PROFILE_OPTION="$REMOTE_DEBUG -agentlib:yjpagent  -Djava.awt.headless=false"
###########################Eng Profiling Config#############################################
MODE_OPT="console"
JAVA_OPTS="-server -XX:+UseParallelGC -Xshare:auto -Xms64m -Xmx256m"
CLASS="com.hkt.client.swingexp.app.main.Main"


while [[ $# > 0 ]] 
do
  key="$1"
  shift

  case $key in
    -c|--console)
      MODE_OPT="console"
      ;;
    -b|--background)
      MODE_OPT="background"
      ;;
    *)
      echo "Unknown option $key"
      # unknown option
      ;;
  esac
done

function console() {

  exec $JAVACMD $JAVA_OPTS -cp $CLASSPATH $YOURKIT_PROFILE_OPTION $CLASS 
}

function background() {
  exec $JAVACMD $JAVA_OPTS -cp $CLASSPATH $YOURKIT_PROFILE_OPTION $CLASS > pos.log 2>&1 < /dev/null &
}

COMMAND=$1
shift

if [ "$MODE_OPT" = "background" ] ; then
  background
else
  console
fi
