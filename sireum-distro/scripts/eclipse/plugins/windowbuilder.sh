#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
#export WB_DROP_URL=${WB_URL:=http://ftp.osuosl.org/pub/eclipse/windowbuilder/WB/release/R}${WB_RELEASE:=201309271200}/WB_v${WB_VERSION:=1.6.1}_UpdateSite_for_Eclipse4.3.zip
export WB_DROP_URL=http://download.eclipse.org/windowbuilder/WB/integration/WB_v1.7.0_UpdateSite_for_Eclipse4.4.zip
export WB_DROP=${WB_DROP_URL##*/}
if [ ! -f $WB_DROP ]; then
  echo
  echo Downloading WindowBuilder $WB_DROP
  echo
  wget $WB_DROP_URL
  echo
fi
#
# WindowBuilder
#
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../eclipsedev/windowbuilder" > eclipse/dsl/links/windowbuilder.link 
mkdir EclipseDev 2> /dev/null
mkdir EclipseDev/windowbuilder 2> /dev/null
mkdir EclipseDev/windowbuilder/eclipse 2> /dev/null
cd EclipseDev/windowbuilder/eclipse
> .eclipseextension
unzip -oq ../../../$WB_DROP
cd ../..
zip -rq windowbuilder.sapp windowbuilder ../eclipse/dsl/links/windowbuilder.link
cd ..
rm -fR eclipse EclipseDev/windowbuilder
echo
echo ...done!
