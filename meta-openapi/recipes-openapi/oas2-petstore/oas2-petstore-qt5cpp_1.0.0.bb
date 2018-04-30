require oas2-petstore.inc
PR = "${INC_PR}.0"

inherit generator-swagger
SWAGGER_ENDPOINT = "clients/qt5cpp"
S = "${WORKDIR}/qt5cpp-client"

edit_post_data() {
    # qt5cpp
    # "sortParamsByRequiredFlag": { "opt": "sortParamsByRequiredFlag", "description": "Sort method arguments to place required parameters before optional parameters.", "type": "boolean", "default": "true" },
    # "ensureUniqueParams": { "opt": "ensureUniqueParams", "description": "Whether to ensure parameter names are unique in an operation (rename parameters that are not).", "type": "boolean", "default": "true" },
    # "allowUnicodeIdentifiers": { "opt": "allowUnicodeIdentifiers", "description": "boolean, toggles whether unicode identifiers are allowed in names or not, default is false", "type": "boolean", "default": "false" },
    # "cppNamespace": { "opt": "cppNamespace", "description": "C++ namespace (convention: name::space::for::api).", "type": "string", "default": "Swagger" }
    jq  \
        --arg cppNamespace "${APPNAME}" \
        '. | .options.cppNamespace=$cppNamespace '
}

