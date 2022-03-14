FROM continuumio/anaconda3
RUN apt-get update && apt-get -y install --reinstall ca-certificates && update-ca-certificates
RUN apt-get update && apt-get -y install openjdk-11-jdk curl rlwrap libssl-dev build-essential zlib1g-dev  libncurses5-dev libgdbm-dev \
    libnss3-dev  libreadline-dev libffi-dev libbz2-dev git lzma-dev liblzma-dev

RUN curl -O https://download.clojure.org/install/linux-install-1.10.3.986.sh && chmod +x linux-install-1.10.3.986.sh && ./linux-install-1.10.3.986.sh
RUN clj -P
RUN pip3 install -U numpy wheel scikit-learn cython


RUN conda create -n st python==3.9 pandas tqdm
SHELL ["conda", "run", "-n", "st", "/bin/bash", "-c"]
RUN export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64 && pip3 install cljbridge

RUN conda install pytorch cpuonly -c pytorch


RUN pip3 install simpletransformers
ENTRYPOINT ["conda", "run", "--no-capture-output", "-n", "st", "python", "-c", "from clojurebridge import cljbridge\ncljbridge.init_jvm(start_repl=True,port=12345,bind='0.0.0.0',cider_nrepl_version='0.27.2')"]
