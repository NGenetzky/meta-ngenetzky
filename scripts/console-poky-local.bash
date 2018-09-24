#!/bin/sh

SCRIPTDIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd -P)"
GITROOT="$(readlink -f ${SCRIPTDIR}/../)"
POKY_DIR="${GITROOT}/yocto/poky"

source "${POKY_DIR}/oe-init-build-env" "$@"
