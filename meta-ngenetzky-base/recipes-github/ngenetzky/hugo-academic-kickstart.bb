SUMMARY = "Static Website Powered by the Academic theme for Hugo."
PV = "2018.06.10"
PR = "r0"

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "c85c9eb9b020e31cce084b4db7a6bc46b59bfa67"
SRC_URI = "gitsm://github.com/NGenetzky/${PN}.git"
S = "${WORKDIR}/git"

B = "${S}/public"
do_build[dirs] = "${S}"
do_build(){
    hugo
}
