# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Python script to backup everything from Trello"

# Last release:
# PV = "0.2.1"
# SRCREV = "89ca5518e3717674818a71d9c6d72abce06c3546"

PV = "2018.12.09"
PR = "r0"

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "7248afd9154321617b857d49a61f3201a7668a01"
SRC_URI = "git://github.com/jtpio/trello-full-backup.git"
S = "${WORKDIR}/git"

B = "${WORKDIR}/env"

activate(){
    . '${B}/bin/activate'
}

console(){
    activate
    # https://github.com/jtpio/trello-full-backup#usage
    # To get the API key:
    #     https://trello.com/app-key
    # To get the token:
    #     https://trello.com/1/authorize?scope=read&expiration=never&name=backup&key=REPLACE_WITH_YOUR_API_KEY&response_type=token
    export TRELLO_API_KEY=""
    export TRELLO_TOKEN=""
}

addtask do_bootstrap after do_setup before do_build
do_bootstrap(){
    python3 -m venv "${B}"
    activate
}

inherit bb_build_shell
do_build_shell_scripts[nostamp] = "1"
addtask do_build_shell_scripts before do_build
python do_build_shell_scripts(){
    workdir = d.getVar('WORKDIR', expand=True)
    export_func_shell('activate', d, os.path.join(workdir, 'activate.sh'), workdir)
    export_func_shell('console', d, os.path.join(workdir, 'console.sh'), workdir)
}

do_build[dirs] = "${B} ${S}"
do_build(){
    activate
    python3 ${S}/setup.py build
    python3 ${S}/setup.py install
}
