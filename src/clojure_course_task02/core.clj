(ns clojure-course-task02.core
  (:gen-class)
  (:import java.io.File))

(defn isMatch [pat name]
  (re-matches pat name))

(defn get-files-list [dir predicate]
  (let[files (.listFiles dir)
       dirs (filter #(.isDirectory %) files)]
  (def  fl(future (doall  (map #(.getName %) (filter #(and (.isFile %) (predicate (.getName %))) files)))))
  (if (empty? dirs)
    @fl
    (flatten (concat (pmap #(get-files-list % predicate) dirs) @fl)))))


(defn find-files [file-name path]
  "TODO: Implement searching for a file using his name as a regexp."
  (get-files-list (File. path) (partial isMatch (re-pattern  file-name))))

(defn usage []
  (println "Usage: $ run.sh file_name path"))

(defn -main [file-name path]
  (if (or (nil? file-name)
          (nil? path))
    (usage)
    (do
      (println "Searching for " file-name " in " path "...")
      (dorun (map println (find-files file-name path))))))
