(require '[clojure.string :as str])

(defn parse-int [str]
  (Integer/parseInt str))

(defn read-file [file]
  (-> (slurp file)
      (str/split-lines)))

(defn number-of-measurements [input]
  (let [[x & xs] input]
    (-> (reduce (fn [acc curr]
                  (let [prev (:prev acc)
                        count (:count acc)
                        acc (assoc acc :prev curr)]
                    (if (< prev curr)
                      (assoc acc :count (inc count))
                      acc)))
                {:prev x :count 0}
                xs)
        (:count))))

(let [input (read-file "resources/input_advent_01.txt")]
  (->
   (map #(parse-int %) input)
   (number-of-measurements)))
