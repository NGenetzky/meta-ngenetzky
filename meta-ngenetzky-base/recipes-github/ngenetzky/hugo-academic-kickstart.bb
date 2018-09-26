SUMMARY = "Static Website Powered by the Academic theme for Hugo."
PV = "2018.06.10"
PR = "r3"

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "c85c9eb9b020e31cce084b4db7a6bc46b59bfa67"
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
