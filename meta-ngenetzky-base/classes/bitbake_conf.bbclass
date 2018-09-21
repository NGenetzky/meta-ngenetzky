do_setup_conf[nostamp] = "1"
do_setup_conf[dirs] = "${B}/conf ${WORKDIR}"
python do_setup_conf(){
    workdir = d.getVar('WORKDIR')
    bdir = d.getVar('B')

    with open(os.path.join(workdir, 'bblayers.conf'), 'r') as conf:
        bblayers = conf.read()
    with open(os.path.join(workdir, 'local.conf'), 'r') as conf:
        local = conf.read()

    for var in [ 'S', 'B']:
        bblayers = bblayers.replace('{{ '+var+' }}', d.getVar(var))

    with open(os.path.join(bdir, 'conf', 'bblayers.conf'), 'w') as conf:
        conf.write(bblayers)
    with open(os.path.join(bdir, 'conf', 'local.conf'), 'w') as conf:
        conf.write(local)
}

