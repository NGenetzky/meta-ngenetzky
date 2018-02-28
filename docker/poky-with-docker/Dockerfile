FROM crops/poky:ubuntu-16.04

USER root

# Install the latest Docker CE binaries
# Source: getintodevops/jenkins-withdocker
RUN apt-get update &&  apt-get -y install \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg2 \
    software-properties-common \
    && curl -fsSL https://download.docker.com/linux/$(. /etc/os-release; echo "$ID")/gpg > /tmp/dkey; apt-key add /tmp/dkey \
    && add-apt-repository \
        "deb [arch=amd64] https://download.docker.com/linux/$(. /etc/os-release; echo "$ID") \
        $(lsb_release -cs) \
        stable" \
    && apt-get update \
    && apt-get -y install docker-ce

RUN usermod -aG docker usersetup

USER usersetup

VOLUME /var/run/docker.sock

