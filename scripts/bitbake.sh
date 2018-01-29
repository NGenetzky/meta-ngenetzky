
GITROOT="${GITROOT-$(readlink -f ./$(git rev-parse --show-cdup))}"

bitbake_setup()
{
    local workdir="${1?}"

    DL_DIR="/data/ngenetzky/yocto-downloads"
    SSTATE_CACHE="/data/ngenetzky/yocto-sstate-cache"
    WORKDIR=${workdir}
}

bitbake_setup_external()
{
    local workdir="${1?}"

    DL_DIR="/media/ngenetzky/lnx-passport/ngenetzky/yocto-downloads"
    SSTATE_CACHE="/media/ngenetzky/lnx-passport/ngenetzky/yocto-sstate-cache"
    WORKDIR=${workdir}
}

do_fetch()
{
    local rev="${1-rocko}"

    local yoctodir="${WORKDIR?}/yocto/"
    mkdir -p "${yoctodir}"
    pushd "${yoctodir}"
    repo init \
        --manifest-url=git://github.com/ngenetzky/yocto-manifests.git \
        -b "${rev}" \
        --groups=poky,rpi
    repo sync -j 4
    popd
}

do_configure()
{
    local name="${1-basic}"
    local confdir="${WORKDIR?}/build/conf"
    mkdir -p "${confdir}"
    install -m 664 --target-directory="${confdir}" \
        "${GITROOT}/projects/${name}/local.conf" \
        "${GITROOT}/projects/${name}/bblayers.conf"
}

bitbake()
{
    docker run \
        --rm -it \
        --env-file ${GITROOT}/projects/basic/env-file.sh \
        -v ${WORKDIR?}:/workdir \
        -v ${DL_DIR?}:/mnt/downloads \
        -v ${SSTATE_CACHE?}:/mnt/sstate-cache \
        -v ${GITROOT?}:/mnt/meta-ngenetzky \
        crops/poky \
        --workdir=/workdir \
        $@
}

bitbake_set_path(){
    local p="${1?}"
    [[ -d ${p} ]] || return 1
    export PATH="${p}/bin:$PATH"
    export PYTHONPATH="${p}/lib:$PYTHONPATH"
}

main()
{
    # bitbake_setup ${1?}
    bitbake_setup_external ${1?}
    do_fetch
    do_configure 'rpi3'
    bitbake
}

if [[ "${BASH_SOURCE[0]}" == "${0}" ]]; then
    # Bash Strict Mode
    set -eu -o pipefail

    set -x
    main "$@"
fi
