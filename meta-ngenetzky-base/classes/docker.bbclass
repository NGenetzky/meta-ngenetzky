
DOCKER_REPOSITORY ??= "meta-ngenetzky/${PN}"
DOCKER_TAG ??= "${PV}-${PR}"
DOCKER_DIR ??= "${TOPDIR}/docker"

def docker_volume_path(d, name):
    dockerdir = d.getVar('DOCKER_DIR',expand=True)
    path = os.path.join(dockerdir, 'volumes', name)
    if not os.path.isdir(path):
        os.makedirs(path)
    return path
