package com.pluralsight;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    // create a static logger instance for this class
    final static Logger logger = LogManager.getLogger(App.class);
    // creating main method
    public static void main(String[] args) {
        // calls the "logmelikeyoudo" method and allows you to pass a string
        logMeLikeYouDo("☕ ");
    }

    // define a private static method that accepts a string as input
    private static void logMeLikeYouDo(String input) {
        if (logger.isDebugEnabled()) {
            logger.debug("This is debug : " + input);
        }
        // check if info logging is enabled
        if (logger.isInfoEnabled()) {
            // if so,
            logger.info("This is info : " + input);
        }
        logger.warn("This is warn : " + input);
        logger.error("This is error : " + input);
        logger.fatal("This is fatal : " + input);
    }

    }