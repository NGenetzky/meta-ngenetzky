#!/bin/bash

OPTDIR="/opt/yocto"

mkoptdir() {
  sudo mkdir "${OPTDIR}"
  sudo chown \
    "$(id -u):$(id -g)" \
    "${OPTDIR}"
}

repo_fetch() {
  local rev="${1-pyro}"
  local groups="${2-poky}"

  repo init \
      --manifest-url=git://github.com/ngenetzky/yocto-manifests.git \
      -b "${rev}" \
      --groups="${groups}"
  repo sync -j 4
}

main() {
  local groups="${1-poky}"

  # TODO: Support other versions
  local v='2.4'
  local rev='rocko'
  local dest="/opt/yocto/${v}"

  [[ -d ${OPTDIR} ]] || mkoptdir
  # mkdir ${dest}
  (
    cd ${dest}
    repo_fetch "${rev}" "${groups}"
  )
}

if [[ "${BASH_SOURCE[0]}" == "${0}" ]]; then
    # Bash Strict Mode
    set -eu -o pipefail

    set -x
    main "$@"
fi
