# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Build using meta-ngenetzky"
PV = "2018.09.20"
PR = "r2"

B = "${WORKDIR}/build"

inherit bb_fetcher
addtask do_unpack before do_setup_conf
SRC_URI = "\
    file://local.conf \
    file://bblayers.conf \
"

inherit bitbake_conf
addtask do_setup_conf before do_build

bitbake_set_path(){
    local p="$(readlink -f ${1?})"
    [[ -d ${p} ]] || return 1
    export PATH="${p}/bin:$PATH"
    export PYTHONPATH="${p}/lib:$PYTHONPATH"
}

console(){
    bitbake_set_path "${S}/bitbake"
    export BBPATH="${B}"
    cd "${B}"
}

inherit bb_build_shell
do_build_shell_scripts[nostamp] = "1"
addtask do_build_shell_scripts before do_build
python do_build_shell_scripts(){
    workdir = d.getVar('WORKDIR', expand=True)
    export_func_shell('console', d, os.path.join(workdir, 'console.sh'), workdir)
}

do_build[depends] = "\
    bitbake:do_unpack \
    meta-ngenetzky:do_unpack \
"
do_build[dirs] = "${S} ${B}"
do_build(){
    local metangenetzky_pv='2018.09.20'
    local bitbake_pv='1.38'

    ln -sfT \
        ${TOPDIR}/tmp/work/bitbake-${bitbake_pv}*/git/ \
        "${S}/bitbake"
    ln -sfT \
        ${TOPDIR}/tmp/work/meta-ngenetzky-${metangenetzky_pv}*/git/ \
        "${S}/meta-ngenetzky"
}
