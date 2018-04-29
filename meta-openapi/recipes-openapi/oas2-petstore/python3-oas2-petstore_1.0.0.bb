require oas2-petstore.inc
PR = "${INC_PR}.0"

inherit generator-swagger
SWAGGER_ENDPOINT = "clients/python-flask"
S = "${WORKDIR}/python-flask-client"

edit_post_data() {
    # python
    # "packageName": { "opt": "packageName", "description": "python package name (convention: snake_case).", "type": "string", "default": "swagger_client" },
    # "projectName": { "opt": "projectName", "description": "python project name in setup.py (e.g. petstore-api).", "type": "string" },
    # "packageVersion": { "opt": "packageVersion", "description": "python package version.", "type": "string", "default": "1.0.0" },
    # "packageUrl": { "opt": "packageUrl", "description": "python package URL.", "type": "string" },
    # "sortParamsByRequiredFlag": { "opt": "sortParamsByRequiredFlag", "description": "Sort method arguments to place required parameters before optional parameters.", "type": "boolean", "default": "true" },
    # "hideGenerationTimestamp": { "opt": "hideGenerationTimestamp", "description": "hides the timestamp when files were generated", "type": "string", "default": "true" },
    # "library": { "opt": "library", "description": "library template (sub-template) to use", "type": "string", "default": "urllib3" }
    jq  \
        --arg packageName "${APPNAME}" \
        --arg projectName "${APPNAME}" \
        --arg packageVersion "${PV}" \
        '. | .options.packageName=$packageName | .options.projectName=$projectName | .options.packageVersion=$packageVersion'
}

inherit setuptools3

