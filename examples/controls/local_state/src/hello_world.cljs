;; EXPERIMENTAL.

(ns examples.local-state
  (:require [reforms.om :include-macros true :as f]
            [reforms.binding.om-state]                      ; Enable binding to local state.
            [om.core :include-macros true :as om]
            [sablono.core :refer-macros [html]]))

(def app-state (atom {}))

(defn simple-view
  [_data owner]
  (reify
    om/IRenderState
    (render-state [_ state]
      (html
        (f/with-options {:form {:horizontal (:orientation-horizontal state)}}
                        (f/panel
                          "Hello, world"
                          [:pre (:name state)]
                          [:div (rand-int 40)]
                          (f/form
                            {:on-submit #(js/alert "Submitted")}
                            (f/text "Your name" owner [:name] :placeholder "Type your name here")
                            (f/form-buttons
                              (f/button-primary "Submit" #(js/alert (:name (om/get-state owner))))
                              (f/button-default "Cancel" #(js/alert "Cancel!")))
                            (f/checkbox "Horizontal form" owner [:orientation-horizontal]))))))))

(defn main-view
  [app-state _owner]
  (reify
    om/IRender
    (render [_]
      (html
        [:div
         [:br]
         [:br]
         (om/build simple-view (:data app-state))]))))

(om/root
  main-view
  app-state
  {:target (. js/document (getElementById "app"))})

