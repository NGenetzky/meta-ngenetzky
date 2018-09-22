# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Docker import Yocto Poky core-image-minimal"
PV = "2.4"
PR = "r1"

DI_BUILDER = "bitbake-poky-debian-2.4-r5.0"
DI_MACHINE = "genericx86-64"
DI_IMAGE = "core-image-minimal-${DI_MACHINE}.tar.xz"

addtask do_fetch before do_build
do_fetch(){
    local builddir="${TOPDIR}/tmp/work/${DI_BUILDER}/build/"
    local imgdeploydir="${builddir}/tmp/deploy/images/${DI_MACHINE}/"
    cp -T \
        "${imgdeploydir}/${DI_IMAGE}" \
        "${WORKDIR}/${DI_IMAGE}"
}

inherit docker

console(){
    local name="${PN}"
    docker run -it \
        --rm \
        --name "${name}" \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}" \
        /bin/sh
}

inherit bb_build_shell
do_build_shell_scripts[nostamp] = "1"
addtask do_build_shell_scripts before do_build
python do_build_shell_scripts(){
    workdir = d.getVar('WORKDIR', expand=True)
    export_func_shell('console', d, os.path.join(workdir, 'console.sh'), workdir)
}

do_build[dirs] = "${S}"
do_build(){
    docker import \
        "${WORKDIR}/${DI_IMAGE}" \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}"

    docker image inspect \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}"
}
