require oas2-petstore.inc
PR = "${INC_PR}.1"

inherit allarch

inherit generator-swagger
SWAGGER_ENDPOINT = "clients/html2"
S = "${WORKDIR}/html2-client"

edit_post_data() {
    # html2
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
        --arg appName "${APPNAME}" \
        --arg appDescription "${DESCRIPTION}" \
        --arg infoUrl "${HOMEPAGE}" \
        '. | .options.appName=$appName | .options.appDescription=$appDescription | .options.infoUrl=$infoUrl '
}

PACKAGES = "${PN}"
FILES_${PN} = "${docdir}/${APPNAME}"
do_install() {
    local d="${D}/${docdir}/${APPNAME}"
    install -d "${d}"
    install -m 664 --target-directory "${d}" \
        "${S}/index.html"
}

