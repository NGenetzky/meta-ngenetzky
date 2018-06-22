
DATA_JSON_TREE_S ?= "${S}"
DATA_JSON_TREE_D ?= "${WORKDIR}/data/"
DATA_JSON_TREE_FILES ?= "\
    ./ \
"

addtask showdata
do_data_json_tree[nostamp] = "1"
do_data_json_tree() {
    install -d \
        "${DATA_JSON_TREE_D}"
    (
        set -x
        for f in ${DATA_JSON_TREE_FILES}; do
            tree -J "${DATA_JSON_TREE_S}/${f}" \
                > "${DATA_JSON_TREE_D}/${f}.tree.json"
        done
    )
}
