# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Build using Poky"
INC_PR = "r5"

B = "${WORKDIR}/build"
YOCTO_CACHE_DIR = "${TOPDIR}/var/cache/yocto-${PV}"

inherit bb_fetcher
addtask do_unpack before do_setup_conf
SRC_URI = "\
    file://bblayers.conf \
    file://local.conf \
    file://site.conf \
"

inherit bitbake_conf
addtask do_bitbake_conf_template after do_unpack before do_build
python do_bitbake_conf_template_append(){
    with open(os.path.join(templateconf, 'site.conf'), 'r') as conf:
        site = conf.read()

    for var in [ 'S', 'B', 'YOCTO_CACHE_DIR']:
        site = site.replace('{{ '+var+' }}', d.getVar(var))

    with open(os.path.join(buildconf, 'site.conf'), 'w') as conf:
        conf.write(site)
}

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
    export_func_shell('do_export_cache', d, os.path.join(workdir, 'do_export_cache.sh'), workdir)
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

do_export_cache[nostamp] = "1"
do_export_cache[dirs] = "\
    ${YOCTO_CACHE_DIR}/downloads \
    ${YOCTO_CACHE_DIR}/sstate-cache \
    ${B} \
"
addtask do_export_cache
do_export_cache(){
    cp -T -R \
        "${B}/downloads" \
        "${YOCTO_CACHE_DIR}/downloads"
    cp -T -R \
        "${B}/sstate-cache" \
        "${YOCTO_CACHE_DIR}/sstate-cache"
}
