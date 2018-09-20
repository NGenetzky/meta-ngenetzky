# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "34bfd7512c8f4c92a8a1934f2b7af9bf0a3084d4"
SRC_URI = "git://github.com/crops/poky-container.git"
S = "${WORKDIR}/git"

inherit docker

console(){
    local name="${PN}"
    docker run -it \
        --rm \
        --name "${name}" \
        --volume "${@docker_volume_path(d,'poky')}:/workspace" \
        --volume "${TOPDIR}:/mnt/topdir:ro" \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}" \
        --workdir="/workspace/"
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
    docker build \
        -t "${DOCKER_REPOSITORY}:${DOCKER_TAG}" \
        .
    docker image inspect \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}"
}
