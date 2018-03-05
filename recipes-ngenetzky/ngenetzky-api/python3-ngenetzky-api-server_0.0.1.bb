require python3-ngenetzky-api-server.inc
PV = "0.0.1-git${SRCPV}"
SRC_URI = "git://github.com/NGenetzky/ngenetzky-api.git;branch=gen/0.0.1;subpath=gen/servers/${SRCNAME}"
SRCREV = "cfa7308cfbe8d6381dc94a33a2d41bbd4f15cbea"

inherit setuptools3

