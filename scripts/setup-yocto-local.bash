#!/bin/bash

SCRIPTDIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd -P)"
GITROOT="${GITROOT-$(readlink -f ${SCRIPTDIR}/../)}"
WORKDIR="${GITROOT}"

source "${GITROOT}/scripts/yocto.bash"

main(){
  local project="${1-basic}"
  yocto_setup "${WORKDIR}"
  yocto_fetch 'rocko' 'poky,oe'
  yocto_conf "${project}"
}

if [[ "${BASH_SOURCE[0]}" == "${0}" ]]; then
    # Bash Strict Mode
    set -eu -o pipefail

    set -x
    main "$@"
fi

