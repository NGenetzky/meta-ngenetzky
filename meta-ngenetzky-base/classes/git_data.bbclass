
GIT_DATA_S ?= "${S}"
GIT_DATA_D ?= "${WORKDIR}/data/"
GIT_DATA_FILES ?= "\
    ./ \
"
GIT_DATA_REF_A ?= "HEAD"

git_log_2_json(){
    # https://gist.githubusercontent.com/textarcana/1306223/raw/43a641db975e5f62ffe4dc44dacfe1fb157677d6/git-log2json.sh
    # Use this one-liner to produce a JSON literal from the Git log:
    git log \
        --pretty=format:'{%n  "commit": "%H",%n  "author": "%aN <%aE>",%n  "date": "%ad",%n  "message": "%f"%n},' \
        $@ | \
        perl -pe 'BEGIN{print "["}; END{print "]\n"}' | \
        perl -pe 's/},]/}]/'
}

addtask git_data_log
git_data_log[nostamp] = "1"
do_git_data_log() {
    install -d \
        "${GIT_DATA_D}"
    for f in ${GIT_DATA_FILES}; do
        (
            set -x
            cd "${S}/${f}"
            git_log_2_json \
                > "${GIT_DATA_D}/${f}.git_log.json"
        )
    done
}

git_log_stat_2_json(){
    # https://gist.githubusercontent.com/textarcana/1306223/raw/43a641db975e5f62ffe4dc44dacfe1fb157677d6/git-stat2json.sh
    # OPTIONAL: use this stand-alone shell script to produce a JSON object with
    # information similar to git --stat.
    #
    # You can then easily cross-reference or merge this with the JSON git log,
    # since both are keyed on the commit hash, which is unique.

    git log \
        --numstat \
        --format='%H' \
        $@ | \
        perl -lawne '
            if (defined $F[1]) {
                print qq#{"insertions": "$F[0]", "deletions": "$F[1]", "path": "$F[2]"},#
            } elsif (defined $F[0]) {
                print qq#],\n"$F[0]": [#
            };
            END{print qq#],#}' | \
        tail -n +2 | \
        perl -wpe 'BEGIN{print "{"}; END{print "}"}' | \
        tr '\n' ' ' | \
        perl -wpe 's#(]|}),\s*(]|})#$1$2#g' | \
        perl -wpe 's#,\s*?}$#}#'
}

addtask git_data_log_stat
git_data_log_stat[nostamp] = "1"
do_git_data_log_stat() {
    install -d \
        "${GIT_DATA_D}"
    for f in ${GIT_DATA_FILES}; do
        (
            set -x
            cd "${S}/${f}"
            git_log_stat_2_json \
                | jq . \
                > "${GIT_DATA_D}/${f}.git_log_stat.json"
        )
    done
}

git_other(){
    (
    set -x
    local ref_a='${GIT_DATA_REF_A}'
    git rev-parse "${ref_a}"
    git cherry -v "${ref_a}"
    git branch -a --contains "${ref_a}"
    git branch -a --merged "${ref_a}"
    git branch -a -v
    )
}

addtask git_data_other
git_data_other[nostamp] = "1"
do_git_data_other() {
    install -d \
        "${GIT_DATA_D}"
    for f in ${GIT_DATA_FILES}; do
        cd "${S}/${f}"
        git_other > "${DATA_JSON_TREE_D}/${f}.other.txt" 2>&1
    done
}
