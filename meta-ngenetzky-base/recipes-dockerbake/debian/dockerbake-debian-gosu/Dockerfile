FROM debian:jessie
# Based on https://denibertovic.com/posts/handling-permissions-with-docker-volumes/
LABEL \
    maintainer="Nathan@Genetzky.us"

COPY [ "./build_gosu.sh", "./entrypoint.sh", "/tmp/" ]

RUN /tmp/build_gosu.sh

EXPOSE 3000
ENTRYPOINT ["bash", "/tmp/entrypoint.sh"]
CMD ["/bin/bash"]
