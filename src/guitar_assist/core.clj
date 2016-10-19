(ns guitar-assist.core
  (:require [clojure.string :as str]))

(defrecord Note [name octave])

(def notes
  '(("E" "F") ("F" "F#") ("F#" "G") ("G" "G#") ("G#" "A")
    ("A" "A#") ("A#" "B") ("B" "C") ("C" "C#") ("C#" "D")
    ("D" "D#") ("D#" "E")))

(def dots '("⠂" "⠆"))

(def FRET_WIDTH 9)
(def NUT_WIDTH 4)

(defn next-note
  [note]
  (second (first (filter #(= note (first %)) notes))))

(defn previous-note
  [note]
  (first (first (filter #(= note (second %)) notes))))

(defn is-sharp
  [note]
  (not (nil? (str/index-of note "#"))))

;; TODO: implement
(defn interval
  [from to]
  0)

(defn n-semitones-from
  [note distance]
  (if (>= distance 0)
    (n-semitones-from (next-note note) (dec distance))
    note))

(defn print-notes
  [from count]
  (if (>= count 0)
    ((print from " ")
     (print-notes (next-note from) (dec count)))
    (print "\n")))

;; Frets

(defn print-nut
  [note]
  (print "" note "|"))

(defn print-fret
  [note]
  (print "--" note)
  (if (= (count note) 2)
    (print " --|")
    (print " ---|")))

(defn print-fret-hidden
  []
  (print "--------|"))

(defn print-frets
  [note fret_count]
  (when (> fret_count 0)
    (do
      (print-fret note)
      (print-frets (next-note note) (dec fret_count)))))

(defn print-string
  [note fret_count]
  (print-nut note)
  (print-frets (next-note note) fret_count))

(defn fret-guide-at
  [index]
  (if (and (not= index 11) (>= index 3) (= (mod index 2) 1))
    (first dots)
    (if (and (>= index 3) (= (mod index 12) 0))
      (second dots)
      " ")))

(defn print-fret-guide-impl
   [size index]
   (print "  " (fret-guide-at index) "    ")
   (if (< index size)
     (print-fret-guide-impl size (inc index))))

(defn print-fret-guide
  [size]
  (print "    ")
  (print-fret-guide-impl size 1))

(defn print-fretboard
  [size]
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

;; TODO print frets should receive a list of notes (possibly hidden) to allow composability and masking ()

;; TODO replace recursion with the 'recur' operator http://clojure.org/reference/special_forms#recur

;; TODO fretboard length should be a program argument
