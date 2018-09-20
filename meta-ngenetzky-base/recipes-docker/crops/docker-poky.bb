# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "34bfd7512c8f4c92a8a1934f2b7af9bf0a3084d4"
SRC_URI = "git://github.com/crops/poky-container.git"
S = "${WORKDIR}/git"

DOCKER_REPOSITORY = "meta-ngenetzky/${PN}"
DOCKER_TAG = "${PV}-${PR}"

console(){
    local name="${PN}"
    docker run -it \
        --rm \
        --name "${name}" \
        --volume "${TOPDIR}:/workspace/" \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}" \
        --workdir="/workspace/"
}

def export_func_shell(func, d, runfile, cwd=None):
    """Execute a shell function from the metadata

    Note on directory behavior.  The 'dirs' varflag should contain a list
    of the directories you need created prior to execution.  The last
    item in the list is where we will chdir/cd to.
    """
    with open(runfile, 'w') as script:
        bb.data.emit_func(func, script, d)

        if bb.msg.loggerVerboseLogs:
            script.write("set -x\n")
        if cwd:
            script.write("cd '%s'\n" % cwd)
        script.write("%s\n" % func)

do_generate_scripts[nostamp] = "1"
addtask do_generate_scripts before do_build
python do_generate_scripts(){
    workdir = d.getVar('WORKDIR', expand=True)
    export_func_shell('console', d, os.path.join(workdir, 'console.sh'), workdir)
}

do_build[dirs] = "${S}"
do_build(){
    docker build \
        -t "${DOCKER_REPOSITORY}:${DOCKER_TAG}" \
        .
    docker image inspect \
        "${DOCKER_REPOSITORY}:${DOCKER_TAG}"
}
