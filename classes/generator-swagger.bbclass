
SWAGGER_URL ?= "https://raw.githubusercontent.com/OAI/OpenAPI-Specification/blob/master/examples/v2.0/yaml/petstore-expanded.yaml"
SWAGGER_DIR = "${WORKDIR}/generator-swagger/"

DEPENDS += "\
    curl-native \
    jq-native \
    unzip-native \
"

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
    local o="${SWAGGER_DIR}/out"
    local f="${WORKDIR}/python-flask-generated.zip"
    install -d "${o}"
    gen_servers "${f}"
    unzip \
        -d "${o}" \
        "${f}"
}

