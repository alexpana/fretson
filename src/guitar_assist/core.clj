(ns guitar-assist.core
  (:require [clojure.string :as str]))

(defrecord Note [name octave])

(def notes
  '(("E" "F") ("F" "F#") ("F#" "G") ("G" "G#") ("G#" "A")
    ("A" "A#") ("A#" "B") ("B" "C") ("C" "C#") ("C#" "D")
    ("D" "D#") ("D#" "E")))

(def scale-intervals
  {
   :major [2 2 1 2 2 2 1]
   :minor [2 1 2 2 1 2 2]
   :chromatic [1 1 1 1 1 1 1 1 1 1 1 1]})

(def scale-modes
  {
   :ionian     {:offset 0 :intervals [2 2 1 2 2 2 1]}
   :dorian     {:offset 1 :intervals [2 1 2 2 2 1 2]}
   :phrygian   {:offset 2 :intervals [1 2 2 2 1 2 2]}
   :lydian     {:offset 3 :intervals [2 2 2 1 2 2 1]}
   :mixolydian {:offset 4 :intervals [2 2 1 2 2 1 2]}
   :aeolian    {:offset 5 :intervals [2 1 2 2 1 2 2]}
   :locrian    {:offset 6 :intervals [1 2 2 1 2 2 2]}})

(def guitar-tunings
  {
   :standard ["E" "B" "G" "D" "A" "E"]})

(def dots '("⠂" "⠆"))

(def FRET_WIDTH 9)

(def NUT_WIDTH 4)

(defn next-note [note]
  (second (first (filter #(= note (first %)) notes))))

(defn previous-note [note]
  (first (first (filter #(= note (second %)) notes))))

(defn is-sharp [note]
  (not (nil? (str/index-of note "#"))))

(defn interval [from to]
  (if (not= to from)
    (inc (interval (next-note from) to))
    0))

(defn n-semitones-from [note distance]
  (if (> distance 0)
    (n-semitones-from (next-note note) (dec distance))
    note))

(defn generate-scale [root steps]
  (loop [result [] note root s steps]
    (if (empty? s)
      (conj result note)
      (recur (conj result note) (n-semitones-from note (first s)) (drop 1 s)))))

(defn scale [root scale-type]
  (generate-scale root (scale-type scale-intervals)))

(defn scale-mode [root mode]
  (generate-scale
   (nth (scale root :major) (:offset (mode scale-modes)))
   (:intervals (mode scale-modes))))

(defn tuning [mode]
  (mode guitar-tunings))

(defn gen-notes [start count]
  (loop [index count note start notes []]
    (if (<= index 0)
      notes
      (recur (dec index) (next-note note) (conj notes note)))))

(defn find-index [what coll]
  (first (keep-indexed (fn [index value] (when (= what value) index)) coll)))

;; TODO replace recursion with the 'recur' operator http://clojure.org/reference/special_forms#recur

;; TODO fretboard length should be a program argument

;; TODO print-fretboard should receive a list of strings

;; TODO (scales) should contain an inifite number of notes
