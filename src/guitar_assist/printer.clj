(ns guitar-assist.printer
  (:require [guitar-assist.core :refer :all]))

(defn print-nut [note]
  (print "" note "|"))

(defn print-fret [note]
  (if (is-sharp note)
    (print (clojure.core/format "-- %s --|" note))
    (print (clojure.core/format "-- %s ---|" note))))

(defn print-fret-hidden []
  (print "--------|"))

(defn print-frets [frets]
  (do
    (doall (for [fret frets]
             (print-fret fret)))))

(defn print-string [note fret_count]
  (do
    (print-nut note)
    (print-frets (gen-notes note fret_count))))

(defn fret-guide-at [index]
  (if (and (not= index 11) (>= index 3) (= (mod index 2) 1))
    (first dots)
    (if (and (>= index 3) (= (mod index 12) 0))
      (second dots)
      " ")))

(defn print-fret-guide-impl [size index]
  (print "  " (fret-guide-at index) "    ")
  (if (< index size)
    (print-fret-guide-impl size (inc index))))

(defn print-fret-guide [size]
  (print "    ")
  (print-fret-guide-impl size 1))

(defn print-fretboard [size]
  (do
    (print-fret-guide size)
    (print "\n")
    (print-string "E" size)
    (print "\n")
    (print-string "B" size)
    (print "\n")
    (print-string "G" size)
    (print "\n")
    (print-string "D" size)
    (print "\n")
    (print-string "A" size)
    (print "\n")
    (print-string "E" size)
    (print "\n")))
