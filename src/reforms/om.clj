(ns reforms.om
  (:require [reforms.core :as core]))

(defmacro with-options
  [& args]
  `(core/with-options ~@args))