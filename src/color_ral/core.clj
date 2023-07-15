(ns color-ral.core
  (:import (org.openqa.selenium By
                                Capabilities
                                Dimension
                                Keys
                                NoSuchElementException
                                OutputType
                                Point
                                TakesScreenshot
                                WebDriver
                                WebElement
                                NoSuchElementException)
           (org.openqa.selenium.support.ui WebDriverWait
                                           ExpectedConditions)
           org.openqa.selenium.edge.EdgeDriver
           org.openqa.selenium.safari.SafariDriver
           org.openqa.selenium.chrome.ChromeDriver
           org.openqa.selenium.ie.InternetExplorerDriver
           (org.openqa.selenium.firefox FirefoxDriver
                                        FirefoxProfile)
           (org.openqa.selenium.interactions Actions
                                             CompositeAction))
  (:gen-class))
(defn- path [index tab] (str "/html/body/div/div/table/tbody/tr[" index "]/td[" tab "]"))
(defn- get-text [^WebDriver driver xpath] (.getText (.findElement driver (By/xpath xpath))))
(defn- trim [str]
  (->> (clojure.string/replace str #"[^\d\w]+|[_]+" " ")
       clojure.string/trim
       clojure.string/lower-case))
(defn- get-color [^WebDriver driver xpath]
  (.getCssValue (.findElement driver (By/xpath xpath)) "background-color"))
(defn- serilize [rgb]
  (->> (clojure.string/split rgb #"\D+")
       (filter (comp not empty?))
       (map read-string)
       (map #(Integer/toString % 16))
       (apply str "#FF")
       clojure.string/upper-case))
(defn -main
  [& args]
  (with-open [writer (clojure.java.io/writer "target/colors.xml" :append false)]
    (spit writer "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n"))
  (with-open [writer (clojure.java.io/writer "target/colors.xml" :append true)
              safari (SafariDriver.)]
    (.get safari (str "http://www.ralcolor.com"))
    (-> safari
        (WebDriverWait. (java.time.Duration/ofSeconds 5))
        (.until (ExpectedConditions/presenceOfElementLocated (By/xpath (path 412 1)))))
    (doseq [index (range 14 440 2)
            tab [3]]
      (.write writer "    <color name=\"")
      (.write writer (->> (path index 1)
                          (get-text safari)
                          trim
                          empty?
                          (#(if % 1 0))
                          (+ tab)
                          (path index)
                          (get-text safari)
                          trim))
      (.write writer "\">")
      (.write writer (->> (path index tab)
                          (get-color safari)
                          serilize))
      (.write writer "</color>\n"))
    (.write writer "</resources>\n")))
