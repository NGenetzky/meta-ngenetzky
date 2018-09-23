# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

PV = "11.3.0"
PR = "r1"

# TODO: Properly build
# inherit bb_fetcher
# addtask do_unpack before do_build
# SRCREV = "f2aaf51b739e12f9f182c6786cc445b512422c04"
# SRC_URI = "git://gitlab.com/gitlab-org/omnibus-gitlab.git;subpath=docker;protocol=https;destsuffix=${PN}-${PV};branch=11-3-stable"

inherit docker

server(){
    local name="${PN}"
    local rootdir="${@docker_volume_path(d,'gitlab')}"
    install -d \
        "${rootdir}/etc/" \
        "${rootdir}/var/log/" \
        "${rootdir}/var/opt/"

    docker run --detach \
        --hostname "gitlab.example.com" \
        --publish 10443:443 \
        --publish 10080:80 \
        --publish 10022:22 \
        --name "${name}" \
        --volume "${rootdir}/etc/:/etc/gitlab" \
        --volume "${rootdir}/var/log/:/var/log/gitlab" \
        --volume "${rootdir}/var/opt/:/var/opt/gitlab" \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}"
}

inherit bb_build_shell
do_build_shell_scripts[nostamp] = "1"
addtask do_build_shell_scripts before do_build
python do_build_shell_scripts(){
    workdir = d.getVar('WORKDIR', expand=True)
    export_func_shell('server', d, os.path.join(workdir, 'server.sh'), workdir)
}

do_build[dirs] = "${S}"
do_build(){
    local dockerhub_repository="gitlab/gitlab-ce"
    local dockerhub_tag="11.3.0-ce.0"
    docker pull \
        "${dockerhub_repository}:${dockerhub_tag}"

    docker image tag \
        "${dockerhub_repository}:${dockerhub_tag}" \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}"

    # TODO: Properly build
    # docker build \
    #     -t "${DOCKER_REPOSITORY}:${DOCKER_TAG}" \
    #     .

    docker image inspect \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}"
}
