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
(defn- get-text [driver xpath] (.getText (.findElement driver (By/xpath xpath))))
(defn- trim [str] (clojure.string/replace str #"[^\d\w]+|[_]+" ""))
(defn -main
  [& args]
  (with-open [xml (clojure.java.io/writer "target/colors.xml" :append false)]
    )
  (with-open [writer (clojure.java.io/writer "target/colors.xml" :append true)
              safari (SafariDriver.)]
    (.get safari (str "http://www.ralcolor.com"))
    (doseq [index (range 14 440 2)
            tab [3]]
      (.write writer (->> (path index 1)
           (get-text safari)
           trim
           empty?
           (#(if % 1 0))
           (+ tab)
           (path index)
           (get-text safari)
           trim)))
    ))
