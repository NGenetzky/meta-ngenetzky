DESCRIPTION = "This is a simple Petstore server that utilizes OAS-2.0."
HOMEPAGE = "http://petstore.swagger.io/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PV = "1.0.0"
SRCREV = "6195da3edbe4ea8b091cb7895248eb5af9793726"
INC_PR = "r1"

# generator-swagger
SWAGGER_URL = "https://raw.githubusercontent.com/OAI/OpenAPI-Specification/${SRCREV}/examples/v2.0/yaml/petstore.yaml"

# Custom Variables
APPNAME = "swagger_petstore"

