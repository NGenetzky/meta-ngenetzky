SUMMARY = "Public Github Repositories for NGenetzky"
PV = "2018.07.27"
PR = "r1"

inherit bb_fetcher
addtask do_unpack before do_build
SRC_URI = "\
    https://api.github.com/users/ngenetzky/repos;downloadfilename=ngenetzky.github_repos.json \
    https://github.com/NGenetzky.keys \
"

