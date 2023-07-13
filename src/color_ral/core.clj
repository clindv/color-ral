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
           org.openqa.selenium.edge.EdgeDriver
           org.openqa.selenium.safari.SafariDriver
           org.openqa.selenium.chrome.ChromeDriver
           org.openqa.selenium.ie.InternetExplorerDriver
           (org.openqa.selenium.firefox FirefoxDriver
                                        FirefoxProfile)
           (org.openqa.selenium.interactions Actions
                                             CompositeAction))
  (:gen-class))
(defn -main
  [& args]
  (with-open [safari (SafariDriver.)]
    (.get safari (str "https://space.bilibili.com/1988296011"))))
