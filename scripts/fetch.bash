
GITROOT="${GITROOT-$(readlink -f ./$(git rev-parse --show-cdup))}"

do_fetch()
{
    local rev="${1-pyro}"
    local groups="${2-poky}"

    local yoctodir="${WORKDIR-.}/yocto/"
    mkdir -p "${yoctodir}"
    pushd "${yoctodir}"
    repo init \
        --manifest-url=git://github.com/ngenetzky/yocto-manifests.git \
        -b "${rev}" \
        --groups="${groups}"
    repo sync -j 4
    popd
}

main()
{
    do_fetch "$@"
}

if [[ "${BASH_SOURCE[0]}" == "${0}" ]]; then
    # Bash Strict Mode
    set -eu -o pipefail

    set -x
    main "$@"
fi
