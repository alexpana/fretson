(ns guitar-assist.core
  (:require [clojure.string :as str]))

(def notes
  '(("E" "F") ("F" "F#") ("F#" "G") ("G#" "A") ("A" "A#")
    ("A#" "B") ("B" "C") ("C" "C#") ("C#" "D") ("D" "D#")
    ("D#" "E")))

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

(defn print-nut
  [note]
  (print note "|"))

(defn print-frets
  [note, fret_count]
  ())

(defn print-string
  [note fret_count]
  (print-nut note)
  (print-frets note fret_count))



(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
