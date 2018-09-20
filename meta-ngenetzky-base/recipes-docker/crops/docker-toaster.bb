# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "17c4585d1a2e3874ea55393a676f6043559b949a"
SRC_URI = "git://github.com/crops/toaster-container.git"
S = "${WORKDIR}/git"

DOCKER_REPOSITORY = "meta-ngenetzky/${PN}"
DOCKER_TAG = "${PV}-${PR}"

server(){
    local name="${PN}"
    docker run \
        --rm -it \
        --name "${name}" \
        --volume "${TOPDIR}:/workdir" \
        --publish "0.0.0.0:18000:8000" \
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
    local dockerhub_repository="crops/toaster-morty"
    local dockerhub_tag="latest-201712112204-a376588"
    docker pull \
        "${dockerhub_repository}:${dockerhub_tag}"
    docker image tag \
        "${dockerhub_repository}:${dockerhub_tag}" \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}"

    # TODO: Properly build
    # local branch="05711ba18587aaaf4a9c465a1dd4537f27ceda93"
    # local gitrepo="git://git.yoctoproject.org/poky.git"
    # docker build \
    #     --build-arg BRANCH=pyro \
    #     --build-arg GITREPO=${gitrepo} \
    #     -t "${DOCKER_REPOSITORY}:${DOCKER_TAG}" \
    #     .

    docker image inspect \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}"
}
