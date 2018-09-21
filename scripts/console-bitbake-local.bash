#!/bin/sh

GITROOT="${GITROOT-$(readlink -f ./$(git rev-parse --show-cdup))}"
BITBAKE_DIR="${GITROOT}/bitbake"
BUILD_DIR="${GITROOT}/build"

bitbake_set_path(){
  local p="$(readlink -f ${1?})"
    [[ -d ${p} ]] || return 1
    export PATH="${p}/bin:$PATH"
    export PYTHONPATH="${p}/lib:$PYTHONPATH"
}

bitbake_set_path "${BITBAKE_DIR}"
export BBPATH="${BUILD_DIR}"
cd "${BUILD_DIR}"

unset bitbake_set_path
