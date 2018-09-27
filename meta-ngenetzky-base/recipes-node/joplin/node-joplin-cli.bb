# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Note taking and to-do application with synchronization capabilities"

PV = "1.0.114"
PR = "r0"

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "7f80f67fd6db5b82c362892574a5e6162ae5f2ce"
SRC_URI = "git://github.com/laurent22/joplin.git"
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
    # TODO: Build from source
    npm install -g --production \
        "joplin@${PV}"
}
