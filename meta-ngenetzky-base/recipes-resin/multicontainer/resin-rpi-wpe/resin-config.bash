# Customising config.txt
# These are some tips and tricks for customizing your raspberry pi. Most of
# them require changing settings in the config.txt file on the SD cards boot
# partition. See here for more details.

# You can also set all of these variables remotely in the Device Configuration
# (for a single device) or Fleet Configuration (for all devices within an
# application) menu. If the setting in config.txt is variable=value, you can
# achieve the same settings by adding a configuration variable with
# RESIN_HOST_CONFIG_variable set to the value value. For example:

# https://www.raspberrypi.org/documentation/configuration/config-txt/README.md

resin_config_dtparam(){
  local app="${1?}"
  resin env add --application "${app}" \
    RESIN_HOST_CONFIG_dtparam \
    'audio=on'
}

resin_config_gpu_mem(){
  local app="${1?}"

  # https://docs.resin.io/reference/OS/advanced/

  # Note that gpu_mem_256 is used by the 256MB Raspberry Pi (Model A - you
  # probably don't have one of these), and gpu_mem_512 is used by the 512MB
  # Raspberry Pi (Model B/B+ - this is probably what you have.)

  # The Model A Raspberry Pi ignores the 512MB setting altogether while the
  # model B and B+ ignore the 256MB setting. Both gpu_mem_256 and gpu_mem_512
  # override gpu_mem.

  # By default we assign 16MB of memory to the GPU (specified by gpu_mem.) This
  # may well be less than you require depending on your application
  # (particularly applications which make heavy use of the Pi's graphics card.)

  resin env add --application "${app}" \
    RESIN_HOST_CONFIG_gpu_mem_256 \
    '128'
  resin env add --application "${app}" \
    RESIN_HOST_CONFIG_gpu_mem_512 \
    '196'
  resin env add --application "${app}" \
    RESIN_HOST_CONFIG_gpu_mem_1024 \
    '396'
}

resin_config_hdmi_720p(){
  local app="${1?}"
  resin env add --application "${app}" \
    RESIN_HOST_CONFIG_hdmi_group \
    '1'
  resin env add --application "${app}" \
    RESIN_HOST_CONFIG_hdmi_mode \
    '4'
}

resin_config_hdmi_for_pitft(){
  local app="${1?}"
  resin env add --application "${app}" \
    RESIN_HOST_CONFIG_hdmi_force_hotplug \
    '1'
  resin env add --application "${app}" \
    RESIN_HOST_CONFIG_hdmi_group \
    '2'
  resin env add --application "${app}" \
    RESIN_HOST_CONFIG_hdmi_mode \
    '87'

  resin env add --application "${app}" \
    RESIN_HOST_CONFIG_hdmi_cvt \
    '320 240 60 1 0 0 0'
  # resin env add --application "${app}" \
  #   RESIN_HOST_CONFIG_hdmi_cvt \
  #   '480 320 60 6 0 0 0'
}

resin_config_pitft_cap(){
  local app="${1?}"
  resin env add --application "${app}" \
    RESIN_HOST_CONFIG_dtoverlay \
    'pitft28-capacitive,rotate=90,speed=62000000,fps=60'
}

resin_config_pitft_res(){
  local app="${1?}"
  resin env add --application "${app}" \
    RESIN_HOST_CONFIG_dtoverlay \
    'pitft28-resistive,rotate=90,speed=62000000,fps=60'
}

main(){
  local app="${1?}"
  set +e
  resin_config_dtparam "${app}"
  resin_config_gpu_mem "${app}"
  resin_config_hdmi_for_pitft "${app}"
  resin_config_pitft_cap "${app}"
}

if [[ "${BASH_SOURCE[0]}" == "${0}" ]]; then
    # Bash Strict Mode
    set -eu -o pipefail

    set -x
    main "$@"
fi

