require python3-ngenetzky-api-server.inc
PV = "0.0.1-git${SRCPV}"
PR = "${INC_PR}.0"
SRC_URI = "git://github.com/NGenetzky/${SRCNAME}.git;branch=gen/0.0.1;destsuffix=${SRCNAME}"
SRCREV = "cfa7308cfbe8d6381dc94a33a2d41bbd4f15cbea"

inherit setuptools3

edit_post_data() {
  jq  \
     --arg packageName "ngenetzky_api" \
     --arg packageVersion "0.0.1" \
     --arg serverPort "80" \
     '. | .options.packageName=$packageName | .options.packageVersion=$packageVersion | .options.serverPort=$serverPort '
}

