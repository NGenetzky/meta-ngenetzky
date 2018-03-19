require python-jatdb-server.inc
PV = "0.0.1-git${SRCPV}"
PR = "${INC_PR}.0"
SRCREV = "2445960faf25df6c078573e303bdc4f56a5803a4"

inherit setuptools3

edit_post_data() {
  jq  \
     --arg packageName "jatdb_server" \
     --arg packageVersion "0.0.1" \
     --arg serverPort "80" \
     '. | .options.packageName=$packageName | .options.packageVersion=$packageVersion | .options.serverPort=$serverPort '
}

