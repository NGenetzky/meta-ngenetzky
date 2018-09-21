# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Build using OpenEmbedded"
PV = "2.4"
PR = "r1"

B = "${WORKDIR}/build"

console(){
    . "${S}/openembedded-core/oe-init-build-env" \
        "${B}" \
        "${S}/bitbake"
}

inherit bb_build_shell
do_build_shell_scripts[nostamp] = "1"
addtask do_build_shell_scripts before do_build
python do_build_shell_scripts(){
    workdir = d.getVar('WORKDIR', expand=True)
    # srcdir = d.getVar('S', expand=True)
    export_func_shell('console', d, os.path.join(workdir, 'console.sh'), workdir)
}

do_build[depends] = "\
    bitbake:do_unpack \
    openembedded-core:do_unpack \
    meta-openembedded:do_unpack \
"
do_build[dirs] = "${S} ${B}"
do_build(){
    local bitbake_pv='1.38'
    local oecore_pv='2.4'

    ln -sfT \
        ${TOPDIR}/tmp/work/bitbake-${bitbake_pv}*/git/ \
        "${S}/bitbake"
    ln -sfT \
        ${TOPDIR}/tmp/work/openembedded-core-${oecore_pv}*/git/ \
        "${S}/openembedded-core"
}
