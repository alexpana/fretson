(ns fretson.main
  (:require [fretson.core :as core])
  (:require [fretson.printer :as printer]))

(defn -main [& args]
  (printer/print-fretboard 12))
