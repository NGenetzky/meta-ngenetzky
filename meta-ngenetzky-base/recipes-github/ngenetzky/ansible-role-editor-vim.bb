SUMMARY = "ngenetzky.editor-vim"
PV = "2018.05.20"
PR = "r0"

inherit bb_fetcher
addtask do_unpack before do_build

SRCREV = "c3556e1f147ed2d6cb0b45ed13b42804fa9d8cfc"
SRC_URI = "git://github.com/NGenetzky/${PN}.git"
S = "${WORKDIR}/git"

inherit data_json
addtask do_data_json_tree after do_unpack before do_build
DATA_JSON_TREE_FILES = "\
    ./ \
"

inherit git_data
addtask do_git_data_log after do_unpack before do_build
addtask do_git_data_log_stat after do_unpack before do_build
addtask do_git_data_other after do_unpack before do_build
