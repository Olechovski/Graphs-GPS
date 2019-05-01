package edu.iup.cosc310.graph.test;

import java.io.Console;
import java.io.IOException;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(JunitTestSuite.class);

      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
       System.out.println(result.wasSuccessful() ? "\nTesting Successful" : "\nTesting Unsuccessful");
   }
}