# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Build using OpenEmbedded"
PV = "2.4"
INC_PR = "r2"

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
    . "${S}/openembedded-core/oe-init-build-env" \
        "${B}" \
        "${S}/bitbake"
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
    meta-openembedded:do_unpack \
    openembedded-core:do_unpack \
"
do_build[dirs] = "${S} ${B}"
do_build(){
    local bitbake_pv='1.38'
    local metaoe_pv='2.4'
    local oecore_pv='2.4'

    ln -sfT \
        ${TOPDIR}/tmp/work/bitbake-${bitbake_pv}*/git/ \
        "${S}/bitbake"
    ln -sfT \
        ${TOPDIR}/tmp/work/meta-openembedded-${metaoe_pv}*/git/ \
        "${S}/meta-openembedded"
    ln -sfT \
        ${TOPDIR}/tmp/work/openembedded-core-${oecore_pv}*/git/ \
        "${S}/openembedded-core"
}
