# Copyright (C) 2018 Nathan Genetzky <nathan@genetzky.us>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Playing out an idea I have using swagger."
HOMEPAGE = "https://github.com/NGenetzky/ngenetzky-api"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
# SECTION = ""
DEPENDS = "\
    curl-native \
    jq-native \
    unzip-native \
"

PV = "0.0.1"
PR = "r2"
SRCREV = "20761760531afb3b9fe36c0741dd6e151623f899"

# I dislike the git fetcher's default destsuffix. I prefer to keep the repo name.
SRCNAME = "${PN}"
S = "${WORKDIR}/${SRCNAME}"
SRC_URI = "\
	git://github.com/NGenetzky/${SRCNAME}.git;destsuffix=${SRCNAME};nobranch=1 \
"

SWAGGER_URL="https://raw.githubusercontent.com/NGenetzky/${SRCNAME}/${SRCREV}/api/swagger.yaml"

# Not machine or architecture dependent.
inherit allarch


post_gen_servers() {
  local server_port="80"
  local swagger_url="${SWAGGER_URL}"
  local gen_reply=$(curl -X POST \
    "http://generator.swagger.io/api/gen/servers/python-flask" \
    -H  "accept: application/json" \
    -H  "content-type: application/json" \
    -d "{  \"spec\": {},  \"options\": {    \"serverPort\": \"${server_port}\"  },  \"swaggerUrl\": \"${swagger_url}\"}" \
    ) || return $?
  echo $gen_reply | jq --raw-output .link
}

gen_servers() {
  local filename="${1-archive.zip}"
  local url=$(post_gen_servers)
  wget --output-document "${filename}" "${url}"
}

addtask do_gen_servers_python_flask after do_prepare_recipe_sysroot before do_patch
do_gen_servers_python_flask() {
    local f="${WORKDIR}/python-flask-generated.zip"
    gen_servers "${f}"
    unzip \
        -d "${WORKDIR}" \
        "${f}"
}

