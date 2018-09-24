#!/bin/bash

SCRIPTDIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd -P)"
GITROOT="${GITROOT-$(readlink -f ${SCRIPTDIR}/../)}"
BITBAKE_DIR="${GITROOT}/bitbake"
BUILD_DIR="${GITROOT}/build"

bitbake_clone(){
    local dest="${BITBAKE_DIR}"
    local remote="https://github.com/openembedded/bitbake"
    if [ ! -d ${dest} ] ; then
      git clone "${remote}" "${dest}"
    fi
}

bitbake_conf() {
  local name="${1?}"
  local confdir="${BUILD_DIR?}/conf"
  mkdir -p "${confdir}"
  install -m 664 --target-directory="${confdir}" \
      "${GITROOT}/projects/${name}/local.conf" \
      "${GITROOT}/projects/${name}/bblayers.conf"
}

main(){
  local project="${1-ngenetzky-base}"
  bitbake_clone
  bitbake_conf "${project}"
}

if [[ "${BASH_SOURCE[0]}" == "${0}" ]]; then
    # Bash Strict Mode
    set -eu -o pipefail

    set -x
    main "$@"
fi

