SUMMARY = "Public Github Repositories for NGenetzky"
PV = "2018.09.22"
PR = "r3"

inherit bb_fetcher
addtask do_unpack before do_build
SRC_URI = "\
    https://api.github.com/users/ngenetzky/repos;downloadfilename=repos.json \
    https://github.com/NGenetzky.keys \
"

inherit bb_build_shell
do_build_shell_scripts[nostamp] = "1"
addtask do_build_shell_scripts after do_unpack before do_build
python do_build_shell_scripts(){
    workdir = d.getVar('WORKDIR', expand=True)
    export_func_shell('do_fetchrepos', d, os.path.join(workdir, 'do_fetchrepos.sh'), workdir)
}

def get_urls_from_repos_json(repos_json, skip_archived=True):
    import json
    with open(repos_json,'r') as jfile:
        repos = json.load(jfile)

    repo_urls = []
    for repo in repos:
        if skip_archived and repo['archived']:
            continue
        repo_urls.append(repo["ssh_url"])
    return ' '.join(repo_urls)

do_fetchrepos[dirs] = "${S}"
addtask fetchrepos after do_unpack
do_fetchrepos(){
    for reponame in ${@get_urls_from_repos_json('${WORKDIR}/repos.json')} ; do
        echo git clone $reponame
    done
}
