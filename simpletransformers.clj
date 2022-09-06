(ns scicloj.ml.simpletransformers
   (:require [libpython-clj2.python :refer [py.- py.] :as py]))






(def  train-data  [
                   ["Example sentence belonging to class 1" 1]
                   ["Example sentence belonging to class 0" 0]])



(def eval-data  [
                 ["Example eval sentence belonging to class 1" 1]
                 ["Example eval sentence belonging to class 0" 0]])


(println "train...")
(py/with-gil-stack-rc-context

  (py/from-import simpletransformers.classification ClassificationModel)



  (let [
        pd (py/import-module "pandas")
        train-df (py. pd DataFrame train-data)
        eval-df (py. pd DataFrame eval-data)
      
        model (ClassificationModel "bert" "prajjwal1/bert-tiny" :use_cuda false :args
                                   {:num_train_epochs 1
                                    :use_multiprocessing false
                                    :overwrite_output_dir true})


        x  (py. model train_model train-df)]
    (println x)))

(println "finished train")
