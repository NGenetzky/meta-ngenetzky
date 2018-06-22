SUMMARY = "Personal Bitbake Layer"
PV = "2018.07.20"
PR = "r2"

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "03b54900f35917e9b70a3f48c2a0ae2069f3d47e"
SRC_URI = "git://github.com/NGenetzky/${PN}.git"
S = "${WORKDIR}/git"

inherit data_json
addtask do_data_json_tree after do_unpack before do_build
DATA_JSON_TREE_FILES = "\
    ./ \
    ./meta-openapi \
    ./static \
    ./classes \
"

inherit git_data
addtask do_git_data_log after do_unpack before do_build
addtask do_git_data_log_stat after do_unpack before do_build
