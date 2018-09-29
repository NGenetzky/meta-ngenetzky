# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)
# Based on https://github.com/ankitrgadiya/docker-c9

SUMMARY = "Dockerbake docker-c9 from debian"

inherit bb_fetcher
addtask do_unpack before do_build

SRC_URI = "\
    file://Dockerfile;subdir=${PN}-${PV} \
"

inherit docker

build_0(){
    # Installation
    apt-get update \
      && apt-get install -y \
        python \
        build-essential \
        libssl-dev \
        apache2-utils \
        libxml2-dev \
        git \
        curl \
        locales \
        tmux \
      && sed -i -e 's/# en_US.UTF-8 UTF-8/en_US.UTF-8 UTF-8/' /etc/locale.gen \
      && locale-gen \
      && git clone https://github.com/creationix/nvm.git ~/.nvm \
      && bash -c "source ~/.nvm/nvm.sh && nvm install 0.10;" \
      && git clone https://github.com/c9/core.git ~/c9sdk \
      && rm -rf ~/c9sdk/.git \
      && cd ~/c9sdk \
      && ./scripts/install-sdk.sh \
      && apt-get remove build-essential -y \
      && apt-get autoremove -y
}

init(){
    source ~/.nvm/nvm.sh
    cd ~ && mkdir workspace
    cd ~/c9sdk
    node server.js -p 3000 -a : -w '/root/workspace/' --listen 0.0.0.0
}

DOCKERBAKE_FUNCTIONS = "\
    build_0 \
    init \
"

inherit bb_build_shell
do_build_shell_scripts[nostamp] = "1"
do_build_shell_scripts[dirs] = "${B}"
addtask do_build_shell_scripts before do_build
python do_build_shell_scripts(){
    keys_for_export = (key for key in d.keys() if not key.startswith("__") and not d.getVarFlag(key, "func", False))
    for key in keys_for_export:
        d.setVarFlag(key,'export', False)

    workdir = d.getVar('WORKDIR', expand=True)
    builddir = d.getVar('B', expand=True)

    for func in d.getVar('DOCKERBAKE_FUNCTIONS','').split():
        fpath = os.path.join(builddir, func + '.sh')
        export_func_shell(func, d, fpath)
        os.chmod(fpath, 0o775)

    export_func_shell('do_server', d, os.path.join(workdir, 'do_server.sh'))
}

do_build[dirs] = "${B}"
do_build(){
    docker build \
        -t "${DOCKER_REPOSITORY}:${DOCKER_TAG}" \
        .
    docker image inspect \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}"
}

server[stamp] = "0"
addtask do_server after do_build
do_server(){
    local name="${PN}"
    docker run -d --rm \
        --name "${name}" \
        --volume "${TOPDIR}:/root/workspace" \
        --publish "0.0.0.0:3000:3000" \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}"
}
