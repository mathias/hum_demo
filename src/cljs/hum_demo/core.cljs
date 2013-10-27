(ns hum-demo
  (:require [hum.core :as hum]
            [dommy.utils :as utils]
            [dommy.core :as dommy])
  (:use-macros [dommy.macros :only [node sel sel1]]))

(def ctx (hum/create-context))
(def vco (hum/create-osc ctx :sawtooth))
(def vcf (hum/create-biquad-filter ctx))
(def output (hum/create-gain ctx))

(hum/connect vco vcf)
(hum/connect vcf output)

(hum/start-osc ctx vco)

(hum/connect output (.-destination ctx))


(defn setup []
  ;; do other stuff
  )

(set! (.-onload js/window) setup)
