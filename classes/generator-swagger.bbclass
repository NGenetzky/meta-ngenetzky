
SWAGGER_URL ?= "https://raw.githubusercontent.com/OAI/OpenAPI-Specification/blob/master/examples/v2.0/yaml/petstore-expanded.yaml"
SWAGGER_DIR = "${WORKDIR}/generator-swagger/"

DEPENDS += "\
    curl-native \
    jq-native \
    unzip-native \
"

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

post_gen_servers() {
    # local server_port="80"
    local options="$(cat ${SWAGGER_DIR}/in/post-data.json)"
    local gen_reply=$(curl -X POST \
    "http://generator.swagger.io/api/gen/servers/python-flask" \
    -H  "accept: application/json" \
    -H  "content-type: application/json" \
    -d "${options}" \
    ) || return $?
    # TODO: Error Checking
    # gen_reply={"code":1,"type":"error","message":"The swagger specification supplied was not valid"}
    echo $gen_reply | jq --raw-output .link
}

gen_servers() {
  local filename="${1-archive.zip}"
  local url=$(post_gen_servers)
  wget --output-document "${filename}" "${url}"
}

addtask do_generator_input after do_prepare_recipe_sysroot before do_patch
do_generator_input() {
    local i="${SWAGGER_DIR}/in"
    install -d "${i}"
    wget \
        --output-document "${i}/swagger.yaml" \
        "${SWAGGER_URL}"
    get_post_data | edit_post_data > "${i}/post-data.json"
}

addtask do_generator_output after do_generator_input before do_patch
do_generator_output() {
    set -ex
    local o="${SWAGGER_DIR}/out"
    local f="${WORKDIR}/python-flask-generated.zip"
    install -d "${o}"
    gen_servers "${f}"
    unzip \
        -d "${o}" \
        "${f}"
}

