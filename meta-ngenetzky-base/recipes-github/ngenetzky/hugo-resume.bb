SUMMARY = "Resume generated with Hugo"
PV = "2018.06.10"
PR = "r3"

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "6331d2d78abd3e921caa3c5bfa011b9944cc0a51"
SRC_URI = "gitsm://github.com/NGenetzky/${PN}.git"
S = "${WORKDIR}/git"

server[dirs] = "${S}"
server(){
    hugo serve
}

inherit bb_build_shell
do_build_shell_scripts[nostamp] = "1"
addtask do_build_shell_scripts before do_build
python do_build_shell_scripts(){
    workdir = d.getVar('WORKDIR', expand=True)
    export_func_shell('do_build', d, os.path.join(workdir, 'do_build.sh'))
    export_func_shell('server', d, os.path.join(workdir, 'server.sh'))
}

B = "${S}/public"
do_build[dirs] = "${S}"
do_build(){
    hugo
}
