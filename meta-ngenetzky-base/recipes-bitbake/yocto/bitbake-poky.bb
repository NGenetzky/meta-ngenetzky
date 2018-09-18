# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Build using Poky"
PV = "2.4"
PR = "r0"

B = "${WORKDIR}/build"
SH_CONSOLE = ". ${S}/poky/oe-init-build-env ${B} ${S}/poky/bitbake"

do_build[depends] = "\
    poky:do_unpack \
"
do_build[dirs] = "${S} ${B}"
do_build(){
    local poky_pv='2.4'

    ln -sfT \
        ${TOPDIR}/tmp/work/poky-${poky_pv}*/git/ \
        "${S}/poky"

    echo "${SH_CONSOLE}" > ${S}/console.sh
}
