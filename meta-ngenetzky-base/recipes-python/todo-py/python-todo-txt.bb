# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Python port of Gina Trapani's popular todo.txt-cli project"

PV = "0.3"
PR = "r0"

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "04d55444a3ec06ca8d2aa0a5e333cdaf27113254"
SRC_URI = "git://github.com/sigmavirus24/Todo.txt-python.git"
S = "${WORKDIR}/git"

B = "${WORKDIR}/env"

activate(){
    . '${B}/bin/activate'
}

addtask do_bootstrap after do_setup before do_build
do_bootstrap(){
    python3 -m venv "${B}"
    activate
    pip install GitPython
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
    python3 ${S}/setup.py build
    python3 ${S}/setup.py install
}
