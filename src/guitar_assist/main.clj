(ns guitar-assist.main
  (:require [guitar-assist.core :as core]))

(defn -main [& args]
  (core/print-string "E" 10)
  (print "hello world\n"))
