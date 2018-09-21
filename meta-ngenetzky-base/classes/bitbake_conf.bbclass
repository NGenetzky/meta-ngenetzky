TEMPLATECONF ??= "${WORKDIR}"
BUILDCONF ??= "${B}/conf"

do_bitbake_conf_templatef[nostamp] = "1"
do_bitbake_conf_template[dirs] = "${BUILDCONF} ${TEMPLATECONF}"
addtask do_bitbake_conf_template before do_build
python do_bitbake_conf_template(){
    templateconf = d.getVar('TEMPLATECONF')
    buildconf = d.getVar('BUILDCONF')

    with open(os.path.join(templateconf, 'bblayers.conf'), 'r') as conf:
        bblayers = conf.read()
    with open(os.path.join(templateconf, 'local.conf'), 'r') as conf:
        local = conf.read()

    for var in [ 'S', 'B']:
        bblayers = bblayers.replace('{{ '+var+' }}', d.getVar(var))

    with open(os.path.join(buildconf, 'bblayers.conf'), 'w') as conf:
        conf.write(bblayers)
    with open(os.path.join(buildconf, 'local.conf'), 'w') as conf:
        conf.write(local)
}

