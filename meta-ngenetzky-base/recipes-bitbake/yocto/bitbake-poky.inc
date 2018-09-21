# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Build using Poky"
INC_PR = "r1"

B = "${WORKDIR}/build"

console(){
    . "${S}/poky/oe-init-build-env" \
        "${B}" \
        "${S}/poky/bitbake"
}

inherit bb_build_shell
do_build_shell_scripts[nostamp] = "1"
addtask do_build_shell_scripts before do_build
python do_build_shell_scripts(){
    workdir = d.getVar('WORKDIR', expand=True)
    export_func_shell('console', d, os.path.join(workdir, 'console.sh'), workdir)
}

do_build[depends] = "\
    poky:do_unpack \
"
do_build[dirs] = "${S} ${B}"
do_build(){
    local poky_pv='${PV}'

    ln -sfT \
        ${TOPDIR}/tmp/work/poky-${poky_pv}*/git/ \
        "${S}/poky"
}
