#!/usr/bin/env sh
# ----------------------------------------------------------------------------
#  Copyright 2001-2006 The Apache Software Foundation.
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
# ----------------------------------------------------------------------------
#
#   Copyright (c) 2001-2006 The Apache Software Foundation.  All rights
#   reserved.


# resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

PRGDIR=`dirname "$PRG"`
BASEDIR=`cd "$PRGDIR/.." >/dev/null; pwd`

# Reset the REPO variable. If you need to influence this use the environment setup file.
REPO=


# OS specific support.  $var _must_ be set to either true or false.
cygwin=false;
darwin=false;
case "`uname`" in
  CYGWIN*) cygwin=true ;;
  Darwin*) darwin=true
           if [ -z "$JAVA_VERSION" ] ; then
             JAVA_VERSION="CurrentJDK"
           else
             echo "Using Java version: $JAVA_VERSION"
           fi
		   if [ -z "$JAVA_HOME" ]; then
		      if [ -x "/usr/libexec/java_home" ]; then
			      JAVA_HOME=`/usr/libexec/java_home`
			  else
			      JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Versions/${JAVA_VERSION}/Home
			  fi
           fi       
           ;;
esac

if [ -z "$JAVA_HOME" ] ; then
  if [ -r /etc/gentoo-release ] ; then
    JAVA_HOME=`java-config --jre-home`
  fi
fi

