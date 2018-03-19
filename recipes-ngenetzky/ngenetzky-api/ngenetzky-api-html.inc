inherit generator-swagger

DESCRIPTION = "Playing out an idea I have using swagger."
HOMEPAGE = "https://github.com/NGenetzky/ngenetzky-api"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

INC_PR = "r1"

SRCNAME = "ngenetzky-api"
SRC_URI = "git://github.com/NGenetzky/${SRCNAME}.git;destsuffix=${SRCNAME}"
SWAGGER_URL = "https://raw.githubusercontent.com/NGenetzky/ngenetzky-api/${SRCREV}/api/swagger.yaml"

SWAGGER_ENDPOINT = "clients/html"
S = "${WORKDIR}/html-client"

edit_post_data() {
    # html
    # "sortParamsByRequiredFlag": { "opt": "sortParamsByRequiredFlag", "description": "Sort method arguments to place required parameters before optional parameters.", "type": "boolean", "default": "true" },
    # "ensureUniqueParams": { "opt": "ensureUniqueParams", "description": "Whether to ensure parameter names are unique in an operation (rename parameters that are not).", "type": "boolean", "default": "true" },
    # "allowUnicodeIdentifiers": { "opt": "allowUnicodeIdentifiers", "description": "boolean, toggles whether unicode identifiers are allowed in names or not, default is false", "type": "boolean", "default": "false" },
    # "appName": { "opt": "appName", "description": "short name of the application", "type": "string" },
    # "appDescription": { "opt": "appDescription", "description": "description of the application", "type": "string" },
    # "infoUrl": { "opt": "infoUrl", "description": "a URL where users can get more information about the application", "type": "string" },
    # "infoEmail": { "opt": "infoEmail", "description": "an email address to contact for inquiries about the application", "type": "string" },
    # "licenseInfo": { "opt": "licenseInfo", "description": "a short description of the license", "type": "string" },
    # "licenseUrl": { "opt": "licenseUrl", "description": "a URL pointing to the full license", "type": "string" },
    # "invokerPackage": { "opt": "invokerPackage", "description": "root package for generated code", "type": "string" },
    # "groupId": { "opt": "groupId", "description": "groupId in generated pom.xml", "type": "string" },
    # "artifactId": { "opt": "artifactId", "description": "artifactId in generated pom.xml", "type": "string" },
    # "artifactVersion": { "opt": "artifactVersion", "description": "artifact version in generated pom.xml", "type": "string" }
    jq  \
        --arg appName "ngenetzky_api" \
        --arg appDescription "${DESCRIPTION}" \
        --arg infoUrl "${HOMEPAGE}" \
        '. | .options.appName=$appName | .options.appDescription=$appDescription | .options.infoUrl=$infoUrl '
}

