(ns guitar-assist.main
  (:require [guitar-assist.core :as core]))

(defn -main [& args]
  (core/print-fretboard 12))
