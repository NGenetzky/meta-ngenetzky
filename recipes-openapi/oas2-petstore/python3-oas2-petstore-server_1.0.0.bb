require oas2-petstore.inc
PR = "${INC_PR}.0"

inherit generator-swagger
SWAGGER_ENDPOINT = "servers/python-flask"
S = "${WORKDIR}/python-flask-server"

edit_post_data() {
    # python-flask
    # "sortParamsByRequiredFlag": { "opt": "sortParamsByRequiredFlag", "description": "Sort method arguments to place required parameters before optional parameters.", "type": "boolean", "default": "true" },
    # "ensureUniqueParams": { "opt": "ensureUniqueParams", "description": "Whether to ensure parameter names are unique in an operation (rename parameters that are not).", "type": "boolean", "default": "true" },
    # "allowUnicodeIdentifiers": { "opt": "allowUnicodeIdentifiers", "description": "boolean, toggles whether unicode identifiers are allowed in names or not, default is false", "type": "boolean", "default": "false" },
    # "packageName": { "opt": "packageName", "description": "python package name (convention: snake_case).", "type": "string", "default": "swagger_server" },
    # "packageVersion": { "opt": "packageVersion", "description": "python package version.", "type": "string", "default": "1.0.0" },
    # "controllerPackage": { "opt": "controllerPackage", "description": "controller package", "type": "string", "default": "controllers" },
    # "defaultController": { "opt": "defaultController", "description": "default controller", "type": "string", "default": "default_controller" },
    # "supportPython2": { "opt": "supportPython2", "description": "support python2", "type": "string", "default": "false" },
    # "serverPort": { "opt": "serverPort", "description": "TCP port to listen to in app.run", "type": "string", "default": "8080" }
    jq  \
        --arg packageName "${APPNAME}_server" \
        --arg packageVersion "${PV}" \
        --arg serverPort "80" \
        '. | .options.packageName=$packageName | .options.packageVersion=$packageVersion | .options.serverPort=$serverPort '
}

inherit setuptools3

