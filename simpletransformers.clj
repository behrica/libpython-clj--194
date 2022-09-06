(ns scicloj.ml.simpletransformers)

(import '[libpython_clj2 java_api])
(java_api/initialize nil)


(defmacro with-properties [property-map & body]
  `(let [pm# ~property-map
         props# (into {} (for [[k# v#] pm#]
                           [k# (System/getProperty k#)]))]
     (doseq [k# (keys pm#)]
       (System/setProperty k# (get pm# k#)))
     (try
       ~@body
       (finally
         (doseq [k# (keys pm#)]
           (if-not (get props# k#)
             (System/clearProperty k#)
             (System/setProperty k# (get props# k#))))))))


(def  train-data  [
                   ["Example sentence belonging to class 1" 1]
                   ["Example sentence belonging to class 0" 0]])



(def eval-data  [
                 ["Example eval sentence belonging to class 1" 1]
                 ["Example eval sentence belonging to class 0" 0]])

(java_api/lockGIL)
(require '[libpython-clj2.require :as py-req]
         '[libpython-clj2.python.io-redirect]
         '[libpython-clj2.python :refer [py.- py.] :as py])

(py/from-import simpletransformers.classification ClassificationModel)
(py-req/require-python '[pandas :as pd])

(println "train...")
(with-properties {"libpython_clj.manual_gil" "true"}
  (let [

        train-df (pd/DataFrame train-data)
        eval-df (pd/DataFrame eval-data)

        model (ClassificationModel "roberta" "roberta-base" :use_cuda false
                                   :args {
                                          :overwrite_output_dir true
                                          :num_train_epochs 1})



        x  (py. model train_model train-df)]
   (println x)))
(java_api/unlockGIL 1)
(println "finished train")
