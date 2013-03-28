(ns clojure-course-task02.core
  (:gen-class)
  (:import java.io.File))

(defn isMatch [pat name]
  (re-matches (re-pattern pat) name))

(defn get-files-list [dir predicate]
  (let[files (.listFiles dir)
       dirs (filter #(.isDirectory %) files)]
  (def  fl(future (vec (map #(.getName %) (filter #(and (.isFile %) (predicate (.getName %))) files)))))
  (flatten (concat @fl (pmap #(get-files-list % predicate) dirs)))))


(defn find-files [file-name path]
  "TODO: Implement searching for a file using his name as a regexp."
  (get-files-list (File. path) (partial isMatch file-name)))

(defn usage []
  (println "Usage: $ run.sh file_name path"))

(defn -main [file-name path]
  (if (or (nil? file-name)
          (nil? path))
    (usage)
    (do
      (println "Searching for " file-name " in " path "...")
      (dorun (map println (find-files file-name path))))))
