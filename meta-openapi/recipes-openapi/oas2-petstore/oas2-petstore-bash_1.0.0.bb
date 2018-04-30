require oas2-petstore.inc
PR = "${INC_PR}.0"

inherit allarch

inherit generator-swagger
SWAGGER_ENDPOINT = "clients/bash"
S = "${WORKDIR}/bash-client"

edit_post_data() {
    # bash
    # "sortParamsByRequiredFlag": { "opt": "sortParamsByRequiredFlag", "description": "Sort method arguments to place required parameters before optional parameters.", "type": "boolean", "default": "true" },
    # "ensureUniqueParams": { "opt": "ensureUniqueParams", "description": "Whether to ensure parameter names are unique in an operation (rename parameters that are not).", "type": "boolean", "default": "true" },
    # "allowUnicodeIdentifiers": { "opt": "allowUnicodeIdentifiers", "description": "boolean, toggles whether unicode identifiers are allowed in names or not, default is false", "type": "boolean", "default": "false" },
    # "curlOptions": { "opt": "curlOptions", "description": "Default cURL options", "type": "string" },
    # "processMarkdown": { "opt": "processMarkdown", "description": "Convert all Markdown Markup into terminal formatting", "type": "boolean", "default": "false" },
    # "scriptName": { "opt": "scriptName", "description": "The name of the script that will be generated (e.g. petstore-cli)", "type": "string" },
    # "generateBashCompletion": { "opt": "generateBashCompletion", "description": "Whether to generate the Bash completion script", "type": "boolean", "default": "false" },
    # "generateZshCompletion": { "opt": "generateZshCompletion", "description": "Whether to generate the Zsh completion script", "type": "boolean", "default": "false" },
    # "hostEnvironmentVariable": { "opt": "hostEnvironmentVariable", "description": "Name of environment variable where host can be defined (e.g. PETSTORE_HOST='http://petstore.swagger.io:8080')", "type": "string" },
    # "basicAuthEnvironmentVariable": { "opt": "basicAuthEnvironmentVariable", "description": "Name of environment variable where username and password can be defined (e.g. PETSTORE_CREDS='username:password')", "type": "string" },
    # "apiKeyAuthEnvironmentVariable": { "opt": "apiKeyAuthEnvironmentVariable", "description": "Name of environment variable where API key can be defined (e.g. PETSTORE_APIKEY='kjhasdGASDa5asdASD')", "type": "boolean", "default": "false" }
    jq  \
        --arg scriptName "${APPNAME}" \
        --arg generateBashCompletion "${DESCRIPTION}" \
        '. | .options.scriptName=$scriptName | .options.generateBashCompletion=$generateBashCompletion'
}

do_install() {
    install -d "${D}/${bindir}/"
    install -m 664 --target-directory "${D}/${bindir}/" \
        "${S}/${APPNAME}"
}

PACKAGES += "${PN}-bash"
FILES_${PN}-bash = "\
    ${datadir}/bash/site-functions/ \
    ${sysconfdir}/bash-completion.d/ \
"
do_install_append() {
    install -d "${D}/${datadir}/bash/site-functions/"
    install -m 664 --target-directory "${D}/${datadir}/bash/site-functions/" \
        "${S}/_${APPNAME}"
    install -d "${D}/${sysconfdir}/bash-completion.d/"
    install -m 664 --no-target-directory \
        "${S}/${APPNAME}.bash-completion" \
        "${D}/${sysconfdir}/bash-completion.d/${APPNAME}"
}

