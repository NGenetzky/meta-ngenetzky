
GITROOT="${GITROOT-$(readlink -f ./$(git rev-parse --show-cdup))}"

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
