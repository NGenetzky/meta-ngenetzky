SUMMARY = "Static Website Powered by the Academic theme for Hugo."
PV = "2018.06.10"
PR = "r2"

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "c85c9eb9b020e31cce084b4db7a6bc46b59bfa67"
SRC_URI = "gitsm://github.com/NGenetzky/${PN}.git"
S = "${WORKDIR}/git"

inherit data_json
addtask do_data_json_tree after do_unpack before do_build
DATA_JSON_TREE_FILES = "\
    ./ \
    ./content \
    ./static \
"

inherit git_data
addtask do_git_data_log after do_unpack before do_build
addtask do_git_data_log_stat after do_unpack before do_build

B = "${S}/public"
do_build[dirs] = "${S}"
do_build(){
    hugo
}
