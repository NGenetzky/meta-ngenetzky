# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Docker import Yocto Poky core-image-minimal"
PV = "2.4"
PR = "r1"

addtask do_fetch before do_build
do_fetch(){
    local builddir="${TOPDIR}/tmp/work/bitbake-poky-2.4-r0/build/"
    cp -T \
        "${builddir}/tmp/deploy/images/qemux86/core-image-minimal-qemux86.tar.bz2" \
        "${WORKDIR}/core-image-minimal.tar.bz2"
    # local builddir="${TOPDIR}/tmp/work/bitbake-poky-docker-from-scratch-2.4-r2.1/build/"
    # cp -T \
    #     "${builddir}/tmp/deploy/images/genericx86-64/core-image-minimal-genericx86-64.tar.xz" \
    #     "${WORKDIR}/core-image-minimal.tar.xz"
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
        "${WORKDIR}/core-image-minimal.tar.bz2" \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}"

    # docker import \
    #     "${WORKDIR}/core-image-minimal.tar.xz" \
    #     "${DOCKER_REPOSITORY}:${DOCKER_TAG}"

    docker image inspect \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}"
}
