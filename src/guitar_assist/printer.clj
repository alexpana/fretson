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

(defn print-frets
  
  ([frets]
   (print-frets frets some?))
  
  ([frets pred]
   (do
     (doall
      (for [fret frets]
        (if (pred fret)
          (print-fret fret)
          (print-fret-hidden)))))))

(defn print-string
  ([note fret_count]
   (print-string note fret_count some?))
  
  ([note fret_count pred]
   (do
     (print-nut note)
     (print-frets (gen-notes (next-note note) fret_count) pred))))

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

(defn print-fretboard [strings size]
  (do
    (print-fret-guide size)
    (print "\n")
    (doall
     (for [string strings]
       (do
         (print-string string size)
         (print "\n"))))))

(defn notes-per-string
  [tuning scale]
  (partition 3 (take
                (* 3 (count (guitar-assist.core/tuning tuning)))
                (cycle (take 7 scale)))))

(defn print-fretboard-with-mask [strings size mask]
  (do
    (print-fret-guide size)
    (print "\n")
    (doall
     (for [string strings]
       (do
         (print-string string size (fn [x] (some #(= x %) mask)))
         (print "\n"))))))

(defn print-fretboard-pred
  [strings size string-pred]
  (do
    (print-fret-guide size)
    (print "\n")
    (doall
     (map-indexed
      (fn [index string]
        (do
          (print-string string size (string-pred (- (count strings) index 1)))
          (print "\n")))
      strings))))

(defn test-pred
  [root mode]
  (fn [s]
     (fn [f]
       (some
        #(= f %)
        (nth (notes-per-string :standard (scale-mode root mode)) s)))))
  
(defn print-scale-mode
  [root mode]
  (print-fretboard-pred
   (tuning :standard)
   24
   (fn [string]
     (fn [fret]
       (some
        #(= fret %)
        (nth (notes-per-string :standard (scale-mode root mode)) string))))))

(defn print-scale [root scale size]
  (print-fretboard-with-mask
   (tuning :standard)
   size
   (guitar-assist.core/scale root scale)))