# For Cygwin, ensure paths are in UNIX format before anything is touched
if $cygwin ; then
  [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
  [ -n "$CLASSPATH" ] && CLASSPATH=`cygpath --path --unix "$CLASSPATH"`
fi

# If a specific java binary isn't specified search for the standard 'java' binary
if [ -z "$JAVACMD" ] ; then
  if [ -n "$JAVA_HOME"  ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
      # IBM's JDK on AIX uses strange locations for the executables
      JAVACMD="$JAVA_HOME/jre/sh/java"
    else
      JAVACMD="$JAVA_HOME/bin/java"
    fi
  else
    JAVACMD=`which java`
  fi
fi

if [ ! -x "$JAVACMD" ] ; then
  echo "Error: JAVA_HOME is not defined correctly." 1>&2
  echo "  We cannot execute $JAVACMD" 1>&2
  exit 1
fi

if [ -z "$REPO" ]
then
  REPO="$BASEDIR"/repo
fi

CLASSPATH="$BASEDIR"/conf:"$REPO"/org/telegram/telegrambots/4.4.0.1/telegrambots-4.4.0.1.jar:"$REPO"/org/telegram/telegrambots-meta/4.4.0.1/telegrambots-meta-4.4.0.1.jar:"$REPO"/com/google/inject/guice/4.2.2/guice-4.2.2.jar:"$REPO"/javax/inject/javax.inject/1/javax.inject-1.jar:"$REPO"/aopalliance/aopalliance/1.0/aopalliance-1.0.jar:"$REPO"/com/fasterxml/jackson/core/jackson-annotations/2.9.9/jackson-annotations-2.9.9.jar:"$REPO"/com/fasterxml/jackson/jaxrs/jackson-jaxrs-json-provider/2.9.9/jackson-jaxrs-json-provider-2.9.9.jar:"$REPO"/com/fasterxml/jackson/jaxrs/jackson-jaxrs-base/2.9.9/jackson-jaxrs-base-2.9.9.jar:"$REPO"/com/fasterxml/jackson/module/jackson-module-jaxb-annotations/2.9.9/jackson-module-jaxb-annotations-2.9.9.jar:"$REPO"/com/fasterxml/jackson/core/jackson-core/2.9.9/jackson-core-2.9.9.jar:"$REPO"/com/fasterxml/jackson/core/jackson-databind/2.9.9/jackson-databind-2.9.9.jar:"$REPO"/org/glassfish/jersey/inject/jersey-hk2/2.29/jersey-hk2-2.29.jar:"$REPO"/org/glassfish/jersey/core/jersey-common/2.29/jersey-common-2.29.jar:"$REPO"/org/glassfish/hk2/osgi-resource-locator/1.0.3/osgi-resource-locator-1.0.3.jar:"$REPO"/com/sun/activation/jakarta.activation/1.2.1/jakarta.activation-1.2.1.jar:"$REPO"/org/glassfish/hk2/hk2-locator/2.5.0/hk2-locator-2.5.0.jar:"$REPO"/org/glassfish/hk2/external/aopalliance-repackaged/2.5.0/aopalliance-repackaged-2.5.0.jar:"$REPO"/org/glassfish/hk2/hk2-api/2.5.0/hk2-api-2.5.0.jar:"$REPO"/org/glassfish/hk2/hk2-utils/2.5.0/hk2-utils-2.5.0.jar:"$REPO"/org/javassist/javassist/3.22.0-CR2/javassist-3.22.0-CR2.jar:"$REPO"/org/glassfish/jersey/media/jersey-media-json-jackson/2.29/jersey-media-json-jackson-2.29.jar:"$REPO"/org/glassfish/jersey/ext/jersey-entity-filtering/2.29/jersey-entity-filtering-2.29.jar:"$REPO"/org/glassfish/jersey/containers/jersey-container-grizzly2-http/2.29/jersey-container-grizzly2-http-2.29.jar:"$REPO"/org/glassfish/hk2/external/jakarta.inject/2.5.0/jakarta.inject-2.5.0.jar:"$REPO"/org/glassfish/grizzly/grizzly-http-server/2.4.4/grizzly-http-server-2.4.4.jar:"$REPO"/org/glassfish/grizzly/grizzly-http/2.4.4/grizzly-http-2.4.4.jar:"$REPO"/org/glassfish/grizzly/grizzly-framework/2.4.4/grizzly-framework-2.4.4.jar:"$REPO"/jakarta/ws/rs/jakarta.ws.rs-api/2.1.5/jakarta.ws.rs-api-2.1.5.jar:"$REPO"/org/glassfish/jersey/core/jersey-server/2.29/jersey-server-2.29.jar:"$REPO"/org/glassfish/jersey/core/jersey-client/2.29/jersey-client-2.29.jar:"$REPO"/org/glassfish/jersey/media/jersey-media-jaxb/2.29/jersey-media-jaxb-2.29.jar:"$REPO"/jakarta/annotation/jakarta.annotation-api/1.3.4/jakarta.annotation-api-1.3.4.jar:"$REPO"/javax/validation/validation-api/2.0.1.Final/validation-api-2.0.1.Final.jar:"$REPO"/jakarta/xml/bind/jakarta.xml.bind-api/2.3.2/jakarta.xml.bind-api-2.3.2.jar:"$REPO"/jakarta/activation/jakarta.activation-api/1.2.1/jakarta.activation-api-1.2.1.jar:"$REPO"/org/json/json/20180813/json-20180813.jar:"$REPO"/org/apache/httpcomponents/httpclient/4.5.9/httpclient-4.5.9.jar:"$REPO"/org/apache/httpcomponents/httpcore/4.4.11/httpcore-4.4.11.jar:"$REPO"/commons-logging/commons-logging/1.2/commons-logging-1.2.jar:"$REPO"/commons-codec/commons-codec/1.11/commons-codec-1.11.jar:"$REPO"/org/apache/httpcomponents/httpmime/4.5.9/httpmime-4.5.9.jar:"$REPO"/commons-io/commons-io/2.6/commons-io-2.6.jar:"$REPO"/org/telegram/telegrambots-abilities/4.4.0.1/telegrambots-abilities-4.4.0.1.jar:"$REPO"/org/apache/commons/commons-lang3/3.9/commons-lang3-3.9.jar:"$REPO"/org/mapdb/mapdb/3.0.7/mapdb-3.0.7.jar:"$REPO"/org/jetbrains/kotlin/kotlin-stdlib/1.2.71/kotlin-stdlib-1.2.71.jar:"$REPO"/org/jetbrains/annotations/13.0/annotations-13.0.jar:"$REPO"/org/eclipse/collections/eclipse-collections-api/10.2.0/eclipse-collections-api-10.2.0.jar:"$REPO"/org/eclipse/collections/eclipse-collections/10.2.0/eclipse-collections-10.2.0.jar:"$REPO"/org/eclipse/collections/eclipse-collections-forkjoin/10.2.0/eclipse-collections-forkjoin-10.2.0.jar:"$REPO"/net/jpountz/lz4/lz4/1.3.0/lz4-1.3.0.jar:"$REPO"/org/mapdb/elsa/3.0.0-M5/elsa-3.0.0-M5.jar:"$REPO"/com/google/guava/guava/27.1-jre/guava-27.1-jre.jar:"$REPO"/com/google/guava/failureaccess/1.0.1/failureaccess-1.0.1.jar:"$REPO"/com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar:"$REPO"/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar:"$REPO"/org/checkerframework/checker-qual/2.5.2/checker-qual-2.5.2.jar:"$REPO"/com/google/errorprone/error_prone_annotations/2.2.0/error_prone_annotations-2.2.0.jar:"$REPO"/com/google/j2objc/j2objc-annotations/1.1/j2objc-annotations-1.1.jar:"$REPO"/org/codehaus/mojo/animal-sniffer-annotations/1.17/animal-sniffer-annotations-1.17.jar:"$REPO"/org/apache/logging/log4j/log4j-core/2.12.0/log4j-core-2.12.0.jar:"$REPO"/org/apache/logging/log4j/log4j-api/2.12.0/log4j-api-2.12.0.jar:"$REPO"/org/apache/logging/log4j/log4j-slf4j-impl/2.11.0/log4j-slf4j-impl-2.11.0.jar:"$REPO"/org/slf4j/slf4j-api/1.8.0-alpha2/slf4j-api-1.8.0-alpha2.jar:"$REPO"/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.3.70/kotlin-stdlib-jdk8-1.3.70.jar:"$REPO"/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.3.70/kotlin-stdlib-jdk7-1.3.70.jar:"$REPO"/org/jetbrains/kotlin/kotlin-stdlib-common/1.3.70/kotlin-stdlib-common-1.3.70.jar:"$REPO"/mysql/mysql-connector-java/8.0.20/mysql-connector-java-8.0.20.jar:"$REPO"/com/google/protobuf/protobuf-java/3.6.1/protobuf-java-3.6.1.jar:"$REPO"/JJ/ExampleTelegramBot/1.0-SNAPSHOT/ExampleTelegramBot-1.0-SNAPSHOT.jar

ENDORSED_DIR=
if [ -n "$ENDORSED_DIR" ] ; then
  CLASSPATH=$BASEDIR/$ENDORSED_DIR/*:$CLASSPATH
fi

if [ -n "$CLASSPATH_PREFIX" ] ; then
  CLASSPATH=$CLASSPATH_PREFIX:$CLASSPATH
fi

# For Cygwin, switch paths to Windows format before running java
if $cygwin; then
  [ -n "$CLASSPATH" ] && CLASSPATH=`cygpath --path --windows "$CLASSPATH"`
  [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --path --windows "$JAVA_HOME"`
  [ -n "$HOME" ] && HOME=`cygpath --path --windows "$HOME"`
  [ -n "$BASEDIR" ] && BASEDIR=`cygpath --path --windows "$BASEDIR"`
  [ -n "$REPO" ] && REPO=`cygpath --path --windows "$REPO"`
fi

exec "$JAVACMD" $JAVA_OPTS  \
  -classpath "$CLASSPATH" \
  -Dapp.name="webBot" \
  -Dapp.pid="$$" \
  -Dapp.repo="$REPO" \
  -Dapp.home="$BASEDIR" \
  -Dbasedir="$BASEDIR" \
  App \
  "$@"
