# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)
# Based on https://denibertovic.com/posts/handling-permissions-with-docker-volumes/

SUMMARY = "Dockerbake awesome IDE from debian"
PR = "r1"

inherit bb_fetcher
addtask do_unpack before do_build

SRC_URI = "\
    file://Dockerfile;subdir=${PN}-${PV} \
"

inherit docker
require debian-gosu.inc
require debian-c9.inc

entrypoint(){
    # This is a little trick since bitbake doesn't call "entrypoint $@"
    local argv=${BASH_ARGV[@]}

    # Add local user
    # Either use the LOCAL_USER_ID if passed in at runtime or
    # fallback

    USER_ID=${LOCAL_USER_ID:-9001}

    useradd --shell /bin/bash -u $USER_ID -o -c "" -m user
    export HOME=/home/user

    exec /usr/local/bin/gosu user $argv
}

init(){
    source /opt/nvm/nvm.sh
    cd ~ && mkdir workspace
    node /opt/c9sdk/server.js -p 3000 -a : -w '/home/user/workspace/' --listen 0.0.0.0
}

DOCKERBAKE_FUNCTIONS = "\
    build_gosu \
    build_c9 \
    entrypoint \
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
    export_func_shell('console', d, os.path.join(workdir, 'console.sh'))
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
        --volume "${TOPDIR}:/home/user/topdir" \
        --publish "0.0.0.0:3000:3000" \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}" \
        "/bin/bash /tmp/init.sh"
}

console(){
    local name="${PN}"
    docker run -it --rm \
        --name "${name}" \
        --volume "${TOPDIR}:/home/user/topdir/" \
        -e LOCAL_USER_ID="$(id -u)" \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}" \
        "/bin/bash"
}
