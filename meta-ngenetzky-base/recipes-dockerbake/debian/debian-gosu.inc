build_gosu_bootstrap(){
    apt-get update && apt-get -y --no-install-recommends install \
        ca-certificates \
        curl
}
build_gosu_preinstall(){
    gpg --keyserver ha.pool.sks-keyservers.net --recv-keys B42F6819007F00F88E364FD4036A9C25BF357DD4
}
build_gosu_install(){
    curl -o /usr/local/bin/gosu -SL "https://github.com/tianon/gosu/releases/download/1.4/gosu-$(dpkg --print-architecture)" \
        && curl -o /usr/local/bin/gosu.asc -SL "https://github.com/tianon/gosu/releases/download/1.4/gosu-$(dpkg --print-architecture).asc" \
        && gpg --verify /usr/local/bin/gosu.asc \
        && rm /usr/local/bin/gosu.asc \
        && chmod +x /usr/local/bin/gosu
}
build_gosu(){
    build_gosu_bootstrap
    build_gosu_preinstall
    build_gosu_install
}
