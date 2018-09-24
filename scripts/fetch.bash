
SCRIPTDIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd -P)"
GITROOT="${GITROOT-$(readlink -f ${SCRIPTDIR}/../)}"

source "${GITROOT}/scripts/yocto.bash"

main() {
    yocto_fetch "$@"
}

if [[ "${BASH_SOURCE[0]}" == "${0}" ]]; then
    # Bash Strict Mode
    set -eu -o pipefail

    set -x
    main "$@"
fi
