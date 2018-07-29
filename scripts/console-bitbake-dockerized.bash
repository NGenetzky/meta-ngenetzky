#!/bin/bash

DOCKER_RUN_ARGS=""
DOCKER_IMAGE="crops/poky"

SHARED_DOWNLOADS="/data/yocto/downloads/"
SHARED_SSTATE="/data/yocto/sstate-cache/"
SHARED_LAYERS="/opt/yocto/"

run() {
  docker run \
      --rm -it \
      -v ${WORKDIR?}:/workdir \
      $@ \
      ${DOCKER_IMAGE} \
      --workdir=/workdir
}

################################################################################
# mnt volumes
#

mnt_downloads() {
  local dl_dir="${1?}"
  [[ -d ${dl_dir} ]] || return 1
  local _int_dl_dir='/mnt/downloads/'
  echo " -v ${dl_dir}:${_int_dl_dir}:ro "
}

mnt_sstate() {
  local sstate_dir="${1?}"
  [[ -d ${sstate_dir} ]] || return 1
  local _int_sstate_dir='/mnt/sstate-cache'
  echo " -v ${sstate_dir}:${_int_sstate_dir}:ro "
}

mnt_yocto_layers() {
  local yoctolayers_dir="${1?}"
  [[ -d ${yoctolayers_dir} ]] || return 1
  local _int_yoctolayers_dir='/mnt/yocto-layers'
  echo " -v ${yoctolayers_dir}:${_int_yoctolayers_dir}:ro "
}

#
################################################################################

main()
{
  local extra_args=""
  export WORKDIR=${WORKDIR-$(pwd)}

  local extra_args+="$(mnt_downloads ${SHARED_DOWNLOADS})"
  local extra_args+="$(mnt_sstate ${SHARED_SSTATE})"
  local extra_args+="$(mnt_yocto_layers ${SHARED_LAYERS})"

  run \
    ${extra_args} \
    ${DOCKER_RUN_ARGS} \
    $@
}

if [[ "${BASH_SOURCE[0]}" == "${0}" ]]; then
    # Bash Strict Mode
    set -eu -o pipefail

    set -x
    main "$@"
fi
