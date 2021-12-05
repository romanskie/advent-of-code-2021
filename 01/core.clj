(defn parse-int [str]
  (Integer/parseInt str))

(defn read-file [file]
  (-> (slurp file)
      (clojure.string/split-lines)))

(defn number-of-measurements [input]
  (let [[x & xs] input]
    (-> (reduce (fn [acc curr]
                  (let [prev (get acc :prev)
                        count (get acc :count)
                        acc (assoc acc :prev curr)]
                    (if (< prev curr)
                      (assoc acc :count (inc count))
                      acc)))
                {:prev x :count 0}
                xs)
        (get :count))))

(let [input (read-file "01/input.txt")
      measurements (map #(parse-int %) input)
      result (number-of-measurements measurements)] result)
