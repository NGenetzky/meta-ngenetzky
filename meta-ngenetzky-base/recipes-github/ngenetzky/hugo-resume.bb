SUMMARY = "Resume generated with Hugo"
PV = "2018.06.10"
PR = "r1"

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "6331d2d78abd3e921caa3c5bfa011b9944cc0a51"
SRC_URI = "gitsm://github.com/NGenetzky/${PN}.git"
S = "${WORKDIR}/git"

inherit data_json
addtask do_data_json_tree after do_unpack before do_build
DATA_JSON_TREE_FILES = "\
    ./ \
    ./content \
    ./static \
"

B = "${S}/public"
do_build[dirs] = "${S}"
do_build(){
    hugo
}
