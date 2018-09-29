FROM debian:latest
# Based on https://github.com/ankitrgadiya/docker-c9
LABEL \
    maintainer="Nathan@Genetzky.us"

COPY [ "./build_0.sh", "./init.sh", "/tmp/" ]

RUN /tmp/build_0.sh

EXPOSE 3000
ENTRYPOINT ["bash", "/tmp/init.sh"]
