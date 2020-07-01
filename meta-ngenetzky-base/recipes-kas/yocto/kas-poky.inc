# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Build using Poky with kas"
INC_PR = "r0"

B = "${WORKDIR}/build"
YOCTO_CACHE_DIR = "${TOPDIR}/var/cache/yocto-${PV}"

inherit bb_fetcher
addtask do_unpack before do_build

console(){
    KAS_REPO_REF_DIR="${YOCTO_CACHE_DIR}/downloads/"
    export KAS_REPO_REF_DIR

    kas shell "${S}/kas.yml"
}

inherit bb_build_shell
do_build_shell_scripts[nostamp] = "1"
addtask do_build_shell_scripts before do_build
python do_build_shell_scripts(){
    workdir = d.getVar('WORKDIR', expand=True)
    export_func_shell('console', d, os.path.join(workdir, 'console.sh'), workdir)
    export_func_shell('do_export_cache', d, os.path.join(workdir, 'do_export_cache.sh'), workdir)
}

do_build[dirs] = "${S} ${B}"
do_build(){
    KAS_REPO_REF_DIR="${YOCTO_CACHE_DIR}/downloads/"
    export KAS_REPO_REF_DIR

    # We must use 'kas' at least once, to ensure it is properly fetched.
    kas shell "${S}/kas.yml" -c 'bitbake-layers show-recipes > recipes.low'
}

do_export_cache[nostamp] = "1"
do_export_cache[dirs] = "\
    ${YOCTO_CACHE_DIR}/downloads \
    ${YOCTO_CACHE_DIR}/sstate-cache \
    ${B} \
"
addtask do_export_cache
do_export_cache(){
    rsync -av \
        "${B}/downloads" \
        "${YOCTO_CACHE_DIR}/downloads"
    rsync -av \
        "${B}/sstate-cache" \
        "${YOCTO_CACHE_DIR}/sstate-cache"
}
