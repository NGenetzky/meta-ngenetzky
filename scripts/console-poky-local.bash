#!/bin/sh

GITROOT="${GITROOT-$(readlink -f ./$(git rev-parse --show-cdup))}"
POKY_DIR="${GITROOT}/yocto/poky"

source "${POKY_DIR}/oe-init-build-env" "$@"
