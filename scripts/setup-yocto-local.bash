#!/bin/bash

GITROOT="${GITROOT-$(readlink -f ./$(git rev-parse --show-cdup))}"
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

