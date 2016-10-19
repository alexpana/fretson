(ns guitar-assist.main
  (:require [guitar-assist.core :as core])
  (:require [guitar-assist.printer :as printer]))

(defn -main [& args]
  (printer/print-fretboard 12))
