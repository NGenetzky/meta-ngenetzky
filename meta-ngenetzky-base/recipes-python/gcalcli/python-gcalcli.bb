# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Python port of Gina Trapani's popular todo.txt-cli project"

PV = "3.4.0"
PR = "r0"

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "250b524235841a58a2afc8a31c4a080310905016"
SRC_URI = "git://github.com/insanum/gcalcli.git"
S = "${WORKDIR}/git"

B = "${WORKDIR}/env"

PYTHON = "python2"

activate(){
    . '${B}/bin/activate'
}

addtask do_bootstrap after do_setup before do_build
do_bootstrap(){
    "${PYTHON}" -m virtualenv "${B}"
    activate
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
    "${PYTHON}" ${S}/setup.py build
    "${PYTHON}" ${S}/setup.py install
}
