(ns scicloj.ml.simpletransformers
   (:require [libpython-clj2.python :refer [py.- py.] :as py]
             [libpython-clj2.require :as py-require]))


(py-require/require-python '[simpletransformers.classification
                              :as classification])
(py-require/require-python '[pandas :as pd])


(def  train-data  [
                   ["Example sentence belonging to class 1" 1]
                   ["Example sentence belonging to class 0" 0]])



(def eval-data  [
                 ["Example eval sentence belonging to class 1" 1]
                 ["Example eval sentence belonging to class 0" 0]])



(let [
      train-df (pd/DataFrame train-data)
      eval-df (pd/DataFrame eval-data)

      model (classification/ClassificationModel

             :use_cuda false
             :model_type "bert"
             :model_name "prajjwal1/bert-tiny"
             :args
             (py/->py-dict
              {:num_train_epochs 5
               :use_multiprocessing false
               "overwrite_output_dir" true}))
      x (py. model train_model train-df)]

  (println x))
