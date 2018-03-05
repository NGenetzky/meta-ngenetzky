#!/bin/bash
di_from_yocto(){
  # local pn="core-image-full-cmdline"
  local pn="core-image-minimal"
  local mach="genericx86-64"
  local pv="v3"
  docker import \
    /data/yocto-pyro/1/build/tmp/deploy/images/genericx86-64/${pn}-${mach}.tar.xz \
    ngenetzky/${pn}:${pv}
}

di_from_yocto
