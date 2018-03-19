
SWAGGER_URL ?= "https://raw.githubusercontent.com/OAI/OpenAPI-Specification/blob/master/examples/v2.0/yaml/petstore-expanded.yaml"
SWAGGER_OUT ?= "${WORKDIR}"
SWAGGER_ENDPOINT ?= "servers/python-flask/"
SWAGGER_DIR = "${WORKDIR}/generator-swagger/"

DEPENDS += "\
    curl-native \
    jq-native \
    unzip-native \
"

# TODO: Using jq is ugly, use python.
get_post_data() {
  jq --null-input \
     --arg swagger_url "${SWAGGER_URL}" \
     '. | .spec={} | .options={} | .swaggerUrl=$swagger_url'
}

edit_post_data() {
  jq  \
     --arg serverPort "80" \
     '. | .options.serverPort=$serverPort'
}

generate_post() {
    local data="$(cat ${SWAGGER_DIR}/in/post-data.json)"
    local gen_reply=$(curl -X POST \
    "http://generator.swagger.io/api/gen/${SWAGGER_ENDPOINT}" \
    -H  "accept: application/json" \
    -H  "content-type: application/json" \
    -d "${data}" \
    ) || return $?
    # TODO: Error Checking
    # gen_reply={"code":1,"type":"error","message":"The swagger specification supplied was not valid"}
    echo $gen_reply | jq --raw-output .link
}

generate() {
  local filename="${1-archive.zip}"
  local url=$(generate_post)
  wget --output-document "${filename}" "${url}"
}

do_generator_input[dirs] = "${SWAGGER_DIR}/in"
addtask do_generator_input after do_prepare_recipe_sysroot before do_patch
do_generator_input() {
    local i="${SWAGGER_DIR}/in"
    install -d "${i}"
    wget \
        --output-document "${i}/swagger.yaml" \
        "${SWAGGER_URL}"
    get_post_data | edit_post_data > "${i}/post-data.json"
}


do_generator_output[dirs] = "${SWAGGER_DIR}/out"
addtask do_generator_output after do_generator_input before do_patch
do_generator_output() {
    set -ex
    local o="${SWAGGER_DIR}/out"
    local f="${SWAGGER_DIR}/generated.zip"
    install -d "${o}"
    generate "${f}"
    unzip \
        -d "${o}" \
        "${f}"
}

SSTATETASKS += "do_generator_output"
do_generator_output[sstate-inputdirs] = "${SWAGGER_DIR}/out"
do_generator_output[sstate-outputdirs] = "${SWAGGER_OUT}"
addtask do_generator_output_setscene
python do_generator_output_setscene () {
    sstate_setscene(d)
}

