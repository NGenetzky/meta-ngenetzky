# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Cloud9 Core - Part of the Cloud9 SDK for Plugin Development"

PV = "2018.08.26"
PR = "r0"

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "e9490da4e721915df42d1c468ea7c98f879fbb11"
SRC_URI = "git://github.com/c9/core.git"
S = "${WORKDIR}/git"

B = "${WORKDIR}/env"
export HOME="${WORKDIR}/home"

activate(){
    . '${B}/bin/activate'
}

server[dirs] = "${HOME}"
server(){
    activate
    node "${S}/server.js" -w "${HOME}"
}

addtask do_bootstrap after do_setup before do_build
do_bootstrap(){
    virtualenv "${B}"
    activate
    pip install nodeenv
    nodeenv -p
}

inherit bb_build_shell
do_build_shell_scripts[nostamp] = "1"
addtask do_build_shell_scripts before do_build
python do_build_shell_scripts(){
    workdir = d.getVar('WORKDIR', expand=True)
    export_func_shell('activate', d, os.path.join(workdir, 'activate.sh'), workdir)
    export_func_shell('server', d, os.path.join(workdir, 'server.sh'), workdir)
}

do_build[dirs] = "${HOME} ${B} ${S}"
do_build(){
    activate
    # Installs into ${HOME}
    bash -xe "${S}/scripts/install-sdk.sh"
}
