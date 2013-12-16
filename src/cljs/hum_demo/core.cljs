(ns hum-demo
  (:require [hum.core :as hum]
            [dommy.utils :as utils]
            [dommy.core :as dommy])
  (:use-macros [dommy.macros :only [node sel sel1]]))

(def ctx (hum/create-context))
(def vco (hum/create-osc ctx :square))
(def vcf (hum/create-biquad-filter ctx))
(def output (hum/create-gain ctx))

(hum/connect vco vcf)
(hum/connect vcf output)

(hum/start-osc vco)

(hum/connect-output output)

(defn note-num-to-frequency [note-num]
  (let [expt-numerator (- note-num 49)
        expt-denominator 12
        expt (/ expt-numerator expt-denominator)
        multiplier (.pow js/Math 2 expt)
        a 440]
  (* multiplier a)))

(defn keyboard-click-handler [event note-num]
  (let [keyboard-key (.-target event)
        freq (note-num-to-frequency note-num)]
    (hum/note-on output vco freq)))

(defn note-off []
  (hum/note-off output))

(defn create-keyboard [keyboard]
  (doseq [i (range 40 53)]
    (.log js/console i)
    (let [keyboard-note (node :li)]
      (dommy/set-attr! keyboard-note :id (str "note-" i))
      (dommy/append! keyboard keyboard-note)
      (if (some #{i} [41 43 46 48 50]) ;; black keys
        (dommy/add-class! keyboard-note "accidental"))
      (dommy/listen! keyboard-note :mousedown #(keyboard-click-handler % i))
      (dommy/listen! keyboard-note :mouseup note-off)))
  (dommy/listen! keyboard :mouseup note-off)
  (dommy/listen! keyboard :mouseleave note-off))
(defn setup []
  (let [keyboard (sel1 "#keyboard ul")]
    (create-keyboard keyboard)))

(set! (.-onload js/window) setup)
