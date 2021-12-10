(require '[clojure.string :as str])

(defn read-file [file]
  (-> (slurp file)
      (str/split-lines)))

(defn part-1 [input])
(part-1 (read-file "resources/input_advent_04.txt"))

"================== part 2"

(defn part-2 [input])
(part-2 (read-file "resources/input_advent_03.txt"))

