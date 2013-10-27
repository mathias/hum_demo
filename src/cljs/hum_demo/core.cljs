(ns hum-demo
  (:require [hum.core :as hum]
            [dommy.utils :as utils]
            [dommy.core :as dommy])
  (:use-macros [dommy.macros :only [node sel sel1]]))


(defn setup []
  ;; do other stuff
  )

(set! (.-onload js/window) setup)
