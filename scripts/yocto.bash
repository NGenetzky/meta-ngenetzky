
GITROOT="${GITROOT-$(readlink -f ./$(git rev-parse --show-cdup))}"

yocto_setup() {
  local workdir="${1?}"

  export DL_DIR="/data/ngenetzky/yocto-downloads"
  export SSTATE_CACHE="/data/ngenetzky/yocto-sstate-cache"
  export WORKDIR=${workdir}

  mkdir -p "${workdir}/yocto"
  ln -Tfs \
    "${DL_DIR}" \
    "${workdir}/yocto/downloads"
  ln -Tfs \
    "${SSTATE_CACHE}" \
    "${workdir}/yocto/sstate-cache"
  ln -Tfs \
    "${GITROOT}" \
    "${workdir}/yocto/src"
}

yocto_fetch() {
  local rev="${1-pyro}"
  local groups="${2-poky}"
  local workdir="${WORKDIR-.}"

  local yoctodir="${workdir}/yocto/${rev}"
  mkdir -p "${yoctodir}"
  ln -Tfs \
    "${rev}" \
    "${workdir}/yocto/layers"

  pushd "${yoctodir}"
  repo init \
      --manifest-url=git://github.com/ngenetzky/yocto-manifests.git \
      -b "${rev}" \
      --groups="${groups}"
  repo sync -j 4
  popd
}

yocto_conf() {
  local name="${1-basic}"
  local confdir="${WORKDIR?}/build/conf"
  mkdir -p "${confdir}"
  install -m 664 --target-directory="${confdir}" \
      "${GITROOT}/projects/${name}/local.conf" \
      "${GITROOT}/projects/${name}/bblayers.conf"
}

yocto_bitbake() {
  local runargs=""
  # --env-file "${ENV_FILE?}"
  docker run \
    --rm -it \
    -v ${WORKDIR?}:/workdir \
    -v ${DL_DIR?}:/mnt/downloads \
    -v ${SSTATE_CACHE?}:/mnt/sstate-cache \
    -v ${GITROOT?}:/mnt/meta-ngenetzky \
    $@ \
    crops/poky \
    --workdir=/workdir
}

main(){
  yocto_setup "${1?}"
  yocto_fetch
  yocto_conf
  yocto_bitbake
}

if [[ "${BASH_SOURCE[0]}" == "${0}" ]]; then
    # Bash Strict Mode
    set -eu -o pipefail

    set -x
    main "$@"
fi
