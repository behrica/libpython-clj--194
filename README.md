# libpython-clj--194

Reproducable crash using transformers

1. Run in one terminal: ./docker_run_crash.sh,
this will start nrepl in Docker container

2. Run in other terminal :
clj -Sdeps '{:deps {nrepl {:mvn/version "0.9.0"}}}' -m nrepl.cmdline --connect --port 12345

3. And in the repl from 2.:
 (load-file "simpletransformers.clj")
