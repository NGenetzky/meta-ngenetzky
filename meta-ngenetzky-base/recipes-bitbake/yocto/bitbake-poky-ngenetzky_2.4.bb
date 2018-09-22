# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

require bitbake-poky.inc

PV = "2.4"
PR = "${INC_PR}.1"

do_build[depends] += "\
    meta-ngenetzky:do_unpack \
"
do_build_append(){
    local metangenetzky_pv='2018.09.20'

    ln -sfT \
        ${TOPDIR}/tmp/work/meta-ngenetzky-${metangenetzky_pv}*/git/ \
        "${S}/meta-ngenetzky"
}
