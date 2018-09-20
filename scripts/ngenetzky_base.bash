#!/bin/bash
GITROOT="${GITROOT-$(readlink -f ./$(git rev-parse --show-cdup))}"
BITBAKE_DIR="${GITROOT}/../bitbake"
BUILD_DIR="${GITROOT}/../build"

bitbake_clone(){
    local dest="${BITBAKE_DIR}"
    local remote="https://github.com/openembedded/bitbake"
    if [ ! -d ${dest} ] ; then
      git clone "${remote}" "${dest}"
    fi
}

bitbake_conf() {
  local name='ngenetzky-base'
  local confdir="${BUILD_DIR?}/conf"
  mkdir -p "${confdir}"
  install -m 664 --target-directory="${confdir}" \
      "${GITROOT}/projects/${name}/local.conf" \
      "${GITROOT}/projects/${name}/bblayers.conf"
}

bitbake_set_path(){
  local p="$(readlink -f ${1?})"
    [[ -d ${p} ]] || return 1
    export PATH="${p}/bin:$PATH"
    export PYTHONPATH="${p}/lib:$PYTHONPATH"
}

main(){
  bitbake_clone
  bitbake_conf
}

if [[ "${BASH_SOURCE[0]}" == "${0}" ]]; then
    # Bash Strict Mode
    set -eu -o pipefail

    set -x
    main "$@"
else
  main
  bitbake_set_path "${BITBAKE_DIR}"
  export BBPATH="${BUILD_DIR}"
  cd "${BUILD_DIR}"

  unset bitbake_clone
  unset bitbake_conf
  unset bitbake_set_path
fi
