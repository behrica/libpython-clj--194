(ns scicloj.ml.simpletransformers)

(require '[libpython-clj2.require :as py-req]
         '[libpython-clj2.python.io-redirect]
         '[libpython-clj2.python :refer [py.- py.] :as py])



(py/from-import simpletransformers.classification ClassificationModel)
(py-req/require-python '[pandas :as pd])

(def  train-data  [
                   ["Example sentence belonging to class 1" 1]
                   ["Example sentence belonging to class 0" 0]])



(def eval-data  [
                 ["Example eval sentence belonging to class 1" 1]
                 ["Example eval sentence belonging to class 0" 0]])


(println "train...")
(py/with-gil
  (let [
        train-df (pd/DataFrame train-data)
        eval-df (pd/DataFrame eval-data)
      
        model (ClassificationModel "roberta" "roberta-base" :use_cuda false :args
                                   {:num_train_epochs 1
                                    :use_multiprocessing false
                                    :overwrite_output_dir true})
                                    

        x  (py. model train_model train-df)]
    (println x)))

(println "finished train")
