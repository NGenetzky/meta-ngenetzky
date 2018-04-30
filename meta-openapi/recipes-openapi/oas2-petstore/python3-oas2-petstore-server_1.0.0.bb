require oas2-petstore.inc
PR = "${INC_PR}.1"

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

PACKAGES += "${PN}-src"
FILES_${PN}-src = "/usr/src/${PN}"
addtask do_src_install after do_install before do_package
do_src_install(){
    local d="${D}/usr/src/${PN}"
    install -d \
        "${d}"
    cp -R --no-target-directory --no-preserve ownership \ 
        "${B}/${APPNAME}_server" \
        "${d}/${APPNAME}_server"

    install -m 664 --target-directory "${d}" \
        "${B}/Dockerfile" \
        "${B}/requirements.txt" \
        "${B}/setup.py"
}

inherit deploy
addtask do_deploy after do_src_install
do_deploy[dirs] = "${DEPLOYDIR} ${D}/usr/src/${PN}"
do_deploy() {
    local shortname="${PN}"
    local longname="${shortname}_${PV}-${PR}"
    local ext=".tar.xz"
    tar --create --xz \
        --file "${DEPLOYDIR}/${longname}${ext}" \
        *
    ln -sT "${longname}${ext}" \
        "${DEPLOYDIR}/${shortname}${ext}"
}

