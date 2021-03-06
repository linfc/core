#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export NAME=antlr4ide
export ANTLR4IDE_DROP_URL=${ANTLR4IDE_URL:=http://dl.bintray.com/jknack/antlr4ide/}${ANTLR4IDE_VERSION:=0.3.5}/antlr4ide-plugin-$ANTLR4IDE_VERSION.zip
export ANTLR4IDE_DROP=antlr4ide-plugin-$ANTLR4IDE_VERSION.zip
if [ ! -f $ANTLR4IDE_DROP ]; then
  echo
  echo Downloading ANTLR4IDE $ANTLR4IDE_VERSION
  echo
  wget $ANTLR4IDE_DROP_URL
  echo
fi
#
# ANTLR4IDE
#
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../eclipsebase/antlr4ide" > eclipse/dsl/links/antlr4ide.link 
mkdir EclipseBase 2> /dev/null
mkdir EclipseBase/antlr4ide 2> /dev/null
mkdir EclipseBase/antlr4ide/eclipse 2> /dev/null
cd EclipseBase/antlr4ide/eclipse
> .eclipseextension
unzip -oq ../../../$ANTLR4IDE_DROP
cd ../..
zip -rq antlr4ide.sapp antlr4ide ../eclipse/dsl/links/antlr4ide.link
cd ..
rm -fR eclipse EclipseBase/antlr4ide
echo
echo ...done!
