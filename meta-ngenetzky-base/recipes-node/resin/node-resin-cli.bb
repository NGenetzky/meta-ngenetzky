# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "The official resin.io CLI tool."

PV = "7.10.5"
PR = "r0"

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "bd59f95e1aa1c93df5550c0152e36942d8990052"
SRC_URI = "git://github.com/resin-io/resin-cli.git"
S = "${WORKDIR}/git"

B = "${WORKDIR}/env"

activate(){
    . '${B}/bin/activate'
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
}

do_build[dirs] = "${B} ${S}"
do_build(){
    activate
    npm install -g --production \
        "resin-cli@${PV}"
}
