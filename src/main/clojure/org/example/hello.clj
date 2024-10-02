(ns org.example.hello
  (:require
   [clojure.spec.alpha :as s]
   [clojure.java.data :as j])
  (:import [org.example Person Address Gamer])
  (:gen-class))

(def oscar
  (-> (Person/builder)
      (.name "Oscar")
      (.age 41)
      (.address
       (-> (Address/builder)
           (.street "Random Ave 54")
           (.zipCode "59876")
           (.build)))
      (.build)))

(def alex
  (-> (Gamer/builder)
      (.name "Alex")
      (.age 13)
      (.address
       (-> (Address/builder)
           (.street "Main St 123")
           (.zipCode "12345")
           (.build)))
      (.favoriteGame "Roblox")
      (.build)))

(s/def :org.example.hello/favorite-game (s/and map? #(string? (get % :favoriteGame))))

(s/def :org.example.hello/favoriteGame string?)

(s/def :org.example.hello/street string?)
(def zip-code-regex #"^(?!00000)\d{5}(?:[ \-](?!0000)\d{4})?$")
(s/def :org.example.hello/zipCode (s/and string? #(re-matches zip-code-regex %)))
(s/def :org.example.hello/address
  (s/keys :req-un [:org.example.hello/street :org.example.hello/zipCode]))
(s/def :org.example.hello/name string?)
(s/def :org.example.hello/age (s/and int?
                                     #(>= % 0)
                                     #(<= % 130)))

(s/def :org.example.hello/person
  (s/keys :req-un [:org.example.hello/name :org.example.hello/age :org.example.hello/address]))

(s/def :org.example.hello/gamer
  (s/merge :org.example.hello/person
           (s/keys :req-un [:org.example.hello/favoriteGame])))

(defn f [obj]
  (j/from-java obj))

(def input-map
  (f alex))
(println input-map)

(s/valid? :org.example.hello/person (f oscar))
(s/valid? :org.example.hello/gamer (f oscar))

(s/valid? :org.example.hello/gamer input-map)
(s/valid? :org.example.hello/favorite-game input-map)
(s/conform :org.example.hello/gamer input-map)
(s/explain :org.example.hello/gamer input-map)

(defmethod j/to-java [Person clojure.lang.APersistentMap] [Person props]
  {:pre [(s/valid? :org.example.hello/person props)]}
  (let [p (-> (Person/builder)
              (.name (:name props))
              (.age (:age props))
              (.address
               (-> (Address/builder)
                   (.street (-> props :address :street))
                   (.zipCode (-> props :address :zipCode))
                   (.build)))
              (.build))]
    [p props]))

(j/to-java Person input-map)
